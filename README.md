# EasySendSMS REST API (v1)

The [EasySendSMS REST API](https://www.easysendsms.com/) offers a comprehensive set of tools designed to handle a wide range of communication tasks. Beyond sending and receiving SMS messages, our API supports HLR lookup for real-time number verification, number validation services, and much more. This powerful API is built for precision and reliability, providing all the necessary features to integrate advanced communication capabilities directly into your applications.

## Authentication

To authenticate requests to the [EasySendSMS REST API](https://www.easysendsms.com/rest-api), you need an API key, available in the EasySendSMS Customer Dashboard under the API settings section.

Include your API key in the request header using the `apikey` field. This authentication step is mandatory for all API requests.

## Request Headers

When making a request, include the following headers:

- `apikey: YOUR_API_KEY`
- `Content-Type: application/json`
- `Accept: application/json`

## Send SMS REST API

The Send SMS API is a versatile and robust solution designed to enable seamless integration of SMS messaging capabilities into your applications, websites, or services. This API allows you to send text messages to mobile phones globally with just a few lines of code, making it an essential tool for businesses looking to enhance communication with their customers, employees, or users.

### Why Use the Send SMS API?

- **Global Reach**: Send SMS messages to recipients worldwide, ensuring that your communication reaches your audience wherever they are.
- **Real-Time Messaging**: Deliver time-sensitive information instantly, making it ideal for alerts, notifications, and updates.
- **Scalability**: Whether you need to send a single message or thousands, the API scales effortlessly to meet your needs.
- **Customization**: Personalize messages with sender names, customize message content, and even send messages in different formats, such as Unicode, to support various languages.
- **Reliable Delivery**: Benefit from high delivery rates and robust infrastructure that ensures your messages are delivered reliably and efficiently.

### Message Type Support

The [EasySendSMS REST API](https://www.easysendsms.com/rest-api) supports various message types and can handle multiple recipients in a single request, ensuring your messages are delivered promptly and reliably.

- **Base URL**: `https://restapi.easysendsms.app/v1/rest/sms/send`
- **Method**: `POST`

### Request Schema

- **Content Type**: `application/json` (for POST)

All requests to the EasySendSMS API should use the `application/json` content type and the `POST` method. Ensure that the request body is in JSON format and that you use raw JSON data in your POST requests. We do not support the `GET` method. All data must be sent in the request body as JSON when using the `POST` method.

#### Required Parameters

| Parameter | Description | Presence |
|-----------|-------------|----------|
| `from` | Sender Name that the message will appear from. Max Length of 15 if numeric. Max Length of 11 if alphanumeric. To prefix the plus sign (“+”) to the sender's address when the message is displayed on their cell phone, please prefix the plus sign to your sender's address while submitting the message (note the plus sign should be URL encoded). Additional restrictions on this field may be enforced by the SMSC. | Required |
| `to` | Mobile number of the recipient that the message will be sent to, e.g., 19876543210 (Do not use + or 00 before the country code). You can use a comma in the `to` parameter to send to multiple numbers, with a maximum of 30 numbers in each request. | Required |
| `text` | The message to be sent. It can be English as plain text or any other language as Unicode, max message length 5 parts. For concatenated (long) messages, every 153 characters are counted as one message for plain text and 67 characters for Unicode, as the rest of the characters will be used by the system for packing extra information for re-assembling the message on the cell phone. | Required |
| `type` | Indicates the type of message. Values for type include: 0: Plain text (GSM 3.38 Character encoding) 1: Unicode (For any other language) | Required |
| `scheduled` | The scheduled date and time for sending the message, formatted in ISO 8601. Example: '2023-12-31T19:35:00'. The time zone used is UTC, so please ensure that the date and time are provided according to UTC. | Optional |

### API Rate Limit

To maintain a high quality of service to all customers, EasySendSMS API applies rate limits for its SMS API. The default request rate limit is 30 requests per second per account and can reach up to 150 requests per second per IP address (contact our support if you wish to have that).

The API will reject all requests exceeding this rate limit with a `429 Too Many Requests` HTTP Status.

### Bulk SMS Sending

A request containing multiple destination numbers will be aborted immediately if any error other than "Invalid mobile number" [Code: 4012] is encountered.

If an "Invalid mobile number" [Code: 4012] is found, that destination number will be skipped, and the request will proceed with the next number.

A maximum of 30 numbers can be submitted per request. Duplicate numbers will be ignored.

### Send SMS REST API Error Codes

| Code | Description | HTTP Status |
|------|-------------|-------------|
| 4001 | One or more required parameters are missing. | 400 |
| 4002 | No API key found in request. | 401 |
| 4003 | Invalid API Key. | 401 |
| 4004 | Invalid IP address. | 403 |
| 4005 | Inactive API Key. | 403 |
| 4006 | Inactive Account. | 403 |
| 4007 | Demo Account Expired. | 403 |
| 4008 | Internal error (do NOT re-submit the same request again). | 500 |
| 4009 | Service not available (do NOT re-submit the same request again). | 503 |
| 4010 | Invalid type parameter. | 400 |
| 4011 | Invalid message. | 400 |
| 4012 | Invalid mobile number. | 400 |
| 4013 | Too many recipients. | 400 |
| 4014 | Invalid sender name. | 400 |
| 4015 | Insufficient credits. | 402 |
| 4016 | Country / network not available. | 400 |
| 4017 | Invalid scheduled datetime format or scheduled time is in the past. | 400 |
| 405 | Method not allowed. | 405 |
| 415 | Unsupported Media Type. | 415 |

### Example Request

```bash
curl -X POST \
-H "apikey: YOUR_API_KEY" \
-H "Content-Type: application/json" \
-H "Accept: application/json" \
-d '{
    "from": "YourSenderName",
    "to": "12345678900,19876543210",
    "text": "Hello, this is a test message!",
    "type": "0"
}' \
"https://restapi.easysendsms.app/v1/rest/sms/send"
```

### Example Error Response

```json
{
    "error": 4012,
    "description": "Invalid mobile number."
}
```

### Example Success Response

```json
{
    "status": "OK",
    "scheduled": "Now",
    "messageIds": [
        "OK: 69991a73-a560-429f-9c5a-3251dc1522bb"
    ]
}
```

### Example Partial Success Response

```json
{
    "status": "OK",
    "scheduled": "Now",
    "messageIds": [
        "ERR: 4012,OK: 87b021d1-0f21-4c13-924a-65699dcde79e"
    ]
}
```

## SMS Balance REST API

This REST API endpoint allows you to request and check your SMS account balance. To retrieve your balance, the client can issue either a `GET` or `POST` request to the [EasySendSMS REST API](https://www.easysendsms.com/rest-api). The request must include the required `APIKEY` header, which contains your API key.

This key can be found in the "Account Settings" under the "REST API" section. Upon successful submission, our system will return a JSON-formatted response that provides the current account balance. Additionally, if the request is successful, the response will include a status code indicating the success of the operation.

- **Base URL**: `https://restapi.easysendsms.app/v1/rest/sms/balance`
- **Method**: `GET` `POST`

### Request Schema

- **Content Type**: `application/x-www-form-urlencoded` (for POST Form Data)
- **Query String**: (for GET or URL parameters in POST)

### API Rate Limit

To maintain a high quality of service to all customers, EasySendSMS API applies rate limits for its Balance API. The default request rate limit is 2 requests per minute per account per IP address.

The API will reject all requests exceeding this rate limit with a `429 Too Many Requests` HTTP Status. You can retry the request after 60 seconds.

### SMS Balance REST API Error Codes

| Code | Description | HTTP Status |
|------|-------------|-------------|
| 4001

 | One or more required parameters are missing. | 400 |
| 4002 | No API key found in request. | 401 |
| 4003 | Invalid API Key. | 401 |
| 4004 | Invalid IP address. | 403 |
| 4005 | Inactive API Key. | 403 |
| 4006 | Inactive Account. | 403 |
| 4007 | Demo Account Expired. | 403 |
| 4008 | Internal error (do NOT re-submit the same request again). | 500 |
| 4009 | Service not available (do NOT re-submit the same request again). | 503 |

### Example Error Response

```json
{
    "error": 4005,
    "description": "Invalid API Key."
}
```

### Example Success Response

```json
{
    "balance": 247247
}
```

## HLR Query REST API

The HLR (Home Location Register) Query API is a powerful tool designed to help users verify and retrieve real-time information about mobile numbers. By using the HLR Query API, users can determine the current status and location of a mobile number, including whether the number is active, which network it belongs to, and whether it has been ported to a different network.

### Why Use the HLR Query API?

- **Enhance Message Delivery**: Ensure that SMS messages are sent to valid and active numbers, reducing the number of failed deliveries.
- **Cost Efficiency**: Avoid sending messages to inactive or non-existent numbers, saving costs on undelivered messages.
- **Up-to-Date Information**: Receive real-time data on the status of mobile numbers, including ported numbers and roaming information, ensuring accurate targeting and communication.

### Request Schema

- **Base URL**: `https://restapi.easysendsms.app/v1/rest/hlr/query`
- **Method**: `POST`
- **Content Type**: `application/json` (for POST)

All requests to the EasySendSMS API should use the `application/json` content type and the `POST` method. Ensure that the request body is in JSON format and that you use raw JSON data in your POST requests.

#### Required Parameters

| Parameter | Description | Presence |
|-----------|-------------|----------|
| `number` | Subscriber's MSISDN to be checked, e.g., 19876543210 (Do not use + or 00 before the country code). Multiple numbers can be queried using commas in the number parameter, with a maximum of 30 numbers per request. | Required |

### API Rate Limit

To maintain a high quality of service to all customers, EasySendSMS API applies rate limits for its HLR API. The default request rate limit is 30 requests per second per account and can reach up to 150 requests per second per IP address (contact our support if you wish to have that).

The API will reject all requests exceeding this rate limit with a `429 Too Many Requests` HTTP Status.

### Bulk HLR Query

A request containing multiple destination numbers will be aborted immediately if any error other than "Invalid Number Parameter" [Code: 4010] is encountered. If an "Invalid Number Parameter" [Code: 4010] is found, that destination number will be skipped, and the request will proceed with the next number.

A maximum of 30 numbers can be submitted per request. Duplicate numbers will be ignored.

### HLR REST API Error Codes

| Code | Description | HTTP Status |
|------|-------------|-------------|
| 4001 | One or more required parameters are missing. | 400 |
| 4002 | No API key found in request. | 401 |
| 4003 | Invalid API Key. | 401 |
| 4004 | Invalid IP address. | 403 |
| 4005 | Inactive API Key. | 403 |
| 4006 | Inactive Account. | 403 |
| 4007 | Demo Account Expired. | 403 |
| 4008 | Internal error (do NOT re-submit the same request again). | 500 |
| 4009 | Service not available (do NOT re-submit the same request again). | 503 |
| 4010 | Invalid Number Parameter. | 400 |
| 4011 | Invalid HLR response. | 500 |
| 4012 | Insufficient credits. | 402 |
| 405 | Method not allowed. | 405 |
| 415 | Unsupported Media Type. | 415 |

### Example Request

```bash
curl -X POST \
-H "apikey: YOUR_API_KEY" \
-H "Content-Type: application/json" \
-H "Accept: application/json" \
-d '{
    "number": "19876543210"
}' \
"https://restapi.easysendsms.app/v1/rest/hlr/query"
```

### Example Error Response

```json
{
    "error": 4010,
    "description": "Invalid Number Parameter."
}
```

### Example Success Response

```json
{
    "12345678900": {
        "number": "12345678900",
        "country": "United States of America",
        "err_desc": "Live",
        "operatorName": "First Communications",
        "type": "Landline",
        "mccmnc": "-",
        "roaming": "False",
        "err_code": "0",
        "status": "Delivered",
        "ported": "True"
    }
}
```

### Example Partial Success Response

```json
{
    "12345678900": {
        "number": "12345678900",
        "country": "United States of America",
        "err_desc": "Live",
        "operatorName": "First Communications",
        "type": "Landline",
        "mccmnc": "-",
        "roaming": "False",
        "err_code": "0",
        "status": "Delivered",
        "ported": "True"
    },
    "611111": {
        "number": "611111",
        "country": "",
        "err_desc": "Number Not Supported",
        "operatorName": "Unknown",
        "type": "",
        "mccmnc": "",
        "roaming": "",
        "err_code": "999",
        "status": "Undelivered",
        "ported": ""
    }
}
```

## Number Validation (NV) Query REST API

The Number Validation (NV) Query is an essential tool designed to verify the validity and status of phone numbers before sending SMS messages or making calls. By using the NV Query, you can ensure that the phone numbers in your database are accurate, active, and formatted correctly, which helps reduce costs associated with failed message deliveries and improves overall communication efficiency.

### Why Use the Number Validation (NV) Query?

- **Accurate Number Verification**: Confirm whether a phone number is valid, active, and correctly formatted, ensuring that your messages reach the intended recipients.
- **Cost Efficiency**: Avoid unnecessary expenses by identifying and removing invalid or inactive numbers from your contact lists before sending messages.
- **Improved Delivery Rates**: By validating numbers in advance, you increase the likelihood of successful message delivery, improving customer engagement and satisfaction.
- **Enhanced Data Quality**: Regular validation helps maintain a clean and up-to-date contact database, which is critical for effective communication and marketing efforts.
- **Compliance Assurance**: Ensure that phone numbers comply with local and international dialing standards, helping you meet regulatory requirements.

### Request Schema

- **Base URL**: `https://restapi.easysendsms.app/v1/rest/nv/query`
- **Method**: `POST`
- **Content Type**: `application/json` (for POST)

All requests to the EasySendSMS API should use the `application/json` content type and the `POST` method. Ensure that the request body is in JSON format and that you use raw JSON data in your POST requests.

#### Required Parameters

| Parameter | Description | Presence |
|-----------|-------------|----------|
| `number` | Subscriber's MSISDN to be checked, e.g., 19876543210 (Do not use + or 00 before the country code). Multiple numbers can be queried using commas in the number parameter, with a maximum of 30 numbers per request. | Required |

### API Rate Limit

To maintain a high quality of service to all customers, EasySendSMS API applies rate limits for its NV API. The default request rate limit is 30 requests per second per account and can reach up to 150 requests per second per IP address (contact our support if you wish to have that).

The API will reject all requests exceeding this rate limit with a `429 Too Many Requests` HTTP Status.

### Bulk NV Query

A request containing multiple destination numbers will be aborted immediately if any error other than "Invalid Number Parameter" [Code: 4010] is encountered. If an "Invalid Number Parameter" [Code: 4010] is found, that destination number will be skipped, and the request will proceed with the next number.

A maximum of 30 numbers can be submitted per request. Duplicate numbers will be ignored.

### NV REST API Error Codes

| Code | Description | HTTP Status |
|------|-------------|-------------|
| 4001 | One or more required parameters are missing. | 400 |
| 4002 | No API key found in request. | 401 |
| 4003 | Invalid API Key. | 401 |
| 4004 | Invalid IP address. | 403 |
| 4005 | Inactive API Key. | 403 |
| 4006 | Inactive Account. | 403 |
| 4007 | Demo Account Expired. | 403 |
| 4008 | Internal error (do NOT re-submit the same request again). |

 500 |
| 4009 | Service not available (do NOT re-submit the same request again). | 503 |
| 4010 | Invalid Number Parameter. | 400 |
| 4011 | Invalid NV response. | 500 |
| 4012 | Insufficient credits. | 402 |
| 405 | Method not allowed. | 405 |
| 415 | Unsupported Media Type. | 415 |

### Example Request

```bash
curl -X POST \
-H "apikey: YOUR_API_KEY" \
-H "Content-Type: application/json" \
-H "Accept: application/json" \
-d '{
    "number": "19876543210"
}' \
"https://restapi.easysendsms.app/v1/rest/nv/query"
```

### Example Error Response

```json
{
    "error": 4010,
    "description": "Invalid Number Parameter."
}
```

### Example Success Response

```json
{
    "results": [
        {
            "status": "VALID",
            "country": "United States of America",
            "iso3166_2": "US",
            "cc": "1",
            "netName": "",
            "mcc": "310",
            "mnc": "316",
            "operator": "",
            "type": "FIXED",
            "netType": "",
            "msisdn": "12345678900"
        }
    ]
}
```

### Example Partial Success Response

```json
{
    "results": [
        {
            "status": "VALID",
            "country": "United States of America",
            "iso3166_2": "US",
            "cc": "1",
            "netName": "",
            "mcc": "310",
            "mnc": "316",
            "operator": "",
            "type": "FIXED",
            "netType": "",
            "msisdn": "123456789001"
        },
        {
            "status": "INVALID",
            "country": "",
            "iso3166_2": "",
            "cc": "",
            "netName": "",
            "mcc": "",
            "mnc": "",
            "operator": "",
            "type": "",
            "netType": "",
            "msisdn": "61111"
        }
    ]
}
```
