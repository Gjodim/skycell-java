Feature: Battery Replacement for IoT Loggers

  Background:
    Given the customer has a fleet of 30,000 loggers across 1,000 locations
    And the customer uses SkyCell’s LoRa-based loggers for asset tracking
    And the customer has a cloud platform, IoT service, gateways and loggers in place

  Scenario: Dynamic Battery Replacement Configuration
    Given the customer wants to replace the logger battery in specific locations
    #When the customer dynamically selects replacement locations in the cloud platform
    When the customer dynamically sets the battery level threshold for replacements
    #When the battery level of a logger in a replacement location falls below the threshold
    Then the LED outside the logger should blink when battery is below threshold
    And the LED should only blink in locations where exchanges happen

    # Additional Scenarios

    # Scenario: Battery Replacement Outside Selected Locations
    # Given the battery level of a logger falls below the threshold
    # And the logger is not in a replacement location
    # When the cloud platform receives the battery level data
    # Then the cloud platform should not trigger a battery replacement alert
    # And the LED on the logger should not blink

    # Scenario: Multiple Replacement Locations
    # Given the customer wants to replace the logger battery in multiple locations
    # And the customer dynamically selects multiple replacement locations in the cloud platform
    # And the customer sets different battery level thresholds for each location
    # When the battery level of a logger in a replacement location falls below its threshold
    # Then the cloud platform should trigger a battery replacement alert for that location
    # And the LED on the logger in that location should blink
    # And the battery replacement alert for other locations should not be triggered
    # And the LED on loggers in other locations should not blink

    # Scenario: Changing Replacement Location
    # Given the customer dynamically selects a replacement location
    # And the battery level of a logger in that location falls below the threshold
    # When the customer changes the replacement location in the cloud platform
    # Then the cloud platform should trigger a battery replacement alert for the new location
    # And the LED on the logger in the new location should blink
    # And the battery replacement alert for the previous location should be canceled
    # And the LED on the logger in the previous location should stop blinking
