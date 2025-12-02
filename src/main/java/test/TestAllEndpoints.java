package test;

import api.SplitwiseAPIImpl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestAllEndpoints {
    public static void main(String[] args) {
        System.out.println("Testing All Splitwise Endpoints");

        SplitwiseAPIImpl api = new SplitwiseAPIImpl();
        String apiKey = api.getAPIKey();
        OkHttpClient client = new OkHttpClient();

        String[] endpoints = {
                "/get_current_user",
                "/get_groups",
                "/get_friends",
                "/get_currencies"
        };

        for (String endpoint : endpoints) {
            try {
                Request request = new Request.Builder()
                        .url("https://www.splitwise.com/api/v3.0" + endpoint)
                        .method("GET", null)
                        .addHeader("Authorization", "Bearer " + apiKey)
                        .addHeader("Content-Type", "application/json")
                        .build();

                Response response = client.newCall(request).execute();
                String responseBody = response.body().string();

                System.out.println("Status: " + response.code());
                if (response.isSuccessful()) {
                    System.out.println("SUCCESS!");
                    // Show first 300 characters of response
                    System.out.println("Response preview: " +
                            responseBody.substring(0, Math.min(300, responseBody.length())));
                } else {
                    System.out.println("FAILED");
                    System.out.println("Full response: " + responseBody);
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}