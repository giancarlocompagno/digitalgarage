package it.digitalgarage.marketplace.commons.model;
public  class Pageable{
		
		private Integer pageNumber;
		private Integer pageSize;
		private Long offset;
		private Sort sort;
		
		public Pageable() {
		}
		
		public Integer getPageNumber() {
			return pageNumber;
		}
		
		public void setPageNumber(Integer pageNumber) {
			this.pageNumber = pageNumber;
		}
		
		public Integer getPageSize() {
			return pageSize;
		}
		
		public void setPageSize(Integer pageSize) {
			this.pageSize = pageSize;
		}
		
		public Long getOffset() {
			return offset;
		}
		
		public void setOffset(Long offset) {
			this.offset = offset;
		}

		public Sort getSort() {
			return sort;
		}

		public void setSort(Sort sort) {
			this.sort = sort;
		}
		
	}