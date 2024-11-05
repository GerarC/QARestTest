#language: en
# Author: Check-in Team
# language: en

Feature: As a customer, I need to get information about one specific product

  Scenario: get information about one product
    Given I'm connected as a customer
    When I request information about product with id 1
    Then I can see information of the product
      | id | title                                                 | category       | description                                                                                                                         | image                                                    |
      | 1  | Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops | men's clothing | Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday | https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg |