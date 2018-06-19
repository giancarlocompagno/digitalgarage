package example.annotation;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import example.MyDao;
import example.MyService;
import example.algoritmodiordinamento.Ordina;
import it.digitalgarage.marketplace.commons.logging.Logger;
import it.digitalgarage.marketplace.commons.logging.LoggerFactory;

@Service
public class MyServiceImpl implements MyService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MyServiceImpl.class);
	
	@Autowired
	private MyDao dao;
	
	@Autowired
	BeanFactory beanFactory;

	@Override
	public void execute() {
		LOGGER.info("SONO NEL SERVICE");
		dao.execute();
		
		Ordina ordina = (Ordina)beanFactory.getBean("descOrdina");
		System.out.println(ordina);
	}

}
