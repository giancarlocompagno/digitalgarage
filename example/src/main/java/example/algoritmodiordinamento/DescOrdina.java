package example.algoritmodiordinamento;

import java.util.List;

import org.springframework.stereotype.Component;
@Component
public class DescOrdina<T> implements Ordina<T>{

	@Override
	public List<T> elabora(List<T> l) {
		return null;
	}
}
