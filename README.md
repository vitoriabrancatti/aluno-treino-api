# API de Alunos e Treinos

Aplicação REST desenvolvida **exclusivamente com recursos do JDK**, seguindo rigorosamente os requisitos do professor: Java puro (JDK 17), servidor HTTP nativo, JDBC e banco de dados **H2 embarcado** em modo arquivo. O projeto demonstra de forma clara **abstração, encapsulamento, herança, polimorfismo, modularização e baixo acoplamento**.

---

## Objetivo

Implementar um CRUD completo de **Alunos** e **Treinos**, onde:

* Um aluno pode possuir vários treinos (**relação 1..N**).
* A API expõe endpoints REST construídos manualmente sobre `com.sun.net.httpserver.HttpServer`.
* Toda a persistência é feita via JDBC diretamente no H2.

---

## Tecnologias Utilizadas

* **Java 17 (OpenJDK)**
* **Maven** (gestão de dependências e build)
* **Servidor HTTP nativo** (`com.sun.net.httpserver.HttpServer`)
* **H2 Database** (modo arquivo – embedded)
* **Jackson** (serialização/deserialização JSON)
* **JDBC puro**

---

## Como Compilar e Executar

1. Certifique‑se de possuir **Java 17+** e **Maven** instalados.

2. Na pasta raiz do projeto, execute:

```bash
mvn clean package
```

3. Rode o servidor usando o jar empacotado com dependências:

```bash
java -jar target/aluno-treino-api-1.0.0-jar-with-dependencies.jar
```

4. O servidor será iniciado em:

```
http://localhost:8080
```

**Banco de dados**: criado automaticamente no primeiro uso em:

```
./data/treinos.mv.db
```

---

## Endpoints da API

### Alunos

#### `POST /alunos`

Corpo:

```json
{ "nome": "...", "cpf": "...", "email": "...", "idade": 20 }
```

#### `GET /alunos`

Retorna todos os alunos.

#### `GET /alunos/{id}`

Retorna um aluno específico.

#### `PUT /alunos/{id}`

Corpo igual ao POST.

#### `DELETE /alunos/{id}`

Remove o aluno.

---

### Treinos

#### `POST /treinos`

Corpo:

```json
{
  "descricao": "Corrida",
  "data": "2024-05-05",
  "duracaoMinutos": 60,
  "nivel": "intermediario",
  "alunoId": 1
}
```

#### `GET /treinos`

Parâmetro opcional:

```
/treinos?alunoId=1
```

#### `GET /treinos/{id}`

#### `PUT /treinos/{id}`

Mesmo formato do POST.

#### `DELETE /treinos/{id}`

---

## Estrutura do Projeto

```
src/
 ├─ domain/
 │   ├─ Pessoa.java          → Classe base abstrata
 │   ├─ Aluno.java           → Herda de Pessoa
 │   └─ Treino.java
 │
 ├─ persistence/
 │   ├─ ConnectionFactory.java
 │   ├─ AlunoRepository.java
 │   └─ TreinoRepository.java
 │
 ├─ service/
 │   ├─ AlunoService.java
 │   └─ TreinoService.java
 │
 ├─ http/
 │   ├─ AlunoHandler.java
 │   ├─ TreinoHandler.java
 │   └─ Router.java
 │
 └─ Main.java (ponto de entrada)
```

Essa organização garante **separação de responsabilidades**, alto **reuso** e **baixo acoplamento**.

---

## Demonstração de Conceitos de POO

* **Abstração:** classe `Pessoa` como modelo geral.
* **Herança:** `Aluno` estende `Pessoa`.
* **Polimorfismo:** métodos sobrescritos e tratamento abstrato em serviços.
* **Encapsulamento:** atributos privados com getters/setters.

---

## Teste Rápido via cURL

Criar aluno:

```bash
curl -X POST http://localhost:8080/alunos -H "Content-Type: application/json" \
  -d '{"nome":"Ana","cpf":"11122233344","email":"ana@email.com","idade":22}'
```

Criar treino:

```bash
curl -X POST http://localhost:8080/treinos -H "Content-Type: application/json" \
  -d '{"descricao":"Corrida","data":"2024-05-10","duracaoMinutos":30,"nivel":"iniciante","alunoId":1}'
```

---
