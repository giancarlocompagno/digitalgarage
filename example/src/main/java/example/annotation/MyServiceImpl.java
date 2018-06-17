package example.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import example.MyDao;
import example.MyService;
import it.digitalgarage.marketplace.commons.logging.Logger;
import it.digitalgarage.marketplace.commons.logging.LoggerFactory;

@Service
public class MyServiceImpl implements MyService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MyServiceImpl.class);
	
	@Autowired
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
