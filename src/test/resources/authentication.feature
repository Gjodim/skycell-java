Feature: Authentication System
  Check the API for the authentication system

  Scenario: Verify successful authentication
    Given the authentication URL is "authURL"
    And the content type is "contentType"
    And the method is "POST"
    And the form parameters are set as follows:
      | client_id  | webapp   |
      | grant_type | password |
      | username   | username |
      | password   | password |
    When I authenticate with username "qa_interview@skycell.ch" and password "Qa_interview2023!"
    Then the response status code should be 200
    And the response body contains a valid access token
