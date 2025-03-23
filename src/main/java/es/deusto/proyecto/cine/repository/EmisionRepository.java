package es.deusto.proyecto.cine.repository;

import es.deusto.proyecto.cine.model.Emision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmisionRepository extends JpaRepository<Emision, Long> {   
}