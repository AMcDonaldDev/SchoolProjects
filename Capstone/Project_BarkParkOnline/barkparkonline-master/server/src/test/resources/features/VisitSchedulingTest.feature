Feature: Test that a User can Schedule Visits
  @visitscheduling
  Scenario: As a user, I want edit a visit
    Given the web server is online
    And "jonsnow" has an account
    And i have a visit scheduled on "2020-01-01" at "12:00"
    And i login as "jonsnow" with password "ghost4ever"
    When i go to the calendar page
    And i open the visit scheduler
    And i edit the visit
    And i change the visit to "2020-01-02" at "10:00"
    Then the visit has been changed to "2020-01-02" at "10:00"
    And the visit is unscheduled

  @visitscheduling
  Scenario: As a user, I try to edit a visit but provide an invalid date
    Given the web server is online
    And "jonsnow" has an account
    And i have a visit scheduled on "2020-01-01" at "12:00"
    And i login as "jonsnow" with password "ghost4ever"
    When i go to the calendar page
    And i open the visit scheduler
    And i edit the visit
    And i change the visit to "nogo-da-te" at "10:00"
    Then the visit is still "2020-01-01" at "12:00"
    And the visit is unscheduled

  @visitscheduling
  Scenario: As a user, I want to add a visit
    Given the web server is online
    And "jonsnow" has an account
    And i do not have a visit scheduled for "2020-01-01" at "12:00"
    And i login as "jonsnow" with password "ghost4ever"
    When i go to the calendar page
    And i select a random park
    And i open the visit scheduler form
    And i fill out the arrival datetime as "2020-01-01" at "12:00"
    And i have "Ghost" selected as a dog to bring
    And i press the add visit button
    Then my visit is listed on the profile of "jonsnow"
    And my visit is scheduled in the park
    And the visit is unscheduled

  @visitscheduling
  Scenario: As a user, I try to add a visit with an invalid date
    Given the web server is online
    And "jonsnow" has an account
    And i do not have a visit scheduled for "2020-01-01" at "12:00"
    And i login as "jonsnow" with password "ghost4ever"
    When i go to the calendar page
    And i select a random park
    And i open the visit scheduler form
    And i fill out the arrival datetime as "uhoh-no-go" at "12:00"
    And i have "Ghost" selected as a dog to bring
    And i press the add visit button
    Then my visit is not listed on the profile of "jonsnow"
    And my visit is not scheduled in the park

  @visitscheduling
  Scenario: As a user, I try to add a visit with an invalid time
    Given the web server is online
    And "jonsnow" has an account
    And i do not have a visit scheduled for "2020-01-01" at "12:00"
    And i login as "jonsnow" with password "ghost4ever"
    When i go to the calendar page
    And i select a random park
    And i open the visit scheduler form
    And i fill out the arrival datetime as "2020-01-01" at "no:go"
    And i have "Ghost" selected as a dog to bring
    And i press the add visit button
    Then my visit is not listed on the profile of "jonsnow"
    And my visit is not scheduled in the park

  @visitscheduling
  Scenario: As a user, I try to add a visit without selecting a dog
    Given the web server is online
    And "jonsnow" has an account
    And i do not have a visit scheduled for "2020-01-01" at "12:00"
    And i login as "jonsnow" with password "ghost4ever"
    When i go to the calendar page
    And i select a random park
    And i open the visit scheduler form
    And i fill out the arrival datetime as "2020-01-01" at "no:go"
    And i have selected no dog to bring
    And i press the add visit button
    Then my visit is not listed on the profile of "jonsnow"
    And my visit is not scheduled in the park

  @visitscheduling
  Scenario: As a user, I want to view my scheduled visits
    Given the web server is online
    And "jonsnow" has an account
    And i have a visit scheduled on "2020-01-01" at "12:00"
    And i login as "jonsnow" with password "ghost4ever"
    When i go to the calendar page
    And i open the visit scheduler
    Then i see my visit
    And the visit is unscheduled

  @visitscheduling
  Scenario: As a user, I want delete a visit
    Given the web server is online
    And "jonsnow" has an account
    And i have a visit scheduled on "2020-01-01" at "12:00"
    And i login as "jonsnow" with password "ghost4ever"
    When i go to the calendar page
    And i open the visit scheduler
    And i edit the visit
    And i delete the visit
    Then my visit is not listed on the profile of "jonsnow"
    And my visit is not scheduled in the park
    And i do not see my visit
    And the visit is unscheduled

