package test;

import api.SplitwiseAPIImpl;
import okhttp3.OkHttpClient;

public class GroupCheck {
    public static void main(String[] args) {
        System.out.println("Group Check");

        SplitwiseAPIImpl api = new SplitwiseAPIImpl();

        try {
            final OkHttpClient client = new OkHttpClient().newBuilder().build();
            final okhttp3.Request request = new okhttp3.Request.Builder()
                    .url("https://www.splitwise.com/api/v3.0/get_groups")
                    .method("GET", null)
                    .addHeader("Authorization", "Bearer " + api.getAPIKey())
                    .addHeader("Content-Type", "application/json")
                    .build();

            final okhttp3.Response response = client.newCall(request).execute();
            final String responseBody = response.body().string();

            System.out.println("Raw API Response:");
            System.out.println(responseBody);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}