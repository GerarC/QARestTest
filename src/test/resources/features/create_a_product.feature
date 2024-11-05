#language: en
# Author: Check-in Team
# language: en

Feature: As a supplier, I need to create new products

  Scenario: Create a new product
    Given I'm connected as a supplier
    When I create a new product with the following details
      | title     | price  | category    | description    | image                        |
      | Product A | 100.0  | Electronics  | Sample product  | https://example.com/image.jpg |
    Then the product is successfully created with these details
      | title     | price  | category    | description    | image                        |
      | Product A | 100.0  | Electronics  | Sample product  | https://example.com/image.jpg |
