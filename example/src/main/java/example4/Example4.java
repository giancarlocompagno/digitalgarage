package example4;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import example.MyController;

public class Example4 {
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config4.class);
		
		MyController controller = context.getBean(MyController.class);
		controller.execute();

		
		context.close();
	}

}
