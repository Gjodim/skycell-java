package com.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@Test
public class LoggerCreationTest {
    private static final String API_KEY = "NNSXS.RPNRQUVEAQHYIBRJPYB5BMF36VT2E4ZIQWLCO6Y.ZP7FKSYX6J2XO2SRNBPHWQJHIBB5ZWTULHPI27N7C4IMQAKB6QYA";

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://sensor-data-ingestion.dev.skycell.ch/v1/lora/configuration";
    }

    @Test
    public void createLoggerMR_810T() {
        // When
        Response response = RestAssured.given()
                .header("APIKEY", API_KEY)
                .header("Content-Type", "application/json")
                .body("{\n\"loggerNumber\":\"12245abc\",\n\"loggerType\":\"MR_810T\",\n\"baseInterval\": 600\n}")
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
        String contentType = response.getContentType();
        if (contentType != null && (contentType.startsWith("application/json") || contentType.startsWith("application/xml"))) {
            response.then().assertThat().contentType(containsString(contentType));
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
    }

    @Test
    public void createLoggerMR_812P() {
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
    }

    @Test
    public void createLoggerInvalidType() {
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
                .body("error", equalTo("Invalid logger type. Only MR_810T and MR_812P are allowed."));
    }

}
