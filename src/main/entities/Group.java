package main.entities;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private Long id;
    private String name;
    private List<User> members;

    public Group(Long id, String name, List<User> members) {
        this.id = id;
        this.name = name;
        this.members = members;
    }
    public Group (Long id, String name) {
        this.id = id;
        this.name = name;
        this.members = new ArrayList<>();
    }
    public void addMember(User user) {
        this.members.add(user);
    }

    public Group() {
        this.members = new java.util.ArrayList<>();
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public List<User> getMembers() { return members; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setMembers(List<User> members) { this.members = members; }

    public void addMember(User user) {
        if (!members.contains(user)) {
            members.add(user);
        }
    }

    public void removeMember(User user) {
        members.remove(user);
    }

    @Override
    public String toString() {
        return name;
    }

}