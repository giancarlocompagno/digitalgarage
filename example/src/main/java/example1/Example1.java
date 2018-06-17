package example1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import example.MyController;

public class Example1 {
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("example1-spring-config.xml");
		MyController controller = applicationContext.getBean(MyController.class);
		controller.execute();
				
		((ClassPathXmlApplicationContext)applicationContext).close();
	}

}
