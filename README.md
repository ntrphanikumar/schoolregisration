# School Registration
School registration system to create students and courses and students can register to courses of their choice

### Tech stack
* Java 11
* Maven
* Spring Boot (with JPA, OAuth2)
* Docker (Mysql8 & Application) with docker compose
* Mysql
* SpringSecurity OAuth2 for both authentication and authorization
* DB migration using Flyway
* Logback with Slf4j for logging
* JUnit & Mockito for unit testing

### Rest APIs
All the apis can be found and imported from postman collection
https://www.getpostman.com/collections/65617e2d095c0745a0b4
##### OAuth
* [Admin Token](https://github.com/ntrphanikumar/schoolregisration/tree/master/apispec/oAuth/AdminToken.md)
* [Student Token](https://github.com/ntrphanikumar/schoolregisration/tree/master/apispec/oAuth/StudentToken.md)
##### Student Management - Only by Admin
* Create Student
  * POST http://localhost:8080/api/v1/students
  * Request Headers:
    * Authorization: Bearer <adminoauthtoken>
    * Content-Type: application/json
  * Request Body:
    ```
    {
      "firstName": "Phani Kumar",
      "lastName": "N T R",
      "email": "ntrphanikumar@gmail.com",
      "username": "phani",
      "password": "phani",
      "mobileNumber": "12345663223" //Optional
    }
    ```
  * Response Body:
    ```
    {
      "id": 3,
      "firstName": "Phani Kumar",
      "lastName": "N T R",
      "email": "ntrphanikumar@gmail.com",
      "username": "phani",
      "mobileNumber": "12345663223"
    }
    ```
  * Details
    * Status: 200 OK on success
    * Status: 401 Unauthorized for invalid token or token is of not admin user
    * Status: 400 Bad Request in case of validation failures
* Update Student
  * PUT http://localhost:8080/api/v1/students/3
  * Request Headers:
    * Authorization: Bearer <adminoauthtoken>
    * Content-Type: application/json
  * Request Body: Only firstname, lastname, email and mobilenumber can be updated
    ```
    {
      "firstName": "Phani Kumar",
      "lastName": "N T R",
      "email": "ntrphanikumar@gmail.com",
      "mobileNumber": "12345663223"
    }
    ```
  * Response Body:
    ```
    {
      "id": 3,
      "firstName": "Phani Kumar",
      "lastName": "N T R",
      "email": "ntrphanikumar@gmail.com",
      "username": "phani",
      "mobileNumber": "12345663223"
    }
    ```
  * Details
    * Status: 200 OK on success
    * Status: 401 Unauthorized for invalid token or token is of not admin user
    * Status: 400 Bad Request in case of validation failures
* Get Student By Id
  * GET http://localhost:8080/api/v1/students/3
  * Request Headers:
    * Authorization: Bearer <adminoauthtoken>
  * Response Body:
    ```
    {
      "id": 3,
      "firstName": "Phani Kumar",
      "lastName": "N T R",
      "email": "ntrphanikumar@gmail.com",
      "username": "phani",
      "mobileNumber": "12345663223"
    }
    ```
  * Details
    * Status: 200 OK on success
    * Status: 401 Unauthorized for invalid token or token is of not admin user
    * Status: 400 Bad Request in case of student not found
* Get All Students
  * GET http://localhost:8080/api/v1/students
  * Request Headers:
    * Authorization: Bearer <adminoauthtoken>
  * Response Body:
    ```
    [
      {
        "id": 1,
        "firstName": "Phani Kumar",
        "lastName": "N T R",
        "email": "ntrphanikumar@gmail.com",
        "username": "phani",
        "mobileNumber": "12345663223"
      }
    ]
    ```
  * Details
    * Status: 200 OK on success
    * Status: 401 Unauthorized for invalid token or token is of not admin user
* Delete Student
  * DELETE http://localhost:8080/api/v1/students/3
  * Request Headers:
    * Authorization: Bearer <adminoauthtoken>
  * Response Body: no content
  * Details
    * Status: 200 OK on success
    * Status: 401 Unauthorized for invalid token or token is of not admin user
    * Status: 400 Bad Request in case of student not found
##### Course Management - Only by Admin
* Create Course
* Update Course
* Get Course By Id
* Get All Courses
* Delete Course
##### Reports - Only by Admin
* Show All Students without their course details
* Show All Courses without their student details
* Get All Courses for Student
* Get All Students for Course
##### Student Course Management - Accessible by Student
* Register to course

### Steps to Build and Run Application
##### Prerequisites
* JDK 11 installed
##### Steps
* ``./mvnw clean install`` to build application jar
* ``docker-compose up`` will build docker image of application and launch both mysql8 db and application
* If you have mysql already installed on localhost, 
  * Update dbusername and password in docker-compose.yml file
  * Start application with ``docker-compose up app``
* If you want to connect to a remote mysql
  * Update details of db in docker-compose.yml file and
  * Start only application with ``docker-compose up app``


### Points to Note
* An OauthClient is created as part of seed data. New ones can directly be seeded to DB with migration.
  * client_id: apiclient
  * client_secret: apiclient
* A default student and a course are seeded as seeddata.
* Admin users cannot be created on fly. Currently an admin user credentails are hardcoded in application.properties
