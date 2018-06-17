package example.simple;

import example.MyDao;
import example.MyService;
import it.digitalgarage.marketplace.commons.logging.Logger;
import it.digitalgarage.marketplace.commons.logging.LoggerFactory;

public class MyServiceImpl implements MyService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MyServiceImpl.class);
	
	private MyDao dao;

	public void setDao(MyDao dao) {
		this.dao = dao;
	}

	@Override
	public void execute() {
		LOGGER.info("SONO NEL SERVICE");
		dao.execute();
	}

}
