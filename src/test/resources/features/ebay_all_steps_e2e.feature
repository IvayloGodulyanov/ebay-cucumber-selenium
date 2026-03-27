@all_steps_e2e
Feature: eBay end-to-end with all existing steps

  Scenario: Execute all existing steps in one flow
    Given I open the eBay homepage
    When I search for "Monopoly" in category "Toys & Hobbies"
    Then the first result title should contain "Monopoly Board Game"
    And the first result shipping should contain "Free International Shipping"
    And the first result should display a price
    Then click on the first item in order to navigate to details page
    And the title of the item should contain "Monopoly"
    And the item price should be the same as on the first page
    And I click on the "See details" link
    And the "Shipping, returns, and payments" popup should be displayed
    And the item can be shipped to "Bulgaria"
    And I select quantity "2"
    And I click Add to cart
    Then I should be on "https://cart.payments.ebay.com/"
    And in the Qty Drop Down List the quantity is "2"
    And the price is displayed for "2" items