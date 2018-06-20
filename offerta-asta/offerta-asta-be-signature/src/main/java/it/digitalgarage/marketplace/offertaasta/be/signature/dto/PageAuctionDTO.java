package it.digitalgarage.marketplace.offertaasta.be.signature.dto;

import java.util.List;

import it.digitalgarage.marketplace.commons.model.Page;
import it.digitalgarage.marketplace.commons.model.Pageable;

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
