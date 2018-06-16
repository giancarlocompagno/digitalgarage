package it.digitalgarage.marketplace.commons.model;

import java.util.List;

public  class Page<T>{
	private int number;
	private int numberOfElements;
	private int totalPages;
	private long totalElement;
	private int size;
	private Pageable pageable;
	private List<T> content;


	public Page(){

	}

	public Page(Page<T> other) {
		this.setContent(other.getContent());
		this.setNumber(other.getNumber());
		this.setNumberOfElements(other.getNumberOfElements());
		this.setPageable(other.getPageable());
		this.setSize(this.getSize());
		this.setTotalElement(other.getTotalElement());
		this.setTotalPages(other.getTotalPages());
	}   


	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getNumberOfElements() {
		return numberOfElements;
	}
	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}

	public long getTotalElement() {
		return totalElement;
	}

	public void setTotalElement(long totalElement) {
		this.totalElement = totalElement;
	}

	public Pageable getPageable() {
		return pageable;
	}

	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}

	public List<T> getContent() {
		return content;
	}
	public void setContent(List<T> content) {
		this.content = content;
	}

	public Page(int number, int numberOfElements, int totalPages, int totalElement, int size, Pageable pageable, List<T> content) {
		this.number = number;
		this.numberOfElements = numberOfElements;
		this.totalPages = totalPages;
		this.totalElement = totalElement;
		this.size = size;
		this.pageable = pageable;
		this.content = content;
	}
}