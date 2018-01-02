# Stock Portfolio Application


## Github 
https://github.com/SteveH1UK/stockportfolio

## Summary

This project contains a service that exposes data for a customers “My stock portfolio” screen (this service could also be used as part of an api offer).

The only validation the service does is to validate that the date is not null and it is between the start of the ledger and the current time. If the customer id does not exist on the sales ledger then there will be an empty list returned.

The stock prices are obtained using a free API - see https://iextrading.com/developer/docs/ for more details.

The stock ledger is implemented as a csv file within the source resources directory.

The input date has the following parameters:
*  1 = Closing date (e.g. 2017-01-03). This must be after the start of the ledger (2017-01-01) and must not be a future date
*  2 = Customer ID (e.g. 74893279)

The output is a data structure that has:
* 1 = Requested Customer ID
* 2 = Date and Time of the request
* 3 = Total sum of the customer's stocks (US $)
* 4 = A Set of customer stock (sorted in alphabetical order of the Stock names). Each record will have the stock name, number of stock and value of stock (US $)


## Usage
The following instructions cover running the project on linux:
```
git clone https://github.com/SteveH1UK/stockportfolio.git
cd stockportfolio
mvn package
java -jar target/stockportfolio-0.0.1-SNAPSHOT.jar 2017-01-03 74893279
```

Since this is  command line jar the results are output to the screen 

If this jar is required within another application (e.g. web front end)then call com.steveh1uk.stockportfolio.core.customer.CustomerStockService.findStockValues  


## Assumptions and notes about this project
* The Free Rest API used for the stock pricing does NOT have historic data so when queries are made then the value is of the current stock value (but with the amount of stock the customer had at the time of the query)
* Blackbury (BBRY) does not appear on the IPX API (Gives "Unknown symbol"). This system uses the code "BB" instead. Replaced BBRY with BB in the ledger file. 
* The Stock Trading Ledger is a CSV file. This is (assumed) to be in ascending date order.
* No pagination has been implemented
* There is not any initial balance the customer had before the ledger starts. Therefore for some queries the Customer might have a negative amount of stock
* Integration tests require that the third party Rest service is up 