package it.digitalgarage.marketplace.swagger.boot;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {
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