<?php

/**
 * EasySendSMS REST API Client
 *
 * A PHP client for sending SMS messages via the EasySendSMS REST API.
 *
 * @see https://www.easysendsms.com/rest-api
 */
class EasySendSmsClient
{
    /**
     * EasySendSMS Send SMS API endpoint.
     */
    private const BASE_URL = 'https://restapi.easysendsms.app/v1/rest/sms/send';

    /**
     * Request timeout in seconds.
     */
    private const TIMEOUT = 30;

    /**
     * @var string The API key for authentication.
     */
    private string $apiKey;

    /**
     * Create a new EasySendSmsClient instance.
     *
     * @param string $apiKey Your EasySendSMS API key.
     *
     * @throws InvalidArgumentException If the API key is empty.
     */
    public function __construct(string $apiKey)
    {
        if (empty(trim($apiKey))) {
            throw new InvalidArgumentException('API key cannot be empty.');
        }

        $this->apiKey = $apiKey;
    }

    /**
     * Send an SMS message.
     *
     * @param string      $from      Sender name or number (max 11 alphanumeric / 15 numeric).
     * @param string      $to        Recipient number(s), comma-separated (max 30 per request).
     *                               Do not use + or 00 before the country code.
     * @param string      $text      Message body (max 5 parts).
     * @param string      $type      "0" for plain text (GSM 3.38), "1" for Unicode.
     * @param string|null $scheduled Optional ISO 8601 UTC datetime, e.g. "2026-12-31T10:00:00".
     *
     * @return array Parsed JSON response as an associative array.
     */
    public function sendSms(
        string  $from,
        string  $to,
        string  $text,
        string  $type = '0',
        ?string $scheduled = null
    ): array {
        $payload = [
            'from' => $from,
            'to'   => $to,
            'text' => $text,
            'type' => $type,
        ];

        if ($scheduled !== null) {
            $payload['scheduled'] = $scheduled;
        }

        $headers = [
            'apikey: '       . $this->apiKey,
            'Content-Type: application/json',
            'Accept: application/json',
        ];

        $ch = curl_init();

        curl_setopt_array($ch, [
            CURLOPT_URL            => self::BASE_URL,
            CURLOPT_POST           => true,
            CURLOPT_POSTFIELDS     => json_encode($payload),
            CURLOPT_HTTPHEADER     => $headers,
            CURLOPT_RETURNTRANSFER => true,
            CURLOPT_TIMEOUT        => self::TIMEOUT,
        ]);

        $responseBody = curl_exec($ch);
        $httpCode     = curl_getinfo($ch, CURLINFO_HTTP_CODE);
        $curlError    = curl_error($ch);

        curl_close($ch);

        // --- Handle cURL-level errors ---
        if ($responseBody === false) {
            $this->printError("cURL Error: {$curlError}");
            return ['error' => "cURL Error: {$curlError}"];
        }

        $result = json_decode($responseBody, true);

        // --- Handle API response ---
        if ($httpCode >= 200 && $httpCode < 300) {
            $this->printSuccess($result);
        } else {
            $this->printApiError($httpCode, $result);
        }

        return $result;
    }

    /**
     * Print a formatted success message to the console.
     *
     * @param array $result The parsed API response.
     */
    private function printSuccess(array $result): void
    {
        echo "\033[92mSMS Sent Successfully!\033[0m" . PHP_EOL;
        echo "  Status:      " . ($result['status'] ?? 'N/A') . PHP_EOL;
        echo "  Scheduled:   " . ($result['scheduled'] ?? 'N/A') . PHP_EOL;

        $messageIds = $result['messageIds'] ?? [];
        foreach ($messageIds as $index => $id) {
            echo "  Message #" . ($index + 1) . ":  " . $id . PHP_EOL;
        }
    }

    /**
     * Print a formatted API error message to the console.
     *
     * @param int   $httpCode The HTTP status code.
     * @param array $result   The parsed API error response.
     */
    private function printApiError(int $httpCode, array $result): void
    {
        echo "\033[91mAPI Error ({$httpCode})\033[0m" . PHP_EOL;
        echo "  Error Code:  " . ($result['error'] ?? 'N/A') . PHP_EOL;
        echo "  Description: " . ($result['description'] ?? 'N/A') . PHP_EOL;
    }

    /**
     * Print a formatted general error message to the console.
     *
     * @param string $message The error message.
     */
    private function printError(string $message): void
    {
        echo "\033[91mError: {$message}\033[0m" . PHP_EOL;
    }
}
