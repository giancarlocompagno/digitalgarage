package example3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import example.MyController;
import example.MyDao;
import example.MyService;
import example.annotation.*;
@Configuration
public class Config3 {
	
	@Bean
	public MyController createMyController(){
		return new MyControllerImpl();
	}
	
	@Bean
	public MyService createMyService(){
		return new MyServiceImpl();
	}
	
	@Bean
	public MyDao createMyDao(){
		return new MyDaoImpl();
	}
	

}
