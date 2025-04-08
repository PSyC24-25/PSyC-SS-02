package es.deusto.proyecto.cine.repository;

import es.deusto.proyecto.cine.model.Sala;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SalaRepository extends JpaRepository<Sala, Long> {   
    Optional<Sala> findByNumero(int numero);
}