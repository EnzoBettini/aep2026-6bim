# ObservaAção

PoC da AEP 2026.2 — Engenharia de Software.

Cidadãos registram problemas urbanos (iluminação, buracos, lixo e poda), alinhado à **ODS 11 — Cidades e Comunidades Sustentáveis**.

## Escopo desta versão

- Backend Java (Spring Boot)
- Uma entidade simples: `Solicitacao`
- CRUD básico
- Sem frontend
- Sem autenticação

## Como executar

Requisito: Java 17.

```bash
cd backend
mvn spring-boot:run
```

- API: http://localhost:8080/api/solicitacoes
- Swagger: http://localhost:8080/swagger-ui.html
- H2 Console: http://localhost:8080/h2-console  
  JDBC URL: `jdbc:h2:file:./data/observacao_db`

## Exemplo de cadastro

```json
{
  "titulo": "Poste apagado na Rua A",
  "descricao": "Poste sem iluminação há três noites.",
  "bairro": "Centro",
  "categoria": "ILUMINACAO"
}
```

Categorias: `ILUMINACAO`, `BURACO`, `LIXO`, `PODA`  
Status: `ABERTA`, `EM_ANDAMENTO`, `RESOLVIDA`
