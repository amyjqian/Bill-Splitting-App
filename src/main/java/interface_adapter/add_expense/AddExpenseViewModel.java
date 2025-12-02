package interface_adapter.add_expense;

public class AddExpenseViewModel {
    private String message;
    private boolean success;

    public AddExpenseViewModel(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
    public String getMessage() {
        return message;
    }
    public boolean isSuccess() {
        return success;
    }

}
