package it.digitalgarage.marketplace.commons.be.jpa;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

public class RelationTree {
	
	private Relation relation;
	
	public void init(Root root){
		if(relation==null || relation.root!=root){
			relation = new Relation(root);
		}
	}
	
	public <Y> Path<Y>  resolveJoin(String path) {
		return relation.resolveJoin(path);
	};
	
	
	

}
