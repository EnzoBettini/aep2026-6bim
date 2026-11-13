# Evidência de cobertura JaCoCo

Gerado após `mvn verify` no módulo `backend`.

| Métrica | Valor |
|---------|------:|
| Instruções cobertas | 1157 |
| Instruções não cobertas | 256 |
| **Cobertura (instruções)** | **81.9%** |
| Mínimo exigido (AEP) | 70% |

## Como reproduzir

```bash
cd backend
mvn verify
```

Relatório HTML: `backend/target/site/jacoco/index.html`

## Captura de tela

![Relatório JaCoCo](cobertura-instrucoes.png)
