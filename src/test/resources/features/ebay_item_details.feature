@item_details
Feature: eBay item details

  Scenario: Open first search result and validate details and shipping popup
    Given I open the eBay homepage
    When I search for "Monopoly" in category "Toys & Hobbies"
    Then the first result should display a price
    And click on the first item in order to navigate to details page
    And the title of the item should contain "Monopoly"
    And the item price should be the same as on the first page
    And I click on the "See details" link
    And the "Shipping, returns, and payments" popup should be displayed
    And the item can be shipped to "Bulgaria"