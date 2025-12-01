package entities;

public class User {
    private String id;
    private String name;
    private String password;

    public User(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getPassword() { return password; }

    public void setName(String name) { this.name = name; }
    public void setPassword(String email) { this.password = email; }

    @Override
    public String toString() {
        return name;
    }
}