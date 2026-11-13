# Modelo de dados MongoDB

Banco: **`observacao`** (URI padrão em `backend/src/main/resources/application.properties`).

## Relacionamento

- Uma solicitação referencia **um** cidadão pelo campo `cidadaoId` (string com o `_id` do Mongo).
- Não há embed de cidadão na solicitação (apenas o id).
- O histórico de mudanças de status fica **embarcado** em cada solicitação.

```
cidadaos (_id)  ←── cidadaoId ──  solicitacoes (_id)
                                      └── historicoStatus[]
```

## Coleção `cidadaos`

| Campo | Tipo | Observação |
|-------|------|------------|
| `_id` | ObjectId (string na API) | Gerado pelo Mongo |
| `nome` | string | Obrigatório |
| `email` | string | Obrigatório, **único** (índice) |
| `telefone` | string | Opcional |

Exemplo:

```json
{
  "_id": "671a12bc34d56e7890abcdef",
  "nome": "Maria Silva",
  "email": "maria.silva@email.com",
  "telefone": "41999887766"
}
```

## Coleção `solicitacoes`

| Campo | Tipo | Observação |
|-------|------|------------|
| `_id` | ObjectId | Gerado pelo Mongo |
| `cidadaoId` | string | Deve existir em `cidadaos` |
| `titulo` | string | Obrigatório |
| `descricao` | string | Obrigatório |
| `bairro` | string | Obrigatório |
| `categoria` | enum | `ILUMINACAO`, `BURACO`, `LIXO`, `PODA` |
| `status` | enum | `ABERTA`, `EM_ANDAMENTO`, `RESOLVIDA` |
| `historicoStatus` | array | Subdocumentos abaixo |

Subdocumento `historicoStatus`:

| Campo | Tipo |
|-------|------|
| `status` | enum (mesmos valores) |
| `data` | data/hora ISO (Instant) |
| `observacao` | string |

Exemplo:

```json
{
  "_id": "68c1f2a8b4d91e3a7c0e9f12",
  "cidadaoId": "671a12bc34d56e7890abcdef",
  "titulo": "Poste apagado na Rua A",
  "descricao": "Sem iluminação há três noites.",
  "bairro": "Centro",
  "categoria": "ILUMINACAO",
  "status": "EM_ANDAMENTO",
  "historicoStatus": [
    {
      "status": "ABERTA",
      "data": "2026-11-01T10:00:00Z",
      "observacao": "Solicitação criada"
    },
    {
      "status": "EM_ANDAMENTO",
      "data": "2026-11-02T14:30:00Z",
      "observacao": "Equipe a caminho"
    }
  ]
}
```

## API

Respostas HTTP usam DTOs (sem `_id` do Mongo no nome do campo — exposto como `id`). Detalhes em [api.md](api.md) e no Swagger (`/swagger-ui.html`).
