# Todo-App
   This API offers CRUD operations on managing TODO items. You can add, list, update via various endpoints exposed.
   Following operations are supported:
   - Add an item to persistence layer.
   - Update status as Done, completion date is automatically updated as current date.
   - Update status as Not Done, completion date is automatically reset.
   - Update description of an existing TODO item.  
   - List all todo items with "Not Done" status, caching is placed on this endpoint for 30 seconds to handle multiple incoming request.
   - List all todo items, caching is placed on this endpoint for 30 seconds to handle multiple incoming request. This endpoint uses the same endpoint as in point above. Providing an option request parameter boolean "notDone", you can choose either to list all statues or only "Not Done". 

# Tech Stack
 - [JDK 11](https://openjdk.java.net/projects/jdk/11/)
 - [SpringBoot 2.5](https://spring.io/blog/2021/08/19/spring-boot-2-5-4-available-now)
 - [Swagger](https://editor.swagger.io/) for writing API contract
 - [Open API 3](https://swagger.io/specification/) for contract standard  
 - [Mapstruct](https://mapstruct.org/) as Mapper Utility between FrontEnd and Persistence layer POJO models
 - [Gradle](https://docs.gradle.org/current/userguide/pmd_plugin.html) as dependency management tool
 - [PMD](https://docs.gradle.org/current/userguide/pmd_plugin.html) for code quality analysis. Report is generated at build/reports/pmd with errors if any, after building the project.
 - [Jacoco](https://docs.gradle.org/current/userguide/jacoco_plugin.html) for Junit Test coverage report. Report is generated at build/reports/jacoco after building the project. Sample coverage has been shown [here](./Test-Coverage.PNG).
 - [Junit 5](https://junit.org/junit5) for unit tests. Report is generated at build/reports/tests after building the project.
 - [WebMvcTest](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/test/autoconfigure/web/servlet/WebMvcTest.html) for integration testing   
 - [Docker](https://www.docker.com/) for containerizing the application. More info in [screenshots](./screenshots).
 - [H2](https://www.h2database.com/html/main.html) as in memory database
 - [Lombok](https://projectlombok.org/) for annotation processor
 - [SpringBoot cache starter](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-cache) library for in memory cache of todo items
 - [Postman](https://www.postman.com/) for integration testing

# Build & Run

 - To build project on Linux/MacOS, use [build.sh](./build.sh) file, for windows - use [build.bat](./build.bat)
 - To run project on Linux/MacOS, use [run.sh](./run.sh), for windows - use [run.bat](./run.bat)
 - [docker-commands.txt](docker-commands.txt) shows all docker related operations
 - Postman collection for hitting endpoints with request body - [TODO.postman_collection.json](TODO.postman_collection.json)
 - Automated tests are part of build process. Passing test is mandatory step for successful build. Sample test report - [Report](./test-report.PNG)

# API Contract 

   Contract is published in [api.yml](./api.yml) file.

# Assumptions
  
  If a TODO item is past its due date, we are only updating its status as 'Past Due', after a new event has come for that particular todo. For example - An item is added today and is due by tomorrow. If an update comes for this event(like udpate description or update status) after its due date, then we will update the status as 'Past Due'. For automatic update of Todo item without this scenario, we can use 'expiryAt' feature of DynamoDB. Whenever an item is expiring, we can run a lambda that puts back the expired item with 'Past Due' status. 
