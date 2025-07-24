[![Only Tests](https://github.com/P4piJoke/blog-rest-api/actions/workflows/only-tests.yml/badge.svg)](https://github.com/P4piJoke/blog-rest-api/actions/workflows/only-tests.yml)
[![Deploy](https://github.com/P4piJoke/blog-rest-api/actions/workflows/deploy.yml/badge.svg)](https://github.com/P4piJoke/blog-rest-api/actions/workflows/deploy.yml)

# Blog REST API

The Blog REST API is a web service that allows users to create, manage, and interact with blog content easily. It includes endpoints for essential entities such as Post, Category, and Comment. Users can create categorized posts for organized content management and engage in discussions by commenting on posts. Role-based access control allows administrators to regulate user permissions, ensuring security and integrity. The API's RESTful design promotes easy integration into diverse platforms, enhancing the blogging experience for both creators and readers.




## Authors

- [@Danylo Papizhuk](https://github.com/P4piJoke)


## Tech Stack

**Client:** Not implemented 

**Server:** Java 17, Spring Boot 3

**Database:** MySQL

**Branching strategy:** GitHub flow


## Run Locally

Firstly, create database

```bash
    create database myblog;
```

Clone the project

```bash
    git clone https://github.com/P4piJoke/blog-rest-api.git
```

Go to the project directory

```bash
    cd blog-rest-api
```

Install dependencies

```bash
    ./mvnw clean install
```

Start the server

```bash
  ./mvnw spring-boot:run
```


## API Reference

#### Get Swagger documentation

```http
  GET /swagger-ui/index.html
```

#### Other detailed information provided in Swagger documentation 

