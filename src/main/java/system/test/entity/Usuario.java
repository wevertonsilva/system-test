package system.test.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import system.test.enuns.EnumCategoriaSenha;

import java.math.BigDecimal;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "system_test", name = "tb_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "forca_senha_percent", nullable = false)
    private BigDecimal forcaSenhaPercentual;

    @Enumerated(STRING)
    @Column(name = "categoria_senha", nullable = false)
    private EnumCategoriaSenha categoriaSenha;

    @Column(name = "categoria_senha_formatada", nullable = false)
    private String categoriaSenhaFormatada;

    @ManyToOne
    @JoinColumn(name = "id_usuario_pai")
    private Usuario usuarioPai;

    @OneToMany(mappedBy = "usuarioPai")
    private List<Usuario> filhos;

}