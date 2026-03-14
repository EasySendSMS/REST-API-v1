"""
EasySendSMS REST API - Send SMS Example (Python)

This script demonstrates how to send SMS messages using the EasySendSMS REST API.
It includes examples for:
  - Single recipient (plain text)
  - Multiple recipients (bulk sending)
  - Unicode message (Arabic)
  - Scheduled message

API Documentation: https://www.easysendsms.com/rest-api
"""

import os
import sys
import json
import requests


# ---------------------------------------------------------------------------
# API CLIENT
# ---------------------------------------------------------------------------

class EasySendSmsClient:
    """A client for interacting with the EasySendSMS REST API."""

    BASE_URL = "https://restapi.easysendsms.app/v1/rest/sms/send"

    def __init__(self, api_key: str):
        """
        Initialize the client with an API key.

        Args:
            api_key: Your EasySendSMS API key.
        """
        if not api_key or not api_key.strip():
            raise ValueError("API key cannot be empty.")

        self._api_key = api_key
        self._headers = {
            "apikey": self._api_key,
            "Content-Type": "application/json",
            "Accept": "application/json",
        }

    def send_sms(
        self,
        sender: str,
        to: str,
        text: str,
        msg_type: str = "0",
        scheduled: str | None = None,
    ) -> dict:
        """
        Send an SMS message.

        Args:
            sender:    Sender name or number (max 11 alphanumeric / 15 numeric).
            to:        Recipient number(s), comma-separated (max 30 per request).
                       Do not use + or 00 before the country code.
            text:      Message body (max 5 parts).
            msg_type:  "0" for plain text (GSM 3.38), "1" for Unicode.
            scheduled: Optional ISO 8601 UTC datetime, e.g. "2026-12-31T10:00:00".

        Returns:
            Parsed JSON response as a dictionary.
        """
        payload = {
            "from": sender,
            "to": to,
            "text": text,
            "type": msg_type,
        }

        if scheduled is not None:
            payload["scheduled"] = scheduled

        try:
            response = requests.post(
                self.BASE_URL,
                headers=self._headers,
                json=payload,
                timeout=30,
            )

            result = response.json()

            if response.ok:
                print(f"\033[92mSMS Sent Successfully!\033[0m")
                print(f"  Status:      {result.get('status')}")
                print(f"  Scheduled:   {result.get('scheduled')}")
                message_ids = result.get("messageIds", [])
                for idx, mid in enumerate(message_ids, start=1):
                    print(f"  Message #{idx}: {mid}")
            else:
                print(f"\033[91mAPI Error ({response.status_code})\033[0m")
                print(f"  Error Code:  {result.get('error')}")
                print(f"  Description: {result.get('description')}")

            return result

        except requests.exceptions.Timeout:
            print("\033[91mError: Request timed out.\033[0m")
            return {"error": "Request timed out"}
        except requests.exceptions.ConnectionError:
            print("\033[91mError: Could not connect to the API.\033[0m")
            return {"error": "Connection failed"}
        except requests.exceptions.RequestException as exc:
            print(f"\033[91mError: {exc}\033[0m")
            return {"error": str(exc)}


# ---------------------------------------------------------------------------
# EXAMPLES
# ---------------------------------------------------------------------------

def main():
    # IMPORTANT: For security, read the API key from an environment variable.
    # DO NOT hardcode your API key in the source code.
    api_key = os.environ.get("EASYSENDSMS_API_KEY")

    if not api_key:
        print(
            "\033[93mWarning: EASYSENDSMS_API_KEY environment variable not set.\n"
            "Please set it to your actual EasySendSMS API key.\033[0m"
        )
        # Using a placeholder for demonstration purposes.
        api_key = "YOUR_API_KEY"

    client = EasySendSmsClient(api_key)

    # --- Example 1: Single Recipient (Plain Text) ---
    print("--- Example 1: Single Recipient (Plain Text) ---")
    client.send_sms(
        sender="YourSender",
        to="12345678901",
        text="Hello from Python! This is a test message.",
        msg_type="0",
    )

    # --- Example 2: Multiple Recipients (Bulk) ---
    print("\n--- Example 2: Multiple Recipients (Bulk) ---")
    client.send_sms(
        sender="AlertSystem",
        to="12345678901,12345678902,12345678903",
        text="System maintenance will begin in 1 hour.",
        msg_type="0",
    )

    # --- Example 3: Unicode Message (Arabic) ---
    print("\n--- Example 3: Unicode Message (Arabic) ---")
    client.send_sms(
        sender="تذكير",
        to="12345678904",
        text="مرحبا، هذه رسالة اختبار!",
        msg_type="1",
    )

    # --- Example 4: Scheduled Message ---
    print("\n--- Example 4: Scheduled Message ---")
    client.send_sms(
        sender="YourSender",
        to="12345678901",
        text="This is a scheduled reminder for your meeting.",
        msg_type="0",
        scheduled="2026-03-15T10:00:00",
    )


if __name__ == "__main__":
    main()
