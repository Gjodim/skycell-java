package com.example;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@Test
public class LoggerCreationTest {
    private static final String API_KEY = "NNSXS.RPNRQUVEAQHYIBRJPYB5BMF36VT2E4ZIQWLCO6Y.ZP7FKSYX6J2XO2SRNBPHWQJHIBB5ZWTULHPI27N7C4IMQAKB6QYA";
    private static final String BASE_URI = "https://sensor-data-ingestion.dev.skycell.ch/v1/lora/configuration";

    @Test
    public void createLoggerMR_810T() {
        // Set the base URI
        RestAssured.baseURI = BASE_URI;

        // Register a default parser
        RestAssured.defaultParser = Parser.JSON;

        // When
        Response response = RestAssured.given()
                .header("APIKEY", API_KEY)
                .header("Content-Type", "application/json")
                .body("{\n\"loggerNumber\":\"98764xyz\",\n\"loggerType\":\"MR_810T\",\n\"baseInterval\": 600\n}")
                .post();

        System.out.println("Response Headers: " + response.getHeaders());

        // Print actual content type
        System.out.println("Actual Content Type: " + response.getContentType());

        // Then
        response.then().statusCode(201)
                .assertThat()
                .body("loggerType", equalTo("MR_810T"))
                .body("loggerNumber", equalTo("12245abc"))
                .body("baseInterval", equalTo(600));

        // Additional check for content type
        if (response.getContentType() != null) {
            response.then().assertThat().contentType(containsString(response.getContentType()));
        } else {
            System.out.println("Content type not defined in the response.");
        }

        // Additional check for empty response body
        String responseBody = response.getBody().asString();
        if (responseBody != null && !responseBody.trim().isEmpty()) {
            System.out.println("Response Body: " + responseBody);
        } else {
            System.out.println("Empty response body.");
        }

        // Reset the base URI (optional, but recommended)
        RestAssured.reset();
    }

    @Test
    public void createLoggerMR_812P() {
        // Set the base URI
        RestAssured.baseURI = BASE_URI;

        // Register a default parser
        RestAssured.defaultParser = Parser.JSON;

        // When
        Response response = RestAssured.given()
                .header("APIKEY", API_KEY)
                .header("Content-Type", "application/json")
                .body("{\n\"loggerNumber\":\"98765xyz\",\n\"loggerType\":\"MR_812P\",\n\"baseInterval\": 600\n}")
                .post();

        System.out.println("Response Headers: " + response.getHeaders());

        // Print actual content type
        System.out.println("Actual Content Type: " + response.getContentType());

        // Then
        response.then().statusCode(201)
                .assertThat()
                .contentType(containsString("application/json"))
                .body("loggerType", equalTo("MR_812P"))
                .body("loggerNumber", equalTo("67390def"))
                .body("baseInterval", equalTo(600));

        // Reset the base URI (optional, but recommended)
        RestAssured.reset();
    }

    @Test
    public void createLoggerInvalidType() {
        // Set the base URI
        RestAssured.baseURI = BASE_URI;

        // Register a default parser
        RestAssured.defaultParser = Parser.JSON;

        // When
        Response response = RestAssured.given()
                .header("APIKEY", API_KEY)
                .header("Content-Type", "application/json")
                .body("{\n\"loggerNumber\":\"98765xyz\",\n\"loggerType\":\"MR_888C\",\n\"baseInterval\": 600\n}")
                .post();

        System.out.println("Response Headers: " + response.getHeaders());

        // Print actual content type
        System.out.println("Actual Content Type: " + response.getContentType());

        // Then
        response.then().statusCode(400)
                .assertThat()
                .contentType(containsString("application/json"))
                .body(equalTo("Invalid logger type. Only MR_810T and MR_812P are allowed."));

        // Reset the base URI (optional, but recommended)
        RestAssured.reset();
    }


}
