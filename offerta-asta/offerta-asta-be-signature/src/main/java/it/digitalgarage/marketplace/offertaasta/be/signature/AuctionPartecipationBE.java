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

@SpringRestService(baseUri="${AUCTION_PARTECIPATION:}")
public interface AuctionPartecipationBE extends RRestBE<AuctionSearchDTO, AuctionDTO>{
	
	
	@RequestMapping(method=RequestMethod.POST,value={"/addBid","/addBid/"})
	public AuctionFullDTO add(@RequestBody BidDTO bid);
	
	@RequestMapping(method=RequestMethod.GET,value="/{oid}")
	public AuctionFullDTO load(@PathVariable("oid") Long oid);
	
	
	@ResponseBody
    @RequestMapping(value="/search",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public PageAuctionDTO search(@RequestBody AuctionSearchDTO example) ;
	
	
	public class PageAuctionDTO extends Page<AuctionDTO>{
		
		public PageAuctionDTO() {
			// TODO Auto-generated constructor stub
		}

		public PageAuctionDTO(int number, int numberOfElements, int totalPages, int totalElement, int size,
				Pageable pageable, List<AuctionDTO> content) {
			super(number, numberOfElements, totalPages, totalElement, size, pageable, content);
			// TODO Auto-generated constructor stub
		}

		public PageAuctionDTO(Page<AuctionDTO> other) {
			super(other);
			// TODO Auto-generated constructor stub
		}
		
		
		
	}


}
