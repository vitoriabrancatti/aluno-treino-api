# API de Alunos e Treinos

Aplicacao REST escrita apenas com recursos do JDK e banco H2 embarcado. O objetivo é expor o CRUD de alunos e seus treinos, demonstrando abstracao, encapsulamento, heranca e polimorfismo.

## Tecnologias
- Java 17
- Maven
- Servidor HTTP nativo (`com.sun.net.httpserver.HttpServer`)
- H2 Database (modo arquivo)
- Jackson para JSON

## Como compilar e executar
1. Certifique-se de ter o Java 17 e o Maven instalados e configurados no PATH.
2. Dentro da pasta do projeto, execute:
   ```bash
   mvn clean package
   ```
3. Rode a aplicacao com o jar empacotado:
   ```bash
   java -jar target/aluno-treino-api-1.0.0-jar-with-dependencies.jar
   ```
4. O servidor ficara disponivel em `http://localhost:8080`.

Os arquivos de banco sao criados automaticamente em `./data/treinos.mv.db` na primeira execucao.

## Endpoints
### Alunos
- `POST /alunos`
  - Corpo: `{ "nome": "...", "cpf": "...", "email": "...", "idade": 20 }`
- `GET /alunos`
- `GET /alunos/{id}`
- `PUT /alunos/{id}`
  - Mesmo formato do POST
- `DELETE /alunos/{id}`

### Treinos
- `POST /treinos`
  - Corpo: `{ "descricao": "...", "data": "2024-05-05", "duracaoMinutos": 60, "nivel": "intermediario", "alunoId": 1 }`
- `GET /treinos`
  - Parametro opcional `alunoId` para filtrar (`/treinos?alunoId=1`).
- `GET /treinos/{id}`
- `PUT /treinos/{id}`
  - Mesmo formato do POST
- `DELETE /treinos/{id}`

## Estrutura
- `domain`: Pessoa, Aluno e Treino garantem heranca e encapsulamento.
- `persistence`: Repositorios fazem o acesso ao H2 via JDBC.
- `service`: Regras de validacao e montagem de DTOs.
- `http`: Handlers REST construidos sobre o servidor HTTP nativo.

## Teste rapido via curl
```bash
curl -X POST http://localhost:8080/alunos -H "Content-Type: application/json" \
  -d '{"nome":"Ana","cpf":"11122233344","email":"ana@email.com","idade":22}'

curl -X POST http://localhost:8080/treinos -H "Content-Type: application/json" \
  -d '{"descricao":"Corrida","data":"2024-05-10","duracaoMinutos":30,"nivel":"iniciante","alunoId":1}'
```
