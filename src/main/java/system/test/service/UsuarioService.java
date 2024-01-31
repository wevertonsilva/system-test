package system.test.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import system.test.config.exception.BadRequestException;
import system.test.entity.Usuario;
import system.test.repository.UsuarioRepository;

import java.util.List;

@Getter
@Service
@RequiredArgsConstructor
public class UsuarioService implements IUsuarioService {

    private final UsuarioRepository repository;
    private final ISenhaService senhaService;

    @Transactional(rollbackFor = Exception.class)
    public Usuario create(final Usuario usuario) throws BadRequestException {
        senhaService.apply(usuario);
        return getRepository().save(usuario);
    }

    public List<Usuario> findListaUsuarios() {
        return getRepository().findUsuariosPais();
    }

}
