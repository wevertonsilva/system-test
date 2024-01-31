package system.test.enuns;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnumCategoriaSenha {

    MUITO_FRACA("Muito fraca"),
    FRACA("Fraca"),
    MEDIANA("Mediana"),
    BOA("Boa"),
    FORTE("Forte");

    private final String descricao;

}
