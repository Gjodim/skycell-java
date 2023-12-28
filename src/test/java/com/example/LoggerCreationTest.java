package com.example;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@Test
public class LoggerCreationTest {
    private static final String apiKey = ConfigurationManager.getProperty("api.key");
    private static final String baseUri = ConfigurationManager.getProperty("api.baseUri");

    @Test
    public void createLogger() {
        createLogger("MR_810T", "98764ABC");
        createLogger("MR_812P", "99885CAB");
        createInvalidLogger("MR_888C", "99885ACB");
    }

    private void createLogger(String loggerType, String loggerNumber) {
        RestAssured.baseURI = baseUri;
        RestAssured.defaultParser = Parser.JSON;

        Response response = RestAssured.given()
                .header("APIKEY", apiKey)
                .header("Content-Type", "application/json")
                .body(String.format("{\n\"loggerNumber\":\"%s\",\n\"loggerType\":\"%s\",\n\"baseInterval\": 600\n}", loggerNumber, loggerType))
                .post();

        printResponseDetails(response);

        response.then().statusCode(201)
                .assertThat()
                .header("Content-Type", containsString("application/json"))
                .body("loggerType", equalTo(loggerType))
                .body("loggerNumber", equalTo(loggerNumber))
                .body("baseInterval", equalTo(600));

        RestAssured.reset();
    }

    private void createInvalidLogger(String loggerType, String loggerNumber) {
        RestAssured.baseURI = baseUri;
        RestAssured.defaultParser = Parser.JSON;

        Response response = RestAssured.given()
                .header("APIKEY", apiKey)
                .header("Content-Type", "application/json")
                .body(String.format("{\n\"loggerNumber\":\"%s\",\n\"loggerType\":\"%s\",\n\"baseInterval\": 600\n}", loggerNumber, loggerType))
                .post();

        printResponseDetails(response);

        response.then().statusCode(400)
                .assertThat()
                .contentType(containsString("application/json"))
                .body(equalTo("Invalid logger type. Only MR_810T and MR_812P are allowed."));

        RestAssured.reset();
    }

    private void printResponseDetails(Response response) {
        System.out.println("Response Headers: " + response.getHeaders());

        String actualContentType = response.getContentType();
        if (actualContentType != null && !actualContentType.trim().isEmpty()) {
            System.out.println("Actual Content Type: " + actualContentType);
            response.then().assertThat().contentType(containsString("application/json"));
        } else {
            System.out.println("Content type not defined in the response or is an empty string.");
        }

        String responseBody = response.getBody().asString();
        if (responseBody != null && !responseBody.trim().isEmpty()) {
            System.out.println("Response Body: " + responseBody);
        } else {
            System.out.println("Empty response body.");
        }
    }

    public void cleanup() {
        // Implement logic to delete or reset the loggers created during the test.
        deleteLogger("MR_810T", "98764ABC");
        deleteLogger("MR_812P", "99885CAB");
        //deleteLogger("MR_888C", "99885ACB");
    }

    private void deleteLogger(String loggerType, String loggerNumber) {
        RestAssured.baseURI = baseUri;
        RestAssured.defaultParser = Parser.JSON;

        Response response = RestAssured.given()
                .header("APIKEY", apiKey)
                .delete(baseUri+"/v1/lora/configuration/{loggerType}/{loggerNumber}", loggerType, loggerNumber);

        response.then().statusCode(204);

        RestAssured.reset();
    }


}
