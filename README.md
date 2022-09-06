# employee-rest-services

#### This application is built using the following tech stack & tools :

    - Java 11
    - Kotlin
    - Spring Boot
    - JUnit & Mockito
    - Postman
    
    
### 1. Build
Run any one of the below commands on source path: 

- Command to build with maven: `mvn clean install`

- Command to build with maven and skip tests: `mvn clean install -DskipTests`


### 2. Run Application
Command to start the application : `mvn spring-boot:run` 
 
OR 

Start it manually by running the `main` method from `RestApiApplication.kt`

### 3. After Start

- The default port(8080) is used to run this application.
- Hit the API's using the below base URL : `http://localhost:8080/api/v1/`
- Available REST API's are mentioned below:
    1. Get all employees details - `GET - http://localhost:8080/api/v1/employees`
    2. Get employee by Id - `GET - http://localhost:8080/api/v1/employee/{id}`
    3. Update or create an employee record - `POST - http://localhost:8080/api/v1/save` with employee as JSON body
    4. Delete employee record by Id - `DELETE - http://localhost:8080/api/v1/delete/{id}`
