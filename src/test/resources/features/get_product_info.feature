#language: en
# Author: Check-in Team
# language: en

Feature: As a customer, I need to get information of available products

  Scenario: get information about the product
    Given I connect to the service
    When I get information about all available products
    Then I can see a list of products with their information

  Scenario: Limit the quantity of product information
    Given I connect to the service
    When I request a list of products with a limit of 5
    Then I receive a list of products with exactly 5 items
