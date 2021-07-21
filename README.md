***DERIBIT Simple API***

_**In this version of the app**_:

- You can call DERIBIT api to get users current balance and reserved funds for all their accounts
- The data for the account balance is then stored in a simple relational db
- Query the stored data using an endpoint I have exposed by passing the account email address
- You can also fetch the withdrawals and deposits history
- Deploy the app locally or using docker
- Run associated unit tests

_**To do**_:

- Withdraw from exchange to external crypto address (client resource is ready however internal mapping not done)
- Transfer between main account and sub account (client resource is ready however internal mapping not done)

_**Imporvements that could be made**_

- Testing will always be on top of the list, ideally an end-to-end test to check the happy path
    - unit testing of the mapper classes
    - mock servers to test client api such as pact broker
- I am not so sure about DERIBITS rest semantics and feel that they could use `POST` to embody
  secrets or sensitive info
- Refactor persistence layer to make better use of libraries which aid with DTO mapping
- Abstract class which can handle db queries in a cleaner fashion to encapsulate db calls
- Platform specific exception class

_**Load the app**_

_*Docker*_

Once you have cloned the repo cd into `deribit-api` directory then,

To begin:

`$ export DERIBIT_CLIENT_ID=ReplaceWithID`

`$ export DERIBIT_CLIENT_SECRET=ReplaceWithSecret`

Alternatively you can override these variables in the `docker-compose.yml` file.

Finally:

`$ docker compose up`

_*Locally*_

You will need to have a postgres instance running and ensure the port its listening to is on 5432.
Alternatively you can use the following to run postgres in a docker container:

`$ docker run --name some-postgres -p 5432:5432 -e POSTGRES_PASSWORD=test -d postgres`

then from the app root folder make sure you run:

`$ mvn clean install -DskipTests -DskipITs`

once you have a clean build you can then run this command

`$ ./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-Dderibit.client.id=ReplaceWithID -Dderibit.client.secret=ReplaceWithSecret"`

_**Using the app**_

* Get summary of the account balance which accepts a `boolean`

`$ curl localhost:8080/account/summaryList/{withPortfolio} | json_pp`

* Get wallet activity (default is 10 api can be improved to accept page pagination and request
  limit)

`curl localhost:8080/account/wallet/activity | json_pp`

* Call local db to retrieve account balance information (user email address)

`curl localhost:8080/account/user/{emailAddress} | json_pp`
