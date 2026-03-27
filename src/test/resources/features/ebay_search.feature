Feature: eBay search

  Scenario: Search Monopoly in Toys & Hobbies and validate first result
    Given I open the eBay homepage
    When I search for "Monopoly" in category "Toys & Hobbies"
    Then the first result title should contain "Monopoly Board Game"
    And the first result shipping should contain "Free International Shipping"
    And the first result should display a price