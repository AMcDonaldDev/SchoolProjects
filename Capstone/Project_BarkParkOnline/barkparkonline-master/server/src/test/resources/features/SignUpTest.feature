Feature: Test that a Guest can Register as a User
  @signup
  Scenario: As a guest, I want to sign up for an account
    Given the web server is online
    And i can access the sign up page
    And "montypython" is removed as a user
    When i complete the sign up form as "montypython"
    And i complete a dog profile
    And i press the sign up button
    Then "montypython" is signed up
    And "montypython" is removed as a user

  @signup
  Scenario: As a guest, I cannot enter a invalid dog age
    Given the web server is online
    And i can access the sign up page
    And "montypython" is removed as a user
    And i complete the sign up form as "montypython"
    When i complete a dog profile
    When i set the dog age to "biscuit"
    Then i cannot press the sign up button

  @signup
  Scenario: As a guest, I cannot enter a invalid dog breed
    Given the web server is online
    And i can access the sign up page
    And "montypython" is removed as a user
    And i complete the sign up form as "montypython"
    When i complete a dog profile
    When i set the dog breed to ""
    Then i cannot press the sign up button

  @signup
  Scenario: As a guest, I cannot enter a invalid dog name
    Given the web server is online
    And i can access the sign up page
    And "montypython" is removed as a user
    And i complete the sign up form as "montypython"
    When i complete a dog profile
    When i set the dog name to ""
    Then i cannot press the sign up button

  @signup
  Scenario: As a guest, I want to sign up for an account but my name is invalid
    Given the web server is online
    And i can access the sign up page
    And "montypython" is removed as a user
    When i complete the sign up form as "montypython"
    And i go to step 2
    And i set first name to "12"
    Then i cannot procede to step 3

  @signup
  Scenario: As a guest, I cannot sign up with a bad username
    Given the web server is online
    And i can access the sign up page
    And "2tiny" is removed as a user
    And i complete step 1 of the sign up form as "2tiny"
    Then i cannot procede to step 2
    And i cannot press the sign up button

  @signup
  Scenario: As a guest, I want to sign up with an invalid password
    Given the web server is online
    And i can access the sign up page
    And "montypython" is removed as a user
    When i complete the sign up form as "montypython"
    When i set the password to "x4"
    Then i cannot procede to step 3
    And i cannot press the sign up button
  
