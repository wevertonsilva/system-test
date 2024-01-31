package system.test.service;

import system.test.config.exception.BadRequestException;
import system.test.entity.Usuario;

import java.util.List;

public interface IUsuarioService {

    Usuario create(final Usuario usuario) throws BadRequestException;

    List<Usuario> findListaUsuarios();

}
