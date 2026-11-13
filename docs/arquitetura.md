# Arquitetura

## Visão geral

Monorepo com **backend** e **`frontend/`** (Vite + React; rotas Início / Cidadãos / Solicitações).

```
aep2026-6bim/
├── backend/          # Spring Boot 3.x, Java 17
├── docs/             # Documentação versionada
└── .github/workflows # CI (testes + JaCoCo)
```

## Backend — pacotes

Organização por **bounded context** + camadas:

| Pacote | Responsabilidade |
|--------|------------------|
| `org.boligon.cidadao.domain` | Entidade `Cidadao` |
| `org.boligon.cidadao.dto` | Request/response HTTP |
| `org.boligon.cidadao.repository` | Spring Data MongoDB |
| `org.boligon.cidadao.service` | Regras de cidadão |
| `org.boligon.cidadao.controller` | `/api/cidadaos` |
| `org.boligon.solicitacao.*` | Mesmo padrão para solicitações |
| `org.boligon.cli` | Menu terminal (submenus Cidadãos / Solicitações, status e histórico) |
| `org.boligon.exception` | Erros + `GlobalExceptionHandler` |

Controllers expõem **DTOs**; entidades de domínio não vão direto no JSON.

## MongoDB

Banco padrão: `observacao` (`spring.data.mongodb.uri` em `application.properties`).

| Collection | Documento principal |
|------------|---------------------|
| `cidadaos` | `nome`, `email` (único), `telefone` |
| `solicitacoes` | `cidadaoId`, dados da ocorrência, `status`, `historicoStatus[]` |

`historicoStatus` é **embarcado** na solicitação (subdocumentos com status, data, observação).

## Execução

- `mvn spring-boot:run` sobe API **e** CLI no mesmo processo (o menu usa stdin).
- Swagger UI: contrato OpenAPI gerado a partir dos controllers.

## Frontend

SPA em `frontend/` consome a API REST (`VITE_API_URL`). Rotas: início, cidadãos, solicitações e detalhe com histórico.

## Melhorias futuras (opcional)

- Desacoplar CLI do `spring-boot:run` em ambientes de teste E2E
- Autenticação / perfis (fora do escopo da 2ª entrega)
