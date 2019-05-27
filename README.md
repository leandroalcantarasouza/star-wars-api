# Star Wars Api

*Star Wars Api Versão 1.0.0*

  Essa api foi criada para representar um CRUD dos planetas de star wars, utilizando a api do https://swapi.co/ para poder recuperar a aparição dos planetas criados pela mesma.
  
  A seguir são apresentados os endpoints criados para permitir a utilização da api.
  Todos os endpoints requisitam cabecalho `Content-Type` com o valor `application/json`.
  
  1. [Como Rodar](#como rodar) 
  1. [Tratamento de Erros](#tratamento de erros)
  1. [Criação de Planetas](#criação de planetas)
  1. [Atualizar Planeta](#atualizar-planeta)
  1. [Encontrar Planeta Por Nome](#encontrar-planeta-nome)
  1. [Encontrar Planeta por ID](#encontrar-planeta-id)
  1. [Excluir Planeta por ID](#excluir-planeta-id)
  
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

# Criação de Planetas

A criação de planetas se dá através do endpoint `http://host:8080/star-wars/api/v1/planetas`, com o método http `POST`.
É necessário enviar um payload, contendo os campos nome, clima e terreno, sendo todos os campos obrigatórios.

Um exemplo do payload é exibido a a seguir:

```json
{
  "clima": "gelado",
  "nome": "hoth",
  "terreno": "montanhas"
}
```
Caso a chamada ocorra com sucesso, será retornado o código `HTTP 201`, indicando que foi criado um novo planeta.
Tambem será retornado o cabeçalho `location`, indicando o endereço em que se encontra o recurso criado anteriormente.

Requisição com sucesso
```
curl -X POST "http://localhost:8080/star-wars/api/v1/planetas" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"clima\": \"gelado\", \"nome\": \"hoth\", \"terreno\": \"montanhas\"}"
```
Resposta
```json
{
  "id": "5ceb6411a7b11b0006e5def4",
  "terreno": "montanhas",
  "clima": "gelado",
  "nome": "hoth",
  "quantidadeAparicaoFilmes": 1
}
```
```
 content-type: application/json;charset=UTF-8 
 date: Mon, 27 May 2019 04:14:09 GMT 
 location: http://localhost:8080/star-wars/api/v1/planetas/5ceb6411a7b11b0006e5def4 
 transfer-encoding: chunked 
```