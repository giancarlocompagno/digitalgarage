package it.digitalgarage.marketplace;


import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import it.digitalgarage.marketplace.commons.fe.filter.LogbackSubystemIdContextListener;

@SpringBootApplication
@EnableAutoConfiguration(exclude={JpaRepositoriesAutoConfiguration.class, HibernateJpaAutoConfiguration.class, DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class,SecurityAutoConfiguration.class})
@ImportResource("classpath:boot-applicationContext.xml")
@PropertySource("classpath:boot.properties")
@EnableCaching
public class Application extends SpringBootServletInitializer {
	
	@Override
	protected WebApplicationContext createRootApplicationContext(ServletContext servletContext) {
		servletContext.addListener(LogbackSubystemIdContextListener.class);
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.setServletContext(servletContext);
		ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
		servlet.setLoadOnStartup(1);
		servlet.addMapping("/");
		return super.createRootApplicationContext(servletContext);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class).bannerMode(Mode.CONSOLE);
	}
	
}