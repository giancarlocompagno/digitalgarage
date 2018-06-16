import java.lang.reflect.Proxy;

import org.springframework.util.StringValueResolver;

import it.digitalgarage.marketplace.commons.restinvoker.factory.SpringRestService;
import it.digitalgarage.marketplace.commons.restinvoker.spring.SpringRestInvokerProxyFactoryBean;

public class Main {
	
	public static void main(String[] args) throws Exception {
		StringValueResolver resolver = new StringValueResolver() {
			
			@Override
			public String resolveStringValue(String strVal) {
				return strVal;
			}
		};
		SpringRestInvokerProxyFactoryBean factoryBean = new SpringRestInvokerProxyFactoryBean();
		factoryBean.setBaseUrl("");
		factoryBean.setRemoteServiceInterfaceClass(ProfiloBE.class);
		factoryBean.setEmbeddedValueResolver(resolver);
		factoryBean.initialize();
		ProfiloBE profiloBE  = (ProfiloBE)factoryBean.getObject();
		System.out.println(((Proxy)profiloBE).getInvocationHandler(profiloBE));
	}
	
	@SpringRestService(baseUri="/")
	public static interface ProfiloBE{
		
		
		
	}

}
