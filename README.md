# Autogyn - Backend

Este é o projeto back-end do sistema **AutoGyn**, desenvolvido com **Spring Boot** e **Java 17**. Ele fornece as APIs REST utilizadas pelo front-end do sistema.

## ✅ Tecnologias utilizadas

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Spring Boot](https://spring.io/projects/spring-boot)
- Spring Web
- Spring Data JPA
- Hibernate
- Banco de dados (ex: PostgreSQL ou MySQL – edite conforme usado)
- Maven

## 👥 Colaboradores

- Kaique  
- Marcos  
- Augusto  
- Ruan

## ⚙️ Requisitos

Antes de rodar o projeto, certifique-se de ter instalado:

- Java 17
- Maven 3.8+  
- Banco de dados (PostgreSQL ou MySQL configurado)
- IDE de sua preferência (IntelliJ, VS Code, Eclipse)

## 🚀 Como rodar o projeto

1. **Clone o repositório**

```bash
git clone https://github.com/seu-usuario/backend-autogyn.git
cd backend-autogyn
```

2. **Configure o banco de dados**

No arquivo `application.properties` ou `application.yml`, configure as credenciais do seu banco:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/autogyn
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
```

3. **Compile e execute o projeto**

```bash
./mvnw spring-boot:run
```

Ou diretamente pela sua IDE executando a classe principal:

```
com.autogyn.AutogynApplication
```

4. **Acesse a aplicação**

Após subir o servidor, a API estará disponível em:  
[http://localhost:8080](http://localhost:8080)

## 📂 Estrutura básica de pastas

```
src/
└── main/
    ├── java/
    │   └── com/
    │       └── autogyn/
    │           ├── controller/
    │           │   └── OrdemServicoController.java
    │           │   └── ClienteController.java
    │           │   └── VeiculoController.java
    │           │
    │           ├── service/
    │           │   └── OrdemServicoService.java
    │           │   └── ClienteService.java
    │           │   └── VeiculoService.java
    │           │
    │           ├── repository/
    │           │   └── OrdemServicoRepository.java
    │           │   └── ClienteRepository.java
    │           │   └── VeiculoRepository.java
    │           │
    │           ├── model/
    │           │   └── OrdemServico.java
    │           │   └── Cliente.java
    │           │   └── Veiculo.java
    │           │
    │           └── AutogynApplication.java
    │
    └── resources/
        ├── application.properties
        ├── static/
        └── templates/
```

## 📮 Endpoints

> Para testar os endpoints, recomenda-se o uso do [Postman](https://www.postman.com/) ou [Insomnia](https://insomnia.rest/).  
> Documentação de endpoints (Swagger) pode ser adicionada futuramente [Swagger](http://localhost:8080/swagger-ui/index.html)

## 📦 Build do projeto

Para gerar o `.jar`:

```bash
./mvnw clean package
```

O artefato será gerado em:  
`target/autogyn-backend.jar`

## 🛠️ Manutenção e melhorias

Este projeto está em constante desenvolvimento. Contribuições são bem-vindas!

---
