package interface_adapter.create_group;

/**
 * The state for the Create Group View Model.
 */
public class CreateGroupState {
    private String name = "";
    private String id = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
