Feature: Logger Creation

  Background:
    Given the base URI is configured

  Scenario Outline: Create valid loggers
    When I create a logger with type "<LoggerType>" and number "<LoggerNumber>"
    Then the logger should be created successfully
    And the logger details should match the expected values

    Examples:
      | LoggerType | LoggerNumber |
      | MR_810T     | 98765432     |
      | MR_812P     | 12345678     |

  Scenario: Create an invalid logger
    When I create an invalid logger with type "MR_888C" and number "99885ACB"
    Then the logger creation should fail with a validation error
    #And the error message should indicate "Invalid logger type. Only MR_810T and MR_812P are allowed."

  @cleanup
  Scenario: Cleanup
    And I delete the logger
    And I delete the logger

