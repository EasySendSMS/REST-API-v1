package com.easysendsms.example;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * A client for interacting with the EasySendSMS REST API.
 *
 * <p>This client uses Java's built-in {@link java.net.http.HttpClient} (Java 11+)
 * and requires no external dependencies.</p>
 *
 * @see <a href="https://www.easysendsms.com/rest-api">EasySendSMS REST API Documentation</a>
 */
public class EasySendSmsClient {

    /** EasySendSMS Send SMS API endpoint. */
    private static final String BASE_URL = "https://restapi.easysendsms.app/v1/rest/sms/send";

    /** Request timeout in seconds. */
    private static final int TIMEOUT_SECONDS = 30;

    private final String apiKey;
    private final HttpClient httpClient;

    /**
     * Create a new EasySendSmsClient instance.
     *
     * @param apiKey Your EasySendSMS API key.
     * @throws IllegalArgumentException if the API key is null or blank.
     */
    public EasySendSmsClient(String apiKey) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalArgumentException("API key cannot be null or blank.");
        }
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                .build();
    }

    /**
     * Send an SMS message.
     *
     * @param from      Sender name or number (max 11 alphanumeric / 15 numeric).
     * @param to        Recipient number(s), comma-separated (max 30 per request).
     *                  Do not use + or 00 before the country code.
     * @param text      Message body (max 5 parts).
     * @param type      "0" for plain text (GSM 3.38), "1" for Unicode.
     * @param scheduled Optional ISO 8601 UTC datetime (e.g. "2026-12-31T10:00:00"), or null.
     * @return The raw JSON response body as a String.
     */
    public String sendSms(String from, String to, String text, String type, String scheduled) {
        try {
            String jsonPayload = buildJsonPayload(from, to, text, type, scheduled);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL))
                    .timeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                    .header("apikey", apiKey)
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            int statusCode = response.statusCode();
            String body = response.body();

            if (statusCode >= 200 && statusCode < 300) {
                printSuccess(body);
            } else {
                printApiError(statusCode, body);
            }

            return body;

        } catch (IOException e) {
            printError("I/O Error: " + e.getMessage());
            return "{\"error\": \"I/O Error: " + e.getMessage() + "\"}";
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            printError("Request interrupted: " + e.getMessage());
            return "{\"error\": \"Request interrupted: " + e.getMessage() + "\"}";
        }
    }

    /**
     * Overloaded convenience method without the scheduled parameter.
     */
    public String sendSms(String from, String to, String text, String type) {
        return sendSms(from, to, text, type, null);
    }

    // -----------------------------------------------------------------------
    // INTERNAL HELPERS
    // -----------------------------------------------------------------------

    /**
     * Build a JSON payload string manually to avoid external library dependencies.
     */
    private String buildJsonPayload(String from, String to, String text, String type, String scheduled) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"from\":").append(escapeJson(from)).append(",");
        sb.append("\"to\":").append(escapeJson(to)).append(",");
        sb.append("\"text\":").append(escapeJson(text)).append(",");
        sb.append("\"type\":").append(escapeJson(type));
        if (scheduled != null && !scheduled.isBlank()) {
            sb.append(",\"scheduled\":").append(escapeJson(scheduled));
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * Escape a string value for safe JSON embedding.
     */
    private String escapeJson(String value) {
        if (value == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder("\"");
        for (char c : value.toCharArray()) {
            switch (c) {
                case '"':  sb.append("\\\""); break;
                case '\\': sb.append("\\\\"); break;
                case '\b': sb.append("\\b");  break;
                case '\f': sb.append("\\f");  break;
                case '\n': sb.append("\\n");  break;
                case '\r': sb.append("\\r");  break;
                case '\t': sb.append("\\t");  break;
                default:
                    if (c < 0x20) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
            }
        }
        sb.append("\"");
        return sb.toString();
    }

    private void printSuccess(String body) {
        System.out.println("\u001B[92mSMS Sent Successfully!\u001B[0m");
        System.out.println("  Response: " + body);
    }

    private void printApiError(int statusCode, String body) {
        System.out.println("\u001B[91mAPI Error (" + statusCode + ")\u001B[0m");
        System.out.println("  Response: " + body);
    }

    private void printError(String message) {
        System.out.println("\u001B[91mError: " + message + "\u001B[0m");
    }
}
