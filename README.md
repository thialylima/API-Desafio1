
# API-Desafio1

Este repositório contém o código de uma API desenvolvida como parte do Desafio 1. A API é construída em Java e utiliza MySQL como banco de dados, com foco no gerenciamento de pedidos de clientes em formato JSON.

## 🚀 Funcionalidades

- **Gerenciamento de Pedidos**: Criação, leitura, atualização e exclusão de pedidos de clientes.
- **Integração com Banco de Dados**: Utiliza MySQL para armazenar informações sobre os pedidos.
- **Formato JSON**: As informações dos pedidos são gerenciadas em formato JSON.

## 🛠️ Tecnologias Utilizadas

- **Java**: Linguagem principal utilizada no desenvolvimento da API.
- **Spring Boot**: Framework para facilitar a criação de APIs RESTful.
- **MySQL**: Banco de dados relacional utilizado para armazenar os dados.
- **Maven**: Gerenciador de dependências e automação de build.
- **Flyway**: Ferramenta para versionamento e migração de esquemas de banco de dados.

## 📂 Estrutura do Projeto

```bash
API-Desafio1/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── thialylima/
│   │   │           └── apidesafio1/
│   │   │               ├── controller/
│   │   │               ├── model/
│   │   │               ├── repository/
│   │   │               └── service/
│   └── resources/
│       └── application.properties
│
├── pom.xml
└── README.md
```

## ⚙️ Configuração

### 1. Clone o repositório

```bash
git clone https://github.com/thialylima/API-Desafio1.git
cd API-Desafio1
```

### 2. Configure o banco de dados

- Certifique-se de ter o MySQL instalado e rodando na sua máquina.

- Atualize o arquivo `application.properties` com suas credenciais do MySQL:

```properties
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA
```

### 3. Compile e execute a aplicação

```bash
mvn clean install
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080/swagger-ui/index.html#/`.

## 🏗️ Request Body (Campos obrigatórios)

```
{
  "numeroControle": "005",
  "nome": "Pipoca",
  "valor_un": 9.65,
  "quantidade": 25,
  "codigoCliente": 2
}
```

## 🤝 Contribuição

Sinta-se à vontade para contribuir com este projeto! Você pode abrir uma issue ou enviar um pull request com melhorias e correções.

