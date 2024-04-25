# Desafio Backend Picpay

Esse repositório tem como objetivo apresentar uma solução para o desafio Backend Picpay Simplificado

## Objetivo: PicPay Simplificado

O PicPay Simplificado é uma plataforma de pagamentos simplificada. Nela é possível depositar e realizar transferências de dinheiro entre usuários. Temos 2 tipos de usuários, os comuns e lojistas, ambos têm carteira com dinheiro e realizam transferências entre eles.

### Requisitos
A seguir estão algumas regras de negócio que são importantes para o funcionamento do PicPay Simplificado:

- Para ambos tipos de usuário, precisamos do Nome Completo, CPF, e-mail e Senha. CPF/CNPJ e e-mails devem ser únicos no sistema. Sendo assim, seu sistema deve permitir apenas um cadastro com o mesmo CPF ou endereço de e-mail;


- Usuários podem enviar dinheiro (efetuar transferência) para lojistas e entre usuários;


- Lojistas só recebem transferências, não enviam dinheiro para ninguém;


- Validar se o usuário tem saldo antes da transferência;

- Antes de finalizar a transferência, deve-se consultar um serviço autorizador externo, use este mock para simular (https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc);


- A operação de transferência deve ser uma transação (ou seja, revertida em qualquer caso de inconsistência) e o dinheiro deve voltar para a carteira do usuário que envia;


- No recebimento de pagamento, o usuário ou lojista precisa receber notificação (envio de email, sms) enviada por um serviço de terceiro e eventualmente este serviço pode estar indisponível/instável. Use este mock para simular o envio (https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6);


- Este serviço deve ser RESTFul.



## Stack utilizada

**Tecnologias:** Java, Spring (Boot, Data, MVC, ), H2, Lombok, JUnit5 e Mockito


## Documentação da API

#### Cadstra um novo cliente

```http
  POST /users
```
Body
```bash
  {
    "fullname": "John Frusciante",
    "document": "435.543.523-34",
    "email": "john1@gmail.com",
    "password": "john123",
    "balance": 1000,
    "userRole": "COMMON"
  }
```

#### Realiza uma nova transferência

```http
  POST /transaction
```

Body
```bash
  {
    "amount": 500,
    "sender": 1,
    "receiver": 3
  } 
```

## Rodando o projeto

Clone o projeto e execute os seguintes comandos

```bash
  ./mvnw clean package
```
```bash
  java -jar target/simplificado-0.0.1-SNAPSHOT.jar
```