Feature: Test actions a user can make to their own profile

  @profile
  Scenario: As a user, I want to view a dog on my profile
    Given i am logged in
    And i am on my profile page
    When i click the +1
    And i enter the name "dogToView"
    And i enter the breed "breedV"
    And i enter the birthday "12-01-2020"
    And i select gender "FEMALE"
    And i click save dog
    Then dog "dogToView" is added
    And i select a dog "DEFAULT" to update
    Then dog "DEFAULT" is in view
    And i select a dog "dogToView" to update
    Then dog "dogToView" is in view

 @profile
  Scenario: As a user, I want to remove a dog on my profile
    Given i am logged in
    And i am on my profile page
    When i click the +1
    And i enter the name "dogToDelete"
    And i enter the breed "breedD"
    And i enter the birthday "12-01-2020"
    And i select gender "FEMALE"
    And i click save dog
    Then dog "dogToDelete" is added
    And i select a dog "dogToDelete" to update
    And i click remove dog
    Then dog "dogToDelete" is removed

  @profile
  Scenario: As a user, I want to update a dog on my profile
    Given i am logged in
    And i am on my profile page
    And i select a dog "DEFAULT" to update
    And i enter the breed "breed edit"
    And i select gender "MALE"
    And i click update dog
    Then dog "DEFAULT" "breed edit" "MALE" is updated

 @profile
  Scenario: As a user, I try to update a dog on my profile with an invalid breed
    Given i am logged in
    And i am on my profile page
    And i select a dog "DEFAULT" to update
    And i enter the breed "4"
    And i select gender "MALE"
    And i click update dog
    Then dog "DEFAULT" "breed edit" "MALE" is not updated

  @profile
  Scenario: As a user, I want to add a dog to my profile
    Given i am logged in
    And i am on my profile page
    When i click the +1
    And i enter the name "dog two"
    And i enter the breed "breed two"
    And i enter the birthday "10-01-2017"
    And i select gender "FEMALE"
    And i click save dog
    Then dog "dog two" is added

  @profile
  Scenario: As a user, I want to add a dog to my profile
    Given i am logged in
    And i am on my profile page
    When i click the +1
    And i enter the name "dog2"
    And i enter the breed "breed two"
    And i enter the birthday "10-01-2017"
    And i select gender "FEMALE"
    And i click save dog
    Then dog "dog2" is not added

  @profile
  Scenario: As a user, I want to add a dog to my profile
    Given i am logged in
    And i am on my profile page
    When i click the +1
    And i enter the name "dog two"
    And i enter the breed "breed2"
    And i enter the birthday "10-01-2017"
    And i select gender "FEMALE"
    And i click save dog
    Then dog "dog two" is not added

  @profile
  Scenario: As a user, I want to add a dog to my profile
    Given i am logged in
    And i am on my profile page
    When i click the +1
    And i enter the name "dog two"
    And i enter the breed "breed two"
    And i select gender "FEMALE"
    And i click save dog
    Then dog "dog two" is not added
