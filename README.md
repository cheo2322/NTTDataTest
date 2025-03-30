# NTTData Test for Senior Developer

## Use Cases

For operations, please check the Postman collection.

1. User creation

   Operation: POST Client

   Endpoint: /nttdata/test/v1/clients

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

   Endpoint: nttdata/test/v1/accounts

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

   Endpoint: nttdata/test/v1/accounts

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

   Endpoint: /nttdata/test/v1/movements

   Request body:
   ```json
   {
   "value": "-575.0",
   "accountNumber": "585545"
   }
   ```

5. Movements Report

   Operation: GET Report

   Endpoint: nttdata/test/v1/reports?clientId=jlema&start=2025-03-29&end=2025-03-29

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