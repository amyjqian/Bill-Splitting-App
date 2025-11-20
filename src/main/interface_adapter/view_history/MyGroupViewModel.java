package interface_adapter.view_history;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import entities.Expense;

public class MyGroupViewModel {

    private final String viewName;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private MyGroupState state = new MyGroupState();

    public MyGroupViewModel(String viewName) {
        this.viewName = viewName;
    }

    public MyGroupState getState() {
        return this.state;
    }

    public void setExpenses(List<Expense> expenses) {
        state.setExpenses(expenses);
    }

    public void setMessage(String message) {
        state.setMessage(message);
    }

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
