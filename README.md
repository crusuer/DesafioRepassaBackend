# Desafio Repassa
Este é um projeto criado como solução para o desafio da entrevista de desenvolvedor Full Stack Repassa.
Neste serviço é possível:
##### ADMIN
* Adicionar/Remover/Atualizar/Ver Empregados
* Adicionar/Remover/Atualizar/Ver Avaliações de Desempenho
* Atribuir funcionários para participarem da revisão de desempenho de outro funcionário
##### FUNCIONÁRIO
* Listar Avaliações de desempenho que precisam de seu feedback
* Enviar feedback

# Setup da Aplicação (Local)

## Pré-requisitos
Antes de iniciar a aplicação, você deve garantir que as seguintes dependencias estão instaladas:

```
Java 11
Maven 3.2.2
Git
```

Você deve prover as seguintes variáveis de ambiente:
 - spring.datasource.platform
 - spring.datasource.url
 - spring.datasource.username
 - spring.datasource.password
 - spring.jpa.properties.hibernate.dialect
 - spring.jpa.properties.hibernate.temp.use_jdbc_metadata_defaults

## Instalação
Baixe o projeto:
```
git clone https://github.com/crusuer/DesafioRepassaBackend.git
```
Acesse o projeto:
```
cd [diretorio_do_projeto]
```
É necessário compilar o código e baixar as dependencias do projeto:
```
mvn clean package
```
Após isso, inicie a aplicação:
```
mvn spring-boot:run
```
# Endpoints da API
Coleção com todas as chamadas da API no Postman: <p>
[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/6d560911fe9badcda14d)
- Se quiser chamar a API localmente, considere `HOST` como *http://localhost:8080*
- Se quiser chamar a API publicada na Nuvem, considere `HOST` como *https://desafio-repassa.herokuapp.com*

## ADMIN
### GET
`HOST`[/admin/employees](#get-admin-employees) <br/>
`HOST`[/admin/employees/{id}](#get-admin-employees-id) <br/>
`HOST`[/admin/evaluations](#get-admin-evaluations) <br/>
`HOST`[/admin/evaluations/{id}](#get-admin-evaluations-id) <br/>

### POST
`HOST`[/admin/employees](#get-admin-employees) <br/>
`HOST`[/admin/evaluations](#get-admin-evaluations) <br/>
`HOST`[/admin/assign](#get-admin-assign) <br/>

### PUT
`HOST`[/admin/employees/{id}](#get-admin-employees-id) <br/>
`HOST`[/admin/evaluations/{id}](#get-admin-evaluations-id) <br/>

### DELETE
`HOST`[/admin/employees/{id}](#get-admin-employees-id) <br/>
`HOST`[/admin/evaluations/{id}](#get-admin-evaluations-id) <br/>
<br/>
## FUNCIONÁRIO
### GET
`HOST`[/user/evaluations](#get-user-evaluations) <br/>
### PUT
`HOST`[/user/evaluations/{id}](#put-user-evaluations-id) <br/>
___
Para ver a documentação da servico no SWAGGER, acesse `HOST`[/swagger-ui.html](#get-swagger-ui)