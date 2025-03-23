package es.deusto.proyecto.cine.repository;

import es.deusto.proyecto.cine.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {   
}