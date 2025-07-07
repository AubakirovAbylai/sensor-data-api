package kz.abylai.spring.Project3.util;

public class MeasurementErrorResponse extends RuntimeException {
    public MeasurementErrorResponse(String message) {
        super(message);
    }

    public MeasurementErrorResponse(String message, long l) {
    }
}
