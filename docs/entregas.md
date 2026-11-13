# Entregas (AEP)

Referência rápida do que já está no repositório e o que vem nas próximas issues.

## Entrega 1 (concluída — tag `entrega-1`)

- CRUD de solicitações (uma collection)
- CLI, API REST, Swagger, MongoDB local
- Testes iniciais + JaCoCo (relatório, sem gate estrito na época)

## Entrega 2 (em andamento)

### Já no `main` / branches mergeadas

- [x] Coleção `cidadaos` + CRUD
- [x] `cidadaoId` em solicitações + listagem por cidadão
- [x] `historicoStatus` embarcado
- [x] `PATCH` status + `GET` histórico
- [x] Refatoração DTOs e pacotes por contexto
- [x] Testes de serviços ampliados

### Em PR / fila (issues GitHub)

- [ ] Gate JaCoCo 70% + GitHub Actions + evidência em `docs/cobertura/` (#15)
- [ ] Ajustes CLI (#10), Swagger (#11)
- [ ] Testes MockMvc (#14)
- [ ] Frontend (#22–#27): listagem, cadastro, detalhe/status/histórico, integração

### Critérios AEP (lembrete)

- Cobertura de testes **≥ 70%** (total do módulo, JaCoCo)
- Pipeline de testes automatizados
- Evolução do modelo NoSQL e da API documentada

Ao fechar cada épico, atualize os checkboxes deste arquivo e, se mudar escopo visível, o [README](../README.md) da raiz.
