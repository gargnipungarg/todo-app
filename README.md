# Todo-App
   This API offers CRUD operations on managing TODO items. You can add, list, update via various endpoints exposed.
   Following operations are supported:
   - Add an item to persistence layer
   - Update status as Done, completion date is automatically updated as current date
   - Update status as Not Done, completion date is automatically reset.
   - Update description of an existing TODO item.  
   - List all todo items with a given status.
   - List all todo items by not passing any status filter.

# Tech Stack
 - [JDK 11](https://openjdk.java.net/projects/jdk/11/)
 - [SpringBoot 2.5](https://spring.io/blog/2021/08/19/spring-boot-2-5-4-available-now)
 - [Swagger](https://editor.swagger.io/) for API contract
 - [Mapstruct](https://mapstruct.org/) as Mapper Utility between FrontEnd and Persistence layer POJO models.
 - [Gradle](https://docs.gradle.org/current/userguide/pmd_plugin.html) as dependency management tool
 - [PMD](https://docs.gradle.org/current/userguide/pmd_plugin.html) for code quality analysis
 - [Jacoco](https://docs.gradle.org/current/userguide/jacoco_plugin.html) for Junit Test coverage report
 - [Junit 5](https://junit.org/junit5) for unit tests
 - [Docker](https://www.docker.com/) for containerizing the application
 - [H2](https://www.h2database.com/html/main.html) as in memory database
 - [Lombok](https://projectlombok.org/) for annotation processor
 - [SpringBoot cache starter](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-cache) library for in memory cache of todo items
 - [Postman](https://www.postman.com/) for integration testing

# Build & Run

 - To build project on Linux/MacOS, use [build.sh](./build.sh) file, for windows - use [build.bat](./build.bat)
 - To run project on Linux/MacOS, use [run.sh](./run.sh), for windows - use [run.bat](./run.bat)
 - [docker-commands.txt](docker-commands.txt) shows all docker related operations
 - Postman collection for hitting endpoints with request body - [TODO.postman_collection.json](TODO.postman_collection.json)
 - Automated tests are part of build process. Passing test is mandatory step for successful build.

# API Contract 

   Contract is published in [api.yml](./api.yml) file.

