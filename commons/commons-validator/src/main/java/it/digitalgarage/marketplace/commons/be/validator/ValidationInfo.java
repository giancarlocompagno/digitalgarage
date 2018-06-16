package it.digitalgarage.marketplace.commons.be.validator;

import java.util.List;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ValidationInfo<I>{
	
		
		/** The Constant EMAIL_PATTERN. */
		public static final String EMAIL_PATTERN = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
		
		/** The Constant NOME_PATTERN. */
		public static final String NOME_PATTERN = "^[_A-z ]{2,}";
		
		/** The Constant STRING_PATTERN. */
		public static final String COGNOME_PATTERN = "^[_a-zA-Z' \u00C0-\u02AF]{2,}";
		
		/** The Constant CF_PATTERN. */
		public static final String CF_PATTERN = "^[a-zA-Z]{6}[0-9]{2}[a-zA-Z][0-9]{2}[a-zA-Z][0-9]{3}[a-zA-Z]$";
		
		/** The Constant PIVA_PATTERN. */
		public static final String PIVA_PATTERN ="[0-9]{11}$";
		
		/** The Constant PASSWORD_PATTERN. */
		public static final String PASSWORD_PATTERN ="(?=^.{8,12}$)((?=.*\\d)(?=.*\\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$";

	
		private Errors errors;
		
		public ValidationInfo(I target){
			errors = new BindException(target, target.getClass().getCanonicalName());
		}
		
		
		public void addValueError(String field,String errorCode, String msg, String... errorArgs){
			errorCode = (errorCode == null || errorCode.isEmpty())? msg : errorCode;
			errors.rejectValue(field,errorCode, errorArgs, msg);
		}

		public void addLogicError(String field, String msg){
			addLogicError(field, null,msg);
		}
		
		public void addLogicError(String field, String errorCode, String msg, String... errorArgs){
			addValueError(field,errorCode,msg,errorArgs);
		}
		
		public void addError(String errorCode,String... errorArgs){
			errors.reject(errorCode, errorArgs, errorCode);
		}




		public void apply(Validator... vs){
			for(Validator v :vs){
				v.validate(getTarget(), errors);
			}
		}
		
		public void apply(SimpleValidator p,String errorCode,String... errorArgs){
			if(!p.validate()){
				addError(errorCode, errorArgs);
			}
		}

        /**** METODI PER IL CHECK DEI VALORI *****/


		public void applyValue(SimpleValidator p,String field,String errorCode,String msg,String... errorArgs){
			if(!p.validate()){
				addValueError(field,errorCode, msg, errorArgs);
			}
		}
		
		public Object getValue(String field){
			String[] split = field.split("\\.");
			return new BeanWrapperImpl(this.getTarget()).getPropertyValue(field);
		}
		
		
		@SuppressWarnings("unchecked")
		public I getTarget(){
			return (I)((BindException)errors).getTarget();
		}
		

		public void nestedErrors(String path, ValidationInfo v){
		    Errors nestedError = v.getErrors();
		    nestedError.setNestedPath(path);
		    this.errors.addAllErrors(nestedError);
        }
		
		
		public Errors getErrors() {
			return errors;
		};


		
		// check elemento vuoto
		public void isEmpty(String field, String desc){
			isEmpty(field, desc,null);
		}
		
		public void isEmpty(String field){
			isEmpty(field, null,null);
		}

		
		public void isEmpty(String field, String desc, String errorCode, String... errorArgs){
            String msg = "Il campo "+evalDesc(field, desc) +" non puo' essere vuoto";
            errorCode = errorCode == null || errorCode.isEmpty() ? msg : errorCode;
            Object value = getValue(field);
            if (value == null || !StringUtils.hasText(value.toString())) {
                addValueError(field, errorCode, msg, errorArgs);
            };
		}


		private String evalDesc(String field, String desc) {
			return desc==null || desc.isEmpty()? splitCamelCase(field) : desc;
		}

		public void isEmptyList(String field, String errorCode, String... errorArgs){
		    String msg = "Il campo "+splitCamelCase(field)+" non puo' essere vuoto";
		    List<Object> value = (List<Object>) getValue(field);
		    if(value== null || value.isEmpty()){
                addValueError(field, errorCode, msg, errorArgs);
            }
        }






	public void evalRegEx(String field,String regex, String desc, String... errorArgs){
		evalRegEx(field,regex,desc,null,errorArgs);
	}

		// check regex
		public void evalRegEx(String field,String regex, String desc, String errorCode, String... errorArgs){
			Object value = getValue(field);
            String msg = "il valore del campo "+evalDesc(field, desc)+" non e' valido";
            errorCode = errorCode== null ? msg : errorCode;
			if(value!=null && !(value.toString()).matches(regex)){
				addValueError(field, errorCode, msg, errorArgs);
			}
		}








	public <D,S> void verifyExistElement(Repository repository, String field, String desc, String... errorArgs){
			this.verifyExistElement(repository,field,desc,null,errorArgs);
	}

		// check esistenza elemento su repository
        public <D,S> void verifyExistElement(Repository repository, String field, String desc, String errorCode, String... errorArgs){
            S key = (S)getValue(field);
            CrudRepository<D,S> crudRepository = (CrudRepository<D,S>) repository;
            if(key!=null && !crudRepository.existsById(key)){
                String msg = "il valore del campo "+ evalDesc(field, desc)+" non e' valido";
                errorCode = errorCode!=null?errorCode:msg;
                addValueError(field, errorCode, msg, errorArgs);
            }
        }
		
		private static String splitCamelCase(String name){
			StringBuilder s = new StringBuilder();
			String[] entities = name.split("\\.");
			String last = entities[entities.length-1];
			for (String w : last.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")) {
		        s.append(w.toLowerCase()+" ");
		    }
			return s.substring(0, s.length()-1);
		}


		
		
	}