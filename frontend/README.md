# Frontend ObservaAção

Vite + React + TypeScript + Tailwind (porta **5173**).

## Desenvolvimento

```bash
npm install
cp .env.example .env   # opcional
npm run dev
```

→ http://localhost:5173

API: `VITE_API_URL` (padrão `http://localhost:8080/api`) — ver `src/api/client.ts`.

## Rotas

| Rota | Conteúdo |
|------|----------|
| `/` | ODS 11 e visão do projeto |
| `/cidadaos` | Cadastro e listagem de cidadãos (#24) |
| `/solicitacoes` | Seção solicitações + atalho ao detalhe |
| `/solicitacoes/:id` | Detalhe, status e histórico (#26) |

Navegação: header **Início · Cidadãos · Solicitações**.
