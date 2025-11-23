package entities;

public class User {
    private final String id;
    private final String password;
    private final String email;
    private final String first_name;
    private final String last_name;

    public User(String id, String first_name, String last_name, String email, String password) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
    }

    // Getters
    public String getId() { return id; }
    public String getEmail() {
        return email;
    }
    public String getFirst_name() {
        return first_name;
    }
    public String getLast_name() {
        return last_name;
    }
    public String getPassword() { return password; }

    @Override
    public String toString() {
        return first_name + " " + last_name;
    }


}