package it.digitalgarage.marketplace.offertaasta.be.model.princingstrategy;

import java.util.List;

import it.digitalgarage.marketplace.offertaasta.be.model.Bid;


public enum PRICING {
	UPWARDS{
		@Override
		public boolean canAdd(Bid b, Bid newBid) {
			return newBid.getPrice().compareTo(b.getPrice())>0;
		}
		
		@Override
		public Bid currentBid(List<Bid> bids) {
			/*Bid curr = null;
			for(Bid bid : bids){
				if(curr == null){
					curr = bid;
				}else{
					if(bid.getPrice().compareTo(curr.getPrice())>0){
						curr = bid;
					}
				}
			}
			return curr;
			*/
			if(bids!=null && bids.size()>0){
				Bid bid = bids.stream().max(
						(a,b)->{
							return a.getPrice().compareTo(b.getPrice());
						}).get();
				return bid;
			}else{
				return null;
			}
			/*
			return Collections.max(bids,new Comparator<Bid>() {
				public int compare(Bid o1, Bid o2) {
					return o1.getPrice().compareTo(o2.getPrice());
				}
			});*/
		}
		
	},DOWNWARDS{
		@Override
		public boolean canAdd(Bid b, Bid newBid) {
			return newBid.getPrice().compareTo(b.getPrice())>0;
		}
		
		@Override
		public Bid currentBid(List<Bid> bids) {
			if(bids!=null && bids.size()>0){
				return bids.stream().min((a,b)->{return a.getPrice().compareTo(b.getPrice());}).get();
			}else{
				return null;
			}
		}
	};
	
	public abstract boolean canAdd(Bid b,Bid newBid);
	
	public abstract Bid currentBid(List<Bid> bid);
	
	public boolean canAdd(List<Bid> bids, Bid newBid) {
		for(Bid  bid: bids){
			if(!canAdd(bid, newBid)){
				return false;
			}
		}
		return true;
	}

}
