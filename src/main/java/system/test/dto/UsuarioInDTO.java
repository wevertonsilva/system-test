package system.test.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioInDTO {

    @NotBlank(message = "usuario.nome")
    private String nome;

    @NotBlank(message = "usuario.senha")
    private String senha;

    @Valid
    private UsuarioPaiInDTO usuarioPai;

    @Getter
    @Setter
    public static class UsuarioPaiInDTO {

        @NotNull(message = "usuario.pai.id")
        private Long id;

    }

}