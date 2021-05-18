# Lanchonete API

Este projeto é uma API desenvolvida para realizar o gerenciamento de pedidos de uma lanchonete.

Foi utilizada a framewok [Spring Boot](https://spring.io/projects/spring-boot) para o desenvolvimento da plataforma.

## Requisitos

- Java 8+
- MySQL8 | PostgreSQL

## Instalação

Baixe o projeto em sua máquina.

Crie uma base de dados para a aplicação utilizando o MySQL8 ou o PostgreSQL. 

```sql
CREATE DATABASE lanchonete;
```

Configure os valore relacionados ao banco de dados no arquivo **src**/**main**/**resources**/**application.properties**.

```properties
spring.datasource.url=jdbc:mysql://{seu_host}:{porta}/{base_de_dados}?useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username={seu_usuario}
spring.datasource.password={sua_senha}
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

Para popular o banco com os dados que serão utilizados pela aplicação, descomente a seguinte linha na primeira vez que for rodar o projeto, mas lembre de comentar novamente para não executar os comandos de SQL toda vez que a aplicação for levantada.

```properties
#spring.datasource.initialization-mode=always
```

## Utilização

Para executar o projeto, vá para o diretório da aplicação e execute o comando a baixo. 

```bash
./mvnw spring-boot:run
```

Para gerar o JAR execute o seguinte comando.

```bash
./mvnw package
# Gerar JAR sem executar os teste
./mvnw package -DskipTests=true
```

A porta padrão que a aplicação utiliza é a **8080**, podendo ser acessada pelo endereço **http://localhost:8080** se você estiver utilizando em sua máquina local.

## Documentação da API
Todos as rotas disponibilizadas pela API estão especificadas [aqui](https://documenter.getpostman.com/view/7742011/TzRYbPxf).

Uma aplicação para testar a API foi hospedada no [Heroku](https://www.heroku.com/about). Link: [https://lanchoneteapi.herokuapp.com](https://lanchoneteapi.herokuapp.com)
