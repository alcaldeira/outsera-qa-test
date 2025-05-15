Feature: Checkout functionality

@debug
  Scenario: Complete a checkout successfully
    Given the user is logged in
    When the user adds an item to the cart
    And proceeds to checkout with first name "John", last name "Doe", and postal code "12345"
    Then the checkout should be completed successfully