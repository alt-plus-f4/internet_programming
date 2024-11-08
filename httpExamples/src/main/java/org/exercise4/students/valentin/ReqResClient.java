package org.exercise4.students.valentin;

import java.net.URI;
import java.net.URLEncoder;
import java.net.URLDecoder;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

public class ReqResClient {
    private static final String BASE_URL = "https://reqres.in/api/users";
    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public ReqResClient() {
        client = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
    }

    // Task 1: List Users
    public void listUsers() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(BASE_URL + "?page=2")).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JsonNode users = objectMapper.readTree(response.body());
            System.out.println("List of Users: " + users.toPrettyString());
        }
    }

    // Task 2: Get Single User
    public void getUserById(int userId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(BASE_URL + "/" + userId)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JsonNode user = objectMapper.readTree(response.body());
            System.out.println("User Details: " + user.toPrettyString());
        } else {
            System.out.println("User not found.");
        }
    }

    // Task 3: Handle "User Not Found"
    public void handleUserNotFound(int userId) throws IOException, InterruptedException {
        getUserById(userId);
    }

    // Task 4: Create a New User
    public void createUser(String name, String job) throws IOException, InterruptedException {
        String json = String.format("{\"name\": \"%s\", \"job\": \"%s\"}", name, job);
        HttpRequest request = HttpRequest.newBuilder(URI.create(BASE_URL))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            JsonNode createdUser = objectMapper.readTree(response.body());
            System.out.println("Created User: " + createdUser.toPrettyString());
        }
    }

    // Task 5: Update User
    public void updateUser(int userId, String name, String job) throws IOException, InterruptedException {
        String json = String.format("{\"name\": \"%s\", \"job\": \"%s\"}", name, job);
        HttpRequest request = HttpRequest.newBuilder(URI.create(BASE_URL + "/" + userId))
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JsonNode updatedUser = objectMapper.readTree(response.body());
            System.out.println("Updated User: " + updatedUser.toPrettyString());
        }
    }

    // Update Only Job
    public void updateUserJob(int userId, String job) throws IOException, InterruptedException {
        String json = String.format("{\"job\": \"%s\"}", job);
        HttpRequest request = HttpRequest.newBuilder(URI.create(BASE_URL + "/" + userId))
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            JsonNode updatedUser = objectMapper.readTree(response.body());
            System.out.println("Updated Job: " + updatedUser.toPrettyString());
        }
    }

    // Task 6: Delete User
    public void deleteUser(int userId) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(BASE_URL + "/" + userId)).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 204) {
            System.out.println("User successfully deleted.");
        } else {
            System.out.println("User not found.");
        }
    }

    // Task 2: URL Validation and Encoding
    public void validateAndEncodeUrl(String inputUrl) {
        try {
            URL url = new URL(inputUrl);
            System.out.println("Valid URL: " + url);

            String encodedUrl = URLEncoder.encode(inputUrl, StandardCharsets.UTF_8);
            System.out.println("Encoded URL: " + encodedUrl);

            String decodedUrl = URLDecoder.decode(encodedUrl, StandardCharsets.UTF_8);
            System.out.println("Decoded URL: " + decodedUrl);
        } catch (Exception e) {
            System.out.println("Invalid URL format.");
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ReqResClient client = new ReqResClient();

        // Test each method
        client.listUsers();
        client.getUserById(2);
        client.handleUserNotFound(23);
        client.createUser("Valentin", "Developer");
        client.updateUser(2, "Valentin", "Lead Developer");
        client.updateUserJob(2, "Senior Developer");
        client.deleteUser(2);

        // Test URL validation and encoding
        client.validateAndEncodeUrl("https://example.com/search?query=hello world & java encoding");
    }
}
