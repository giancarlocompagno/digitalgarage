package example.simple;

import example.MyController;
import example.MyService;
import it.digitalgarage.marketplace.commons.logging.Logger;
import it.digitalgarage.marketplace.commons.logging.LoggerFactory;

public class MyControllerImpl implements MyController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MyControllerImpl.class);
	
	private MyService service;
	
	public void setService(MyService service) {
		this.service = service;
	}

	@Override
	public void execute() {
		LOGGER.info("SONO NEL CONTROLLER");
		service.execute();
	}

}
