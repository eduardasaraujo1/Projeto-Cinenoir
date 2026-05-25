# 🎬 CineNoir

Sistema de cinema desenvolvido com **Spring Boot** (backend) e **HTML/CSS/JS puro** (frontend), com banco de dados SQLite. Permite compra de ingressos, avaliação de filmes por críticos, gerenciamento de sessões e painel administrativo.

---

## Tecnologias

**Backend**
- Java 17
- Spring Boot 3.5
- Spring Data JPA + Hibernate
- SQLite (via `sqlite-jdbc` + `hibernate-community-dialects`)
- Maven

**Frontend**
- HTML5, CSS3, JavaScript puro (sem frameworks)
- Comunicação via `fetch` com a API REST
- Servidor de desenvolvimento: `python3 -m http.server`

---

## Estrutura do Projeto

```
Projeto-Cinenoir/
├── cinema-backend/          # API REST Spring Boot
│   ├── src/main/java/br/cinenoir/
│   │   ├── controller/      # AuthController, FilmeController, SessaoController, CriticaController
│   │   ├── model/           # Filme, Sessao, Usuario, Critica, Bilhete
│   │   ├── repository/      # Interfaces JPA
│   │   └── CinemaApplication.java
│   ├── src/main/resources/
│   │   └── application.properties
│   ├── cinema.db            # Banco SQLite
│   └── pom.xml
└── cinema-frontend/         # Páginas HTML
    ├── index.html           # Home com programação e filmes em cartaz
    ├── filmes.html          # Detalhes do filme com críticas
    ├── assentos.html        # Seleção de assentos
    ├── carrinho.html        # Carrinho de compras
    ├── login.html           # Login
    ├── cadastro.html        # Cadastro em 4 passos
    ├── perfil.html          # Perfil do usuário
    ├── esqueci-senha.html   # Recuperação de senha
    ├── critico.html         # Painel do crítico
    ├── painel.html          # Painel admin/funcionário
    └── img/                 # Imagens dos filmes
```

---

## Como Rodar

### Pré-requisitos

- Java 17+
- Maven
- Python 3 (para o servidor frontend)
- SQLite3 (opcional, para inspecionar o banco)

### 1. Backend

```bash
cd cinema-backend
mvn spring-boot:run
```

O backend sobe na porta **8080**.

### 2. Frontend

```bash
cd cinema-frontend
python3 -m http.server 3000
```

O frontend fica disponível em **http://localhost:3000**.

---

## Usuários de Teste

| Usuário | Senha | Tipo |
|---|---|---|
| `admin` | `admin123` | Admin |
| `funcionario` | `func123` | Funcionário |
| `mario` | `mario1234` | Crítico |
| `duda` | `duda123` | Crítico |
| `eduarda` | `eduarda1234` | Estudante |
| `teste` | `Teste123__` | Cliente |

---

## Funcionalidades

### Site (Público)

- **Home (`index.html`)** — hero animado, seletor de datas com sessões por filme, seção "Em Cartaz" com cards
- **Filme (`filmes.html`)** — hero com cena de fundo, sinopse, diretor, elenco, classificação etária, críticas de especialistas
- **Assentos (`assentos.html`)** — mapa de assentos com ocupação dinâmica da API, modal de confirmação
- **Carrinho (`carrinho.html`)** — ingressos, produtos do balcão, cupons promocionais (PROMO5, PROMO10, PROMO20), resumo com total e finalização

### Autenticação

- **Login (`login.html`)** — autenticação via `POST /api/auth/login`
- **Cadastro (`cadastro.html`)** — fluxo em 4 passos com validação
- **Recuperação de senha (`esqueci-senha.html`)** — fluxo completo com token

### Área do Usuário

- **Perfil (`perfil.html`)** — dados pessoais, alteração de senha, histórico de compras, cupons disponíveis
  - Estudantes: 50% de desconto automático
  - Idosos: desconto meia-entrada

### Painel do Crítico (`critico.html`)

- Lista todos os filmes em cartaz com nota média e quantidade de críticos
- Atribuição de nota (1–10) e escrita de crítica (mínimo 20 caracteres)
- Publicação via `POST /api/criticas/filme/{id}` com recálculo automático da média
- Aba "Minhas Críticas" com histórico persistido e botão de exclusão
- Exclusão via `DELETE /api/criticas/{id}` com recálculo automático da nota do filme
- Ingresso gratuito para críticos (100% de desconto)

### Painel Admin/Funcionário (`painel.html`)

Acessado automaticamente após login com tipo `admin` ou `funcionario`.

- **Dashboard** — filmes em cartaz, total de sessões, usuários cadastrados
- **Filmes** — CRUD completo (nome, gênero, duração, classificação etária, diretor, elenco, poster, cena de fundo, valor base)
- **Sessões** — CRUD com data, horário, tipo de sala, vínculo com filme
- **Salas** — visualização dos tipos disponíveis (Comum, 3D, XD, XD/3D)
- **Usuários** — listagem e exclusão *(somente admin; funcionário não tem acesso)*

---

## API REST

### Filmes
| Método | Rota | Descrição |
|---|---|---|
| GET | `/api/filmes` | Lista todos os filmes |
| GET | `/api/filmes/cartaz` | Lista filmes em cartaz |
| GET | `/api/filmes/{id}` | Busca filme por ID |
| POST | `/api/filmes` | Cria filme |
| PUT | `/api/filmes/{id}` | Atualiza filme |
| DELETE | `/api/filmes/{id}` | Remove filme |

### Sessões
| Método | Rota | Descrição |
|---|---|---|
| GET | `/api/sessoes/filme/{filmeId}` | Sessões de um filme |
| GET | `/api/sessoes/{id}` | Busca sessão por ID |
| POST | `/api/sessoes` | Cria sessão |
| PUT | `/api/sessoes/{id}` | Atualiza sessão |
| DELETE | `/api/sessoes/{id}` | Remove sessão |
| PATCH | `/api/sessoes/{id}/ocupar` | Marca assentos como ocupados |

### Autenticação
| Método | Rota | Descrição |
|---|---|---|
| POST | `/api/auth/login` | Login |
| POST | `/api/auth/cadastro` | Cadastro |
| GET | `/api/auth/usuario/{user}` | Busca usuário |
| GET | `/api/auth/usuarios` | Lista todos os usuários |
| DELETE | `/api/auth/usuarios/{id}` | Remove usuário |
| PUT | `/api/auth/alterar-senha` | Altera senha |
| POST | `/api/auth/esqueci-senha` | Solicita reset de senha |
| POST | `/api/auth/reset-senha` | Redefine senha com token |

### Críticas
| Método | Rota | Descrição |
|---|---|---|
| GET | `/api/criticas/filme/{filmeId}` | Críticas de um filme |
| GET | `/api/criticas/usuario/{nome}` | Críticas de um crítico |
| POST | `/api/criticas/filme/{filmeId}` | Publica crítica |
| DELETE | `/api/criticas/{id}` | Remove crítica |

---

## Tipos de Usuário

| Tipo | Acesso |
|---|---|
| `cliente` | Compra ingressos, gerencia perfil |
| `estudante` | Idem + 50% de desconto automático |
| `critico` | Idem + painel do crítico + ingresso gratuito |
| `funcionario` | Painel admin (filmes, sessões, salas) |
| `admin` | Painel completo + gerenciamento de usuários |

---

## Banco de Dados

O banco SQLite (`cinema.db`) fica em `cinema-backend/cinema.db`.

### Tabelas

- `filmes` — catálogo de filmes
- `sessoes` — sessões com data, horário, sala e assentos ocupados
- `usuarios` — contas de usuários
- `critica` — avaliações dos críticos vinculadas a filmes
- `bilhetes` — ingressos gerados nas compras

### Cupons Disponíveis

| Cupom | Desconto |
|---|---|
| `PROMO5` | 5% |
| `PROMO10` | 10% |
| `PROMO20` | 20% |

Cada cupom pode ser usado apenas uma vez por usuário.

---

## Configuração

`cinema-backend/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:sqlite:cinema.db
spring.datasource.driver-class-name=org.sqlite.JDBC
spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect
spring.jpa.hibernate.ddl-auto=update
server.port=8080
```
