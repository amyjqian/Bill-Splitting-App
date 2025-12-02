package interface_adapter.view_history;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class MyGroupViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private MyGroupState state = new MyGroupState();

    public MyGroupViewModel() {}
    public MyGroupViewModel(String viewName) {}

    public MyGroupState getState() { return this.state; }

    public List<List<Object>> getExpenses() { return state.getExpenses(); }
    public String getMessage() { return state.getMessage(); }

    public void setExpenses(List<List<Object>> expenses) {
        state.setExpenses(expenses);
    }

    public void setMessage(String message) {
        state.setMessage(message);
    }

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, new MyGroupState(state));
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
