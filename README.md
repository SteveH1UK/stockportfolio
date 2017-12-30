# Stock Portfolio Application


## Github 
https://github.com/SteveH1UK/stockportfolio

## Summary

This project contains a service that exposes data for a customers “My stock portfolio” screen (this service could also be used as part of an api offer).



## Usage


## Assumptions and notes about this project
* The Free Rest API used does NOT have historic data so when queries are made then the value is of the current value (but with the amount of stock the customer has)
* The Stock Trading Ledger is a CSV file. This is (assumed) to be in ascending date order.
* No pagination
* Ignore any initial balance the customer has before the ledger starts. Therefore for some queries the Customer might have a negative amount of stock
* Stock sorted on stock code
* Integration tests require that the third party Rest service is up (no switch implemented to ignore these tests)



## TODO List

* ~~Working skeleton of non web project (improve knowledge of Spring Boot)~~  29 Jan
* ~~Store on github~~ 29 Jan
* ~~Create Repository for CSV and do “integration” testing for this using Apache CSV~~ 30 Jan
* ~~Raining day tests for CSV processing~~ 30 Jan
* ~~End to end test (skeleton)~~
* Code find prices with mock to give expectations for data
* Use free API rest service
* Review Code
