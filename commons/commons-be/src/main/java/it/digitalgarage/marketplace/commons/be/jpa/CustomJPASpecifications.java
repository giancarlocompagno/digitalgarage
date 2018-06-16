package it.digitalgarage.marketplace.commons.be.jpa;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.springframework.data.jpa.domain.Specification;


public class CustomJPASpecifications<T> implements Specification<T> {

	private SearchCriteria criteria;
	private RelationTree relationTree;

	public CustomJPASpecifications(SearchCriteria criteria,RelationTree relationTree) {
		super();
		this.criteria = criteria;
		this.relationTree = relationTree;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,CriteriaBuilder builder) {
		relationTree.init(root);
		query.distinct(true);
		switch (criteria.getOperation()) {
		case EQUALS: {
			return builder.equal(relationTree.resolveJoin(criteria.getKey()), criteria.getValue());	
		}
		case NOT_EQUALS: {
			return builder.notEqual(relationTree.resolveJoin(criteria.getKey()), criteria.getValue());
		}
		case EQUALS_IGNORE_CASE: {
			return builder.equal(builder.upper(relationTree.resolveJoin(criteria.getKey())), criteria.getValue().toString().toUpperCase());	
		}
		case GTE: {
			return builder.greaterThanOrEqualTo(relationTree.resolveJoin(criteria.getKey()), (Comparable) criteria.getValue());			
		}	
		case LTE: {
			return builder.lessThanOrEqualTo(relationTree.resolveJoin(criteria.getKey()), (Comparable) criteria.getValue());
		}
		case GT: {
				return builder.greaterThan(relationTree.resolveJoin(criteria.getKey()), (Comparable) criteria.getValue());			
		}	
		case LT: {
				return builder.lessThan(relationTree.resolveJoin(criteria.getKey()), (Comparable) criteria.getValue());		
		}	
		case LIKE: {			
			return builder.like(builder.lower(root.<String> get(criteria.getKey())), "%"+criteria.getValue().toString().toLowerCase()+"%");
		}	
		case IN:	{		
			Expression<Set> genExpression =   root.<Set>get(criteria.getKey());			
			return builder.in(genExpression).value((Set) criteria.getValue());
		}
		case EXISTS:
		case NOT_EXISTS:{
			Exists exists = (Exists) criteria.getValue();
			Subquery subquery = query.subquery(exists.getClassExists());
			Root subRoot = subquery.from(exists.getClassExists());
			subquery.select(builder.literal(1));

			List<Predicate> predicates = exists.getJoinCondition().entrySet().stream().map((e)->builder.equal(get(root,e.getKey()), get(subRoot,e.getValue()))).collect(Collectors.toList());
			List<Predicate> predicates2 = exists.getWhereCondition().entrySet().stream().map((e)->builder.equal(subRoot.get(e.getKey()), e.getValue())).collect(Collectors.toList());
			predicates.addAll(predicates2);
			subquery.where(predicates.toArray(new Predicate[]{}));
			return criteria.getOperation() == SearchCriteria.OPERATION.EXISTS ? builder.exists(subquery) : builder.not(builder.exists(subquery));
			}
		case NOT_IN:	{				
			if (criteria.getValue() instanceof List) {				
				if (criteria.getValue() instanceof List<?>) {
				    return builder
				    		.not(relationTree.resolveJoin(criteria.getKey())
				    		.in(((List<?>) criteria.getValue()).toArray()));
				}			
			}
		}
		case IS_NULL:	{		
			Expression<Set> genExpression =   root.<Set>get(criteria.getKey());			
			return builder.isNull(genExpression);
		}
		case IS_NOT_NULL:	{		
			Expression<Set> genExpression =   root.<Set>get(criteria.getKey());			
			return builder.isNotNull(genExpression);
		}
		default:
			return builder.equal(relationTree.resolveJoin(criteria.getKey()), criteria.getValue());							        			        
		}
		
	}

	public Path<?> get(Root<?> root,String key){
		Path<?> path = root;
		for(String p : key.split("\\.")) {
			path = path.get(p);
		}
		return path;
	}



	public SearchCriteria getCriteria() {
		return criteria;
	}



	public void setCriteria(SearchCriteria criteria) {
		this.criteria = criteria;
	}

//	private <Y,T>Path<Y> navigatePath(Root<T> root,String name){
//		String[] arrS = name.split("\\.");
//		if (arrS.length >1){
//			Join<T, Object> join= null;
//			for (int i=0; i <arrS.length-1;i++){
//				if (i==0){
//					join = root.join(arrS[i]);
//				}else{
//					join = join.join(arrS[i]);        			
//				}
//			}
//			return join.get(arrS[arrS.length-1]);
//		}else{
//			return root.get(name);
//		}
//	}
//	
//	
//	
//	
//	private Path<Object> getFieldPath(String key, Root<T> root) {
//	    Path<Object> fieldPath = null;
//	    if (key.contains(".")) {
//	        String[] fields = key.split("\\.");
//	        for (String field : fields) {
//	            if (fieldPath == null) {
//	                fieldPath = root.get(field);
//	            } else {
//	                fieldPath = fieldPath.get(field);
//	            }
//	        }
//	    } else {
//	        fieldPath = root.get(key);
//	    }
//	    return fieldPath;
//	}

}

