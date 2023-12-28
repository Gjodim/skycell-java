Feature: User Authentication

  Scenario: User logs in and obtains an access token
    Given the Keycloak API is available
    When a user authenticates with valid credentials
    Then the authentication should be successful
