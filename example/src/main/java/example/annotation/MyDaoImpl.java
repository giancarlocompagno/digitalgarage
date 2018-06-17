package example.annotation;

import org.springframework.stereotype.Repository;

import example.MyDao;
import it.digitalgarage.marketplace.commons.logging.Logger;
import it.digitalgarage.marketplace.commons.logging.LoggerFactory;

@Repository
public class MyDaoImpl implements MyDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MyDaoImpl.class);
	
	@Override
	public void execute() {
		LOGGER.info("ESEGUO IL DAO");
	}

}
