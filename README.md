# Transaction
Projeto para rotina de transações financeira

# Funcionalidades

Em transaction é possivel
 * Cadastrar uma conta
 * Visualizar as informações de uma conta cadastrada
 * Realizar a uma transação

# Pré-requisitos
- [Maven](https://maven.apache.org/install.html)
- [Docker](https://docs.docker.com/install/) e [Docker-compose](https://docs.docker.com/compose/install/)
- [Java 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

# Como executar

###### Iniciar
```
make start
```

###### Executar testes
```
make test
```

# Endpoints

> POST /accounts

Request Body: 
```
{ 
    "document_number": "12345678900" 
} 
```
Response Body: 
```
{
    "account_id": 1,
    "document_number": "43242"
}
```

> GET /accounts/:accountId

Response Body: 
```
{ 
    "document_number": "12345678900" 
} 
```

>POST /transactions
Request Body: 
```
{ 
    "account_id": 1, 
    "operation_type_id": 4,
    "amount": 123.45 
}
```
