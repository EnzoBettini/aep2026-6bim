# Cobertura de testes (JaCoCo)

- **[evidencia-cobertura.md](evidencia-cobertura.md)** — métricas da última medição versionada
- **cobertura-instrucoes.png** — captura do relatório (`Total` do bundle)

Regenerar após mudanças relevantes nos testes:

```bash
cd backend && mvn verify
# atualizar PNG a partir de target/site/jacoco/index.html
```

O gate no `pom.xml` usa **cobertura total de instruções ≥ 70%** (`mvn verify`), não o percentual de cada pacote na tabela.

Arquivo legado `docs/cobertura-jacoco.png` (entrega 1) foi removido — use apenas esta pasta.
