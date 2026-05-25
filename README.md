# 🎬 CineNoir

Sistema de cinema desenvolvido com **Spring Boot** (backend) e **HTML/CSS/JS puro** (frontend), com banco de dados SQLite. Permite compra de ingressos, avaliação de filmes por críticos, gerenciamento de sessões e painel administrativo.

---

## Tecnologias

**Backend**
- Java 17
- Spring Boot 3.5
- Spring Data JPA + Hibernate
- SQLite
- Maven Wrapper (não exige Maven instalado)

**Frontend**
- HTML5, CSS3, JavaScript puro (sem frameworks)
- Comunicação via `fetch` com a API REST

---

## Pré-requisitos

Apenas o **Java 17** e o **Python** precisam estar instalados. O Maven já está embutido no projeto via Maven Wrapper.

| Ferramenta | Versão mínima | Download |
|---|---|---|
| Java (JDK) | 17 | https://www.oracle.com/java/technologies/downloads/#java17 |
| Python | 3.x | https://www.python.org/downloads/ |

Após instalar, verifique no terminal:
```
java -version
python --version
```

> O banco de dados SQLite já está incluído no projeto (`cinema-backend/cinema.db`) com dados de exemplo — não é necessário configurar nada.

---

## Como Rodar

O projeto precisa de **dois terminais abertos ao mesmo tempo**: um para o backend e outro para o frontend.

---

### 🪟 Windows

> ⚠️ Use o **Prompt de Comando (cmd.exe)** — não o PowerShell.

**Terminal 1 — Backend:**

Abra o Prompt de Comando e execute:

```cmd
git clone https://github.com/eduardasaraujo1/Projeto-Cinenoir.git
cd Projeto-Cinenoir
cd cinema-backend
mvnw.cmd spring-boot:run
```

Na primeira execução o Maven baixa as dependências automaticamente — pode levar alguns minutos. Aguarde aparecer:

```
Started CinemaApplication in X.XXX seconds
```

**Terminal 2 — Frontend:**

Abra um **novo** Prompt de Comando e execute:

```cmd
cd C:\Users\SEU_USUARIO\Projeto-Cinenoir\cinema-frontend
python -m http.server 3000
```

> Substitua `SEU_USUARIO` pelo seu nome de usuário do Windows.

Acesse **http://localhost:3000** no navegador.

---

### 🐧 Linux

**Terminal 1 — Backend:**

```bash
git clone https://github.com/eduardasaraujo1/Projeto-Cinenoir.git
cd Projeto-Cinenoir/cinema-backend
./mvnw spring-boot:run
```

Aguarde:
```
Started CinemaApplication in X.XXX seconds
```

**Terminal 2 — Frontend:**

```bash
cd Projeto-Cinenoir/cinema-frontend
python3 -m http.server 3000
```

Acesse **http://localhost:3000** no navegador.

Ou use o script incluído no projeto para subir tudo de uma vez:

```bash
bash iniciar.sh
```

Para encerrar:

```bash
pkill -f "spring-boot" && pkill -f "http.server"
```

---

### 🍎 macOS

**Terminal 1 — Backend:**

```bash
git clone https://github.com/eduardasaraujo1/Projeto-Cinenoir.git
cd Projeto-Cinenoir/cinema-backend
./mvnw spring-boot:run
```

Aguarde:
```
Started CinemaApplication in X.XXX seconds
```

**Terminal 2 — Frontend:**

```bash
cd Projeto-Cinenoir/cinema-frontend
python3 -m http.server 3000
```

Acesse **http://localhost:3000** no navegador.

---

### ⚠️ Observações

- Na **primeira execução** o Maven baixa todas as dependências — requer internet e pode demorar.
- O backend **precisa estar rodando** antes de acessar o frontend no navegador.
- As portas **8080** (backend) e **3000** (frontend) precisam estar livres.

---

## Estrutura do Projeto

```
Projeto-Cinenoir/
├── iniciar.sh                        # Sobe backend e frontend de uma vez (Linux/Mac)
├── README.md
├── cinema-backend/                   # API REST Spring Boot
│   ├── .mvn/wrapper/
│   │   └── maven-wrapper.properties  # Maven Wrapper — não exige Maven instalado
│   ├── src/main/java/br/cinenoir/
│   │   ├── controller/               # AuthController, FilmeController, SessaoController, CriticaController
│   │   ├── model/                    # Filme, Sessao, Usuario, Critica, Bilhete
│   │   ├── repository/               # Interfaces JPA
│   │   └── CinemaApplication.java
│   ├── src/main/resources/
│   │   └── application.properties
│   ├── cinema.db                     # Banco SQLite com dados de exemplo
│   ├── mvnw                          # Maven Wrapper (Linux/Mac)
│   ├── mvnw.cmd                      # Maven Wrapper (Windows)
│   └── pom.xml
└── cinema-frontend/                  # Páginas HTML
    ├── index.html                    # Home com programação e filmes em cartaz
    ├── filmes.html                   # Detalhes do filme com críticas
    ├── assentos.html                 # Seleção de assentos
    ├── carrinho.html                 # Carrinho de compras
    ├── login.html                    # Login
    ├── cadastro.html                 # Cadastro em 4 passos
    ├── perfil.html                   # Perfil do usuário
    ├── esqueci-senha.html            # Recuperação de senha
    ├── critico.html                  # Painel do crítico
    ├── painel.html                   # Painel admin/funcionário
    └── img/                          # Imagens dos filmes
```

---

## Usuários de Teste

| Usuário | Senha | Tipo | Acesso |
|---|---|---|---|
| `admin` | `admin123` | Admin | Painel completo |
| `funcionario` | `func123` | Funcionário | Painel sem gestão de usuários |
| `mario` | `mario1234` | Crítico | Painel do crítico + ingresso gratuito |
| `duda` | `duda123` | Crítico | Painel do crítico + ingresso gratuito |
| `eduarda` | `eduarda1234` | Estudante | 50% de desconto |
| `teste` | `Teste123__` | Cliente | Compra normal |

---

## Funcionalidades

### Site (Público)

- **Home (`index.html`)** — hero animado, seletor de datas com sessões por filme, seção "Em Cartaz" com cards
- **Filme (`filmes.html`)** — hero com cena de fundo, sinopse, diretor, elenco, classificação etária, críticas
- **Assentos (`assentos.html`)** — mapa de assentos com ocupação dinâmica via API
- **Carrinho (`carrinho.html`)** — ingressos, produtos do balcão, cupons promocionais, resumo e finalização

### Autenticação

- **Login (`login.html`)** — autenticação via `POST /api/auth/login`
- **Cadastro (`cadastro.html`)** — fluxo em 4 passos com validação
- **Recuperação de senha (`esqueci-senha.html`)** — fluxo completo com token

### Área do Usuário

- **Perfil (`perfil.html`)** — dados pessoais, alteração de senha, histórico de compras, cupons
  - Estudantes: 50% de desconto automático
  - Críticos: ingresso gratuito

### Painel do Crítico (`critico.html`)

- Lista todos os filmes em cartaz com nota média
- Atribuição de nota (1–10) e escrita de crítica (mínimo 20 caracteres)
- Recálculo automático da média do filme ao publicar ou excluir crítica
- Aba "Minhas Críticas" com histórico persistido no banco

### Painel Admin/Funcionário (`painel.html`)

- **Dashboard** — visão geral do sistema
- **Filmes** — CRUD completo (nome, gênero, duração, classificação, diretor, elenco, poster, cena de fundo, valor)
- **Sessões** — CRUD com data, horário e tipo de sala
- **Salas** — visualização dos tipos disponíveis (Comum, 3D, XD, XD/3D)
- **Usuários** — listagem e exclusão *(somente admin)*

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

## Cupons Disponíveis

| Cupom | Desconto |
|---|---|
| `PROMO5` | 5% |
| `PROMO10` | 10% |
| `PROMO20` | 20% |

Cada cupom pode ser usado apenas uma vez por usuário.

---

## Portas Utilizadas

| Serviço | Porta | URL |
|---|---|---|
| Frontend | 3000 | http://localhost:3000 |
| Backend | 8080 | http://localhost:8080 |
