package it.digitalgarage.marketplace.tomcat.boot;

import javax.sql.DataSource;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.client.KeycloakClientRequestFactory;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
public class OnStartConfiguration {

	@Value("${jdbc.url:jdbc:postgresql://127.0.0.1:5432/database}")
	private String url;

	@Value("${jdbc.username:gruppo0}")
	private String username;

	@Value("${jdbc.password:@MJUNHYBGT}")
	private String password;

	@Bean
	public TomcatEmbeddedServletContainerFactory tomcatFactory() {
		return new TomcatEmbeddedServletContainerFactory() {


			@Override
			protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(Tomcat tomcat) {
				tomcat.enableNaming();
				return super.getTomcatEmbeddedServletContainer(tomcat);
			}


			@Override
            protected void postProcessContext(Context context) {
                ContextResource contextResource = context.getNamingResources().findResource("jndi/ds/digitalgarage");
                if (contextResource == null) {
                    contextResource = new ContextResource();
                    contextResource.setName("jndi/ds/digitalgarage");
                    contextResource.setType(DataSource.class.getName());
                    contextResource.setProperty("username", username);
                    contextResource.setProperty("password", password);
                    contextResource.setProperty("driverClassName", org.postgresql.Driver.class.getName());
                    contextResource.setProperty("url", url);
                    context.getNamingResources().addResource(contextResource);
                }
                super.postProcessContext(context);
            }

			/**
			 * @Override protected TomcatEmbeddedServletContainer getTomcatEmbeddedServletContainer(
			Tomcat tomcat) {
			tomcat.enableNaming();
			return super.getTomcatEmbeddedServletContainer(tomcat);
			}

			 @Override protected void postProcessContext(Context context) {
			 ContextResource resource = new ContextResource();
			 resource.setName("jdbc/myDataSource");
			 resource.setType(DataSource.class.getName());
			 resource.setProperty("driverClassName", "your.db.Driver");
			 resource.setProperty("url", "jdbc:yourDb");

			 context.getNamingResources().addResource(resource);
			 }
			 */

		};
	}
}
//
//@Configuration
//@EnableWebSecurity
//class KeycloakSecurityConfigurer extends KeycloakWebSecurityConfigurerAdapter {
//
//	@Bean
//	public GrantedAuthoritiesMapper grantedAuthoritiesMapper() {
//		SimpleAuthorityMapper mapper = new SimpleAuthorityMapper();
//		mapper.setConvertToUpperCase(true);
//		return mapper;
//	}
//
//	@Override
//	protected KeycloakAuthenticationProvider keycloakAuthenticationProvider() {
//		final KeycloakAuthenticationProvider provider = super.keycloakAuthenticationProvider();
//		provider.setGrantedAuthoritiesMapper(grantedAuthoritiesMapper());
//		return provider;
//	}
//
//	@Override
//	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
//		auth.authenticationProvider(keycloakAuthenticationProvider());
//	}
//
//	@Override
//	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
//		return new NullAuthenticatedSessionStrategy();
//	}
//
//	@Override
//	protected void configure(final HttpSecurity http) throws Exception {
//		super.configure(http);
//		http
//				.authorizeRequests()
//				.antMatchers("/concessione/*").hasAnyRole()
//				.anyRequest().permitAll();
//	}
//
//	@Bean
//	KeycloakConfigResolver keycloakConfigResolver() {
//		return new KeycloakSpringBootConfigResolver();
//	}
//
//	@Bean
//	public FilterRegistrationBean keycloakAuthenticationProcessingFilterRegistrationBean(
//			final KeycloakAuthenticationProcessingFilter filter) {
//		final FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
//		registrationBean.setEnabled(false);
//		return registrationBean;
//	}
//
//	@Bean
//	public FilterRegistrationBean keycloakPreAuthActionsFilterRegistrationBean(
//			final KeycloakPreAuthActionsFilter filter) {
//		final FilterRegistrationBean registrationBean = new FilterRegistrationBean(filter);
//		registrationBean.setEnabled(false);
//		return registrationBean;
//	}
//}

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackageClasses = KeycloakSecurityComponents.class)
class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        SimpleAuthorityMapper grantedAuthorityMapper = new SimpleAuthorityMapper();
        grantedAuthorityMapper.setPrefix("ROLE_");
        grantedAuthorityMapper.setConvertToUpperCase(true);
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(grantedAuthorityMapper);
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }


    @Autowired
    public KeycloakClientRequestFactory keycloakClientRequestFactory;

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public KeycloakRestTemplate keycloakRestTemplate() {
        return new KeycloakRestTemplate(keycloakClientRequestFactory);
    }

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Bean
    public KeycloakConfigResolver KeycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.csrf().disable()
                .authorizeRequests()
////                .antMatchers("/concessione**").hasRole("USER")
                .anyRequest().permitAll();

    }

}

@Configuration
@EnableSwagger2
class SwaggerConfig extends WebMvcConfigurationSupport {
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select().apis(RequestHandlerSelectors.basePackage("it"))
				.paths(PathSelectors.any())
				.build().pathMapping("/").apiInfo(apiInfo()).useDefaultResponseMessages(false);

	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {

		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/").setCachePeriod(3600).resourceChain(true).addResolver(new PathResourceResolver());
		registry.addResourceHandler("/swagger-ui.html")
				.addResourceLocations(getStaticLocations()).setCachePeriod(3600).resourceChain(true).addResolver(new PathResourceResolver());
		registry.addResourceHandler("/*")
				.addResourceLocations(getStaticLocations()).setCachePeriod(3600).resourceChain(true).addResolver(new PathResourceResolver());
	}

	private String[] getStaticLocations() {

		String[] result = new String[5];
		result[0] = "/";
		result[1] = "classpath:/META-INF/resources/";
		result[2] = "classpath:/resources/";
		result[3] = "classpath:/static/";
		result[4] = "/resources/";

		return result;
	}
	@Bean
	public ApiInfo apiInfo() {
		final ApiInfoBuilder builder = new ApiInfoBuilder();
		builder.title("Asset2i Api").version("1.0").license("(C) Engineering Ingegneria Informatica S.p.A. ")
				.description("");

		return builder.build();
	}
}

@Configuration
@EnableWebMvc
class ApplicationConfigurerAdapter extends WebMvcConfigurerAdapter{

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Bean
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("classpath:/META-INF/resources/");
		resolver.setSuffix(".html");
		return resolver;
	}

}