package it.digitalgarage.marketplace.offertaasta.be.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.digitalgarage.marketplace.commons.model.Pageable;
import it.digitalgarage.marketplace.offertaasta.be.signature.RicercaAstaGuest;
import it.digitalgarage.marketplace.offertaasta.be.signature.dto.AuctionDTO;
import it.digitalgarage.marketplace.offertaasta.be.signature.dto.AuctionSearchDTO;
import it.digitalgarage.marketplace.offertaasta.be.signature.dto.PageAuctionDTO;

@Service
@Transactional
public class RicercaAstaGuestService implements RicercaAstaGuest{
	@Override
	public PageAuctionDTO search(AuctionSearchDTO example) {
		// TODO Auto-generated method stub
		AuctionDTO aDTO=new AuctionDTO();
		aDTO.setPricing("452.05");
		aDTO.setTitle("Tempo");
		ArrayList<AuctionDTO> list=new ArrayList<>();
		list.add(aDTO);
		return new PageAuctionDTO(1,2,3,4,5, new Pageable(), list);
		/*int number, int numberOfElements, int totalPages, int totalElement, int size,
		Pageable pageable, List<AuctionDTO> content*/
	}

	@Override
	public AuctionDTO getAuction(Long oid) {
		AuctionDTO aDTO = new AuctionDTO();
		if(oid > 100 && oid < 0)
			return aDTO;
		aDTO.setOid(oid);
		aDTO.setDescription("Descrizione del DTO " + oid);
		aDTO.setPricing("100,00" + oid.toString());
		
		// TODO Auto-generated method stub
		return aDTO;
	}
	

}
