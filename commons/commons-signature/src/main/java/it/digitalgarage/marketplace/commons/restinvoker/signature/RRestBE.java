package it.digitalgarage.marketplace.commons.restinvoker.signature;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import it.digitalgarage.marketplace.commons.model.ASearchDTO;
import it.digitalgarage.marketplace.commons.model.DTO;
import it.digitalgarage.marketplace.commons.model.Page;
import it.digitalgarage.marketplace.commons.restinvoker.factory.SpringRestService;

//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;

@SpringRestService(baseUri="/")
public interface RRestBE<SEARCH extends ASearchDTO,RESULT extends DTO> {

    @ResponseBody
    @RequestMapping(value="/search",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public <T extends Page<RESULT>> T search(@RequestBody SEARCH example);

    
    
   

}
