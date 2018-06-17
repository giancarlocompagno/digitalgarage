package it.digitalgarage.marketplace.offertaasta.be.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.digitalgarage.marketplace.commons.be.jpa.QBEUtils;
import it.digitalgarage.marketplace.commons.be.jpa.QBEUtils.MakeSpecification;
import it.digitalgarage.marketplace.commons.be.security.MarketplaceSecurityContext;
import it.digitalgarage.marketplace.offertaasta.be.converter.ConverterUtils;
import it.digitalgarage.marketplace.offertaasta.be.model.Auction;
import it.digitalgarage.marketplace.offertaasta.be.model.Bid;
import it.digitalgarage.marketplace.offertaasta.be.model.User;
import it.digitalgarage.marketplace.offertaasta.be.model.exception.AddBidNotValidException;
import it.digitalgarage.marketplace.offertaasta.be.repository.AuctionRepository;
import it.digitalgarage.marketplace.offertaasta.be.repository.UserRepository;
import it.digitalgarage.marketplace.offertaasta.be.signature.AuctionPartecipationBE;
import it.digitalgarage.marketplace.offertaasta.be.signature.dto.AuctionDTO;
import it.digitalgarage.marketplace.offertaasta.be.signature.dto.AuctionFullDTO;
import it.digitalgarage.marketplace.offertaasta.be.signature.dto.AuctionSearchDTO;
import it.digitalgarage.marketplace.offertaasta.be.signature.dto.BidDTO;

@Service
@Transactional
public class AuctionPartecipationBEService implements AuctionPartecipationBE {
	
	@Autowired
	AuctionRepository auctionRepository;
	
	@Autowired
	UserRepository userRepository;

	@Override
	public PageAuctionDTO search(AuctionSearchDTO example) {
		Map<String,String> m = new HashMap<>();
		m.put("description", "description");
		m.put("title", "title");



		org.springframework.data.domain.Pageable pageable = QBEUtils.makePageable(example,(list)->{
			return list.stream().filter((k)->m.containsKey(k)).map((e)->m.get(e)).collect(Collectors.toList());
		});
		MakeSpecification<Auction> makeSpecification = QBEUtils.makeSpecification(Auction.class);
		
		makeSpecification
				.like("title",example.getTitle())
				.like("description",example.getDescription())
				;
		if(example.isActive()){
			makeSpecification.
				lte("startAuction", new Timestamp(System.currentTimeMillis())).
				gte("endAuction", new Timestamp(System.currentTimeMillis()));
		}

		Specification<Auction> spec = makeSpecification.get();
		
		org.springframework.data.domain.Page<Auction> es = auctionRepository.findAll(spec, pageable);
		
		return  new PageAuctionDTO(QBEUtils.makePage(es, (entity)->{
			return ConverterUtils.convert(entity, AuctionDTO.class);
		}));
	}

	@Override
	public AuctionFullDTO add(BidDTO bidDTO) {
		
		Long oidAuction = bidDTO.getAuctionOid();
		Long versionAuction = bidDTO.getAuctionVersion();
		List<Auction> auctions = auctionRepository.find(oidAuction, versionAuction);
		if(!auctions.isEmpty()){
			Auction auction = auctions.get(0);
			User user = userRepository.getOne(MarketplaceSecurityContext.getUsername());
			Bid bid = new Bid(user,bidDTO.getPrice());
			auction.addBid(bid);
			auctionRepository.save(auction);
		}else{
			throw new AddBidNotValidException("Version auction is not valid");
		}
		return load(bidDTO.getAuctionOid());
	}

	@Override
	public AuctionFullDTO load(Long oid) {
		return ConverterUtils.convert(auctionRepository.getOne(oid), AuctionFullDTO.class,new ConverterUtils.ImageConverter());
	}

}
