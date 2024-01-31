package system.test.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import system.test.entity.Usuario;
import system.test.repository.UsuarioRepository;

import static java.util.Collections.singletonList;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService service;

    @Mock
    private UsuarioRepository repository;

    @Mock
    private ISenhaService senhaService;

    private InOrder inOrder;

    @BeforeEach
    void setUp() {
        inOrder = inOrder(repository, senhaService);
    }

    private Usuario getUsuario() {
        return Usuario.builder()
                .nome("Teste")
                .senha("Teste123456")
                .build();
    }

    @Test
    @SneakyThrows
    @DisplayName("Deve salvar um registro de usuário")
    void deveSalvar() {
        final Usuario usuario = getUsuario();

        service.create(usuario);
        inOrder.verify(senhaService).apply(usuario);
        inOrder.verify(repository).save(usuario);
    }

    @Test
    @SneakyThrows
    @DisplayName("Deve retornar lista de usuários pais para montagem em tela")
    void deveRetornarLista() {
        final Usuario usuario = getUsuario();

        when(repository.findUsuariosPais()).thenReturn(singletonList(usuario));

        service.findListaUsuarios();
        inOrder.verify(repository).findUsuariosPais();
    }

}
