# Desenvolvimento

## Requisitos

- **Java 17** (versão do projeto; CI usa Temurin 17)
- **Maven 3.x**
- **MongoDB** em `localhost:27017` (database `observacao`)

## Comandos úteis

```bash
cd backend
mvn spring-boot:run    # API + CLI
mvn test               # testes unitários
mvn verify             # testes + relatório JaCoCo + gate ≥ 70%
```

Relatório local: `backend/target/site/jacoco/index.html`  
Evidência versionada: [cobertura/](cobertura/).

## CI

Workflow **Testes e cobertura** (`.github/workflows/testes.yml`):

- Dispara em `push`/`pull_request` na `main`
- Executa `mvn -B verify` em `backend/`
- Anexa artefato `jacoco-report` na execução do Actions

## Fluxo de trabalho (equipe)

1. Issue no GitHub com escopo claro
2. Branch `issue-N-descricao` a partir da `main`
3. Commits convencionais; corpo com `Closes #N` quando fechar a issue
4. Pull request → review → merge na `main`

Planejamento interno (ordem de tarefas) fica em arquivo local **não versionado** (`ORDEM-EQUIPE.md`).

## Estrutura de testes

```
backend/src/test/java/org/boligon/
├── cidadao/          # (conforme forem criados)
├── solicitacao/      # domínio, service, controller
└── cli/
```

Prioridade: serviços e regras de negócio; controllers com MockMvc conforme issues.

## Onde documentar mudanças

| Tipo de mudança | Atualizar |
|-----------------|-----------|
| Novo endpoint ou DTO | `docs/api.md` + Swagger (anotações) |
| Nova collection/campo | `docs/arquitetura.md` |
| Novo módulo (ex.: frontend) | `docs/entregas.md` + README raiz (só resumo) |
| Cobertura / CI | `docs/cobertura/evidencia-cobertura.md` se a métrica for registrada de novo |
