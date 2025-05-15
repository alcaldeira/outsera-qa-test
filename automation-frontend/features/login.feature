@login
Feature: Login

  Scenario: User logs in successfully with valid credentials
    Given the user is on the login page
    When they login with username "standard_user" and password "secret_sauce"
    Then they should see the products page

  Scenario: User logs in with invalid credentials
    Given the user is on the login page
    When they login with username "locked_out_user" and password "1221"
    Then they should see the error message "Epic sadface: Username and password do not match any user in this service"

  Scenario: Login attempt with empty username
    Given the user is on the login page
    When they login with username "" and password "1221"
    Then they should see the error message "Epic sadface: Username is required"