Feature: Test that a Registered User can Login
  @login
  Scenario: As a user, I want to login
    Given the web server is online
    And i can access the login page
    When i type in my username
    And i type in my password
    And i press the login button
    Then i am logged into the server

  @login
  Scenario: As a user, I want to login but an invalid password is entered
    Given the web server is online
    And i can access the login page
    When i type in my username
    And i type in my password as "ghostever"
    And i press the login button
    Then i am not logged into the server

  @login
  Scenario: As a user, I want to login but an invalid username is entered
    Given the web server is online
    And i can access the login page
    When i type in my username as "jonnow"
    And i type in my password
    And i press the login button
    Then i am not logged into the server

  @login
  Scenario: As a user, I want to login but and invalide username and invalid passwords are entered
    Given the web server is online
    And i can access the login page
    When i type in my username as "jonnow"
    And i type in my password as "ghostever"
    And i press the login button
    Then i am not logged into the server
