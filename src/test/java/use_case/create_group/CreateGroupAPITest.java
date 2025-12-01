package use_case.create_group;

import api.SplitwiseAPIImpl;
import entities.Group;
import entities.User;
import org.json.JSONException;

import java.io.IOException;

public class CreateGroupAPITest {
    public static void main(String[] args) {
//        String apiKey = System.getenv("SPLITWISE_API_KEY");
//        System.out.println("SPLITWISE_API_KEY: " + apiKey);
        SplitwiseAPIImpl api = new SplitwiseAPIImpl();
        try {
            String groupName = "Updated API Test Group";

            System.out.println("Creating group: " + groupName);
            Group group = api.createGroup(groupName);
            System.out.println("Group Members: " + group.getMembers().toString());


            System.out.println("Group created successfully");
            System.out.println("Group ID: " + group.getId());
            System.out.println("Group Name: " + group.getName());

        } catch (Exception e) {
            System.out.println("Group creation failed: " + e.getMessage());
        }
    }
}
