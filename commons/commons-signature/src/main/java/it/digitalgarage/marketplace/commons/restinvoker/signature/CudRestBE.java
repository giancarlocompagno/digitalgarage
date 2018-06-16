package it.digitalgarage.marketplace.commons.restinvoker.signature;

//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import it.digitalgarage.marketplace.commons.restinvoker.factory.SpringRestService;

@SpringRestService(baseUri="/")
public interface CudRestBE<T,ID> {

    @ResponseBody
    @RequestMapping(value="/{id}",method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public T load(@PathVariable("id") ID id) throws EntityNotFoundException;

    @ResponseBody
    @RequestMapping(value="/{id}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") ID id) throws NotDeletedException;

    @ResponseBody
    @RequestMapping(value={"","/"},method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public T save(@RequestBody T t) throws NotSavedException;

}
