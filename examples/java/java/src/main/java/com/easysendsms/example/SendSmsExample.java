package com.easysendsms.example;

/**
 * EasySendSMS REST API - Send SMS Example (Java)
 *
 * <p>This program demonstrates how to send SMS messages using the EasySendSMS REST API.
 * It includes examples for:</p>
 * <ul>
 *   <li>Single recipient (plain text)</li>
 *   <li>Multiple recipients (bulk sending)</li>
 *   <li>Unicode message (Arabic)</li>
 *   <li>Scheduled message</li>
 * </ul>
 *
 * <p>Requires Java 11+ (uses {@code java.net.http.HttpClient}).</p>
 *
 * @see <a href="https://www.easysendsms.com/rest-api">EasySendSMS REST API Documentation</a>
 */
public class SendSmsExample {

    public static void main(String[] args) {

        // IMPORTANT: For security, read the API key from an environment variable.
        // DO NOT hardcode your API key in the source code.
        String apiKey = System.getenv("EASYSENDSMS_API_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            System.out.println("\u001B[93mWarning: EASYSENDSMS_API_KEY environment variable not set.");
            System.out.println("Please set it to your actual EasySendSMS API key.\u001B[0m");
            System.out.println();
            // Using a placeholder for demonstration purposes.
            apiKey = "YOUR_API_KEY";
        }

        EasySendSmsClient client = new EasySendSmsClient(apiKey);

        // --- Example 1: Single Recipient (Plain Text) ---
        System.out.println("--- Example 1: Single Recipient (Plain Text) ---");
        client.sendSms(
                "YourSender",
                "12345678901",
                "Hello from Java! This is a test message.",
                "0"
        );

        // --- Example 2: Multiple Recipients (Bulk) ---
        System.out.println("\n--- Example 2: Multiple Recipients (Bulk) ---");
        client.sendSms(
                "AlertSystem",
                "12345678901,12345678902,12345678903",
                "System maintenance will begin in 1 hour.",
                "0"
        );

        // --- Example 3: Unicode Message (Arabic) ---
        System.out.println("\n--- Example 3: Unicode Message (Arabic) ---");
        client.sendSms(
                "\u062A\u0630\u0643\u064A\u0631",  // "تذكير" (Reminder in Arabic)
                "12345678904",
                "\u0645\u0631\u062D\u0628\u0627\u060C \u0647\u0630\u0647 \u0631\u0633\u0627\u0644\u0629 \u0627\u062E\u062A\u0628\u0627\u0631!",  // "مرحبا، هذه رسالة اختبار!"
                "1"
        );

        // --- Example 4: Scheduled Message ---
        System.out.println("\n--- Example 4: Scheduled Message ---");
        client.sendSms(
                "YourSender",
                "12345678901",
                "This is a scheduled reminder for your meeting.",
                "0",
                "2026-03-15T10:00:00"
        );
    }
}
