package example.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import example.MyController;
import example.MyService;
import it.digitalgarage.marketplace.commons.logging.Logger;
import it.digitalgarage.marketplace.commons.logging.LoggerFactory;
@Controller
public class MyControllerImpl implements MyController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MyControllerImpl.class);
	
	@Autowired
	private MyService service;
	
	@Override
	public void execute() {
		LOGGER.info("SONO NEL CONTROLLER");
		service.execute();
	}

}
