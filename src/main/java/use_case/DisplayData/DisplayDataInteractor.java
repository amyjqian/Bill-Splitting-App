package use_case.DisplayData;

import java.util.Map;

public class DisplayDataInteractor implements DisplayDataInputBoundary {

    private final ExpenseDataAccessInterface dataAccess;
    private final DisplayDataOutputBoundary presenter;

    public DisplayDataInteractor(ExpenseDataAccessInterface dataAccess,
                                 DisplayDataOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute() {
        Map<String, Map<String, Object>> data = dataAccess.getAllExpenses();
        presenter.present(data);
    }
}
