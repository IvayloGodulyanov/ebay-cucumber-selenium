Feature: eBay add to cart

  Scenario: Add two items to cart and validate quantity and price
    Given I open the eBay homepage
    When I search for "Monopoly" in category "Toys & Hobbies"
    Then the first result should display a price
    And click on the first item in order to navigate to details page
    And I select quantity "2"
    And I click Add to cart
    Then I should be on "https://cart.payments.ebay.com/"
    And in the Qty Drop Down List the quantity is "2"
    And the price is displayed for "2" items
