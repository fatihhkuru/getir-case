# ReadingIsGood Getir Case Study
ReadingIsGood is an online books retail firm which operates only on the Internet. Main target of ReadingIsGood is to deliver books from its one centralized warehouse to their customers within the same day. That is why stock consistency is the first priority for their vision operations.

## Before Start
In http://localhost:8080//api/customer/auth/signup path everybody can easily set his role. This is an assumption to make the login process logic easier. The only people who are admins can add new books and update stock number.

## How to Run

```
mvn clean package -DskipTests
$ docker-compose build
$ docker-compose up -d
```

## Technologies
- OpenJdk 11
- MongoDb
- Spring boot 2
- Spring Security
- Docker

## Test
- address : http://localhost:8080/
- postman request : ./Api Documentation.postman_collection.json
