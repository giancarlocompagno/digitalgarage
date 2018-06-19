package it.digitalgarage.marketplace.offertaasta.be.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.digitalgarage.marketplace.commons.model.Page;
import it.digitalgarage.marketplace.offertaasta.be.signature.AuctionPartecipationBE;
import it.digitalgarage.marketplace.offertaasta.be.signature.AuctionPartecipationBE.PageAuctionDTO;
import it.digitalgarage.marketplace.offertaasta.be.signature.dto.AuctionDTO;
import it.digitalgarage.marketplace.offertaasta.be.signature.dto.AuctionFullDTO;
import it.digitalgarage.marketplace.offertaasta.be.signature.dto.AuctionSearchDTO;
import it.digitalgarage.marketplace.offertaasta.be.signature.dto.BidDTO;

@RequestMapping(value="/auction")
@RestController
public class AuctionPartecipationBEController implements AuctionPartecipationBE{
	
	
	
	
	
	@Autowired
	@Qualifier("auctionPartecipationBEService")
	AuctionPartecipationBE service;
	
	
	@RequestMapping(method=RequestMethod.GET,value="/ciao")
	public void ciao(HttpServletRequest request,HttpServletResponse response){
		String nome = request.getParameter("nome");
		
		
		try {
			response.getWriter().write("CIAO "+nome);
			response.getWriter().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@ResponseBody
    @RequestMapping(value="/search",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public PageAuctionDTO search(@RequestBody AuctionSearchDTO example) {
		return service.search(example);
	}

	@Override
	public AuctionFullDTO add(BidDTO bid) {
			return service.add(bid);
		}
	@Override
	public AuctionFullDTO load(Long oid) {
		return service.load(oid);
	}

}
