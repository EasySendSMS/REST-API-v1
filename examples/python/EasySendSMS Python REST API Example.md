# EasySendSMS Python REST API Example

This folder contains a Python script demonstrating how to send SMS messages using the [EasySendSMS REST API](https://www.easysendsms.com/).

## Project Structure

```
examples/python/
├── send_sms.py          # Main script with API client and usage examples
├── requirements.txt     # Python dependencies
└── README.md            # This file
```

### `send_sms.py`

The main source file contains:

- **`EasySendSmsClient` class**: A dedicated API client that handles HTTP requests, authentication, JSON serialization, and response parsing with color-coded console output.
- **4 Usage Examples**: Single recipient, bulk (multiple recipients), Unicode Arabic, and scheduled message.

## Prerequisites

- [Python 3.9+](https://www.python.org/downloads/)
- An active [EasySendSMS](https://www.easysendsms.com/) account.
- Your EasySendSMS API Key (found in **Account Settings → REST API** in the dashboard).

## How to Run

### 1. Install Dependencies

```bash
pip install -r requirements.txt
```

### 2. Set Your API Key

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

### 3. Run the Script

```bash
python send_sms.py
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
