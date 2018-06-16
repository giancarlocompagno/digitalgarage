package it.digitalgarage.marketplace.offertaasta.be.signature;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import it.digitalgarage.marketplace.commons.model.Page;
import it.digitalgarage.marketplace.commons.restinvoker.factory.SpringRestService;
import it.digitalgarage.marketplace.commons.restinvoker.signature.CudRestBE;
import it.digitalgarage.marketplace.commons.restinvoker.signature.RRestBE;
import it.digitalgarage.marketplace.offertaasta.be.signature.dto.TipoAstaDTO;
import it.digitalgarage.marketplace.offertaasta.be.signature.dto.TipoAstaSearchDTO;
@SpringRestService(baseUri = "${TIPO_ASTA_BE:''}")
public interface TipoAstaBE extends CudRestBE<TipoAstaDTO,String>,RRestBE<TipoAstaSearchDTO, TipoAstaDTO> {
	
	
   @Override
   @ResponseBody
   @RequestMapping(value="/search",method = RequestMethod.POST)
   @ResponseStatus(HttpStatus.OK)
   public PageTipoAstaDTO search(@RequestBody TipoAstaSearchDTO example);
	
	
	
	
	
	
	public class PageTipoAstaDTO extends Page<TipoAstaDTO>{
		
		public PageTipoAstaDTO() {
		}
		
		public PageTipoAstaDTO(Page<TipoAstaDTO> controllo){
			super(controllo);
		}
		
	}
}
