package system.test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import system.test.enuns.EnumCategoriaSenha;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioOutDTO {

    private Long id;
    private String nome;
    private BigDecimal forcaSenhaPercentual;
    private EnumCategoriaSenha categoriaSenha;
    private String categoriaSenhaFormatada;

}