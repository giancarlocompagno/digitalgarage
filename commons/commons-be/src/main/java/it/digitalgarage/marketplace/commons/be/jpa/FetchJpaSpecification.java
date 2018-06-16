package it.digitalgarage.marketplace.commons.be.jpa;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class FetchJpaSpecification<T> implements Specification<T>{
	
	private String attributeName;
	
	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		root.fetch(attributeName);
		return null;
	}
	

}
