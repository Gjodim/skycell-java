package com.example;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@Test
public class AuthenticationStepsTest {
    private Response response;

    @BeforeClass
    public void setUp() {
        // Set the baseURI directly
        RestAssured.baseURI = "https://keycloak.dev.skycell.ch/realms/skycell/protocol/openid-connect/token";
    }

    @Test
    public void authenticationTest() {
        // Given
        String username = "qa_interview@skycell.ch";
        String password = "Qa_interview2023!";

        // When
        response = RestAssured.given()
                .contentType(ContentType.URLENC)
                .formParams("client_id", "webapp", "grant_type", "password", "username", username, "password", password)
                .post();

        // Print the response body for debugging
        System.out.println("Response Body: " + response.getBody().asString());

        // Then
        response.then().statusCode(200);

        // Additional validation logic for the access token
        JsonPath jsonPath = response.jsonPath();
        String accessToken = jsonPath.getString("access_token");

        System.out.println("Access Token Is: " + accessToken);

        assertThat(accessToken, not(emptyOrNullString()));
        // Add more validation logic based on your specific requirements
    }
}
