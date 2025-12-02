package interface_adapters.displayData;

import java.util.Map;

public class DisplayDataViewModel {

    private Map<String, Map<String, Object>> data;

    public Map<String, Map<String, Object>> getData() {
        return data;
    }

    public void setData(Map<String, Map<String, Object>> data) {
        this.data = data;
    }
}
