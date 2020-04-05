# Freind Management Rest API
#### Java / DevOPs  Assignment

# Assignment 
A demo application that showcases this developer's skills on using Java platform and its related technologies.

## Assignment Requirements
- Use Java 1.8 and its associated features (ie. streaming api, lambda functions, etc.)
- Use Spring Boot and its related frameworks
- Each API endpoint must have its proper documentation when viewed in a browser using Swagger
- Must have unit tests
- Deployable to a cloud provider
- Accessible once deployed to your cloud provided
- Have a comprehensive initial (README) documentation about what you did for this project.

# Implementation

## Tech Stack
- Java 1.8
- Spring Boot
    - Starter Web
    - Starter Data-JPA
- Lombok
    - For builder pattern, getters/setters, and constructors
- swagger2
- docker, docker-compose
    - Create a Dockerfile to wrap the app as a docker image
    - Create a docker-compose file to deploy the app with a mysql service
    - This docker-compose can be used to deploy the app in a cloud instance easily

## Build and Deploy
Checkout the Project
```shell script
git clone https://github.com/madumalt/friendmanagement.git
```
Go inside the Project Directory
```shell script
cd friendmanagement
```
Build the project
```shell script
./mvnw clean install
```
Run as docker-compose service (make sure you are in the directory where you can find the Dockerfile and docker-compose.yml)
```shell script
docker-compose up -d --build
```

## Rest API Swagger Documentation
Once you deploy the app you can find the swagger-ui in the following location.
```
http://<server-ip>:8888/swagger-ui.html
```

## Rest Endpoints

### API to create a friend connection between two email addresses
#### API Endpoint: `/friends/add`
Sample Request:
```
{
"friends": [ "andy@example.com", "john@example.com" ]
}
```
Sample Response:
```
{
"success": true
}
```

### API to retrieve the friends list for an email address
#### API Endpoint: `/friends/list`
Sample Request:
```
{
"email": "andy@example.com"
}
```
Sample Response:
```
{
"success": true,
"friends": [ "john@example.com" ],
"count": 1
}
```

### API to retrieve the common friends list between two email addresses.
#### API Endpoint: `/friends/common`
Sample Request:
```
{
"friends": [ "andy@example.com", "john@example.com" ]
}
```
Sample Response:
```
{
"success": true,
"friends": [ "common@example.com" ],
"count": 1
}
```

### API to subscribe to updates from an email address
#### API Endpoint: `/friends/subscribe`
Sample Request:
```
{
"requestor": "lisa@example.com",
"target": "john@example.com"
}
```
Sample Response:
```
{
"success": true
}
```

### API to block updates from an email address
#### API Endpoint: `/friends/block`
Sample Request:
```
{
"requestor": "andy@example.com",
"target": "john@example.com"
}
```
Sample Response:
```
{
"success": true
}
```

### API to retrieve all email addresses that can receive updates from an email address
#### API Endpoint: `/friends/notify`
Sample Request:
```
{
"sender": "john@example.com",
"text": "Hello World! kate@example.com"
}
```
Sample Response:
```
{
"success": true,
"recipients": [ "lisa@example.com", "kate@example.com" ]
}
```
