package system.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import system.test.entity.Usuario;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT DISTINCT u FROM Usuario u WHERE u.usuarioPai IS NULL")
    List<Usuario> findUsuariosPais();

}
