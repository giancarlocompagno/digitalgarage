package it.digitalgarage.marketplace.offertaasta.be.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.digitalgarage.marketplace.commons.model.Page;
import it.digitalgarage.marketplace.offertaasta.be.signature.AuctionPartecipationBE;
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
	
	
	@Override
	public PageAuctionDTO search(AuctionSearchDTO example) {
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
