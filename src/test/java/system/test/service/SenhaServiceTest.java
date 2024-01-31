package system.test.service;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import system.test.entity.Usuario;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static system.test.enuns.EnumCategoriaSenha.FORTE;

@ExtendWith(MockitoExtension.class)
class SenhaServiceTest {

    private static final String SENHA = "Teste123456";
    private static final BigDecimal SCORE = valueOf(83);

    @InjectMocks
    private SenhaService service;

    private Usuario getUsuario() {
        return Usuario.builder()
                .nome("Teste")
                .senha(SENHA)
                .build();
    }

    @Test
    @SneakyThrows
    @DisplayName("Deve determinar e obter o score percentual a partir da senha do usuário")
    void deveDeterminarForcaSenha() {
        final Usuario usuario = getUsuario();

        service.determinarForcaSenha(usuario);
        assertNotEquals(ZERO, usuario.getForcaSenhaPercentual());
        assertEquals(SCORE, usuario.getForcaSenhaPercentual());
    }

    @Test
    @SneakyThrows
    @DisplayName("Deve determinar a categoria da senha do usuário a partir do score")
    void deveDeterminarCategoriaSenha() {
        final Usuario usuario = getUsuario();
        usuario.setForcaSenhaPercentual(SCORE);

        service.atualizarCategoria(usuario);
        assertEquals(FORTE, usuario.getCategoriaSenha());
        assertEquals(FORTE.getDescricao(), usuario.getCategoriaSenhaFormatada());
    }

    @Test
    @SneakyThrows
    @DisplayName("Deve criptografar a senha do usuário utilizando BCrypt")
    void deveCriptografarSenha() {
        final Usuario usuario = getUsuario();
        usuario.setSenha(SENHA);

        service.criptografarSenha(usuario);
        assertNotEquals(SENHA, usuario.getSenha());
        assertTrue(BCrypt.checkpw(SENHA, usuario.getSenha()));
    }

}
