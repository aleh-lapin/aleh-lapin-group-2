package by.pochem.web.listeners;

import javax.servlet.http.HttpSessionEvent;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import by.pochem.server.services.web.FilesService;

@Component
public class HttpSessionListener implements javax.servlet.http.HttpSessionListener, ApplicationContextAware {

	@Autowired
	private FilesService filesService;
	
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (applicationContext instanceof WebApplicationContext) {
            ((WebApplicationContext) applicationContext).getServletContext().addListener(this);
        } else {
            throw new RuntimeException("Must be inside a web application context");
        }
    }

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		//TODO
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		//TODO
		se.getSession().invalidate();
	}           
}
