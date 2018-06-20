package it.digitalgarage.marketplace.offertaasta.be.signature;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import it.digitalgarage.marketplace.commons.model.Page;
import it.digitalgarage.marketplace.commons.model.Pageable;
import it.digitalgarage.marketplace.commons.restinvoker.factory.SpringRestService;
import it.digitalgarage.marketplace.commons.restinvoker.signature.RRestBE;
import it.digitalgarage.marketplace.offertaasta.be.signature.dto.AuctionDTO;
import it.digitalgarage.marketplace.offertaasta.be.signature.dto.AuctionFullDTO;
import it.digitalgarage.marketplace.offertaasta.be.signature.dto.AuctionSearchDTO;
import it.digitalgarage.marketplace.offertaasta.be.signature.dto.BidDTO;
import it.digitalgarage.marketplace.offertaasta.be.signature.dto.PageAuctionDTO;

public interface RicercaAstaGuest extends RRestBE<AuctionSearchDTO, AuctionDTO>{
	
    public PageAuctionDTO search(AuctionSearchDTO example);
    
    public AuctionDTO getAuction(Long oid);
    
    
    
	
}
