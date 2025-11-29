package main.use_case.DisplayData;

import java.util.Map;

public interface DisplayDataOutputBoundary {
    void presentData(Map<String, Map<String, Object>> data);
}
