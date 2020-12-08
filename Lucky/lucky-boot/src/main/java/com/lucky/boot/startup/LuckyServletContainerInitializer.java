package com.lucky.boot.startup;

import com.lucky.boot.conf.ServerConfig;
import com.lucky.boot.web.ListenerMapping;
import com.lucky.framework.ApplicationContext;
import com.lucky.framework.uitls.base.Assert;
import com.lucky.boot.web.FilterMapping;
import com.lucky.boot.web.ServletMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.EventListener;
import java.util.Set;

public class LuckyServletContainerInitializer implements ServletContainerInitializer {
	
	public final ServerConfig serverCfg=ServerConfig.getServerConfig();

	private static final Logger log= LogManager.getLogger("c.l.j.s.LuckyServletContainerInitializer");
	
	public LuckyServletContainerInitializer(ApplicationContext applicationContext) {
		serverCfg.init(applicationContext);
	}


	@Override
	public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
		ServletRegistration.Dynamic servlet;
		FilterRegistration.Dynamic filter;
		for(ServletMapping sm:serverCfg.getServletList()) {
			servlet=ctx.addServlet(sm.getName(), sm.getServlet());
			servlet.setLoadOnStartup(sm.getLoadOnStartup());
			servlet.addMapping(sm.getUrlPatterns());
			servlet.setAsyncSupported(sm.isAsyncSupported());
			servlet.setInitParameters(sm.getInitParams());
			log.info("Add Servlet `name="+sm.getName()+" mapping="+Arrays.toString(sm.getUrlPatterns())+" class="+sm.getServlet().getClass().getName()+"`");
		}
		
		for(FilterMapping fm:serverCfg.getFilterList()) {
			DispatcherType dispatcherTypes = fm.getDispatcherTypes()[0];
			filter=ctx.addFilter(fm.getName(), fm.getFilter());
			filter.setAsyncSupported(fm.isAsyncSupported());
			filter.setInitParameters(fm.getInitParams());
			filter.addMappingForUrlPatterns(EnumSet.of(dispatcherTypes), true,fm.getUrlPatterns());
			filter.addMappingForServletNames(EnumSet.of(dispatcherTypes), true,fm.getServletNames());
			log.info("Add Filter `name="+fm.getName()+" mapping="+Arrays.toString(fm.getUrlPatterns())+" class="+ fm.getFilter().getClass().getName()+"`");
		}
		
		for(ListenerMapping lm:serverCfg.getListenerList()) {
			ctx.addListener(lm.getListener());
			log.info("Add Listener `class="+lm.getListener().getClass().getName()+"`");
		}
		log.info("Tomcat SessionTimeOut \"" +serverCfg.getSessionTimeout()+"min\"");
		if(!Assert.isNull(serverCfg.getClosePort())){
			log.info("Tomcat Shutdown-Port \"" +serverCfg.getClosePort()+"\"");
		}
		if(!Assert.isNull(serverCfg.getShutdown())){
			log.info("Tomcat Shutdown-Command \"" +serverCfg.getShutdown()+"\"");
		}
		log.info("Tomcat BaseDir \"" +serverCfg.getBaseDir()+"\"");
		if(!Assert.isNull(serverCfg.getDocBase())){
			log.info("Tomcat DocBase \"" +serverCfg.getDocBase()+"\"");
		}
		log.info("Tomcat ContextPath : \"" +serverCfg.getContextPath()+"\"");
	}

}