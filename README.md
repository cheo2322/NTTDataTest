# NTTData Test for Senior Developer

## Steps to Reproduce

Please, make sure you have these tools installed in your system:

- Java 17
- Maven, or use the maven wrapper into the project. Alternatively,
  you can use your IDE for `maven` tasks
- Docker (In Windows OS you need to install Docker Desktop)

1. Clone this repository in your local
2. Go to the root folder
3. Go to the person and account folders and `pacakge` the project
   ```bash
   cd person
   mvn clean package
   cd ..
   cd account
   mvn clean package
   ```
   
4. Go to the root folder and start the docker services
   ```bash
   docker compose up --build -d
   ```

5. Nice job! You can start to make request to the app.

## Use Cases

For operations, please check the Postman collection.

1. User creation

   Operation: POST Client

   Endpoint: `/nttdata/test/v1/clients`

   Request body:
    ```json
   {
   "name": "Juan Osorio",
   "gender": "MALE",
   "age": 29,
   "identification": "1234567893",
   "address": "13 de junio y Equinoccial",
   "phoneNumber": "098874587",
   "clientId": "josorio",
   "password": "1245",
   "status": "true"
   }
   ```

2. User Account creation

   Operation: POST Account

   Endpoint: `nttdata/test/v1/accounts`

   Request body:
   ```json
   {
   "accountNumber": "478758",
   "accountType": "SAVING",
   "initialBalance": 2000.0,
   "status": true,
   "clientId": "jlema"
   }
   ```
3. New Checking Account for Jose Lema

   Operation: POST Account

   Endpoint: `nttdata/test/v1/accounts`

   Request body:
   ```json
   {
   "accountNumber": "585545",
   "accountType": "CHECKING",
   "initialBalance": 1000.0,
   "status": true,
   "clientId": "jlema"
   }
   ```

4. Perform Movements

   Operation: POST Movement

   Endpoint: `/nttdata/test/v1/movements`

   Request body:
   ```json
   {
   "value": "-575.0",
   "accountNumber": "585545"
   }
   ```

5. Movements Report

   Operation: GET Report

   Endpoint: `nttdata/test/v1/reports?clientId=jlema&start=2025-03-29&end=2025-03-29`

   Response body:
   ```json
   [
    {
        "date": "2025-03-29",
        "client": "Jose Lema",
        "accountNumber": "585545",
        "type": "CHECKING",
        "initialBalance": 1000.0,
        "status": true,
        "movement": 1000.0,
        "availableFunds": 1000.0
    },
    {
        "date": "2025-03-29",
        "client": "Jose Lema",
        "accountNumber": "585545",
        "type": "CHECKING",
        "initialBalance": 1000.0,
        "status": true,
        "movement": -575.0,
        "availableFunds": 425.0
    }
   ]
   ```