# EasySendSMS Java REST API Example

This folder contains a Java application demonstrating how to send SMS messages using the [EasySendSMS REST API](https://www.easysendsms.com/).

## Project Structure

```
examples/java/
├── pom.xml                                                  # Maven build file
├── README.md                                                # This file
└── src/
    └── main/
        └── java/
            └── com/
                └── easysendsms/
                    └── example/
                        ├── EasySendSmsClient.java           # Dedicated API client class
                        └── SendSmsExample.java              # Main class with 4 usage examples
```

### `EasySendSmsClient.java`

A self-contained API client class that handles:

- HTTP requests via Java's built-in `java.net.http.HttpClient` (Java 11+)
- Authentication (`apikey` header)
- JSON payload construction (no external libraries needed)
- Proper JSON string escaping (including Unicode)
- Response output with color-coded console messages

### `SendSmsExample.java`

The entry-point class that demonstrates four common use cases:

1. **Single Recipient** (plain text)
2. **Multiple Recipients** (bulk sending)
3. **Unicode Message** (Arabic)
4. **Scheduled Message**

## Prerequisites

- [Java 11+](https://adoptium.net/) (JDK)
- [Apache Maven 3.6+](https://maven.apache.org/download.cgi) (optional, for building with Maven)
- An active [EasySendSMS](https://www.easysendsms.com/) account
- Your EasySendSMS API Key (found in **Account Settings → REST API** in the dashboard)

> **Note:** This example uses Java's built-in `java.net.http.HttpClient` and has **zero external dependencies**.

## How to Run

### Option A: Using Maven

#### 1. Set Your API Key

**macOS / Linux:**

```bash
export EASYSENDSMS_API_KEY="YOUR_API_KEY"
```

**Windows (Command Prompt):**

```cmd
set EASYSENDSMS_API_KEY=YOUR_API_KEY
```

**Windows (PowerShell):**

```powershell
$env:EASYSENDSMS_API_KEY="YOUR_API_KEY"
```

#### 2. Build and Run

```bash
cd examples/java
mvn clean compile exec:java -Dexec.mainClass="com.easysendsms.example.SendSmsExample"
```

Or build a JAR and run it:

```bash
mvn clean package
java -jar target/java-send-sms-example-1.0.0.jar
```

### Option B: Using javac Directly

If you do not have Maven installed, you can compile and run directly:

```bash
cd examples/java

# Compile
javac -d out src/main/java/com/easysendsms/example/*.java

# Run
java -cp out com.easysendsms.example.SendSmsExample
```

## Expected Output

The application runs four examples and prints formatted API responses to the console. Success messages appear in green, errors in red, and warnings in yellow.

```
--- Example 1: Single Recipient (Plain Text) ---
SMS Sent Successfully!
  Response: {"status":"OK","scheduled":"Now","messageIds":["OK: 1a2b3c4d-..."]}

--- Example 2: Multiple Recipients (Bulk) ---
SMS Sent Successfully!
  Response: {"status":"OK","scheduled":"Now","messageIds":["OK: ...","OK: ...","OK: ..."]}

--- Example 3: Unicode Message (Arabic) ---
SMS Sent Successfully!
  Response: {"status":"OK","scheduled":"Now","messageIds":["OK: ..."]}

--- Example 4: Scheduled Message ---
SMS Sent Successfully!
  Response: {"status":"OK","scheduled":"2026-03-15T10:00:00","messageIds":["OK: ..."]}
```

If there is an API error (e.g., insufficient credits), the output will look like this:

```
API Error (402)
  Response: {"error":4015,"description":"Insufficient credits."}
```

## API Reference

| Detail | Value |
| --- | --- |
| **Base URL** | `https://restapi.easysendsms.app/v1/rest/sms/send` |
| **Method** | `POST` |
| **Content-Type** | `application/json` |
| **Authentication** | `apikey` header |
| **Documentation** | [https://www.easysendsms.com/rest-api](https://www.easysendsms.com/rest-api) |
