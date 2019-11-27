# Desafio Repassa
Este é um projeto criado como solução para o desafio da entrevista de desenvolvedor Full Stack Repassa.


# Setup da Aplicação (Local)

## Pré-requisitos
Antes de iniciar a aplicação, você deve garantir que as seguintes dependencias estão instaladas:

```
Java 11
Maven 3.2.2
Git
```

You also need to provide the following environment properties:
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
These endpoints allow you to manage places. 
- If you are running the app locally, you can consider `HOST` como *http://localhost:8080*
- If you want the Cloud app, you can consider `HOST` as *https://desafio-repassa.herokuapp.com*

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
To check each endpoint parameters and responses, go to `HOST`/swagger-ui.html