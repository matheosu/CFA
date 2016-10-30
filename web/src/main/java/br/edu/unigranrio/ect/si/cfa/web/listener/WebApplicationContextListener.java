package br.edu.unigranrio.ect.si.cfa.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Locale;

public class WebApplicationContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        String[] locale = servletContext.getInitParameter("locale").split("_");
        Locale.setDefault(new Locale(locale[0], locale[1]));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
