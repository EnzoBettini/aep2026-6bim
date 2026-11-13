# API REST

Base URL (local): `http://localhost:8080`

Contrato interativo: **Swagger UI** em `/swagger-ui.html` (OpenAPI 3).

## Cidadãos — `/api/cidadaos`

| Método | Caminho | Descrição |
|--------|---------|-----------|
| `POST` | `/api/cidadaos` | Criar |
| `GET` | `/api/cidadaos` | Listar |
| `GET` | `/api/cidadaos/{id}` | Buscar por ID |
| `PUT` | `/api/cidadaos/{id}` | Atualizar |
| `DELETE` | `/api/cidadaos/{id}` | Excluir |
| `GET` | `/api/cidadaos/{id}/solicitacoes` | Solicitações do cidadão |

## Solicitações — `/api/solicitacoes`

| Método | Caminho | Descrição |
|--------|---------|-----------|
| `POST` | `/api/solicitacoes` | Criar (exige `cidadaoId` válido) |
| `GET` | `/api/solicitacoes` | Listar |
| `GET` | `/api/solicitacoes/{id}` | Buscar por ID |
| `PUT` | `/api/solicitacoes/{id}` | Atualizar |
| `DELETE` | `/api/solicitacoes/{id}` | Excluir |
| `PATCH` | `/api/solicitacoes/{id}/status` | Mudança de status (com histórico) |
| `GET` | `/api/solicitacoes/{id}/historico` | Linha do tempo de status |

## Exemplo — solicitação (resposta)

```json
{
  "id": "68c1f2a8b4d91e3a7c0e9f12",
  "cidadaoId": "671a12bc34d56e7890abcdef",
  "titulo": "Poste apagado na Rua A",
  "descricao": "Sem iluminação há três noites.",
  "bairro": "Centro",
  "categoria": "ILUMINACAO",
  "status": "ABERTA",
  "historicoStatus": [
    {
      "status": "ABERTA",
      "dataHora": "2026-11-13T12:00:00Z",
      "observacao": "Solicitação criada"
    }
  ]
}
```

**Categorias:** `ILUMINACAO`, `BURACO`, `LIXO`, `PODA`  
**Status:** `ABERTA`, `EM_ANDAMENTO`, `RESOLVIDA`

Erros comuns: `404` (não encontrado), `400` (validação/regra de negócio) — corpo padronizado pelo `GlobalExceptionHandler`.

> Ao adicionar endpoints ou DTOs, atualize esta tabela e confira o Swagger.
