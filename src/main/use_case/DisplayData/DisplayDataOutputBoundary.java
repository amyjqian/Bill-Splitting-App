package main.use_case.DisplayData;

import java.util.Map;

public interface DisplayDataOutputBoundary {
    void present(Map<String, Map<String, Object>> data);
}
