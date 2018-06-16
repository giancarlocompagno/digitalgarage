package it.digitalgarage.marketplace.commons.be.jpa;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

public class Relation {
	
	Join join;
	Root root;
	
	Map<String,Relation> relation = new HashMap<>();
	
	public Relation(Join join) {
		this.join = join;
	}
	
	public Relation(Root root) {
		this.root = root;
	}
	
	
	public <Y,T> Path<Y> resolveJoin(String path){
		int index = path.indexOf('.'); 
		if(index>0){
			String base = path.substring(0, index);
			Relation r = relation.get(base);
			if(r==null){
				Join j = null;
				if(root!=null){
					j = root.join(base);
				}else{
					j = join.join(base);
				}
				r = new Relation(j);
				relation.put(base, r);
			}
			return r.resolveJoin(path.substring(index+1));
		}else{
			if(root!=null){
				return root.get(path);
			}else{
				return join.get(path);
			}
		}
	}
	
	

}
