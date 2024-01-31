-- db/migration/V2__insert_tb_usuario

INSERT INTO system_test.tb_usuario
    (nome, senha, forca_senha_percent, categoria_senha, categoria_senha_formatada, id_usuario_pai)
VALUES
    ('Usuário teste', '$2a$10$UeOPJDyrPPwhP3fTlWQ02uSPJ1CqeUquDZG.fOkXt9V0KNher/bBC', 44, 'MEDIANA', 'Mediana', null);