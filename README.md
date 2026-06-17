# StencilWeb

Aplicação web full-stack construída com **Spring Boot 3**, **MySQL** e **Thymeleaf**, com autenticação de usuários, upload de imagens e deploy containerizado com Docker e MySQL.

O StencilWEB foi desenvolvido como versão Web do STENCIL, **software standalone de gamificação para ambientes educacionais de baixa disponibilidade de tecnologia** aplicado no 
Programa Sabará + Inglês —projeto de extensão do IFMG Campus Sabará com foco no ensino de pensamento computacional, programação, robótica e inglês para alunos do 
Ensino Fundamental II— como parte da minha pesquisa no impacto da gamificação no aprendizado dentro e fora de sala de aula. O artigo resultante da pesquisa pode ser encontrado em
https://www.conic-semesp.org.br/anais/files/2025/trabalho-1000014348.pdf?1764588986. O repositório do STENCIL se encontra em https://www.github.com/pmmc026/stencil.

---

## Tecnologias

| Camada | Tecnologia |
|---|---|
| Backend | Java 21, Spring Boot 3.4 |
| Segurança | Spring Security |
| Frontend | Thymeleaf, HTML, CSS, Bootstrap |
| Persistência | Spring Data JPA, MySQL 8 |
| Validação | Spring Boot Validation |
| Containerização | Docker, Docker Compose |
| Build | Maven Wrapper |

---

## Funcionalidades

- Autenticação e autorização de usuários via Spring Security
- Upload de imagens com armazenamento em volume persistente
- Views renderizadas no servidor com templates Thymeleaf
- Endpoint de saúde e métricas via Spring Actuator
- Ambiente totalmente containerizado — aplicação + MySQL sobem juntos com `docker-compose`
- `wait-for-it.sh` garante que a aplicação só inicia após o banco de dados estar pronto

---

## Pré-requisitos

- Docker e Docker Compose
- Java 21+ (somente para desenvolvimento local, sem Docker)
- Maven (ou use o wrapper incluso `./mvnw`)

---

## Como executar

### Com Docker (recomendado)

1. Clone o repositório:

```bash
git clone https://github.com/lvmendess/stencilWeb.git
cd stencilWeb
```

2. Crie um arquivo `.env` na raiz do projeto com a senha do banco:

```env
DB_PASSWORD=sua_senha_aqui
```

3. Crie um arquivo `env.properties` no diretório src\main\resources\env.properties com usuário e senha do banco:

```env.properties
DB_PASSWORD=sua_senha_aqui
DB_USER=seu_usuario_aqui
```

4. Suba todos os serviços:

```bash
docker-compose up --build
```

A aplicação estará disponível em `http://localhost:8080`.
O MySQL ficará exposto localmente na porta `3307`.

> Na primeira execução, a inicialização pode levar ~30 segundos enquanto o MySQL sobe e passa no health check.

---

### Localmente (sem Docker)

1. Certifique-se de ter uma instância MySQL rodando com um banco chamado `Stencil`.

2. Configure a conexão em `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/Stencil
spring.datasource.username=root
spring.datasource.password=sua_senha
```

3. Execute a aplicação:

```bash
./mvnw spring-boot:run
```

---

## Estrutura do projeto

```
stencilWeb/
├── src/
│   ├── main/
│   │   ├── java/com/stencilwebclient/stencilweb/
│   │   │   ├── controller/     # Controllers MVC
│   │   │   ├── service/        # Serviços
│   │   │   ├── repository/     # Repositórios JPA
│   │   │   ├── model/          # Entidades JPA
│   │   │   └── config/         # Configurações de segurança e aplicação
│   │   └── resources/
│   │       ├── templates/      # Templates HTML (Thymeleaf)
│   │       ├── static/         # CSS, JS e assets estáticos
│   │       └── application.properties
│   └── test/
├── Dockerfile
├── docker-compose.yml
├── wait-for-it.sh
└── pom.xml
```

---

## Visão geral do Docker

O `docker-compose.yml` define dois serviços:

- `mysqldb` — MySQL 8 com health check e volume persistente para os dados
- `api_service` — a aplicação Spring Boot, construída via Dockerfile com build multi-stage (Maven + JDK), aguarda o MySQL estar saudável antes de iniciar

As imagens enviadas pelos usuários são persistidas em um volume Docker (`uploads-data`) montado em `/uploads/images`.

---

## Variáveis de ambiente

| Variável | Descrição |
|---|---|
| `DB_PASSWORD` | Senha root do MySQL (obrigatório no `.env` e `env.properties`) |
| `DB_USER` | Usuário root do MySQL (obrigatório no `env.properties`)
| `APP_UPLOAD_DIR` | Diretório de armazenamento das imagens (padrão: `/uploads/images`) |

---

## Executando os testes

```bash
./mvnw test
```

---

## Licença

Conforme o propósito educacional e social do projeto, este software foi publicado sob a lincença GPL V3 para manter seu acesso e desenvolvimento livre.
