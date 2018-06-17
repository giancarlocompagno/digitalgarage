package it.digitalgarage.marketplace.offertaasta.be.signature.dto;

import it.digitalgarage.marketplace.commons.model.ASearchDTO;

public class AuctionSearchDTO extends ASearchDTO<Long> {

	
	private String title;
	
	private String description;
	
	private boolean active = true;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}

	
}
