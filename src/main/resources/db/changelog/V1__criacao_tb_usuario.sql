-- db/migration/V1__criacao_tb_usuario

CREATE TABLE system_test.tb_usuario (
    id                          BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    nome                        VARCHAR(255) NOT NULL,
    senha                       VARCHAR(255) NOT NULL,
    forca_senha_percent         TINYINT UNSIGNED NOT NULL,
    categoria_senha             VARCHAR(20) NOT NULL,
    categoria_senha_formatada   VARCHAR(20) NOT NULL,
    id_usuario_pai              BIGINT NULL,
    FOREIGN KEY (id_usuario_pai) REFERENCES system_test.tb_usuario(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;