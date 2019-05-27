# Star Wars Api

*Star Wars Api Versão 1.0.0*

  Essa api foi criada para representar um CRUD dos planetas de star wars, utilizando a api do https://swapi.co/ para poder recuperar a aparição dos planetas criados pela mesma.
  
  A seguir são apresentados os endpoints criados para permitir a utilização da api.
  Todos os endpoints requisitam cabecalho `Content-Type` com o valor `application/json`.
  
  1. [Como Rodar](#como rodar) 
  1. [Tratamento de Erros](#tratamento de erros)
  1. [Criação de Planetas](#Criação de Planetas)
  1. [Atualização de Planetas](#Atualização de Planetas)
  1. [Exclusão de Planeta por ID](#Exclusão de Planeta por ID)
  1. [Encontrar Planeta por ID](#Encontrar Planeta por ID)
  1. [Listar Todos os Planetas](#Listar Todos os Planetas)
  1. [Encontrar Planeta Por Nome](#Encontrar Planeta Por Nome)

  
# Como Rodar

Para poder rodar subir a aplicação, deve-se executar o maven a partir da pasta raiz, e assim gerar o arquivo .jar. Deve-se possuir um banco de dados mongodb em execução para poder executar os testes que fazem consultas ao banco de dados. \
Após gerado o arquivo jar, pode-se executar a aplicação. Ela foi estruturada para subir em 2 modos:

    * Modo Localhost - Modo padrão, o qual ao executar, espera-se que o banco de dados mongodb esteja conectado em localhost, na porta padrão do mongodb 27017.
    * Modo cloud - Modo ativado ao se criar uma variável de ambiente com o nome APP_PROFILE, com o valor cloud. Nesse modo é necessário criar outras 2 variáveis de ambiente (MONGO_URI e MONGO_PORT), indicando respectivamente o endereço do mongodb e a sua porta.

Pode-se executar o arquivo docker-compose.yml para poder executar ambos os serviços, configurados com o modo cloud.
Para executar deve-se ter instalado o docker-compose e executar o comando docker-compose up na raiz do projeto.\
A aplicação é acessível pela url http://host:8080/star-wars/api, onde host é igual a localhost no modo localhost, ou o endereço de execução do ambiente do docker no modo cloud.\
Na url base (http://host:8080/star-wars/api), encontra-se configurado um ambiente de execução do swagger, o qual poderá ser usado para se interagir com a api.

**[⬆ voltar ao topo](#star wars api)**

# Tratamento De Erros

A api possui algumas validações padrões que são retornadas em caso de erros.
A seguir são apresentadas alguns `códigos http` utilizados na api e a explicação do que se referem. 

* Código 204 - Indicado para dizer que o retorno de uma lista não trouxe nenhum resultado.

* Código 400 - Utilizado para indicar campos obrigatórios que não foram preenchidos.
O retorno do código 400, também vem acompanhado de um objeto descrevendo as causas do erro.
```json
{
  "violations": [
    {
      "field": "clima",
      "message": "must not be blank",
      "rejectedValue": null
    }
  ],
  "error": "Erro de validação."
}
```

* Código 412 - Utilizado para erros de regras de negócio, como o nome de um planeta já estar cadastrado.
O retorno do código 412, vem acompanhado de um objeto descrevendo o que causou o erro.

Exemplo:
```json
{
  "violations": [
    "O nome desse planeta já existe."
  ],
  "error": "Erro de validação."
}
```

* Código 500 - Utilizado para indicar erros irrecuperáveis da api.
O retorno do código 500, vem acompanhado com um ticket, o qual pode ser utilizado para verificar no log a causa da ocorrência do erro.
```json
{
    "ticket": 1558931543738,
    "error": "Erro desconhecido."
}
```

**[⬆ voltar ao topo](#star wars api)**

# Criação de Planetas

A criação de planetas se dá através do endpoint `http://host:8080/star-wars/api/v1/planetas`, com o método http `POST`.
É necessário enviar um payload, contendo os campos nome, clima e terreno, sendo todos os campos obrigatórios.

Um exemplo do payload é exibido a a seguir:

```json
{
  "clima": "montanha",
  "nome": "hoth",
  "terreno": "gelado"
}
```
Caso a chamada ocorra com sucesso, será retornado o código `HTTP 201`, indicando que foi criado um novo planeta.
Tambem será retornado o cabeçalho `location`, indicando o endereço em que se encontra o recurso criado anteriormente.

Requisição com sucesso
```
curl -X POST "http://localhost:8080/star-wars/api/v1/planetas" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"clima\": \"montanha\", \"nome\": \"hoth\", \"terreno\": \"gelado\"}"
```
Resposta
```json
{
  "id": "5ceb6a92a7b11b0007e1d423",
  "terreno": "gelado",
  "clima": "montanha",
  "nome": "hoth",
  "quantidadeAparicaoFilmes": 1
}
```
```
content-type: application/json;charset=UTF-8 
date: Mon, 27 May 2019 04:41:54 GMT 
location: http://localhost:8080/star-wars/api/v1/planetas/5ceb6a92a7b11b0007e1d423 
transfer-encoding: chunked 
```

**[⬆ voltar ao topo](#star wars api)**

# Atualização de Planetas

A atualização de planetas se dá através do endpoint `http://host:8080/star-wars/api/v1/planetas/identificadorDoPlaneta`, com o método http `PATCH`.
É necessário enviar um payload, contendo os campos nome, clima e terreno, sendo todos os campos obrigatórios.

Um exemplo do payload é exibido a a seguir:

```json
{
  "clima": "quente",
  "nome": "Mustafar",
  "terreno": "vulcânico"
}
```
Caso a chamada ocorra com sucesso, será retornado o código `HTTP 200`, indicando que foi atualizado o planeta especificado.

Requisição com sucesso
```
curl -X PATCH "http://localhost:8080/star-wars/api/v1/planetas/5ceb69c4a7b11b0007e1d422" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"clima\": \"quente\", \"nome\": \"Mustafar\", \"terreno\": \"vulcânico\"}"
```
Resposta
```json
{
  "id": "5ceb69c4a7b11b0007e1d422",
  "terreno": "vulcânico",
  "clima": "quente",
  "nome": "Mustafar",
  "quantidadeAparicaoFilmes": 1
}

```
```
content-type: application/json;charset=UTF-8 
 date: Mon, 27 May 2019 04:39:13 GMT 
 transfer-encoding: chunked 
```

**[⬆ voltar ao topo](#star wars api)**

# Exclusão de Planeta por ID

A exclusão de planetas se dá através do endpoint `http://host:8080/star-wars/api/v1/planetas/identificadorDoPlaneta`, com o método http `DELETE`.

Caso a chamada ocorra com sucesso, será retornado o código `HTTP 200`, indicando que o planeta especificado foi excluído .

Requisição com sucesso
```
curl -X DELETE "http://localhost:8080/star-wars/api/v1/planetas/5ceb5bcda7b11b000635aa73" -H "accept: */*"
```
Resposta
```
content-length: 0 
date: Mon, 27 May 2019 04:47:55 GMT 
```

**[⬆ voltar ao topo](#star wars api)**

# Encontrar Planeta por ID

Para encontra um planeta, sabendo o seu identificador, a chamada ocorre através do endpoint `http://host:8080/star-wars/api/v1/planetas/identificadorDoPlaneta`, com o método http `GET`.

Caso a chamada ocorra com sucesso, será retornado o código `HTTP 200`, com a resposta contendo os dados do planeta pesquisado.
Um código `HTTP 404` é retornado, caso o planeta não seja encontrado.

Requisição com sucesso
```
http://localhost:8080/star-wars/api/v1/planetas/5ceb6a92a7b11b0007e1d423
```
Resposta
```json
{
  "id": "5ceb6a92a7b11b0007e1d423",
  "terreno": "gelado",
  "clima": "montanha",
  "nome": "hoth",
  "quantidadeAparicaoFilmes": 1
}

```
```
content-type: application/json;charset=UTF-8 
date: Mon, 27 May 2019 04:52:07 GMT 
transfer-encoding: chunked 
```

**[⬆ voltar ao topo](#star wars api)**

# Listar Todos os Planetas

Para listar todos os planetas cadastrados, deve-se chamar o endpoint `http://host:8080/star-wars/api/v1/planetas`, com o método http `GET`.

A chamada pode contar os campos adicionais:
* numeroPagina - Indica qual página se deseja visualizar os resultados. Deve-se informar um número inteiro positivo maior que 0. Caso não seja especificado, assume-se que a visualização é da 1 página.
* quantidadePorPagina - Indica quantos resultados se deseja visualizar a cada página. Deve-se informar um número inteiro positivo maior que 1. Caso não seja especificado, assume-se que a visualização é da 10 resultados por página.
Caso a chamada ocorra com sucesso, será retornado o código `HTTP 200`, com a resposta contendo os dados do planeta pesquisado, com as suas informações de paginação, caso necessite-se procurar em outras páginas de resultado.\

Requisição com sucesso
```
curl -X GET "http://localhost:8080/star-wars/api/v1/planetas?numeroPagina=0&quantidadePorPagina=15" -H "accept: */*"
```

Resposta
```json 
{
"content": [
 {
   "id": "5ceb5bd9a7b11b000635aa75",
   "terreno": "ice",
   "clima": "novo",
   "nome": "batata",
   "quantidadeAparicaoFilmes": 0
 },
 {
   "id": "5ceb5bdea7b11b000635aa76",
   "terreno": "ice",
   "clima": "novo",
   "nome": "batata2",
   "quantidadeAparicaoFilmes": 0
 },
 {
   "id": "5ceb6a92a7b11b0007e1d423",
   "terreno": "gelado",
   "clima": "montanha",
   "nome": "hoth",
   "quantidadeAparicaoFilmes": 1
 },
 {
   "id": "5ceb5b55a7b11b000635aa71",
   "terreno": "frio",
   "clima": "gelado",
   "nome": "naboo",
   "quantidadeAparicaoFilmes": 4
 }
],
"pageable": {
 "sort": {
   "sorted": false,
   "unsorted": true,
   "empty": true
 },
 "pageSize": 15,
 "pageNumber": 0,
 "offset": 0,
 "paged": true,
 "unpaged": false
},
"last": true,
"totalElements": 4,
"totalPages": 1,
"first": true,
"sort": {
 "sorted": false,
 "unsorted": true,
 "empty": true
},
"numberOfElements": 4,
"size": 15,
"number": 0,
"empty": false
}
```
```
content-type: application/json;charset=UTF-8 
date: Mon, 27 May 2019 05:03:36 GMT 
transfer-encoding: chunked 
```
**[⬆ voltar ao topo](#star wars api)**

# Encontrar Planeta Por Nome

Para procurar planetas cadastrados pelo seu nome, deve-se chamar o endpoint `http://host:8080/star-wars/api/v1/planetas`, com o método http `GET` e adicionar o campo opcional:

* nome - Indica o termo de busca pelo nome que se deseja encontrar.

Igual a chamada à todos os planetas, a chamada por nome também permite passar os campos adicionais de `númeroPagina` e `quantidadePorPagina`, se utilizando das mesmas regras caso não sejam informados. 

Caso a chamada ocorra com sucesso, será retornado o código `HTTP 200`, com a resposta contendo os dados do planeta pesquisado, com as suas informações de paginação, caso necessite-se procurar em outras páginas de resultado.\

Requisição com sucesso
```
curl -X GET "http://localhost:8080/star-wars/api/v1/planetas?nome=batata" -H "accept: */*"
```

Resposta
```json 
{
  "content": [
    {
      "id": "5ceb5bd9a7b11b000635aa75",
      "terreno": "ice",
      "clima": "novo",
      "nome": "batata",
      "quantidadeAparicaoFilmes": 0
    },
    {
      "id": "5ceb5bdea7b11b000635aa76",
      "terreno": "ice",
      "clima": "novo",
      "nome": "batata2",
      "quantidadeAparicaoFilmes": 0
    }
  ],
  "pageable": {
    "sort": {
      "sorted": false,
      "unsorted": true,
      "empty": true
    },
    "pageSize": 10,
    "pageNumber": 0,
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "last": true,
  "totalElements": 2,
  "totalPages": 1,
  "first": true,
  "sort": {
    "sorted": false,
    "unsorted": true,
    "empty": true
  },
  "numberOfElements": 2,
  "size": 10,
  "number": 0,
  "empty": false
}
```
```
content-type: application/json;charset=UTF-8 
date: Mon, 27 May 2019 05:10:27 GMT 
transfer-encoding: chunked 
```