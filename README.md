# Ubitricity coding challenge

## Domain vocabulary:
EV - electric vehicle. CP - charging point, an element in an infrastructure that supplies electric energy for the recharging of electric vehicles.

## Problem details:
The task is to implement a simple application to manage the charging points installed at Carpark Ubi. Carpark Ubi has 10 charging points installed. When a car is connected it consumes either 20 Amperes (fast charging) or 10 Amperes (slow charging). Carpark Ubi installation has an overall current input of 100 Amperes so it can support fast charging for a maximum of 5 cars or slow charging for a maximum of 10 cars at one time. A charge point notifies the application when a car is plugged or unplugged. The application must distribute the available current of 100 Amperes among the charging points so that when possible all cars use fast charging and when the current is not sufficient some cars are switched to slow charging. Cars which were connected earlier have lower priority than those which were connected later. The application must also provide a report with a current state of each charging point returning a list of charging point, status (free or occupied) and - if occupied – the consumed current.

## Suggested solution
1) Find record by id
2) Validate if status has been changed
3) Depending on status refresh enity properties
4) Look throw all occupied charge points, sort them by last_update timestamp (used as priority), if enough power then put on fast charge, for the other points with lowest priority set slow charge type
5) Flush changes 

## Before start
Environment for running the app:
* Either Docker or PostgreSQL instance for DB.
* Docker for running integrational tests. 
* Java 1.8 or above.
* MVN 3.8 or above.
* Browser on your choise.

Change properties in [application.yml](src/main/resources/application.yml). Add your DB driver to [pom.xml](pom.xml). In
this case we are using PostgreSQL.


Three environment variables need to be present for the app to start Spring Boot application:

```
UBITRICITY_DB_CONNECTION_URL=jdbc:postgresql://<hostname>:5432;databaseName=<dbname>
UBITRICITY_DB_USERNAME=<username>
UBITRICITY_DB_PASSWORD=<password>
```

Don't forget to specify driver class for DB. For example I choose: org.postgresql.Driver 

```properties
chargepoint:
    amperes:
        max:100
spring:
    application:
name:codingchallenge
    flyway:
        enabled:true
        out-of-order:true
        schemas:codingchallenge
placeholderReplacement:false
    datasource:
        url: ${UBITRICITY_DB_CONNECTION_URL}
        driverClassName: org.postgresql.Driver
        username:${UBITRICITY_DB_USERNAME}
        password:${UBITRICITY_DB_PASSWORD}
server:
    port:9999
```

You can also build and run it as .jar file.
Invoke 1 if you are using Windows platform, either use 2 option 
```
1) mvnw.cmd clean install
2) mvnw clean install
```
After packaging you will find in target directory jar file.
```
java -jar target\coding-challenge-0.0.1-SNAPSHOT.jar
```

## Start
* Run application
* Open your browser and open page `http://localhost:9999/swagger-ui.html`. If you've changed the server port property, pay attention on this.
* All available methods you can find via Swagger UI:
    * get report about charge points you can access via `/api/v0/charge-points` route.
    * for sending request about specific CP is being occupied use: `/api/v0/charge-points/{cpId}/plug`. Pay attention that cpId it's id of existing CP, use it as path param in URL.
    * for sending request about specific CP was free use: `/api/v0/charge-points/{cpId}/unplug`. Pay attention that cpId it's id of existing CP, use it as path param in URL.

## Test
* Make sure Docker is running
  Invoke 1 if you are using Windows platform, either use 2 option
```
1) mvnw.cmd test
2) mvnw test
```

## Read more about libraries

[Spring Data](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#preface).

[Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/).

[Testcontainers](https://www.testcontainers.org/quickstart/junit_5_quickstart/).
