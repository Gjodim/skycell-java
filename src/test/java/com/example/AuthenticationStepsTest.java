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
    private final String baseUri = ConfigurationManager.getProperty("keycloak.baseUri");

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = baseUri;
    }

    @Test
    public void authenticationTest() {
        String username = ConfigurationManager.getProperty("keycloak.username");
        String password = ConfigurationManager.getProperty("keycloak.password");

        response = RestAssured.given()
                .contentType(ContentType.URLENC)
                .formParams("client_id", "webapp", "grant_type", "password", "username", username, "password", password)
                .post();

        System.out.println("Response Body: " + response.getBody().asString());

        response.then().statusCode(200);

        JsonPath jsonPath = response.jsonPath();
        String accessToken = jsonPath.getString("access_token");

        System.out.println("Access Token Is: " + accessToken);

        assertThat(accessToken, not(emptyOrNullString()));

        ConfigurationManager.setProperty("access_token", accessToken);

    }
}
