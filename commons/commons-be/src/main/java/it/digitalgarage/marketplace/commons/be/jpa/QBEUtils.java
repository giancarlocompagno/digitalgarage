package it.digitalgarage.marketplace.commons.be.jpa;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;

import it.digitalgarage.marketplace.commons.be.jpa.SearchCriteria.OPERATION;
import it.digitalgarage.marketplace.commons.model.ASearchDTO;
import it.digitalgarage.marketplace.commons.model.Page;
import it.digitalgarage.marketplace.commons.model.Sort;


public class QBEUtils {
	
	
	public static <S> Pageable makePageable(ASearchDTO searchDTO){
		return makePageable(searchDTO,(a)->a);
	}
	
	public static <S> Pageable makePageable(ASearchDTO searchDTO,Function<List<String>,List<String>> a){
		if(searchDTO!=null && searchDTO.getPageable()!=null && searchDTO.getPageable().getPageSize()!=null && searchDTO.getPageable().getPageNumber()!=null){
			int size = searchDTO.getPageable().getPageSize();
			int page = searchDTO.getPageable().getPageNumber();
			Sort sort = searchDTO.getPageable().getSort();
			if(sort!=null && sort.getField()!=null && !sort.getField().isEmpty()){
				List<String> values = a.apply(sort.getField());
				if(!values.isEmpty()){
					return PageRequest.of(page, size, sort.isAsc() ? Direction.ASC: Direction.DESC, values.toArray(new String[]{}));
				}else{
					return PageRequest.of(page, size);
				}
			}else{
				return PageRequest.of(page, size);
			}
		}
		return Pageable.unpaged();
	}
	
	
	public static <V,T> Page<T> makePage(org.springframework.data.domain.Page<V> page,Function<V, T> f){
		org.springframework.data.domain.Page<T> p2 = page.map(f);	
		Page<T> pWeb= new Page<>();

		pWeb.setTotalElement(p2.getTotalElements());
		pWeb.setContent(p2.getContent());
		pWeb.setNumber(p2.getNumber());
		pWeb.setNumberOfElements(p2.getNumberOfElements());
		pWeb.setPageable(makePageable(p2.getPageable()));
		pWeb.setSize(p2.getSize());
		pWeb.setTotalPages(p2.getTotalPages());
		
		return pWeb;
	}
	
	
	
	private static it.digitalgarage.marketplace.commons.model.Pageable makePageable(Pageable pageable) {
		 it.digitalgarage.marketplace.commons.model.Pageable  p = new  it.digitalgarage.marketplace.commons.model.Pageable ();
		 if(pageable!=null && pageable.isPaged()){
			 p.setPageNumber(pageable.getPageNumber());
			 p.setPageSize(pageable.getPageSize());
			 p.setOffset(pageable.getOffset());
			 p.setSort(makeSort(pageable.getSort()));
		}
		return p;
	}


	private static Sort makeSort(org.springframework.data.domain.Sort sort) {
		Sort s = new Sort();
		s.setField(sort.stream().map((o)->o.getProperty()).collect(Collectors.toList()));
		if(!s.getField().isEmpty()){
			s.setAsc(sort.getOrderFor(s.getField().get(0)).isAscending());
		}
		return s;
	}


	public static <SEARCH extends ASearchDTO,T> MakeExample<SEARCH,T> makeExample(SEARCH a,Class<T> t){
		return new MakeExample<SEARCH,T>(a,t);  
	}
	
	public static <T> MakeSpecification<T> makeSpecification(Class<T> t){
		return new MakeSpecification<T>(t);  
	}
	
	
	public static class MakeExample<SEARCH extends ASearchDTO,T>{
		
		
		private T t;
		private SEARCH aserch;
		
		private MakeExample(SEARCH a,Class<T> tClazz){
			this.aserch = a;
			try{
				this.t= tClazz.newInstance();
			}catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		
		public <V>  MakeExample<SEARCH,T> add(Function<SEARCH,V> df, BiConsumer<T,V> f){
			f.accept(t, df.apply(aserch));
			return this;
		}
		
		public Example<T> get(){
			return Example.of(t);
		}
		
	}
	
	
	public static class MakeSpecification<T>{
		
		public Specification<T> specification;
		private Class<T> tClazz;
		private RelationTree relationTree = new RelationTree();
		
		
		private MakeSpecification(Class<T> tClazz) {
			this.tClazz = tClazz;
		}
		
		
		public void add(Specification<T> s) {
			if(this.specification == null){
				this.specification = s;//Specification.where(s);
			}else{
				this.specification = this.specification.and(s);
			}
		}
		
		public  MakeSpecification<T> add(SearchCriteria criteria){
			add(new CustomJPASpecifications<>(criteria,relationTree));
			return this;
		}
		
		public MakeSpecification<T> equal(String key,Object value){
			add(key,OPERATION.EQUALS,value);
			return this;
		}

		public MakeSpecification<T> notequal(String key,Object value){
			add(key, OPERATION.NOT_EQUALS,value);
			return this;
		}
		
		public MakeSpecification<T> like(String key,Object value){
			add(key,OPERATION.LIKE,value);
			return this;
		}
		
		public MakeSpecification<T> equalIgnoreCase(String key,Object value){
			add(key,OPERATION.EQUALS_IGNORE_CASE,value);
			return this;
		}
		
		
		public MakeSpecification<T> between(String key,Object from,Object to){
			if(from!=null){
				gte(key, from);
			}
			if(to!=null){
				lte(key, to);
			}
			return this;
		}

		public MakeSpecification<T> in(String key,Set<?> values){
			if(values!=null){
				add(key,OPERATION.IN,values);
			}
			return this;
		}

		public MakeSpecification<T> exists(Exists exists){
			if(exists!=null){
				add(new SearchCriteria(null,OPERATION.EXISTS,exists));
			}
			return this;
		}

		public MakeSpecification<T> notExists(Exists exists){
			if(exists!=null){
				add(new SearchCriteria(null,OPERATION.NOT_EXISTS,exists));
			}
			return this;
		}

		
		public MakeSpecification<T>  gte(String key,Object from){
			add(key,OPERATION.GTE,from);
			return this;
		}
		
		public MakeSpecification<T>  lte(String key,Object to){
			add(key,OPERATION.LTE,to);
			return this;
		}
		
		public  MakeSpecification<T> add(String key, OPERATION operation, Object value){
			if(value!=null){
				add(new SearchCriteria(key, operation, value));
			}
			return this;
		}
		
		
		public  MakeSpecification<T> isNull(String key){
			add(key,OPERATION.IS_NULL);
			return this;
		}

		public  MakeSpecification<T> isNotNull(String key){
			add(key,OPERATION.IS_NOT_NULL);
			return this;
		}
		public  MakeSpecification<T> add(String key, OPERATION operation){
			add(new SearchCriteria(key, operation));
			return this;
		}
		
		public Specification<T> get(){
			return specification;
		}
		
	}

}
