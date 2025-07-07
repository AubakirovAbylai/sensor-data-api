package kz.abylai.spring.Project3.controllers;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import kz.abylai.spring.Project3.dto.SensorDTO;
import kz.abylai.spring.Project3.models.Sensor;
import kz.abylai.spring.Project3.service.SensorService;
import kz.abylai.spring.Project3.util.MeasurementErrorResponse;
import kz.abylai.spring.Project3.util.MeasurementException;
import kz.abylai.spring.Project3.util.SensorValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sensors")
public class SensorRestController {
    private final SensorService sensorService;
    private final ModelMapper modelMapper;
    private final SensorValidator validator;

    @Autowired
    public SensorRestController(SensorService sensorService, ModelMapper modelMapper, SensorValidator validator) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @PostMapping("/registration")
    public void registration(@Valid @RequestBody SensorDTO sensorDTO, BindingResult bindingResult) {
         Sensor sensor = convertToSensor(sensorDTO);
         validator.validate(sensor, bindingResult);

         if(bindingResult.hasErrors()){
             throw new ValidationException();
         }

        sensorService.save(sensor);
    }

    @ExceptionHandler()
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementException ex) {
        MeasurementErrorResponse measurementErrorResponse = new MeasurementErrorResponse(
                ex.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(measurementErrorResponse, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    private SensorDTO convertToSensorDTO(Sensor sensor){
        return modelMapper.map(sensor, SensorDTO.class);
    }
}
