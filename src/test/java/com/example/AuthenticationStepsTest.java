package com.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class AuthenticationStepsTest {

    private Response response;
    private final String baseUri = ConfigurationManager.getProperty("keycloak.baseUri");

    @Given("the Keycloak API is available")
    public void givenTheKeycloakAPIIsAvailable() {
        RestAssured.baseURI = baseUri;
    }

    @When("a user authenticates with valid credentials")
    public void whenAUserAuthenticatesWithValidCredentials() {
        String username = ConfigurationManager.getProperty("keycloak.username");
        String password = ConfigurationManager.getProperty("keycloak.password");

        response = RestAssured.given()
                .contentType(ContentType.URLENC)
                .formParams("client_id", "webapp", "grant_type", "password", "username", username, "password", password)
                .post();
    }

    @Then("the authentication should be successful")
    public void thenTheAuthenticationShouldBeSuccessful() {
        response.then().statusCode(200);

        JsonPath jsonPath = response.jsonPath();
        String accessToken = jsonPath.getString("access_token");

        assertThat(accessToken, not(emptyOrNullString()));

        ConfigurationManager.setProperty("access.token", accessToken);
        System.out.println("Access Token Is: " + ConfigurationManager.getProperty("access.token"));
    }
}
