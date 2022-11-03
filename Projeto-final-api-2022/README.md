# Projeto-Final-API

Criar uma API de ECommerce utilizando a base de dados do ecommerce da disciplina de 
banco de dados.

- Utilizar o padrão de projeto MVC e DTO
- Criar as entidades (Model)
- Criar os serviços (Service)
- Criar os recursos (Controller)

## Funcionalidades/Requisitos:

- Inserir e editar uma categoria
- Inserir e editar um produto
- Ao inserir um novo produto, obrigatoriamente deverá estar atrelado a uma 
categoria
- Ao listar os produtos, deverá exibir a categoria referente a esse produto
- Inserir e editar um cliente
- Ao inserir um cliente deverá preencher o cep e consultar no serviço 
externo do viacep 
- Ao inserir/alterar um registro de cliente deverá enviar um e-mail para o 
mesmo informando 
- Inserir e editar um pedido
- Ao inserir um novo pedido, obrigatoriamente deverá estar atrelado a um 
cliente
- O pedido deverá ter um status (Finalizado e não finalizado) e poderá ser 
alterado 
- Listar um determinado pedido pelo número do pedido totalizando o pedido

## Validações:
#### Cliente: 
- Email
- CPF
- Caso utilize Enum, crie uma validação
- Case tenha necessidade, crie uma classe de exception para tratamento de exceção 
específica

## Tratamento de exceções:

- Criar uma classe com anotação @ControllerAdvice para tratar o retorno de erro da 
API

## Serviço externo:

- https://viacep.com.br/ws/{cep}/json/

## Documentação:

- Utilizar o Swagger para documentação

## Tecnologias utilizadas:

- Projeto deverá ser criado utilizando java com spring boot

## 👨‍💻 Autores 👩‍💻
- [Anderson Andrade](https://github.com/Afa1908)
- [Graziela Falk](https://github.com/grazifalk)
- [Priscila Almeida](https://github.com/Priscila-M)
- [Quezia Menezes](https://github.com/QueziaMenezes)
- [Vinicius Polletti](https://github.com/ViniciusPolletti)
