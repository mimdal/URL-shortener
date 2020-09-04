# URL-shortener

[![Apache License, Version 2.0, January 2004](https://img.shields.io/github/license/apache/maven.svg?label=License)][license]

## About
A URL Shortener with links tracking feature. (Sample project to learn about all spring boot features). 
The application has two different sections, backend and front-end. 
Both of them developed in single spring boot application due to the project is for learning and not a production environment.


## Building
You need Java JDK 8 to build Plugin. Make sure Maven is using Java JDK 8 by setting JAVA_HOME before running Maven:

- export JAVA_HOME=/PATH/TO/JDK/8
- mvn clean package

## How Run

### by Maven
Open terminal and run following command: `mvn spring-boot:run`

### direct
After successful build open Terminal, change working directorty to target folder and run following command: 
```bash
java -jar URL-shortener-${version}.jar
``` 

## Technologies/Tools used
- spring boot
- maven: for automate build
- swagger: for API document
- lombok: to reduce boilerplate code. (eg. getter-setter-builder-tostring)
- h2: in memory database


## Backend Section
see ServiceController class. by swagger API document is exposed to 
[this](http://localhost:8080/swagger-ui.html) address.

## Frontend Section 
see FrontendController class. The controller connects to backend services by restTemplate.
by [this](http://localhost:8080/short-link/) address could see the application UI.

[license]: https://www.apache.org/licenses/LICENSE-2.0