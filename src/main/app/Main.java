package main.app;
import io.github.cdimascio.dotenv.Dotenv;

public class Main {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                .directory(".")
                .load();
        String apiKey = dotenv.get("SPLITWISE_API_KEY");

        System.out.println("Loaded API_KEY = " + apiKey);
        System.out.println("Working dir = " + System.getProperty("user.dir"));
    }
}