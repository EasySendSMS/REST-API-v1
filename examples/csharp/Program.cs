using System;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Text.Json;
using System.Text.Json.Serialization;
using System.Threading.Tasks;

// --- DATA MODELS FOR API REQUEST AND RESPONSE ---

/// <summary>
/// Represents the request payload for the Send SMS API.
/// </summary>
public class SmsRequest
{
    [JsonPropertyName("from")]
    public string From { get; set; } = string.Empty;

    [JsonPropertyName("to")]
    public string To { get; set; } = string.Empty;

    [JsonPropertyName("text")]
    public string Text { get; set; } = string.Empty;

    [JsonPropertyName("type")]
    public string Type { get; set; } = "0"; // Default to plain text

    [JsonPropertyName("scheduled")]
    [JsonIgnore(Condition = JsonIgnoreCondition.WhenWritingNull)]
    public string? Scheduled { get; set; }
}

/// <summary>
/// Represents a successful API response.
/// </summary>
public class SmsSuccessResponse
{
    [JsonPropertyName("status")]
    public string Status { get; set; } = string.Empty;

    [JsonPropertyName("scheduled")]
    public string Scheduled { get; set; } = string.Empty;

    [JsonPropertyName("messageIds")]
    public string[] MessageIds { get; set; } = [];
}

/// <summary>
/// Represents an error API response.
/// </summary>
public class SmsErrorResponse
{
    [JsonPropertyName("error")]
    public int ErrorCode { get; set; }

    [JsonPropertyName("description")]
    public string Description { get; set; } = string.Empty;
}

// --- DEDICATED API CLIENT ---

/// <summary>
/// A client for interacting with the EasySendSMS REST API.
/// </summary>
public class EasySendSmsClient
{
    private static readonly HttpClient _httpClient = new HttpClient();
    private const string BaseUrl = "https://restapi.easysendsms.app/v1/rest/sms/send";
    private readonly string _apiKey;

    public EasySendSmsClient(string apiKey)
    {
        if (string.IsNullOrWhiteSpace(apiKey))
        {
            throw new ArgumentException("API key cannot be null or whitespace.", nameof(apiKey));
        }
        _apiKey = apiKey;
        _httpClient.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
        _httpClient.DefaultRequestHeaders.Add("apikey", _apiKey);
    }

    /// <summary>
    /// Sends an SMS message.
    /// </summary>
    /// <param name="request">The SMS request details.</param>
    /// <returns>A string representing the JSON response from the API.</returns>
    public async Task<string> SendSmsAsync(SmsRequest request)
    {
        try
        {
            var jsonRequest = JsonSerializer.Serialize(request);
            var content = new StringContent(jsonRequest, Encoding.UTF8, "application/json");

            var response = await _httpClient.PostAsync(BaseUrl, content);

            var jsonResponse = await response.Content.ReadAsStringAsync();

            if (response.IsSuccessStatusCode)
            {
                var successResponse = JsonSerializer.Deserialize<SmsSuccessResponse>(jsonResponse);
                Console.ForegroundColor = ConsoleColor.Green;
                Console.WriteLine("SMS Sent Successfully!");
                Console.ResetColor();
                Console.WriteLine($"Status: {successResponse?.Status}");
                Console.WriteLine($"Scheduled: {successResponse?.Scheduled}");
                Console.WriteLine($"Message IDs: {string.Join(", ", successResponse?.MessageIds ?? [])}");
            }
            else
            {
                var errorResponse = JsonSerializer.Deserialize<SmsErrorResponse>(jsonResponse);
                Console.ForegroundColor = ConsoleColor.Red;
                Console.WriteLine($"API Error ({(int)response.StatusCode})");
                Console.ResetColor();
                Console.WriteLine($"Error Code: {errorResponse?.ErrorCode}");
                Console.WriteLine($"Description: {errorResponse?.Description}");
            }
            return jsonResponse;
        }
        catch (Exception ex)
        {
            Console.ForegroundColor = ConsoleColor.DarkRed;
            Console.WriteLine($"An unexpected error occurred: {ex.Message}");
            Console.ResetColor();
            return $"{{\"error\": \"Unexpected error: {ex.Message}\"}}";
        }
    }
}

// --- MAIN PROGRAM ---

public class Program
{
    public static async Task Main(string[] args)
    {
        // IMPORTANT: For security, read the API key from an environment variable.
        // DO NOT hardcode your API key in the source code.
        var apiKey = Environment.GetEnvironmentVariable("EASYSENDSMS_API_KEY");

        if (string.IsNullOrWhiteSpace(apiKey))
        {
            Console.ForegroundColor = ConsoleColor.Yellow;
            Console.WriteLine("Warning: EASYSENDSMS_API_KEY environment variable not set.");
            Console.WriteLine("Please set it to your actual EasySendSMS API key.");
            Console.ResetColor();
            // Using a placeholder for demonstration purposes.
            apiKey = "YOUR_API_KEY"; 
        }

        var client = new EasySendSmsClient(apiKey);

        Console.WriteLine("--- Sending Example 1: Single Recipient ---");
        var singleSms = new SmsRequest
        {
            From = "YourSender",
            To = "12345678901",
            Text = "Hello from C#! This is a test message.",
            Type = "0"
        };
        await client.SendSmsAsync(singleSms);

        Console.WriteLine("\n--- Sending Example 2: Multiple Recipients ---");
        var bulkSms = new SmsRequest
        {
            From = "AlertSystem",
            To = "12345678901,12345678902",
            Text = "System maintenance will begin in 1 hour.",
            Type = "0"
        };
        await client.SendSmsAsync(bulkSms);
        
        Console.WriteLine("\n--- Sending Example 3: Unicode (Arabic) Message ---");
        var unicodeSms = new SmsRequest
        {
            From = " تذكير", // "Reminder" in Arabic
            To = "12345678903",
            Text = "مرحبا، هذه رسالة اختبار!", // "Hello, this is a test message!" in Arabic
            Type = "1"
        };
        await client.SendSmsAsync(unicodeSms);
    }
}
