package it.digitalgarage.marketplace.offertaasta.be.signature.dto;

import java.util.ArrayList;
import java.util.List;

public class AuctionFullDTO extends AuctionDTO {
	
	private List<Long> productImages = new ArrayList<>();

	public List<Long> getProductImages() {
		return productImages;
	}

	public void setProductImages(List<Long> productImages) {
		this.productImages = productImages;
	}
	
	
	

}
