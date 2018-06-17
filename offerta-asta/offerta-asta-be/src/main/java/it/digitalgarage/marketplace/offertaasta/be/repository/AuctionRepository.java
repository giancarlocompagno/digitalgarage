package it.digitalgarage.marketplace.offertaasta.be.repository;

import java.security.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.digitalgarage.marketplace.offertaasta.be.model.Auction;
import it.digitalgarage.marketplace.offertaasta.be.model.Product;


@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long>,JpaSpecificationExecutor<Auction>{
	
	@Query(value="SELECT a FROM Auction a where startAuction <= :timestamp AND endAuction > :timestamp and suspend = false")
	public List<Auction> activeAuction(@Param("timestamp")Timestamp timestamp);
	
	@Query(value="SELECT a FROM Auction a WHERE a.supplier.username = :username")
	public List<Auction> auctionBySupplier(@Param("username") String username);
	
	@Query(value="SELECT a FROM Auction a WHERE a.product = :product")
	public List<Auction> auctionByProduct(@Param("product") Product product);
	@Query(value="select a from Auction a where a.oid = :oid and a.version = :version")
	public List<Auction> find(@Param("oid")Long oid,@Param("version") Long version);

	
}
