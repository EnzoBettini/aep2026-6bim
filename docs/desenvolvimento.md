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

1. Pegar uma issue no GitHub (escopo e ordem em `ORDEM-EQUIPE.md`, local)
2. Trabalhar em cima da **`main`** atualizada (`git pull`)
3. Commit convencional na `main`; **no corpo do commit**, `Closes #N` para fechar a issue automaticamente
4. `git push origin main` — o CI roda no push

Pull requests continuam opcionais (revisão em dupla), mas **não são obrigatórios** nesta fase do projeto.

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
