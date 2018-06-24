package it.example;

import it.example.service.SuccessLoginHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import it.example.model.Utente;
import it.example.repository.UtenteRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SpringBootApplication
public class SimpleSpringBootApplication {
	
	private static final Logger log = LoggerFactory.getLogger(SimpleSpringBootApplication.class);



	public static void main(String[] args) {
		SpringApplication.run(SimpleSpringBootApplication.class, args);
	}
	
//	@Bean(name="simpleCommandLine")
//	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//		return args -> {
//
//			System.out.println("Let's inspect the beans provided by Spring Boot:");
//
//			String[] beanNames = ctx.getBeanDefinitionNames();
//			Arrays.sort(beanNames);
//			for (String beanName : beanNames) {
//				System.out.println(beanName);
//			}
//
//		};
//	}
	
	
	@Bean(name="repositoryCommandLine")
	public CommandLineRunner commandLineRunner(UtenteRepository repository) {
		return (args) -> {
			// save a couple of customers
			repository.save(new Utente("jack","Jack", "Bauer"));
			repository.save(new Utente("chloe","Chloe", "O'Brian"));
			repository.save(new Utente("kim","Kim", "Bauer"));
			repository.save(new Utente("david","David", "Palmer"));
			repository.save(new Utente("michelle","Michelle", "Dessler"));

			// fetch all customers
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (Utente customer : repository.findAll()) {
				log.info(customer.toString());
			}
			log.info("");

			// fetch an individual customer by ID
			repository.findById("jack")
				.ifPresent(customer -> {
					log.info("Utente found with findById(\"jack\"):");
					log.info("--------------------------------");
					log.info(customer.toString());
					log.info("");
				});

			// fetch customers by last name
			log.info("Utente found with findByLastName('Bauer'):");
			log.info("--------------------------------------------");
			repository.findByLastName("Bauer").forEach(bauer -> {
				log.info(bauer.toString());
			});
			// for (Customer bauer : repository.findByLastName("Bauer")) {
			// 	log.info(bauer.toString());
			// }
			log.info("");
		};
	}
}


@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	SuccessLoginHandler successLoginHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/oauth_login", "/css/**","/js/**","/img/**")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.oauth2Login().loginPage("/oauth_login")
				.successHandler(successLoginHandler);

	}


}

@Controller
class LoginController{

	@GetMapping("/oauth_login")
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response){
		return new ModelAndView("index");
	}
}

@Configuration
class WebConfig extends WebMvcConfigurationSupport {

	private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
			"classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/public/"};

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {

		registry.addResourceHandler("/**").addResourceLocations(
				CLASSPATH_RESOURCE_LOCATIONS);
		registry
				.addResourceHandler("/resources/**")
				.addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS)
				.setCachePeriod(3600)
				.resourceChain(true)
				.addResolver(new PathResourceResolver());
	}

	@Bean
	public InternalResourceViewResolver setupViewResolver()  {
		InternalResourceViewResolver resolver =  new InternalResourceViewResolver();
		resolver.setPrefix ("");
		resolver.setSuffix (".html");
		return resolver;
	}

}


/**

**/