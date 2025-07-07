package kz.abylai.spring.Project3.dto;

import kz.abylai.spring.Project3.models.Measurement;

import java.util.ArrayList;
import java.util.List;

public class MeasurementResponse {
    private List<MeasurementDTO> measurements;

    public MeasurementResponse(List<MeasurementDTO> measurements) {
        this.measurements = measurements;
    }

    public List<MeasurementDTO> getMeasurementDTO() {
        return measurements;
    }
    public void setMeasurementDTO(List<MeasurementDTO> measurements) {
        this.measurements = measurements;
    }
}
