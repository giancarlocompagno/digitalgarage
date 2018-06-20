package it.digitalgarage.marketplace.offertaasta.be.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.digitalgarage.marketplace.commons.model.Page;
import it.digitalgarage.marketplace.commons.model.Pageable;
import it.digitalgarage.marketplace.offertaasta.be.signature.RicercaAstaGuest;
import it.digitalgarage.marketplace.offertaasta.be.signature.dto.AuctionDTO;
import it.digitalgarage.marketplace.offertaasta.be.signature.dto.AuctionSearchDTO;
import it.digitalgarage.marketplace.offertaasta.be.signature.dto.PageAuctionDTO;

@RestController
@RequestMapping(value="visitatore")
public class VisitatoreController{
	@Autowired
	@Qualifier("ricercaAstaGuestService")
	RicercaAstaGuest ricercaService;
	
	@ResponseBody
    @RequestMapping(value="/search",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
	public PageAuctionDTO search(AuctionSearchDTO example) {
		// TODO Auto-generated method stub
		return ricercaService.search(example);
	}
	
	@ResponseBody
    @RequestMapping(value="/auction/{oid}",method = RequestMethod.GET)
	//public ResponseEntity<AuctionDTO> auctionDetail(@PathVariable("oid")String oid) {
	public ResponseEntity<AuctionDTO> auctionDetail(@PathVariable("oid")String oid) {
		// TODO Auto-generated method stub
		AuctionDTO aDto = ricercaService.getAuction(Long.parseLong(oid));
		return new ResponseEntity<AuctionDTO>(aDto, HttpStatus.OK);
	}
	
	@ResponseBody
    @RequestMapping(value="auction",method = RequestMethod.POST)
	public ResponseEntity<AuctionDTO> auctionDetail(@RequestBody AuctionDTO prova) {
		// TODO Auto-generated method stub
		AuctionDTO aDto = ricercaService.getAuction(prova.getOid());
		return new ResponseEntity<AuctionDTO>(aDto, HttpStatus.OK);
	}
	
	/**
	 * modalit� ?var=contenuto
	 * 
	@ResponseBody
    @RequestMapping(value="/auction",method = RequestMethod.GET)
	//public ResponseEntity<AuctionDTO> auctionDetail(@PathVariable("oid")String oid) {
	public ResponseEntity<AuctionDTO> auctionDetail(@RequestParam("oid")String oid) {
		// TODO Auto-generated method stub
		AuctionDTO aDto = ricercaService.getAuction(Long.parseLong(oid));
		return new ResponseEntity<AuctionDTO>(aDto, HttpStatus.OK);
	}
	*/
	
	 
}
