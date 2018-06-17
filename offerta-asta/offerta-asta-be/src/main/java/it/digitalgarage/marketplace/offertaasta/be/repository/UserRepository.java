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
import it.digitalgarage.marketplace.offertaasta.be.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, String>,JpaSpecificationExecutor<User>{
	
	
}
