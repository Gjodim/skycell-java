package com.example;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class LoggerCreationTest {
    private static final String apiKey = ConfigurationManager.getProperty("api.key");
    private static final String baseUri = ConfigurationManager.getProperty("api.baseUri");
    private static Response response;
    private static String createdLoggerType;
    private static String createdLoggerNumber;

    @Given("the base URI is configured")
    public void configureBaseUri() {
        RestAssured.baseURI = baseUri;
        RestAssured.defaultParser = Parser.JSON;
    }

    @When("I create a logger with type {string} and number {string}")
    public void createLogger(String loggerType, String loggerNumber) {
        response = RestAssured.given()
                .header("APIKEY", apiKey)
                .header("Content-Type", "application/json")
                .body(String.format("{\n\"loggerNumber\":\"%s\",\n\"loggerType\":\"%s\",\n\"baseInterval\": 600\n}", loggerNumber, loggerType))
                .post();

        createdLoggerType = loggerType;
        createdLoggerNumber = loggerNumber;
    }

    @Then("the logger should be created successfully")
    public void validateLoggerCreation() {
        response.then().statusCode(201);
    }

    @And("the logger details should match the expected values")
    public void validateLoggerDetails() {
        response.then()
                .assertThat()
                .header("Content-Type", containsString("application/json"))
                .body("loggerType", equalTo(createdLoggerType))
                .body("loggerNumber", equalTo(createdLoggerNumber))
                .body("baseInterval", equalTo(600));
    }

    @When("I create an invalid logger with type {string} and number {string}")
    public void createInvalidLogger(String loggerType, String loggerNumber) {
        response = RestAssured.given()
                .header("APIKEY", apiKey)
                .header("Content-Type", "application/json")
                .body(String.format("{\n\"loggerNumber\":\"%s\",\n\"loggerType\":\"%s\",\n\"baseInterval\": 600\n}", loggerNumber, loggerType))
                .post();
    }

    @Then("the logger creation should fail with a validation error")
    public void validateInvalidLoggerCreation() {
        response.then().statusCode(400);
    }

    @After("@cleanup")
    public void deleteLogger() {
        RestAssured.given()
                .header("APIKEY", apiKey)
                .delete(baseUri + "/v1/lora/configuration/{loggerType}/{loggerNumber}", createdLoggerType, createdLoggerNumber)
                .then().statusCode(204);
    }
}
