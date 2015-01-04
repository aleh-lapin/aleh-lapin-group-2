package by.pochem.web.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import by.pochem.web.config.WebAppContextConfig;
import by.pochem.server.spring.MasterContextLoaderListener;

public class SpringWebAppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext webAppContext = new AnnotationConfigWebApplicationContext();
		webAppContext.register(WebAppContextConfig.class);

		servletContext.addListener(new MasterContextLoaderListener(webAppContext, "by.pochem.server.config.ServerAppContextConfig"));

		AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
		dispatcherContext.register(DispatcherContextConfig.class);

		DispatcherServlet dispatcher = new DispatcherServlet(dispatcherContext);
		dispatcher.setThrowExceptionIfNoHandlerFound(true);

		ServletRegistration.Dynamic appServlet = servletContext.addServlet("appServlet", dispatcher);
		appServlet.setLoadOnStartup(1);
		appServlet.addMapping("/");

		EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);
		
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("utf-8");
        characterEncodingFilter.setForceEncoding(true);

        FilterRegistration.Dynamic characterEncoding = servletContext.addFilter("characterEncoding", characterEncodingFilter);
        characterEncoding.addMappingForUrlPatterns(dispatcherTypes, true, "/*");
		
	}

}
