# EasySendSMS PHP REST API Example

This folder contains a PHP script demonstrating how to send SMS messages using the [EasySendSMS REST API](https://www.easysendsms.com/).

## Project Structure

```
examples/php/
├── EasySendSmsClient.php   # Dedicated API client class
├── send_sms.php            # Main script with 4 usage examples
├── composer.json            # PHP project metadata and requirements
└── README.md                # This file
```

### `EasySendSmsClient.php`

A self-contained API client class that handles:

- HTTP requests via cURL
- Authentication (`apikey` header)
- JSON serialization of request payloads
- Response parsing (success and error)
- Color-coded console output

### `send_sms.php`

The entry-point script that demonstrates four common use cases:

1. **Single Recipient** (plain text)
2. **Multiple Recipients** (bulk sending)
3. **Unicode Message** (Arabic)
4. **Scheduled Message**

## Prerequisites

- [PHP 8.0+](https://www.php.net/downloads)
- PHP cURL extension (`ext-curl`)
- PHP JSON extension (`ext-json`)
- An active [EasySendSMS](https://www.easysendsms.com/) account
- Your EasySendSMS API Key (found in **Account Settings → REST API** in the dashboard)

> **Note:** This example uses PHP's built-in cURL functions. No Composer dependencies or third-party libraries are required.

## How to Run

### 1. Set Your API Key

For security, the script reads your API key from an environment variable named `EASYSENDSMS_API_KEY`.

**macOS / Linux:**

```bash
export EASYSENDSMS_API_KEY="YOUR_API_KEY"
```

To make it permanent, add the line above to your `~/.bashrc`, `~/.zshrc`, or equivalent shell profile file.

**Windows (Command Prompt):**

```cmd
set EASYSENDSMS_API_KEY=YOUR_API_KEY
```

**Windows (PowerShell):**

```powershell
$env:EASYSENDSMS_API_KEY="YOUR_API_KEY"
```

### 2. Run the Script

```bash
php send_sms.php
```

## Expected Output

The script runs four examples and prints formatted API responses to the console. Success messages appear in green, errors in red, and warnings in yellow.

```
--- Example 1: Single Recipient (Plain Text) ---
SMS Sent Successfully!
  Status:      OK
  Scheduled:   Now
  Message #1:  OK: 1a2b3c4d-5e6f-7g8h-9i0j-klmnopqrstuv

--- Example 2: Multiple Recipients (Bulk) ---
SMS Sent Successfully!
  Status:      OK
  Scheduled:   Now
  Message #1:  OK: 2b3c4d5e-6f7g-8h9i-0j1k-lmnopqrstuvw
  Message #2:  OK: 3c4d5e6f-7g8h-9i0j-1k2l-mnopqrstuvwx
  Message #3:  OK: 4d5e6f7g-8h9i-0j1k-2l3m-nopqrstuvwxy

--- Example 3: Unicode Message (Arabic) ---
SMS Sent Successfully!
  Status:      OK
  Scheduled:   Now
  Message #1:  OK: 5e6f7g8h-9i0j-1k2l-3m4n-opqrstuvwxyz

--- Example 4: Scheduled Message ---
SMS Sent Successfully!
  Status:      OK
  Scheduled:   2026-03-15T10:00:00
  Message #1:  OK: 6f7g8h9i-0j1k-2l3m-4n5o-pqrstuvwxyza
```

If there is an API error (e.g., insufficient credits), the output will look like this:

```
API Error (402)
  Error Code:  4015
  Description: Insufficient credits.
```

## API Reference

| Detail | Value |
| --- | --- |
| **Base URL** | `https://restapi.easysendsms.app/v1/rest/sms/send` |
| **Method** | `POST` |
| **Content-Type** | `application/json` |
| **Authentication** | `apikey` header |
| **Documentation** | [https://www.easysendsms.com/rest-api](https://www.easysendsms.com/rest-api) |
