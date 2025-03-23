package es.deusto.proyecto.cine.repository;

import es.deusto.proyecto.cine.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {   
}