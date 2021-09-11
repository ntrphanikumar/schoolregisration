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
* Admin Token
* Student Token
##### Student Management - Only by Admin
* Create Student
* Update Student
* Get Student By Id
* Get All Students
* Delete Student
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

### Points to Note
* An OauthClient is created as part of seed data. New ones can directly be seeded to DB with migration.
  * client_id: apiclient
  * client_secret: apiclient
* A default student and a course are seeded as seeddata.
* Admin users cannot be created on fly. Currently an admin user credentails are hardcoded in application.properties
