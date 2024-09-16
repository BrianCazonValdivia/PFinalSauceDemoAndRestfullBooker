Feature: Employees endpoint
  Background: Employees endpoints should allow to get, create, update and delete employees

    Scenario: /employees should return all the employees
      Given I perform a GET call to the employees endpoint
      Then I verify that the status code is 200
      And I verify that the body does not have size 0

      Scenario: /employee/{id} should return an specific employee
        Given I perform a GET call to the employees endpoint with id "1"
        Then I verify that the status code is 200
        And The message is "Successfully! Record has been fetched."
        And The employee name is "Tiger Nixon"
        And The employee age is 61
        And The employee salary is 320800

        Scenario Outline: /create should create an employee
          Given I perform a POST call to the create endpoint with the following data
            | <name> | <age> | <salary> |
          Then I verify that the status code is 200
          And I verify that the body does not have size 0
          And The message is "Successfully! Record has been added."
          And I verify the following data in the body response
            | <name> | <age> | <salary> |
          Examples:
            | name     | age | salary |
            | Mauricio | 28  | 6000   |
            | Jorge    | 48  | 3500   |