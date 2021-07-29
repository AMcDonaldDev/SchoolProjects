Feature: Test that a Logged In User can Logout
  @logout
  Scenario: As a user, I want to login, then logout
    Given the web server is online
    And i can access the login page
    And i login as "jonsnow" with password "ghost4ever"
    And i am identified as "Jon Snow"
    When i access the home page
    And i try to click the dropdown toggle
    And i press the logout button
    Then i am taken to the logout page
    And "jonsnow" is logged out of the server

  @logout
  Scenario: As a guest, i cannot logout
    Given the web server is online
    And i access the home page
    And i am identified as "Guest"
    When i try to click the dropdown toggle
    Then i cannot click logout