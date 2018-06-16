package it.digitalgarage.marketplace.offertaasta.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import it.digitalgarage.marketplace.offertaasta.be.model.TipoAsta;


@Repository
public interface TipoAstaRepository extends JpaRepository<TipoAsta, String>,JpaSpecificationExecutor<TipoAsta>{

}
