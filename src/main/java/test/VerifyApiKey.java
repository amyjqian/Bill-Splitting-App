package test;

import api.SplitwiseAPIImpl;

public class VerifyApiKey {
    public static void main(String[] args) {
        System.out.println("Verifying API Key");

        SplitwiseAPIImpl api = new SplitwiseAPIImpl();
        String apiKey = api.getAPIKey();

        System.out.println("API Key: " + apiKey);
        System.out.println("API Key Length: " + apiKey.length());
        System.out.println("Expected Key: smmaCgUHfNZ3KRPzuny1KxRqLGMYoPzlHj6ABJwA");
        System.out.println("Keys Match: " + apiKey.equals("smmaCgUHfNZ3KRPzuny1KxRqLGMYoPzlHj6ABJwA"));

        // Test if it starts/ends correctly
        System.out.println("Starts with: " + apiKey.substring(0, 4));
        System.out.println("Ends with: " + apiKey.substring(apiKey.length() - 4));
    }
}