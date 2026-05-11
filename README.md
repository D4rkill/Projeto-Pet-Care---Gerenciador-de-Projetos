
# 🧩 AC2 - Sistema de Projetos + Plataforma PetCare

Projeto desenvolvido para a disciplina de **Desenvolvimento Web Back-End**, do curso de **Análise e Desenvolvimento de Sistemas (ADS)** da **UniFacens**.

A aplicação foi construída com **Spring Boot**, aplicando arquitetura em camadas, API REST, persistência com JPA, banco H2, regras de negócio em camada Service, tratamento global de erros e frontend simples para demonstração em sala.


## 🎯 Objetivo do Projeto

O objetivo deste projeto é desenvolver e apresentar dois sistemas backend completos e integrados, demonstrando conhecimento em:

- Arquitetura em camadas
- API REST com Spring Boot
- Persistência com Spring Data JPA
- Banco de dados H2
- Relacionamentos entre entidades
- Regras de negócio reais
- Tratamento de erros
- Integração com frontend simples
- Testes via navegador, frontend ou Postman

---

# 📌 Estrutura Geral

O projeto é dividido em duas partes principais:

```txt
Parte 1 - Sistema de Controle de Projetos
Parte 2 - Plataforma PetCare
````

Cada parte segue a mesma organização em camadas:

```txt
Controller → Service → Repository → Entity → Banco de Dados
```

---

# 🧱 Tecnologias Utilizadas

* Java 17
* Spring Boot 3.3.5
* Spring Web
* Spring Data JPA
* Spring Validation
* H2 Database
* Lombok
* HTML
* CSS
* JavaScript
* Maven

---

# 📂 Estrutura de Pacotes

```txt
src/main/java/com/facens/ac2
├── Ac2Application.java
├── config
│   └── DataInitializer.java
├── exception
│   ├── ApiError.java
│   ├── BusinessException.java
│   ├── GlobalExceptionHandler.java
│   └── ResourceNotFoundException.java
├── projetos
│   ├── controller
│   ├── dto
│   ├── entity
│   ├── repository
│   └── service
└── petcare
    ├── controller
    ├── dto
    ├── entity
    ├── repository
    └── service
```

Frontend:

```txt
src/main/resources/static
├── index.html
├── styles.css
└── app.js
```

---

# 🔹 Parte 1 - Sistema de Controle de Projetos

## Objetivo

Criar uma API REST para gerenciamento de:

* Projetos
* Funcionários
* Setores
* Vínculos entre funcionários e projetos

---

## Entidades Principais

### Setor

Representa o setor/departamento da empresa.

Relacionamento:

```txt
Um setor possui vários funcionários.
Um funcionário pertence a um setor.
```

---

### Funcionário

Representa um funcionário da empresa.

Relacionamentos:

```txt
Um funcionário pertence a um setor.
Um funcionário pode participar de vários projetos.
```

---

### Projeto

Representa um projeto da empresa.

Relacionamento:

```txt
Um projeto pode ter vários funcionários.
Um funcionário pode participar de vários projetos.
```

Esse relacionamento é implementado com `@ManyToMany`.

---

## Relacionamentos da Parte 1

```txt
Setor 1:N Funcionário
Funcionário N:N Projeto
Projeto N:N Funcionário
```

---

## Regras de Negócio da Parte 1

As regras ficam concentradas na camada `Service`.

Exemplos:

* Não permitir cadastro de setor com nome duplicado.
* Não permitir projeto com data de início posterior à data final.
* Não permitir vínculo duplicado entre funcionário e projeto.
* Validar se setor existe antes de cadastrar funcionário.
* Validar se funcionário existe antes de vincular ao projeto.
* Validar se projeto existe antes de consultar ou vincular.

---

## Endpoints da Parte 1

### Setores

```http
POST /api/setores
GET  /api/setores
GET  /api/setores/{id}
```

### Funcionários

```http
POST /api/funcionarios
GET  /api/funcionarios
GET  /api/funcionarios/{id}/projetos
```

### Projetos

```http
POST /api/projetos
GET  /api/projetos
GET  /api/projetos/{id}
GET  /api/projetos/periodo?inicio=2026-05-01&fim=2026-06-30
POST /api/projetos/{projetoId}/funcionarios/{funcionarioId}
```

---

## Exemplos de Teste - Parte 1

### Cadastrar Setor

```http
POST http://localhost:8080/api/setores
```

```json
{
  "nome": "Tecnologia"
}
```

---

### Cadastrar Funcionário

```http
POST http://localhost:8080/api/funcionarios
```

```json
{
  "nome": "William Oliveira",
  "setorId": 1
}
```

---

### Cadastrar Projeto

```http
POST http://localhost:8080/api/projetos
```

```json
{
  "descricao": "Sistema Interno de Gestão",
  "dataInicio": "2026-05-20",
  "dataFim": "2026-06-20"
}
```

---

### Vincular Funcionário ao Projeto

```http
POST http://localhost:8080/api/projetos/1/funcionarios/1
```

---

### Buscar Projeto com Funcionários

```http
GET http://localhost:8080/api/projetos/1
```

---

# 🔹 Parte 2 - Plataforma PetCare

## Objetivo

Expandir os conceitos da Parte 1 para um sistema mais completo, simulando uma plataforma de atendimento veterinário.

A plataforma permite:

* Cadastro de tutores
* Cadastro de animais
* Cadastro de veterinários
* Agendamento de consultas
* Registro de prontuário
* Cadastro de vacinas
* Registro de vacinação

---

## Entidades Principais

### Tutor

Representa o responsável pelo animal.

Relacionamento:

```txt
Um tutor pode ter vários animais.
Um animal pertence a um tutor.
```

---

### Animal

Representa o pet cadastrado no sistema.

Relacionamentos:

```txt
Um animal pertence a um tutor.
Um animal pode ter várias consultas.
Um animal pode ter vários prontuários.
Um animal pode ter vários registros de vacinação.
```

---

### Veterinário

Representa o profissional responsável pelo atendimento.

Relacionamento:

```txt
Um veterinário pode atender várias consultas.
Um veterinário possui uma especialidade.
```

---

### Consulta

Representa um atendimento agendado.

Relacionamentos:

```txt
Uma consulta pertence a um animal.
Uma consulta pertence a um veterinário.
Uma consulta possui uma especialidade solicitada.
```

---

### Prontuário

Representa o histórico clínico do animal.

Contém:

* Diagnóstico
* Tratamento
* Observações
* Animal
* Veterinário
* Consulta relacionada, quando houver

---

### Vacina

Representa uma vacina disponível para aplicação.

Contém:

* Nome
* Fabricante
* Intervalo de reforço em meses

---

### Registro de Vacinação

Representa uma vacina aplicada em um animal.

Contém:

* Animal
* Vacina
* Veterinário
* Data de aplicação
* Próxima data de reforço
* Observação

---

## Regras de Negócio da Parte 2

As regras ficam concentradas na camada `Service`.

### Associação Tutor ↔ Animal

Não é permitido cadastrar um animal sem tutor válido.

```txt
Antes de cadastrar o animal, o sistema verifica se o tutor existe.
```

---

### Conflito de Agenda

Não é permitido agendar duas consultas para o mesmo veterinário no mesmo horário, desde que a consulta anterior não esteja cancelada.

```txt
Se o veterinário já tiver consulta ativa naquele horário, o sistema bloqueia o agendamento.
```

---

### Especialidade do Veterinário

O veterinário só pode atender consultas da sua própria especialidade.

Exemplo:

```txt
Um veterinário de Dermatologia não pode atender uma consulta de Cardiologia.
```

---

### Registro de Prontuário

O prontuário só pode ser vinculado a uma consulta concluída.

Também são validados:

* Animal da consulta
* Veterinário da consulta
* Se a consulta já possui prontuário

---

### Registro de Vacinação

A vacinação só pode ser registrada por veterinários com especialidade:

```txt
VACINACAO
CLINICA_GERAL
```

O sistema calcula automaticamente a próxima data de reforço com base no intervalo configurado na vacina.

---

## Endpoints da Parte 2

### Tutores

```http
POST /api/petcare/tutores
GET  /api/petcare/tutores
GET  /api/petcare/tutores/{id}
```

### Animais

```http
POST /api/petcare/animais
GET  /api/petcare/animais
GET  /api/petcare/animais/{id}
GET  /api/petcare/animais/tutor/{tutorId}
```

### Veterinários

```http
POST /api/petcare/veterinarios
GET  /api/petcare/veterinarios
GET  /api/petcare/veterinarios/{id}
```

### Consultas

```http
POST  /api/petcare/consultas
GET   /api/petcare/consultas
GET   /api/petcare/consultas/{id}
GET   /api/petcare/consultas/animal/{animalId}
GET   /api/petcare/consultas/veterinario/{veterinarioId}
PATCH /api/petcare/consultas/{id}/concluir
PATCH /api/petcare/consultas/{id}/cancelar
```

### Prontuários

```http
POST /api/petcare/prontuarios
GET  /api/petcare/prontuarios
GET  /api/petcare/prontuarios/animal/{animalId}
```

### Vacinas

```http
POST /api/petcare/vacinas
GET  /api/petcare/vacinas
```

### Vacinações

```http
POST /api/petcare/vacinacoes
GET  /api/petcare/vacinacoes
GET  /api/petcare/vacinacoes/animal/{animalId}
```

---

## Exemplos de Teste - PetCare

### Cadastrar Tutor

```http
POST http://localhost:8080/api/petcare/tutores
```

```json
{
  "nome": "Carlos Tutor",
  "email": "carlos@email.com",
  "telefone": "15999990000"
}
```

---

### Cadastrar Animal

```http
POST http://localhost:8080/api/petcare/animais
```

```json
{
  "nome": "Rex",
  "especie": "Cachorro",
  "raca": "Golden Retriever",
  "idade": 5,
  "porte": "GRANDE",
  "tutorId": 1
}
```

---

### Cadastrar Veterinário

```http
POST http://localhost:8080/api/petcare/veterinarios
```

```json
{
  "nome": "Dra. Ana",
  "crmv": "CRMV-SP-12345",
  "especialidade": "CLINICA_GERAL"
}
```

---

### Agendar Consulta

```http
POST http://localhost:8080/api/petcare/consultas
```

```json
{
  "dataHora": "2026-05-20T14:00:00",
  "especialidadeConsulta": "CLINICA_GERAL",
  "observacao": "Consulta de rotina",
  "animalId": 1,
  "veterinarioId": 1
}
```

---

### Concluir Consulta

```http
PATCH http://localhost:8080/api/petcare/consultas/1/concluir
```

---

### Registrar Prontuário

```http
POST http://localhost:8080/api/petcare/prontuarios
```

```json
{
  "animalId": 1,
  "veterinarioId": 1,
  "consultaId": 1,
  "diagnostico": "Animal saudável, sem alterações clínicas relevantes.",
  "tratamento": "Manter alimentação balanceada e rotina de exercícios.",
  "observacoes": "Retorno recomendado em 6 meses."
}
```

---

### Cadastrar Vacina

```http
POST http://localhost:8080/api/petcare/vacinas
```

```json
{
  "nome": "V10",
  "fabricante": "PetVac",
  "intervaloReforcoMeses": 12
}
```

---

### Registrar Vacinação

```http
POST http://localhost:8080/api/petcare/vacinacoes
```

```json
{
  "dataAplicacao": "2026-05-10",
  "observacao": "Primeira dose aplicada sem reação.",
  "animalId": 1,
  "vacinaId": 1,
  "veterinarioId": 3
}
```

---

# ⚠️ Exemplos de Regras Bloqueadas

## Conflito de Agenda

Ao tentar cadastrar duas consultas para o mesmo veterinário no mesmo horário, o sistema retorna erro:

```json
{
  "error": "Regra de negócio",
  "message": "Veterinário já possui consulta nesse horário."
}
```

---

## Especialidade Incorreta

Ao tentar agendar uma consulta de especialidade diferente da especialidade do veterinário, o sistema retorna erro:

```json
{
  "error": "Regra de negócio",
  "message": "Veterinário não atende a especialidade solicitada."
}
```

---

# 🌐 Frontend

O projeto possui um frontend simples em HTML, CSS e JavaScript, servido diretamente pelo Spring Boot.

Para acessar:

```txt
http://localhost:8080
```

O frontend permite demonstrar:

* Cadastro de setor
* Cadastro de funcionário
* Cadastro de projeto
* Vínculo entre funcionário e projeto
* Listagem de projetos
* Cadastro de tutor
* Cadastro de animal
* Cadastro de veterinário
* Agendamento de consulta
* Listagem de consultas
* Teste de conflito de agenda
* Teste de especialidade incorreta

---

# 🗃️ Banco de Dados

O projeto utiliza banco H2 em memória.

Console H2:

```txt
http://localhost:8080/h2-console
```

Configuração:

```txt
JDBC URL: jdbc:h2:mem:ac2db
User: sa
Password: deixe vazio
```

---

# ⚙️ Como Executar o Projeto

## 1. Clonar ou abrir o projeto

Abra o projeto no VS Code, IntelliJ IDEA ou Eclipse.

---

## 2. Instalar dependências

O Maven baixa as dependências automaticamente.

---

## 3. Rodar o projeto

No terminal, execute:

```bash
mvnw.cmd spring-boot:run
```

Em Linux ou macOS:

```bash
./mvnw spring-boot:run
```

---

## 4. Acessar o frontend

```txt
http://localhost:8080
```

---

## 5. Acessar o H2 Console

```txt
http://localhost:8080/h2-console
```

---

# 🚀 Massa Inicial de Dados

O projeto possui a classe `DataInitializer`, que cria dados automaticamente ao iniciar a aplicação.

São criados automaticamente:

* Setores
* Funcionários
* Projetos
* Vínculos entre funcionários e projetos
* Tutores
* Animais
* Veterinários
* Consultas
* Vacinas
* Registros de vacinação

Isso facilita a demonstração em sala, pois o sistema já inicia com dados prontos para teste.

---

# 🧠 Arquitetura do Projeto

## Controller

Responsável por receber as requisições HTTP e chamar a camada Service.

Exemplo:

```txt
POST /api/projetos
```

O Controller recebe o JSON, valida o DTO e encaminha para a Service.

---

## Service

Responsável pelas regras de negócio.

Exemplos:

* Validar datas de projeto
* Bloquear vínculo duplicado
* Bloquear conflito de agenda
* Validar especialidade do veterinário
* Validar tutor antes de cadastrar animal
* Validar consulta antes de gerar prontuário

---

## Repository

Responsável pela comunicação com o banco de dados usando Spring Data JPA.

Exemplo:

```java
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
}
```

---

## Entity

Representa as tabelas do banco de dados.

Exemplo:

```java
@Entity
@Table(name = "projetos")
public class Projeto {
}
```

---

## DTO

Responsável por organizar os dados de entrada e saída da API.

Exemplos:

```txt
CreateProjetoRequest
ProjetoResponse
CreateConsultaRequest
ConsultaResponse
```

---

## Exception Handler

O projeto possui tratamento global de erros com `@RestControllerAdvice`.

Isso permite retornar erros padronizados em JSON.

Exemplo:

```json
{
  "timestamp": "2026-05-10T20:00:00",
  "status": 400,
  "error": "Regra de negócio",
  "message": "Veterinário já possui consulta nesse horário.",
  "path": "/api/petcare/consultas",
  "fields": null
}
```

---

# ✅ Funcionalidades Implementadas

## Sistema de Projetos

* Cadastro de setor
* Consulta de setor
* Listagem de setores com funcionários
* Cadastro de funcionário
* Listagem de funcionários
* Cadastro de projeto
* Listagem de projetos
* Busca de projeto por ID com funcionários
* Busca de projetos por período
* Busca de projetos por funcionário
* Vínculo funcionário ↔ projeto

---

## Plataforma PetCare

* Cadastro de tutor
* Listagem de tutores
* Cadastro de animal
* Listagem de animais
* Consulta de animais por tutor
* Cadastro de veterinário
* Listagem de veterinários
* Agendamento de consulta
* Listagem de consultas
* Consulta por animal
* Consulta por veterinário
* Conclusão de consulta
* Cancelamento de consulta
* Registro de prontuário
* Histórico de prontuários por animal
* Cadastro de vacina
* Registro de vacinação
* Histórico de vacinação por animal
* Cálculo automático da próxima dose de reforço

---

# 📌 Observação

Este projeto foi desenvolvido com fins acadêmicos para demonstrar domínio de arquitetura em camadas, API REST, JPA, regras de negócio, tratamento de erros e integração com frontend.

```
```
