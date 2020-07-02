# wallet
CryptoCurrency Wallet Simulator with basic CRUD operations and crypto currency exchange with the help of CryptoCompare API.

### Dependencies
* [Google gson](https://github.com/google/gson) for parsing json objects.

### Supported end points
* End Points can be tested with curl command
* [Curl Doc](https://gist.github.com/subfuzion/08c5d85437d5d4f00e58) curl documentation.

|End Point|Description|
|---|---|
|curl -v localhost:8080/wallets|Get all the available wallet.|
|curl -v localhost:8080/wallets/2|Get the wallet with the specified id.|
|curl -v localhost:8080/allCoins|Get all the available coins.|
|curl -v localhost:8080/price/<fsym>/<tsyms>(ex. curl -v localhost:8080/price/ETH/BTC,USD,EUR)|Get the price for the single symbol.|
|curl -v localhost:8080/priceMulti/<fsyms>/<tsyms> (ex. curl -v localhost:8080/priceMulti/ETH,DASH/BTC,USD,EUR|Get the price for the multiple symbols.|
|curl -X POST localhost:8080/wallets -H "Content-type:application/json" -d "{\"name\": \"name\", \"address\": \"address\", \"noOfCoins\": \"noOfCoins\",\"balance\": \"balance\",\"coin\": \"coin type\"}" (ex. curl -X POST localhost:8080/wallets -H "Content-type:application/json" -d "{\"name\": \"Wallet4\", \"address\": \"London\", \"noOfCoins\": 2,\"balance\": 234.67,\"coin\": \"BTC\"}")|Create a new wallet with specified information|
|curl -X PUT localhost:8080/wallets/3 -H "Content-type:application/json" -d "{\"name\": \"name\", \"address\": \"address\", \"noOfCoins\": \"noOfCoins\",\"balance\": \"balance\",\"coin\": \"coin type\"}" (ex. curl -X PUT localhost:8080/wallets/3 -H "Content-type:application/json" -d "{\"name\": \"Wallet4\", \"address\": \"London\", \"noOfCoins\": 2,\"balance\": 234.67,\"coin\": \"BTC\"}")|Update the wallet with specified information.|
|curl -X DELETE localhost:8080/wallets/3|Delete the wallet with the specified id.|
|curl -X PUT localhost:8080/buyCoins/3/1|Buy coins for the wallet by obtaining the price using the Cryptocompare API.|
|curl -X PUT localhost:8080/transfer/1/3/45|Transfer the amount between two wallets.|
