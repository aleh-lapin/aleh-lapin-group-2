package by.pochem.web.subdomain;

import java.lang.reflect.Method;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import by.pochem.server.cache.CompaniesCache;

public class SubdomainRequestMappingHandlerMapping extends
        RequestMappingHandlerMapping {

	private CompaniesCache cache;
	
	public void setCache(CompaniesCache cache) {
		this.cache = cache;
	}

	@Override
    protected RequestCondition<?> getCustomTypeCondition(Class<?> handlerType) {
        SubdomainMapping typeAnnotation = AnnotationUtils.findAnnotation(
                handlerType, SubdomainMapping.class);
        return createCondition(typeAnnotation);
    }

    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        SubdomainMapping methodAnnotation = AnnotationUtils.findAnnotation(
                method, SubdomainMapping.class);
        return createCondition(methodAnnotation);
    }

    private RequestCondition<?> createCondition(SubdomainMapping accessMapping) {
        return (accessMapping != null) ? new SubdomainRequestCondition(
                cache, accessMapping.tld(), accessMapping.value()) : null;
    }

}
