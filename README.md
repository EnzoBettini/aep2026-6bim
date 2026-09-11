# ObservaAção

PoC da AEP 2026.2 — Engenharia de Software.

Cidadãos registram problemas urbanos (iluminação, buracos, lixo e poda), alinhado à **ODS 11 — Cidades e Comunidades Sustentáveis**.

## Escopo desta versão

- Backend Java (Spring Boot)
- CLI no terminal com CRUD
- MongoDB NoSQL local, uma coleção: `solicitacoes`
- Sem frontend
- Sem autenticação

## Como executar

Requisitos: Java 17 e MongoDB Community Server na porta 27017.

Instala o Mongo local: https://www.mongodb.com/try/download/community

No Windows, no instalador, deixa marcado **Install MongoDB as a Service**. Ele sobe sozinho no boot, em `localhost:27017`.

Opcional: MongoDB Compass, para ver a collection `solicitacoes`.

A conexão fica em `application.properties`:

`spring.data.mongodb.uri=mongodb://localhost:27017/observacao`


```bash
cd backend
mvn spring-boot:run
```

O menu do CLI aparece no terminal:

1. Cadastrar solicitação
2. Listar solicitações
3. Buscar por ID
4. Atualizar solicitação
5. Excluir solicitação
0. Sair

A API continua disponível em paralelo:

- API: http://localhost:8080/api/solicitacoes
- Swagger: http://localhost:8080/swagger-ui.html

## Exemplo de documento

```json
{
  "id": "68c1f2a8b4d91e3a7c0e9f12",
  "titulo": "Poste apagado na Rua A",
  "descricao": "Poste sem iluminação há três noites.",
  "bairro": "Centro",
  "categoria": "ILUMINACAO",
  "status": "ABERTA"
}
```

Categorias: `ILUMINACAO`, `BURACO`, `LIXO`, `PODA`  
Status: `ABERTA`, `EM_ANDAMENTO`, `RESOLVIDA`
