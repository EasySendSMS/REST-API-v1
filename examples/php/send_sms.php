<?php

/**
 * EasySendSMS REST API - Send SMS Example (PHP)
 *
 * This script demonstrates how to send SMS messages using the EasySendSMS REST API.
 * It includes examples for:
 *   - Single recipient (plain text)
 *   - Multiple recipients (bulk sending)
 *   - Unicode message (Arabic)
 *   - Scheduled message
 *
 * API Documentation: https://www.easysendsms.com/rest-api
 */

require_once __DIR__ . '/EasySendSmsClient.php';

// ---------------------------------------------------------------------------
// CONFIGURATION
// ---------------------------------------------------------------------------

// IMPORTANT: For security, read the API key from an environment variable.
// DO NOT hardcode your API key in the source code.
$apiKey = getenv('EASYSENDSMS_API_KEY');

if (empty($apiKey)) {
    echo "\033[93mWarning: EASYSENDSMS_API_KEY environment variable not set." . PHP_EOL;
    echo "Please set it to your actual EasySendSMS API key.\033[0m" . PHP_EOL . PHP_EOL;
    // Using a placeholder for demonstration purposes.
    $apiKey = 'YOUR_API_KEY';
}

$client = new EasySendSmsClient($apiKey);

// ---------------------------------------------------------------------------
// EXAMPLES
// ---------------------------------------------------------------------------

// --- Example 1: Single Recipient (Plain Text) ---
echo "--- Example 1: Single Recipient (Plain Text) ---" . PHP_EOL;
$client->sendSms(
    from: 'YourSender',
    to:   '12345678901',
    text: 'Hello from PHP! This is a test message.',
    type: '0'
);

// --- Example 2: Multiple Recipients (Bulk) ---
echo PHP_EOL . "--- Example 2: Multiple Recipients (Bulk) ---" . PHP_EOL;
$client->sendSms(
    from: 'AlertSystem',
    to:   '12345678901,12345678902,12345678903',
    text: 'System maintenance will begin in 1 hour.',
    type: '0'
);

// --- Example 3: Unicode Message (Arabic) ---
echo PHP_EOL . "--- Example 3: Unicode Message (Arabic) ---" . PHP_EOL;
$client->sendSms(
    from: 'تذكير',
    to:   '12345678904',
    text: 'مرحبا، هذه رسالة اختبار!',
    type: '1'
);

// --- Example 4: Scheduled Message ---
echo PHP_EOL . "--- Example 4: Scheduled Message ---" . PHP_EOL;
$client->sendSms(
    from:      'YourSender',
    to:        '12345678901',
    text:      'This is a scheduled reminder for your meeting.',
    type:      '0',
    scheduled: '2026-03-15T10:00:00'
);
