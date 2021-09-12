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
###### Admin Token
  * POST https://localhost:8080/oauth/token?grant_type=password
  * RequestHeaders
    * Authorization: Basic Auth with ``$clientid:$clientsecret``
    * Content-Type: application/x-www-form-urlencoded
  * RequestBody
    * username: <studentusername>
    * password: <studentpassword>
  * ResponseBody
    ```
    {
      "access_token": "OrdrZMjLT_EotJP1G7sn2CAjMGI",
      "token_type": "bearer",
      "expires_in": 43199,
      "scope": "read write user_info"
    }
    ```
###### Student Token
  * POST https://localhost:8080/oauth/token?grant_type=password
  * RequestHeaders
    * Authorization: Basic Auth with ``$clientid:$clientsecret``
    * Content-Type: application/x-www-form-urlencoded
  * RequestBody
    * username: <adminuser>
    * password: <adminpassword>
  * ResponseBody
    ```
    {
      "access_token": "OrdrZMjLT_EotJP1G7sn2CAjMGI",
      "token_type": "bearer",
      "expires_in": 43199,
      "scope": "read write user_info"
    }
    ```
##### Student Management - Only by Admin
###### Create Student
  * POST http://localhost:8080/api/v1/students
  * Request Headers:
    * Authorization: ``Bearer <adminoauthtoken>``
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
    * Username should be unique
###### Update Student
  * PUT http://localhost:8080/api/v1/students/3
  * Request Headers:
    * Authorization: ``Bearer <adminoauthtoken>``
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
###### Get Student By Id
  * GET http://localhost:8080/api/v1/students/3
  * Request Headers:
    * Authorization: ``Bearer <adminoauthtoken>``
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
###### Get All Students
  * GET http://localhost:8080/api/v1/students
  * Request Headers:
    * Authorization: ``Bearer <adminoauthtoken>``
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
###### Delete Student
  * DELETE http://localhost:8080/api/v1/students/3
  * Request Headers:
    * Authorization: ``Bearer <adminoauthtoken>``
  * Response Body: no content
  * Details
    * Status: 200 OK on success
    * Status: 401 Unauthorized for invalid token or token is of not admin user
    * Status: 400 Bad Request in case of student not found
##### Course Management - Only by Admin
###### Create Course
  * POST http://localhost:8080/api/v1/courses
  * Request Headers:
    * Authorization: ``Bearer <adminoauthtoken>``
    * Content-Type: application/json
  * Request Body:
    ```
    {
      "name": "DS & Algos Basic11",
      "description": "Data structures and Algos basic course"
    }
    ```
  * Response Body:
    ```
    {
      "id": 2,
      "name": "DS & Algos Basic11",
      "description": "Data structures and Algos basic course"
    }
    ```
  * Details
    * Status: 200 OK on success
    * Status: 401 Unauthorized for invalid token or token is of not admin user
    * Status: 400 Bad Request in case of validation failures
    * Course name should be unique
###### Update Course
  * POST http://localhost:8080/api/v1/courses/2
  * Request Headers:
    * Authorization: ``Bearer <adminoauthtoken>``
    * Content-Type: application/json
  * Request Body:
    ```
    {
      "description": "Data structures and Algos basic course"
    }
    ```
  * Response Body:
    ```
    {
      "id": 2,
      "name": "DS & Algos Basic11",
      "description": "Data structures and Algos basic course"
    }
    ```
  * Details
    * Status: 200 OK on success
    * Status: 401 Unauthorized for invalid token or token is of not admin user
    * Status: 400 Bad Request in case of validation failures
    * Only description can be modified
###### Get Course By Id
* GET http://localhost:8080/api/v1/courses/2
* Request Headers:
    * Authorization: ``Bearer <adminoauthtoken>``
* Response Body:
  ```
  {
    "id": 2,
    "name": "DS & Algos Basic11",
    "description": "Data structures and Algos basic course"
  }
  ```
* Details
    * Status: 200 OK on success
    * Status: 401 Unauthorized for invalid token or token is of not admin user
    * Status: 400 Bad Request in case of course not found
###### Get All Courses
* GET http://localhost:8080/api/v1/courses
* Request Headers:
    * Authorization: ``Bearer <adminoauthtoken>``
* Response Body:
  ```
  [
    {
        "id": 1,
        "name": "DS & Algos Basic",
        "description": "Data structures and Algos basic course"
    },
    {
        "id": 2,
        "name": "DS & Algos Basic11",
        "description": "Data structures and Algos basic course"
    }
  ]
  ```
* Details
    * Status: 200 OK on success
    * Status: 401 Unauthorized for invalid token or token is of not admin user
    * Status: 400 Bad Request in case of course not found
###### Delete Course
* DELETE http://localhost:8080/api/v1/courses/2
* Request Headers:
  * Authorization: ``Bearer <adminoauthtoken>``
* Response Body: no content
* Details
  * Status: 200 OK on success
  * Status: 401 Unauthorized for invalid token or token is of not admin user
  * Status: 400 Bad Request in case of course not found
##### Report APIs
###### Show All Students without their course details
* GET http://localhost:8080/api/v1/reports/listStudents
* Request Headers:
  * Authorization: ``Bearer <adminoauthtoken>``
* Response Body:
  ```
  [
    {
        "id": 2,
        "firstName": "Hello",
        "lastName": "valo",
        "email": "ntrphanikumar@gmail.com",
        "username": "phani1"
    },
    {
        "id": 3,
        "firstName": "Hello",
        "lastName": "valo",
        "email": "ntrphanikumar@gmail.com",
        "username": "phani2"
    }
  ]
  ```
* Details
  * Status: 200 OK on success
  * Status: 401 Unauthorized for invalid token or token is of not admin user
###### Show All Courses without their student details
* GET http://localhost:8080/api/v1/reports/listCourses
* Request Headers:
    * Authorization: ``Bearer <adminoauthtoken>``
* Response Body:
  ```
  [
    {
        "id": 1,
        "name": "DS & Algos Basic",
        "description": "Data structures and Algos basic course"
    },
    {
        "id": 2,
        "name": "DS & Algos Basic11",
        "description": "Data structures and Algos basic course"
    }
  ]
  ```
* Details
  * Status: 200 OK on success
  * Status: 401 Unauthorized for invalid token or token is of not admin user
###### Get All Courses for Student
* GET http://localhost:8080/api/v1/reports/coursesFor/1
* Request Headers:
    * Authorization: ``Bearer <adminoauthtoken>``
* Response Body:
  ```
  [
    {
        "id": 1,
        "name": "DS & Algos Basic",
        "description": "Data structures and Algos basic course"
    },
    {
        "id": 2,
        "name": "DS & Algos Basic11",
        "description": "Data structures and Algos basic course"
    }
  ]
  ```
* Details
  * Status: 200 OK on success
  * Status: 401 Unauthorized for invalid token or token is of not admin user
  * Status: 400 Bad Request in case of student not found
###### Get All Students for Course
* GET http://localhost:8080/api/v1/reports/studentsFor/1
* Request Headers:
    * Authorization: ``Bearer <adminoauthtoken>``
* Response Body:
  ```
  [
    {
        "id": 2,
        "firstName": "Hello",
        "lastName": "valo",
        "email": "ntrphanikumar@gmail.com",
        "username": "phani1"
    },
    {
        "id": 3,
        "firstName": "Hello",
        "lastName": "valo",
        "email": "ntrphanikumar@gmail.com",
        "username": "phani2"
    }
  ]
  ```
* Details
    * Status: 200 OK on success
    * Status: 401 Unauthorized for invalid token or token is of not admin user
    * Status: 400 Bad Request in case of course not found
##### Student Course Management - Accessible by Student
###### Register to course
* GET http://localhost:8080/api/v1/registerTo/1
  * In the url path param ``1`` is courseid
  * student is determined from his oauth token to avoid ambiguity
* Request Headers:
    * Authorization: ``Bearer <studentoauthtoken>``
* Response Body:
  ```
  {
    "id": 1,
    "firstName": "Phani Kumar",
    "lastName": "N T R",
    "email": "ntrphanikumar@gmail.com",
    "username": "phani",
    "courses": [
        {
            "id": 1,
            "name": "DS & Algos Basic",
            "description": "Data structures and Algos basic course"
        }
    ]
  }
  ```
* Details
    * Status: 200 OK on success
    * Status: 401 Unauthorized for invalid token or token is of not admin user
    * Status: 400 Bad Request in case of course not found

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
* Max students per course and Max courses per student are now configured in application.properties
  * We can move them to course and student entities respectively and we will be able to support different configuration for each course and student.
