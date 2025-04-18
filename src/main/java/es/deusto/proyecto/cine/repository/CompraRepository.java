package es.deusto.proyecto.cine.repository;

import es.deusto.proyecto.cine.model.Compra;
import es.deusto.proyecto.cine.model.Emision;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {   
    List<Compra> findByEmision(Emision emision);
}