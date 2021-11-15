# job4j_url_shortcut

[![Build Status](https://app.travis-ci.com/kalenikov/job4j_url_shortcut.svg?branch=master)](https://app.travis-ci.com/kalenikov/job4j_url_shortcut)

### Project description

This project represent url shortener rest-service with jwt-authorization

### Used technologies

* Java 14
* Spring Boot 2
* Spring Security & JWT authorization
* Spring Data JPA
* PostgreSQL

### Run project

#### Using Docker

````
mvn package -Dmaven.test.skip && docker build -t shortcut . && docker-compose up
````

#### Using k8s

````
kubectl apply -f postgresdb-secret.yml
kubectl get secrets
kubectl apply -f postgesdb-configmap.yml
kubectl get configmaps
kubectl apply -f postgresdb-deployment.yml
kubectl logs -l app=postgresdb
kubectl describe pod
kubectl apply -f spring-boot-deployment.yml
kubectl logs -l app=spring-boot
kubectl describe pod
````

### Using REST API

#### Register site

````
curl -X POST 'http://localhost:8080/api/auth/reg' \
--header 'Content-Type: application/json' \
--data-raw '{
    "site": "ya.ru"
}'
````

#### Getting token

````
curl -X POST 'http://localhost:8080/api/auth/login' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
    "login": "your_login",
    "password": "your_password"
}'
````

#### Convert link

````
curl -X POST 'http://localhost:8080/api/v1/links/convert/' \
--header 'Authorization: Bearer your_token\
--header 'Content-Type: application/json' \
--data-raw '{
    "url": "https://www.reddit.com/r/popular/?geo_filter=IT"
}'
````

#### Get link from code

````
curl 'http://localhost:8080/api/v1/links/your_code' \
--header 'Authorization: Bearer your_token\
--header 'Content-Type: application/json' \
````

#### Getting statistic

````
curl -X 'http://localhost:8080/v1/links/statistic' \
--header 'Authorization: Bearer your_token\
````

