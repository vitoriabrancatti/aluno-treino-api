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

# Banco de Dados — H2

### Caminho do arquivo H2

```
C:/Users/Vitória/Repositorios/aluno-treino-api/data
```

### Como acessar o console H2

1. Inicie o projeto (Main.java)
2. Acesse no navegador:

```
http://localhost:8082
```

3. Configure a conexão:

* **JDBC URL**:

```
jdbc:h2:file:~/Repositorios/aluno-treino-api/data/alunos
```

* **User**: sa
* **Password**: (vazio)

---

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
# Exemplos de Requisições — Postman

## **1. Criar Aluno — POST /alunos**

**URL:** [http://localhost:8080/alunos](http://localhost:8080/alunos)

```json
{
  "nome": "Vitória",
  "email": "vitoria@email.com",
  "idade": 23,
  "objetivo": "Hipertrofia"
}
```

## **2. Listar Alunos — GET /alunos**

**Resposta:**

```json
[
  {
    "id": 1,
    "nome": "Vitória",
    "email": "vitoria@email.com",
    "idade": 23,
    "objetivo": "Hipertrofia"
  }
]
```

## **3. Criar Treino — POST /treinos**

**URL:** [http://localhost:8080/treinos](http://localhost:8080/treinos)

```json
{
  "alunoId": 1,
  "descricao": "Agachamento",
  "carga": 40,
  "repeticoes": 12
}
```

## **4. Buscar Treinos — GET /treinos**

**Resposta:**

```json
[
  {
    "id": 1,
    "alunoId": 1,
    "descricao": "Agachamento",
    "carga": 40,
    "repeticoes": 12
  }
]
```

## **5. Atualizar Treino — PUT /treinos/{id}**

```json
{
  "descricao": "Agachamento Livre",
  "carga": 45,
  "repeticoes": 10
}
```

## **6. Deletar Treino — DELETE /treinos/{id}**

Sem body.

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

# Desenho Arquitetural (Camadas)

```
   [HTTP Layer]
        ↓
   Handlers (AlunoHandler, TreinoHandler)
        ↓
   [Service Layer]
        ↓
   AlunoService / TreinoService
        ↓
   [Persistence Layer]
        ↓
   Repositories (SQL + JDBC)
        ↓
   H2 Database (arquivo)
```

* **Handlers**: recebem requisições e chamam os serviços.
* **Services**: aplicam regras de negócio.
* **Repositories**: acessam o banco via JDBC.
* **Domain**: apenas as entidades.
* **Main**: inicia o servidor e registra as rotas.

---

# Diagrama UML das Entidades

```
            Pessoa (abstract)
          ----------------------
          - id: Long
          - nome: String
          - email: String
          ----------------------
                    ↑ extends

                Aluno
          ----------------------
          - idade: int
          - objetivo: String
          ----------------------


                Treino
          ----------------------
          - id: Long
          - alunoId: Long
          - descricao: String
          - carga: int
          - repeticoes: int
          ----------------------
```

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

# Justificativas Técnicas

### Uso do H2 em arquivo

* Não exige instalação de banco externo.
* Fácil de transportar entre máquinas.
* Bom para aprendizado e prototipação.

### Arquitetura em camadas

* Separação clara de responsabilidades.
* Facilita manutenção e evolução.

### JDBC puro

* Ajuda a aprender o "básico do básico" antes de usar frameworks.
* Total controle das queries.

### Handlers manuais para as rotas

* Aproximação didática ao funcionamento interno de frameworks como Spring.

---

# Como Rodar o Projeto em Outra Máquina

1. Baixe o repositório:

```
git clone https://github.com/vitoriabrancatti/aluno-treino-api.git
```

2. Confirme se tem **Java 17+** instalado.

```
java -version
```

3. Crie o diretório do banco caso não exista:

```
mkdir data
```

4. Compile:

```
javac -cp libs/h2.jar -d out src/**/*.java
```

5. Execute:

```
java -cp "out;libs/h2.jar" Main
```

6. Pronto! API disponível em:

```
http://localhost:8080
```

---

# Próximos Passos e Melhorias Futuras

* [ ] Validações mais fortes nos DTOs
* [ ] Retornar erros padronizados (JSON)
* [ ] Paginação para listagem de alunos
* [ ] Relacionamento 1-N exibindo treinos dentro do aluno
* [ ] Testes unitários com JUnit
* [ ] Adicionar logs estruturados
* [ ] Configurar Docker para rodar tudo com um comando

---

# Licença

Projeto livre para uso educacional.

---
