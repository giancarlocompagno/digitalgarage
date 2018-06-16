package it.digitalgarage.marketplace.commons.be.validator;


import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.data.repository.Repository;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;

import it.digitalgarage.marketplace.commons.be.validator.model.ValidationModelCampo;
import it.digitalgarage.marketplace.commons.be.validator.model.ValidationModelConverter;
import it.digitalgarage.marketplace.commons.be.validator.model.ValidationModelEntita;

public class VisitorValidator<T,DTO> implements Validator{


    private ValidationModelConverter validationModelConverter;
    private ValidationInfo validationInfo;
    private BeanFactory beanFactory;
    private Class<DTO> clazzDTO;
    private String configurationName;

    private static final String ROOT_LABEL = "";

    public VisitorValidator(Class<DTO> clazzDTO,ValidationInfo vi,ValidationModelConverter validationModelConverter, BeanFactory beanFactory,String configurationName){
        this.validationModelConverter = validationModelConverter;
        this.validationInfo = vi;
        this.beanFactory = beanFactory;
        this.clazzDTO = clazzDTO;
        this.configurationName = configurationName;
    }


   /* public Validate<T> visit(String field){
        return null;
    }

    public ValidationModelConverter getValidationModelConverter() {
        return validationModelConverter;
    }

    public void setValidationModelConverter(ValidationModelConverter validationModelConverter) {
        this.validationModelConverter = validationModelConverter;
    }

    public ValidationInfo getValidationInfo() {
        return validationInfo;
    }

    public void setValidationInfo(ValidationInfo validationInfo) {
        this.validationInfo = validationInfo;
    }*/

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    public Object get(Field f,Object o){
        try {
            return f.get(o);
        } catch (IllegalAccessException e) {
            throw  new RuntimeException(e);
        }
    }


    @Override
    public void validate(Object o, Errors errors) {
        recursiveValidate(o, validationInfo, configurationName);
    }

    private void recursiveValidate(Object o, ValidationInfo validationInfo, String configurationName){
        T target = (T)o;
        Field[] fields = target.getClass().getDeclaredFields();

        for (Field f : fields){
            f.setAccessible(true);
            ValidationInfoProperty vip  = f.getDeclaredAnnotation(ValidationInfoProperty.class);
            ValidationInfoValue viv = new ValidationInfoValue(vip,f);

            if(!viv.isIgnore()) {
                if(List.class.isAssignableFrom(f.getType())) {
                    validateList(/*progressiveIndex,*/validationInfo, /*f.getName(),*/ viv, target, f);
                } else if(clazzDTO.isAssignableFrom(f.getType())){
                	System.out.println("------------>"+f.getType());
                    Object dto = get(f,target);
                    if(dto!=null){
                        ValidationInfo<?> from = validateElement(dto, viv);//, progressiveIndex,progressiveLabel+"."+f.getName());
                        copy(validationInfo,from,f.getName(),null);
                    }else{
                        System.out.println("-------------------> verificare se dto "+viv.getOriginalFieldName()+" is empty");
                    }
                } else {
                	ValidationModelEntita validationModelEntita = validationModelConverter.getValidationModelMap().get(configurationName);
                	if(validationModelEntita!=null){
                		Map<String, ValidationModelCampo> elenco = validationModelEntita.getElencoCampi();
                		if(elenco!=null){
                			ValidationModelCampo vmc = elenco.get(f.getName());
                			if(vmc!=null){
                				checkValidation(validationInfo, vmc, viv, get(f,target));
                			}else{
                				System.out.println("VMC is null per "+f.getName());
                			}
                		}else{
                			System.out.println("elenco is null per "+validationModelEntita.getCodiceEntita());
                		}
                	}else{
                		System.out.println("validationModelEntita is null per "+configurationName);
                	}

                }

            }
        }
    }

    private void validateList(ValidationInfo validationInfo, ValidationInfoValue viv, T target, Field f) {

        // logica di lista
        List<?> listaObject = (List<?>) get(f, target);
        if(listaObject != null && !listaObject.isEmpty()){
            for (int i = 0; i < listaObject.size(); i++) {
                ValidationInfo vint = validateElement(listaObject.get(i), viv);
                copy(validationInfo,vint,f.getName(),i);
            }
        }
    }

    // validazione singolo elemento
    private ValidationInfo validateElement(/*ValidationInfo nestedValidationInfo,String parent, String campo, Field f,*/Object target, ValidationInfoValue viv)/*, Integer progressiveIndex, String progressiveLabel)*/{
            /* recupero le validazioni dal model converter */

            ValidationModelEntita valModelEntita = validationModelConverter.getValidationModelMap().getOrDefault(viv.getKeyFieldName(), null);
            Map<String,ValidationModelCampo> valModelMap = (valModelEntita==null)?new HashMap<String,ValidationModelCampo>():valModelEntita.getElencoCampi();

            ValidationInfo elementValidationInfo = new ValidationInfo(target);


            /*if(internalValue == null){
                nestedValidationInfo.isEmptyElement(fieldToRetrieve,progressiveLabel);
            } else {*/
                if (!valModelMap.isEmpty()) {
                    // sono in oggetto entita e devo ricavare ancora info ricorsive
                        recursiveValidate(target, elementValidationInfo, viv.getKeyFieldName());
                    //copy(elementValidationInfo,nestedValidationInfo,f.getName(),progressiveIndex);
                } /*else {
                    ValidationModelCampo vmc = validationModelConverter.getValidationModelMap().get(fieldToRetrieve).getElencoCampi().get(f.getName());
                    checkValidation(nestedValidationInfo, vmc, viv, progressiveIndex, internalValue, progressiveLabel+"."+f.getName());
                }*/

                return elementValidationInfo;


            //}

    }

    private void checkValidation(ValidationInfo nestedValidationInfo, ValidationModelCampo vmc, ValidationInfoValue viv, Object internalValue) {
    	// sono in oggetto foglia e recupero le info per le validazioni semplici (esistenza, regex, e non vuoto)
    	//
    	if(vmc!=null){
    		// controllo se richiesto
    		if (vmc.getRequired()) {
    			nestedValidationInfo.isEmpty(vmc.getNomeCampo(),vmc.getDescrizione());
    		}

    		// controllo su repo
    		if (!viv.getRepository().isEmpty()) {
    			String repository = viv.getRepository();
    			Repository bean = (Repository) this.beanFactory.getBean(repository);
    			nestedValidationInfo.verifyExistElement(bean ,vmc.getNomeCampo(),vmc.getDescrizione());
    		}
    		
    		// controllo regex
    		if(!(internalValue instanceof Date)){
    			if (vmc.getRegex() != null) {
    				nestedValidationInfo.evalRegEx(vmc.getNomeCampo(), vmc.getRegex(),vmc.getDescrizione());
    			}
    			/**INSERITO DA GIANCARLO CONTROLLO LUNGHEZZA*/
    			if(internalValue!=null && vmc.getMaxLength()!=null && internalValue.toString().length()>vmc.getMaxLength()){
    				nestedValidationInfo.addLogicError(vmc.getNomeCampo(), "Lunghezza massima ammessa "+vmc.getMaxLength());
    			}
    		}
    	}
    }

    //gestioneTerritoriale[0], anagrafica
    public void copy(ValidationInfo<?> to, ValidationInfo<?> from,String nestedPath,Integer progressiveIndex){
        List<ObjectError> ge = from.getErrors().getGlobalErrors();
        ge.forEach((error)->{
            to.getErrors().reject(error.getCode(),error.getArguments(),error.getDefaultMessage());
        });
        List<FieldError> fe = from.getErrors().getFieldErrors();
        String internalNestedPath = nestedPath==null || nestedPath.equals("")?"":nestedPath+(progressiveIndex!=null?"["+progressiveIndex+"]":"")+".";
        fe.forEach((error)->{
            to.getErrors().rejectValue(internalNestedPath+error.getField(),error.getCode(),error.getArguments(),error.getDefaultMessage());
        });

    }

}
