# System Test

Teste técnico fullstack para empresa Netdeal.

#### Para rodar o projeto, siga os passos abaixo:
1. Clone o repositório.
2. Certifique-se de ter o MySQL instalado e em execução.
3. Na pasta do projeto, execute os comandos 'mvn clean install'.
4. Se as credenciais locais do MySQL forem diferentes de:
**username = root | password = admin**, altere no arquivo 'application.properties' na pasta 'src/main/resources' para as credenciais corretas.
5. Execute o projeto a partir da IDE ou executando o comando 'mvn spring-boot:run' na pasta do projeto.

#### Informações úteis:
- A documentação da API pode ser acessada no [Swagger](http://localhost:8080/swagger-ui/index.html#/).
- Caso ocorra erro de CORS, certifique-se de alterar a variável ALLOWED_ORIGINS na classe **system.test.config.common.WebConfig.class**