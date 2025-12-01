package main.app;

public class Main {

    public static void main(String[] args) {

        String apiKey = System.getenv("SPLITWISE_API_KEY");

        System.out.println("Loaded API_KEY = " + apiKey);
        System.out.println("Working dir = " + System.getProperty("user.dir"));
    }
}