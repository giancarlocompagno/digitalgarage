package it.digitalgarage.marketplace.commons.model;

import java.util.ArrayList;
import java.util.List;

public class Sort{
		
		public Sort() {
			// TODO Auto-generated constructor stub
		}
		
		private List<String> field = new ArrayList<>();
		private boolean asc;
		
		public void setAsc(boolean asc) {
			this.asc = asc;
		}
		
		public void setField(List<String> field) {
			this.field = field;
		}
		
		public List<String> getField() {
			return field;
		}
		
		public boolean isAsc() {
			return asc;
		}
		
		
		
	}