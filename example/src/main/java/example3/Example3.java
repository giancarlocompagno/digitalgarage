package example3;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import example.MyController;

public class Example3 {
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config3.class);
		
		MyController controller = context.getBean(MyController.class);
		controller.execute();

		
		context.close();
	}

}
