package it.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import it.example.model.Utente;
import it.example.repository.UtenteRepository;

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






/**

**/