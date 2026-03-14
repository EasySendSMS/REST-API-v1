# EasySendSMS C# REST API Example

This folder contains a .NET 8 console application demonstrating how to send an SMS using the [EasySendSMS REST API](https://www.easysendsms.com/).

## Project Structure

- `EasySendSmsExample.csproj`: The C# project file. It includes a dependency on `System.Text.Json` for JSON serialization.
- `Program.cs`: The main source code file. It contains:
    - **Data Models**: `SmsRequest`, `SmsSuccessResponse`, and `SmsErrorResponse` classes to represent the API data structures.
    - **API Client**: A dedicated `EasySendSmsClient` class that handles HTTP requests, authentication, and response parsing.
    - **Main Program**: The entry point that demonstrates how to use the client to send various types of SMS messages.
- `README.md`: This file.

## Prerequisites

- [.NET 8 SDK](https://dotnet.microsoft.com/download/dotnet/8.0) or later.
- An active [EasySendSMS](https://www.easysendsms.com/) account.
- Your EasySendSMS API Key.

## How to Run

1.  **Set Your API Key**

    For security, the program is designed to read your API key from an environment variable named `EASYSENDSMS_API_KEY`.

    **Windows (Command Prompt):**
    ```sh
    setx EASYSENDSMS_API_KEY "YOUR_API_KEY"
    ```
    *(Note: You may need to restart your terminal for the change to take effect.)*

    **Windows (PowerShell):**
    ```powershell
    $env:EASYSENDSMS_API_KEY="YOUR_API_KEY"
    ```

    **macOS / Linux:**
    ```sh
    export EASYSENDSMS_API_KEY="YOUR_API_KEY"
    ```
    *(To make it permanent, add this line to your `~/.bash_profile`, `~/.zshrc`, or equivalent shell profile file.)*

2.  **Navigate to the Project Directory**

    Open your terminal and change to this directory:
    ```sh
    cd examples/csharp
    ```

3.  **Run the Application**

    Execute the following command:
    ```sh
    dotnet run
    ```

## Expected Output

The application will run three examples and print the formatted API responses to the console. It will use green for success and red for errors.

```
--- Sending Example 1: Single Recipient ---
SMS Sent Successfully!
Status: OK
Scheduled: Now
Message IDs: OK: 1a2b3c-4d5e-6f7g-8h9i-jklmno

--- Sending Example 2: Multiple Recipients ---
SMS Sent Successfully!
Status: OK
Scheduled: Now
Message IDs: OK: 2b3c4d-5e6f-7g8h-9i0j-klmnop, OK: 3c4d5e-6f7g-8h9i-0j1k-lmnopq

--- Sending Example 3: Unicode (Arabic) Message ---
SMS Sent Successfully!
Status: OK
Scheduled: Now
Message IDs: OK: 4d5e6f-7g8h-9i0j-1k2l-mnopqr
```

If there is an API error (e.g., insufficient credits), the output will look like this:

```
API Error (402)
Error Code: 4015
Description: Insufficient credits.
```
