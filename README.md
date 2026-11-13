# ObservaAção

**PoC** da AEP 2026.2 — Engenharia de Software 6S (Unicesumar).  
Cidadãos registram problemas urbanos em apoio à **ODS 11 — Cidades e Comunidades Sustentáveis**.

## Problema e ODS 11

Cidades precisam de canais simples para **ouvir a população** sobre iluminação, buracos, lixo e poda. O ObservaAção centraliza essas ocorrências, vincula cada registro a um **cidadão** e mantém **histórico de status** até a resolução — alinhado à meta de tornar comunidades mais inclusivas e sustentáveis.

## Tecnologias

| Camada | Stack |
|--------|--------|
| Backend | Java **17**, Spring Boot **3.2**, Spring Data MongoDB |
| API | REST + **Swagger** (springdoc OpenAPI 3) |
| Banco | **MongoDB** Community (local) |
| Qualidade | **JUnit 5**, **MockMvc**, **JaCoCo** (gate ≥ 70% no `mvn verify`) |
| CI | GitHub Actions (`.github/workflows/testes.yml`) |
| Frontend | **Vite**, React, TypeScript, Tailwind, **Vitest** |
| CLI | Menu no terminal (sobe junto com o Spring Boot) |

Documentação complementar: [`docs/README.md`](docs/README.md).

## Modelo NoSQL (resumo)

Duas coleções no database `observacao`:

| Coleção | Papel |
|---------|--------|
| `cidadaos` | Quem abre solicitações (`email` único) |
| `solicitacoes` | Ocorrência + `cidadaoId` + `historicoStatus[]` embarcado |

Exemplos JSON, campos e diagrama: **[`docs/modelo-dados.md`](docs/modelo-dados.md)**.

## Arquitetura em camadas (backend)

Organização por contexto (`cidadao`, `solicitacao`) e camada:

**controller** → **service** → **repository** → **domain** (entidades Mongo), com **DTOs** na borda HTTP.

Detalhes e pacotes Java: [`docs/arquitetura.md`](docs/arquitetura.md).

## Instalar e executar

### Pré-requisitos

- **Java 17**, **Maven 3.x**
- **MongoDB** em `localhost:27017` ([download Community](https://www.mongodb.com/try/download/community))
- **Node.js 20+** (só para o frontend)

### Backend (API + CLI)

```bash
cd backend
mvn spring-boot:run
```

- API: http://localhost:8080  
- Swagger: http://localhost:8080/swagger-ui.html  
- O **menu CLI** aparece no mesmo terminal (submenus Cidadãos / Solicitações).

Conexão MongoDB: `spring.data.mongodb.uri=mongodb://localhost:27017/observacao` em `backend/src/main/resources/application.properties`.

### Frontend (opcional)

```bash
cd frontend
npm install
npm run dev
```

→ http://localhost:5173 (CORS já configurado para essa origem).

## Testes e cobertura

### Backend (JaCoCo + gate 70%)

```bash
cd backend
mvn verify
```

- Relatório: `backend/target/site/jacoco/index.html`
- Evidência versionada: [`docs/cobertura/`](docs/cobertura/)

### Frontend (sem backend)

```bash
cd frontend
npm test
```

### CI

Push na `main` dispara `mvn verify` no GitHub Actions (artefato JaCoCo na execução).

Fluxo da equipe: [`docs/desenvolvimento.md`](docs/desenvolvimento.md).

## Evolução: 1ª → 2ª entrega

| Aspecto | Entrega 1 (`entrega-1`) | Entrega 2 (`main` atual) |
|---------|-------------------------|---------------------------|
| MongoDB | Só `solicitacoes` | `cidadaos` + `solicitacoes` |
| Relacionamento | — | `cidadaoId` obrigatório |
| Status | Campo simples | `historicoStatus` embarcado + `PATCH` / `GET` histórico |
| API | CRUD solicitações | CRUD cidadãos, solicitações, status, histórico |
| Testes | JaCoCo relatório | Gate **70%** + MockMvc + Actions |
| Interface | CLI + Swagger | + **frontend** web e testes Vitest |
| Pacotes | Monolítico | DTOs e pacotes por contexto |

Checklist detalhado: [`docs/entregas.md`](docs/entregas.md).

## Entregas, tags e vídeos

| Entrega | Tag Git | Vídeo demo |
|---------|---------|------------|
| 1ª entrega | [`entrega-1`](https://github.com/EnzoBettini/aep2026-6bim/releases/tag/entrega-1) | conforme material da turma / release |
| 2ª entrega | `entrega-2` *(após issue #20)* | *(issue #19 — a publicar)* |

Repositório: https://github.com/EnzoBettini/aep2026-6bim

## Estrutura do repositório

| Caminho | Conteúdo |
|---------|----------|
| `backend/` | Spring Boot, testes, JaCoCo |
| `frontend/` | React + Vitest |
| `docs/` | Arquitetura, API, modelo de dados, cobertura |
| `.github/workflows/` | Pipeline de testes |
