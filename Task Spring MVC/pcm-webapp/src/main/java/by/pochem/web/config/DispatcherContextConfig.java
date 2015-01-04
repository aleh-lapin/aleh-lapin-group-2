package by.pochem.web.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import by.pochem.server.cache.CompaniesCache;
import by.pochem.web.auth.authorize.HandlerSecurityInterceptor;
import by.pochem.web.interceptrors.ContextPathInterceptor;
import by.pochem.web.subdomain.SubdomainRequestMappingHandlerMapping;

@Configuration
@PropertySource({ "classpath:files.upload.pcm.properties" })
@ComponentScan({"by.pochem.web.controllers", "by.pochem.web.binders", "by.pochem.web.handlers", "by.pochem.web.listeners"})
public class DispatcherContextConfig extends WebMvcConfigurationSupport {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private HandlerSecurityInterceptor handlerSecurityInterceptor;
		
	@Bean
	@Autowired
	public RequestMappingHandlerMapping requestMappingHandlerMapping(CompaniesCache companiesCache) {
		SubdomainRequestMappingHandlerMapping handlerMapping = 
				new SubdomainRequestMappingHandlerMapping();
		handlerMapping.setOrder(0);
		handlerMapping.setInterceptors(getInterceptors());
		handlerMapping.setContentNegotiationManager(mvcContentNegotiationManager());
		return handlerMapping;
	}

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/files/**").addResourceLocations("file:" + env.getProperty("file.repository.path"));
    }
	
	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(handlerSecurityInterceptor);
		registry.addInterceptor(new ContextPathInterceptor());
	}

	@Bean(name = "viewResolver")
	public InternalResourceViewResolver getViewResolver() {
	    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
	    viewResolver.setExposeContextBeansAsAttributes(true);
	    viewResolver.setPrefix("/WEB-INF/views/");
	    viewResolver.setSuffix(".jsp");
	    viewResolver.setViewClass(JstlView.class);
	    return viewResolver;
	}
	
	@Bean
	public MultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(5 * 1024 * 1024);
		return multipartResolver;
	}
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(converter());
        addDefaultHttpMessageConverters(converters);
    }

    @Bean
    public MappingJackson2HttpMessageConverter converter() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        return converter;
    }
	
}
