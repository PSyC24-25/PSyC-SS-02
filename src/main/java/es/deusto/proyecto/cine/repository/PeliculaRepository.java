package es.deusto.proyecto.cine.repository;

import es.deusto.proyecto.cine.model.Pelicula;
import jakarta.transaction.Transactional;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Long> {   
    Optional<Pelicula> findByCodPelicula(Long codigo);
    Optional<Pelicula> findByTitulo(String titulo);

    @Transactional
    void deleteByCodPelicula (Long codigo);
}