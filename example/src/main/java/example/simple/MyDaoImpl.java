package example.simple;

import example.MyDao;
import it.digitalgarage.marketplace.commons.logging.Logger;
import it.digitalgarage.marketplace.commons.logging.LoggerFactory;

public class MyDaoImpl implements MyDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MyDaoImpl.class);
	
	@Override
	public void execute() {
		LOGGER.info("ESEGUO IL DAO");
	}

}
