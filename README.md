# Skycell Java Project

This project contains automated tests for the Skycell system using Java, Rest Assured, and Cucumber.
The contents of the solution are:
1. batteryReplacement.feature
2. authentication.feature + AuthenticationStepsTest
3. loggerCreation.feature + LoggerCreationTest
4. OpenAPI + GitHub actions

## Table of Contents

- [Introduction](#introduction)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Dependencies](#dependencies)
- [Generate Sources](#generate-sources)
- [Installation](#installation)
- [Running Tests](#running-tests)
- [Configuration](#configuration)
- [CI/CD Pipeline](#cicd-pipeline)

## Introduction

This Java project is designed for automated testing of the Skycell system. It uses Rest Assured for API testing and Cucumber for behavior-driven development (BDD).

## Prerequisites

Before running the tests, ensure you have the following installed:

- Java (JDK 8 or later)
- Maven

## Project Structure

```text
src/
|-- main/
|   `-- java/
|       `-- com/
|           `-- example/
|               `-- ConfigurationManager.java
|        `-- resources/
|               `-- application.yaml
|-- test/
|   `-- java/
|       `-- com/
|           `-- example/
|               |-- AuthenticationStepsTest.java
|               |-- LoggerCreationTest.java
|-- resources/
|   `-- config.properties
|   `-- batteryReplacement.feature
|   `-- authentication.feature
|   `-- loggerCreation.feature
pom.xml
README.md
```

- src/main/java: Contains the main source code for your application and helper for configuration management.
- application.yaml contains the Openapi JsonSchema specification
- src/test/java: Contains your test classes.
- resources: Contains configuration files such as config.properties and gherkin .feature files.
- pom.xml: Maven project configuration.

## Dependencies
- Rest Assured: Testing and validating REST services.
- Cucumber: BDD framework for writing human-readable descriptions of software behaviors.
- JUnit: Testing framework.
- Allure: Test report generation.
- OpenAPI Generator: API code generation.
- Other dependencies are listed in the pom.xml file.

## Generate Sources
To generate OpenAPI sources, use the following Maven command:
```sh
mvn clean generate-sources
```

## Installation
To install and do initial setup run, use the following Maven command:
```sh
mvn clean install
```

## Running Tests
To run the tests, use the following Maven command:
```shell
mvn clean test
```

## Configuration
Configure your application and test settings in the config.properties file.

## CI/CD Pipeline
The project includes a GitHub Actions workflow for continuous integration. The pipeline is triggered every night at 1am CET.

