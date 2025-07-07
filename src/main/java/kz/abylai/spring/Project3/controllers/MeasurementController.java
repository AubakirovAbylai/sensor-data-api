package kz.abylai.spring.Project3.controllers;

import kz.abylai.spring.Project3.dto.MeasurementDTO;
import kz.abylai.spring.Project3.dto.MeasurementResponse;
import kz.abylai.spring.Project3.dto.SensorDTO;
import kz.abylai.spring.Project3.models.Measurement;
import kz.abylai.spring.Project3.models.Sensor;
import kz.abylai.spring.Project3.service.MeasurementService;
import kz.abylai.spring.Project3.service.SensorService;
import kz.abylai.spring.Project3.util.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("measurements")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;
    private final MeasurementValidator measurementValidator;
    private final SensorService sensorService;

    @Autowired
    public MeasurementController(MeasurementService measurementService,
                                 ModelMapper modelMapper,
                                 MeasurementValidator measurementValidator,
                                 SensorService sensorService) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
        this.sensorService = sensorService;
    }

    @GetMapping
    public MeasurementResponse measurementDTOList(){
        return new MeasurementResponse(measurementService.findAll().stream()
                .map(this::convertToMeasurementDTO).collect(Collectors.toList()));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody MeasurementDTO measurementDTO,
                                                     BindingResult bindingResult){
        Measurement measurement = convertToMeasurement(measurementDTO);

        measurementValidator.validate(measurement, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new MeasurementException(bindingResult.getFieldError().getDefaultMessage());
        }

        measurementService.save(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("rainyDaysCount")
    public Integer rainyDaysCount(){
        return measurementService.rainyDaysCount();
    }

    private Measurement convertToMeasurement(MeasurementDTO dto) {
        Measurement measurement = modelMapper.map(dto, Measurement.class);

        String sensorName = dto.getSensor().getName();
        Sensor sensor = sensorService.findByName(sensorName)
                .orElseThrow(() -> new SensorNotFoundException("Сенсор с именем " + sensorName + " не найден"));

        measurement.setSensor(sensor);
        return measurement;
    }


    private MeasurementDTO convertToMeasurementDTO(Measurement measurement){
        return modelMapper.map(measurement, MeasurementDTO.class);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO, Sensor.class);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementException e){
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(), System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
