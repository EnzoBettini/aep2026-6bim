# ObservaAção

Sistema para registro de problemas urbanos (iluminação, buracos, lixo, poda), alinhado à **ODS 11 — Cidades e Comunidades Sustentáveis**.  
Projeto da AEP 2026.2 — Engenharia de Software 6S.

## O que tem hoje (2ª entrega — backend)

- **API REST** + **Swagger** (`/swagger-ui.html`)
- **MongoDB**: coleções `cidadaos` e `solicitacoes` (histórico de status embarcado)
- **CLI** no terminal (CRUD; sobe junto com o Spring Boot)
- **Testes** + **JaCoCo** (gate ≥ 70% em `mvn verify`) e CI no GitHub Actions
- **Frontend**: previsto nas issues do épico #22 (ainda não no repositório)

Documentação completa: **[`docs/`](docs/README.md)** (arquitetura, API, desenvolvimento, entregas, cobertura).

## Requisitos

Java **17**, Maven, MongoDB em `localhost:27017` (database `observacao`).

```bash
cd backend
mvn spring-boot:run
```

- API base: http://localhost:8080  
- Swagger: http://localhost:8080/swagger-ui.html  

Configuração: `backend/src/main/resources/application.properties`

## Testes e cobertura

```bash
cd backend
mvn verify
```

Relatório: `backend/target/site/jacoco/index.html` · Evidência: [`docs/cobertura/`](docs/cobertura/)

## Repositório

| Caminho | Descrição |
|---------|-----------|
| `backend/` | Código Spring Boot |
| `docs/` | Documentação versionada |
| `.github/workflows/` | Pipeline de testes |

Fluxo de contribuição: uma issue → branch → PR → `main` (detalhes em [desenvolvimento](docs/desenvolvimento.md)).
