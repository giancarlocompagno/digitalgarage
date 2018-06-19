package example3;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import example.MyController;
import example.MyService;

public class Example3 {
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config3.class);
		
		MyController controller = context.getBean(MyController.class);
		MyService service = context.getBean(MyService.class);
		controller.execute();

		
		context.close();
	}

}
