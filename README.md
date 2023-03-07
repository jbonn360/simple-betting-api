# Simple Betting API
## Run the app
To run the application, run the main function in the Kotlin source file 'SimpleBettingApiApplication.kt'. This file is present in the following directory: '../simple-betting-api/src/main/kotlin/com/betting/simplebettingapi/'

Alternatively, the application can also be built into a JAR file and run with the following command: 

    java -jar simple-betting-api.jar

By default, the application binds to port 8080.

Requirements: JRE 11+, Kotlin 1.7.0+

## REST Endpoints

## Create a new Account

### Request

`POST /api/v1/account`

    curl -i -H 'Accept: application/json' -d 'username=Username1&name=George&surname=Borg' http://localhost:8080/api/v1/account

## Get Account Details

### Request

`GET /api/v1/account/accountId`

    curl -i -H 'Accept: application/json' http://localhost:8080/api/v1/account/1

## Get List of Transaction for an Account

### Request

`GET /api/v1/transactions/account/accountId`

    curl -i -H 'Accept: application/json' http://localhost:8080/api/v1/account/1

## Place a Bet

### Request

`POST /api/v1/bets/account/accountId`

    curl -i -H 'Accept: application/json' -d 'betAmount=200&numberBetOn=5' http://localhost:8080/api/v1/bets/account/1

## Get Bet Details

### Request

`GET /api/v1/bets/betId`

    curl -i -H 'Accept: application/json' http://localhost:8080/api/v1/bets/1

## Get List of Bets for an Account

### Request

`GET /api/v1/bets/account/accountId`

    curl -i -H 'Accept: application/json' http://localhost:8080/api/v1/bets/account/1

## Get Leaderboard

### Request

`POST /api/v1/leaderboard`

    curl -i -H 'Accept: application/json' -d 'size=5' http://localhost:8080/api/v1/leaderboard
