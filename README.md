# EasySendSMS REST API (v1)

## Overview

The [EasySendSMS REST API](https://www.easysendsms.com/) offers a comprehensive set of tools designed to handle a wide range of communication tasks. Beyond sending and receiving SMS messages, our API supports HLR lookup for real-time number verification, number validation services, and much more. This powerful API is built for precision and reliability, providing all the necessary features to integrate advanced communication capabilities directly into your applications.


---

## Code Examples

EasySendSMS provides **ready-to-run examples in multiple programming languages** to help developers integrate the API quickly. Each example is self-contained, includes detailed setup instructions, and demonstrates how to authenticate, send messages, and handle API responses.

| Language | Description | View on GitHub |
| :--- | :--- | :--- |
| **C#** | A .NET 8 console application with zero external dependencies, using the built-in `HttpClient`. | [![C#](https://img.shields.io/badge/C%23-512BD4?logo=csharp&logoColor=white)](https://github.com/EasySendSMS/REST-API-v1/tree/main/examples/csharp) |
| **Java** | A Maven project for Java 11+ using the built-in `java.net.http.HttpClient` with no external libraries required. | [![Java](https://img.shields.io/badge/Java-ED8B00?logo=openjdk&logoColor=white)](https://github.com/EasySendSMS/REST-API-v1/tree/main/examples/java) |
| **Python** | A Python 3.9+ script using the standard `requests` library, organized into a dedicated client class. | [![Python](https://img.shields.io/badge/Python-3776AB?logo=python&logoColor=white)](https://github.com/EasySendSMS/REST-API-v1/tree/main/examples/python) |
| **PHP** | A PHP 8.0+ script with no Composer dependencies, using the built-in cURL extension. | [![PHP](https://img.shields.io/badge/PHP-777BB4?logo=php&logoColor=white)](https://github.com/EasySendSMS/REST-API-v1/tree/main/examples/php) |

### Quick Start

Clone the repository and run your preferred language example:

```bash
# 1. Clone the repository
git clone https://github.com/EasySendSMS/REST-API-v1.git

# 2. Navigate to an example directory (e.g., Python)
cd REST-API-v1/examples/python

# 3. Follow the instructions in the example's README.md file
pip install -r requirements.txt
export EASYSENDSMS_API_KEY="YOUR_API_KEY"
python send_sms.py
```



---

## Base URL

The base URL for all API endpoints is:

```
https://restapi.easysendsms.app
```

| Endpoint | Full URL |
| --- | --- |
| Send SMS | `https://restapi.easysendsms.app/v1/rest/sms/send` |
| SMS Balance | `https://restapi.easysendsms.app/v1/rest/sms/balance` |
| HLR Query | `https://restapi.easysendsms.app/v1/rest/hlr/query` |
| Number Validation (NV) | `https://restapi.easysendsms.app/v1/rest/nv/query` |

---

## Authentication

To authenticate requests to the [EasySendSMS REST API](https://www.easysendsms.com/rest-api), you need an API key, available in the EasySendSMS Customer Dashboard under the API settings section.

Include your API key in the request header using the `apikey` field. This authentication step is mandatory for all API requests.

---

## Request Headers

When making a request, include the following headers:

| Header | Value |
| --- | --- |
| `apikey` | `YOUR_API_KEY` |
| `Content-Type` | `application/json` |
| `Accept` | `application/json` |

---

## Rate Limits

To ensure a high quality of service for all customers, the EasySendSMS API applies rate limits across all endpoints. If you exceed the rate limit, the API will return an HTTP `429 Too Many Requests` status code.

| Endpoint | Default Rate Limit | Maximum Rate Limit |
| --- | --- | --- |
| Send SMS | 30 requests/second per account | Up to 150 requests/second per IP (contact support) |
| SMS Balance | 2 requests/minute per account per IP | -- |
| HLR Query | 30 requests/second per account | Up to 150 requests/second per IP (contact support) |
| Number Validation (NV) | 30 requests/second per account | Up to 150 requests/second per IP (contact support) |

> For the SMS Balance endpoint, you can retry the request after 60 seconds if rate limited.

---

# Endpoints

---

## 1. Send SMS

The Send SMS API is a versatile and robust solution designed to enable seamless integration of SMS messaging capabilities into your applications, websites, or services. This API allows you to send text messages to mobile phones globally with just a few lines of code, making it an essential tool for businesses looking to enhance communication with their customers, employees, or users.

#### Why Use the Send SMS API?

- **Global Reach**: Send SMS messages to recipients worldwide, ensuring that your communication reaches your audience wherever they are.
- **Real-Time Messaging**: Deliver time-sensitive information instantly, making it ideal for alerts, notifications, and updates.
- **Scalability**: Whether you need to send a single message or thousands, the API scales effortlessly to meet your needs.
- **Customization**: Personalize messages with sender names, customize message content, and even send messages in different formats, such as Unicode, to support various languages.
- **Reliable Delivery**: Benefit from high delivery rates and robust infrastructure that ensures your messages are delivered reliably and efficiently.

### Endpoint

```
POST /v1/rest/sms/send
```

### Request Headers

| Header | Value |
| --- | --- |
| `apikey` | `YOUR_API_KEY` |
| `Content-Type` | `application/json` |
| `Accept` | `application/json` |

### Request Body Parameters

All requests to the EasySendSMS API should use the `application/json` content type and the `POST` method. Ensure that the request body is in JSON format and that you use raw JSON data in your POST requests. We do not support the `GET` method. All data must be sent in the request body as JSON when using the `POST` method.

| Parameter | Description | Presence |
|-----------|-------------|----------|
| `from` | Sender Name that the message will appear from. Max Length of 15 if numeric. Max Length of 11 if alphanumeric. To prefix the plus sign ("+") to the sender's address when the message is displayed on their cell phone, please prefix the plus sign to your sender's address while submitting the message (note the plus sign should be URL encoded). Additional restrictions on this field may be enforced by the SMSC. | Required |
| `to` | Mobile number of the recipient that the message will be sent to, e.g., 19876543210 (Do not use + or 00 before the country code). You can use a comma in the `to` parameter to send to multiple numbers, with a maximum of 30 numbers in each request. | Required |
| `text` | The message to be sent. It can be English as plain text or any other language as Unicode, max message length 5 parts. For concatenated (long) messages, every 153 characters are counted as one message for plain text and 67 characters for Unicode, as the rest of the characters will be used by the system for packing extra information for re-assembling the message on the cell phone. | Required |
| `type` | Indicates the type of message. Values for type include: 0: Plain text (GSM 3.38 Character encoding) 1: Unicode (For any other language) | Required |
| `scheduled` | The scheduled date and time for sending the message, formatted in ISO 8601. Example: '2023-12-31T19:35:00'. The time zone used is UTC, so please ensure that the date and time are provided according to UTC. | Optional |

#### Message Type Support

The [EasySendSMS REST API](https://www.easysendsms.com/rest-api) supports various message types and can handle multiple recipients in a single request, ensuring your messages are delivered promptly and reliably.

#### Bulk SMS Sending

A request containing multiple destination numbers will be aborted immediately if any error other than "Invalid mobile number" [Code: 4012] is encountered.

If an "Invalid mobile number" [Code: 4012] is found, that destination number will be skipped, and the request will proceed with the next number.

A maximum of 30 numbers can be submitted per request. Duplicate numbers will be ignored.

## SMS Send API Examples

Below are example requests and responses for sending SMS messages using the EasySendSMS REST API.

---

### Example 1: Standard Text Message (Single Recipient)

This example shows how to send a standard plain text message to a single mobile number.

#### Request

```bash
curl -X POST \
-H "apikey: YOUR_API_KEY" \
-H "Content-Type: application/json" \
-H "Accept: application/json" \
-d '{
    "from": "SenderName",
    "to": "12345678900",
    "text": "Hello, this is a test message!",
    "type": "0"
}' \
"https://restapi.easysendsms.app/v1/rest/sms/send"
```

#### Success Response

```json
{
    "status": "OK",
    "scheduled": "Now",
    "messageIds": [
        "OK: 69991a73-a560-429f-9c5a-3251dc1522bb"
    ]
}
```

---

### Example 2: Unicode Message (Arabic)

This example demonstrates how to send a message containing Unicode characters, such as Arabic. Note that the `type` parameter is set to `1`.

#### Request

```bash
curl -X POST \
-H "apikey: YOUR_API_KEY" \
-H "Content-Type: application/json" \
-H "Accept: application/json" \
-d '{
    "from": "SenderName",
    "to": "12345678900",
    "text": "مرحبا، هذه رسالة اختبار!",
    "type": "1"
}' \
"https://restapi.easysendsms.app/v1/rest/sms/send"
```

#### Success Response

```json
{
    "status": "OK",
    "scheduled": "Now",
    "messageIds": [
        "OK: 8f3d9b7a-5c1e-4b9a-8d2f-1c7e9a0b3d1c"
    ]
}
```

---

### Example 3: Scheduled Message

This example shows how to schedule an SMS to be sent at a future date and time. The `scheduled` parameter must be a valid ISO 8601 timestamp in UTC.

#### Request

```bash
curl -X POST \
-H "apikey: YOUR_API_KEY" \
-H "Content-Type: application/json" \
-H "Accept: application/json" \
-d '{
    "from": "SenderName",
    "to": "12345678900",
    "text": "This is a scheduled message for the team meeting.",
    "type": "0",
    "scheduled": "2026-03-15T10:00:00Z"
}' \
"https://restapi.easysendsms.app/v1/rest/sms/send"
```

#### Success Response

```json
{
    "status": "OK",
    "scheduled": "2026-03-15T10:00:00Z",
    "messageIds": [
        "OK: a1b2c3d4-e5f6-7890-1234-567890abcdef"
    ]
}
```

---

### Example 4: Bulk Message (Multiple Recipients)

This example shows how to send the same message to multiple recipients in a single API call. The `to` parameter accepts a comma-separated list of up to 30 phone numbers.

#### Request

```bash
curl -X POST \
-H "apikey: YOUR_API_KEY" \
-H "Content-Type: application/json" \
-H "Accept: application/json" \
-d '{
    "from": "PromoAlert",
    "to": "12345678901,12345678902,12345678903",
    "text": "Flash sale ends tonight! Use code SAVE50 for 50% off.",
    "type": "0"
}' \
"https://restapi.easysendsms.app/v1/rest/sms/send"
```

#### Success Response

```json
{
    "status": "OK",
    "scheduled": "Now",
    "messageIds": [
        "OK: 1e1a7c30-a160-429f-9c5a-3251dc1522cc",
        "OK: 2f2b8d41-b271-430a-ad6b-4362ed2633dd",
        "OK: 3g3c9e52-c382-441b-be7c-5473fe3744ee"
    ]
}
```

---

### Example 5: Partial Success (with Invalid Number)

This example demonstrates how the API handles a request with both valid and invalid recipient numbers. The API will skip the invalid number (`1234`) and successfully send the message to the valid number.

#### Request

```bash
curl -X POST \
-H "apikey: YOUR_API_KEY" \
-H "Content-Type: application/json" \
-H "Accept: application/json" \
-d '{
    "from": "SenderName",
    "to": "12345678900,1234",
    "text": "Hello, this is a test message!",
    "type": "0"
}' \
"https://restapi.easysendsms.app/v1/rest/sms/send"
```

#### Partial Success Response

```json
{
    "status": "OK",
    "scheduled": "Now",
    "messageIds": [
        "OK: 69991a73-a560-429f-9c5a-3251dc1522bb",
        "ERR: 4012"
    ]
}
```

---

### Example 6: General Error Response

This is an example of a generic error response, which occurs when a request fails due to issues like an invalid mobile number.

#### Error Response

```json
{
    "error": 4012,
    "description": "Invalid mobile number."
}
```


### Error Codes

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

---

## 2. SMS Balance

The **SMS Balance REST API** allows developers to retrieve the current SMS credit balance associated with their EasySendSMS account.

Clients may send either a `GET` or `POST` request to the EasySendSMS REST API endpoint. Each request must include a valid **APIKEY** header containing the account API key.

Your API key can be found in the **Account Settings → REST API** section inside the EasySendSMS dashboard.

If the request is valid, the API returns a **JSON response** containing the current SMS balance and a status code indicating the result of the request.

### Endpoint

```
GET  /v1/rest/sms/balance
POST /v1/rest/sms/balance
```

### Request Headers

| Header | Value |
| --- | --- |
| `APIKEY` | `YOUR_API_KEY` |
| `Content-Type` | `application/json` |

### Request

- **GET Request**: No query parameters are required for this endpoint. The request must include the `APIKEY` header.

- **POST Request**: No request body is required. The request must include the `APIKEY` header. This endpoint **does not require a request body**. Send the request with an **empty payload**. Some HTTP clients automatically add a `Content-Type` header when sending `POST` requests. If this happens, ensure the request still contains **no body**.

### Example Request (POST)

```bash
curl -X POST "https://restapi.easysendsms.app/v1/rest/sms/balance" \
  -H "APIKEY: YOUR_API_KEY" \
  -H "Content-Type: application/json" \
  -H "Content-Length: 0"
```

### Example Success Response

```json
{
    "balance": 247247
}
```

### Example Error Response

```json
{
    "error": 4003,
    "description": "Invalid API Key."
}
```

### Error Codes

| Code | Description | HTTP Status |
|------|-------------|-------------|
| 4001 | One or more required parameters are missing. | 400 |
| 4002 | No API key found in the request. | 401 |
| 4003 | Invalid API key. | 401 |
| 4004 | Invalid IP address. | 403 |
| 4005 | API key is inactive. | 403 |
| 4006 | Account is inactive. | 403 |
| 4007 | Demo account has expired. | 403 |
| 4008 | Internal server error. Do **not** retry the same request. | 500 |
| 4009 | Service temporarily unavailable. Do **not** retry the same request immediately. | 503 |

---

## 3. HLR Query

The HLR (Home Location Register) Query API is a powerful tool designed to help users verify and retrieve real-time information about mobile numbers. By using the HLR Query API, users can determine the current status and location of a mobile number, including whether the number is active, which network it belongs to, and whether it has been ported to a different network.

#### Why Use the HLR Query API?

- **Enhance Message Delivery**: Ensure that SMS messages are sent to valid and active numbers, reducing the number of failed deliveries.
- **Cost Efficiency**: Avoid sending messages to inactive or non-existent numbers, saving costs on undelivered messages.
- **Up-to-Date Information**: Receive real-time data on the status of mobile numbers, including ported numbers and roaming information, ensuring accurate targeting and communication.

### Endpoint

```
POST /v1/rest/hlr/query
```

### Request Headers

| Header | Value |
| --- | --- |
| `apikey` | `YOUR_API_KEY` |
| `Content-Type` | `application/json` |
| `Accept` | `application/json` |

### Request Parameters

All requests to the EasySendSMS API should use the `application/json` content type and the `POST` method. Ensure that the request body is in JSON format and that you use raw JSON data in your POST requests.

| Parameter | Description | Presence |
|-----------|-------------|----------|
| `number` | Subscriber's MSISDN to be checked, e.g., 19876543210 (Do not use + or 00 before the country code). Multiple numbers can be queried using commas in the number parameter, with a maximum of 30 numbers per request. | Required |

#### Bulk HLR Query

A request containing multiple destination numbers will be aborted immediately if any error other than "Invalid Number Parameter" [Code: 4010] is encountered. If an "Invalid Number Parameter" [Code: 4010] is found, that destination number will be skipped, and the request will proceed with the next number.

A maximum of 30 numbers can be submitted per request. Duplicate numbers will be ignored.

### Example Request

```bash
curl -X POST \
-H "apikey: YOUR_API_KEY" \
-H "Content-Type: application/json" \
-H "Accept: application/json" \
-d '{
    "number": "12345678900"
}' \
"https://restapi.easysendsms.app/v1/rest/hlr/query"
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

### Example Request (Partial Request With Invalid Number)

```bash
curl -X POST \
-H "apikey: YOUR_API_KEY" \
-H "Content-Type: application/json" \
-H "Accept: application/json" \
-d '{
    "number": "12345678900,611111"
}' \
"https://restapi.easysendsms.app/v1/rest/hlr/query"
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

### Example Error Response

```json
{
    "error": 4010,
    "description": "Invalid Number Parameter."
}
```

### Error Codes

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

---

## 4. Number Validation (NV)

The Number Validation (NV) Query is an essential tool designed to verify the validity and status of phone numbers before sending SMS messages or making calls. By using the NV Query, you can ensure that the phone numbers in your database are accurate, active, and formatted correctly, which helps reduce costs associated with failed message deliveries and improves overall communication efficiency.

#### Why Use the Number Validation (NV) Query?

- **Accurate Number Verification**: Confirm whether a phone number is valid, active, and correctly formatted, ensuring that your messages reach the intended recipients.
- **Cost Efficiency**: Avoid unnecessary expenses by identifying and removing invalid or inactive numbers from your contact lists before sending messages.
- **Improved Delivery Rates**: By validating numbers in advance, you increase the likelihood of successful message delivery, improving customer engagement and satisfaction.
- **Enhanced Data Quality**: Regular validation helps maintain a clean and up-to-date contact database, which is critical for effective communication and marketing efforts.
- **Compliance Assurance**: Ensure that phone numbers comply with local and international dialing standards, helping you meet regulatory requirements.

### Endpoint

```
POST /v1/rest/nv/query
```

### Request Headers

| Header | Value |
| --- | --- |
| `apikey` | `YOUR_API_KEY` |
| `Content-Type` | `application/json` |
| `Accept` | `application/json` |

### Request Parameters

All requests to the EasySendSMS API should use the `application/json` content type and the `POST` method. Ensure that the request body is in JSON format and that you use raw JSON data in your POST requests.

| Parameter | Description | Presence |
|-----------|-------------|----------|
| `number` | Subscriber's MSISDN to be checked, e.g., 19876543210 (Do not use + or 00 before the country code). Multiple numbers can be queried using commas in the number parameter, with a maximum of 30 numbers per request. | Required |

#### Bulk NV Query

A request containing multiple destination numbers will be aborted immediately if any error other than "Invalid Number Parameter" [Code: 4010] is encountered. If an "Invalid Number Parameter" [Code: 4010] is found, that destination number will be skipped, and the request will proceed with the next number.

A maximum of 30 numbers can be submitted per request. Duplicate numbers will be ignored.

### Example Request

```bash
curl -X POST \
-H "apikey: YOUR_API_KEY" \
-H "Content-Type: application/json" \
-H "Accept: application/json" \
-d '{
    "number": "12345678900"
}' \
"https://restapi.easysendsms.app/v1/rest/nv/query"
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

### Example Request (Partial Request With Invalid Number)

```bash
curl -X POST \
-H "apikey: YOUR_API_KEY" \
-H "Content-Type: application/json" \
-H "Accept: application/json" \
-d '{
    "number": "12345678900,61111"
}' \
"https://restapi.easysendsms.app/v1/rest/nv/query"
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
            "msisdn": "12345678900"
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

### Example Error Response

```json
{
    "error": 4010,
    "description": "Invalid Number Parameter."
}
```

### Error Codes

| Code | Description | HTTP Status |
|------|-------------|-------------|
| 4001 | One or more required parameters are missing. | 400 |
| 4002 | No API key found in request. | 401 |
| 4003 | Invalid API Key. | 401 |
| 4004 | Invalid IP address. | 403 |
| 4005 | Inactive API Key. | 403 |
| 4006 | Inactive Account. | 403 |
| 4007 | Demo account expired. | 403 |
| 4008 | Internal error (do **NOT** re-submit the same request again). | 500 |
| 4009 | Service not available (do **NOT** re-submit the same request again). | 503 |
| 4010 | Invalid number parameter. | 400 |
| 4011 | Invalid NV response. | 500 |
| 4012 | Insufficient credits. | 402 |
| 405  | Method not allowed. | 405 |
| 415  | Unsupported media type. | 415 |

---

## Error Handling

When an error occurs, the API returns a JSON object containing an `error` code and a human-readable `description`. Developers should inspect the `error` field to determine the cause and take appropriate corrective action. Refer to the endpoint-specific error code tables above for a complete list of possible error codes.

#### Error Response Format

```json
{
    "error": <error_code>,
    "description": "<error_description>"
}
```

#### Example Error Response

```json
{
    "error": 4012,
    "description": "Invalid mobile number."
}
```

---

## HTTP Status Codes

The EasySendSMS API uses conventional HTTP status codes to indicate the success or failure of an API request.

| HTTP Status Code | Meaning |
| --- | --- |
| `200 OK` | The request was successful. |
| `400 Bad Request` | The request was unacceptable, often due to a missing required parameter. |
| `401 Unauthorized` | No valid API key provided. |
| `402 Payment Required` | Insufficient credits. |
| `403 Forbidden` | The API key is inactive or does not have permissions for the requested action. |
| `405 Method Not Allowed` | The HTTP method used is not supported for the endpoint. |
| `415 Unsupported Media Type` | The request content type is not supported. |
| `429 Too Many Requests` | Rate limit exceeded. Retry after the appropriate cooldown period. |
| `500 Internal Server Error` | An unexpected error occurred on the server. Do **not** retry the same request. |
| `503 Service Unavailable` | The service is temporarily offline. Do **not** retry the same request immediately. |

---

## Best Practices

- **Validate Numbers Before Sending**: Use the HLR Query or Number Validation (NV) endpoints to verify that phone numbers are valid and active before sending SMS messages. This reduces failed deliveries and saves credits.
- **Batch Recipients Efficiently**: When sending messages to multiple recipients, use the comma-separated `to` parameter (up to 30 numbers per request) to minimize the number of API calls and stay within rate limits.
- **Handle Rate Limits Gracefully**: Implement retry logic with exponential backoff to handle `429 Too Many Requests` responses. Avoid hammering the API with rapid retries.
- **Use Correct Content-Type**: Always set `Content-Type: application/json` and send raw JSON in the request body for `POST` requests.
- **Secure Your API Key**: Never expose your API key in client-side code or public repositories. Store it securely on the server side.
- **Monitor Your Balance**: Periodically check your SMS credit balance using the SMS Balance endpoint to ensure uninterrupted service.
