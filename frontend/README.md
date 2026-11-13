# Frontend ObservaAção

Vite + React + TypeScript + Tailwind.

## Desenvolvimento

Backend na porta `8080` e MongoDB ativos:

```bash
npm install
npm run dev
```

Abre em http://localhost:5173

API base: `VITE_API_URL` (padrão `http://localhost:8080/api`).

## Telas

- `/` — início (ODS 11) e atalho para abrir solicitação por ID
- `/solicitacoes/:id` — detalhe, mudança de status e histórico (#26)
