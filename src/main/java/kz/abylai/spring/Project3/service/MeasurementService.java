package kz.abylai.spring.Project3.service;

import kz.abylai.spring.Project3.dto.MeasurementDTO;
import kz.abylai.spring.Project3.models.Measurement;
import kz.abylai.spring.Project3.models.Sensor;
import kz.abylai.spring.Project3.repository.MeasurementRepositories;
import kz.abylai.spring.Project3.util.SensorNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private MeasurementRepositories repositories;
    private SensorService sensorService;
    private ModelMapper modelMapper;

    @Autowired
    public MeasurementService(MeasurementRepositories repositories, SensorService sensorService,  ModelMapper modelMapper) {
        this.repositories = repositories;
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
    }

    public List<Measurement> findAll() {
        return repositories.findAll();
    }

    public Measurement findById(int id) {
        Optional<Measurement> measurement = repositories.findById(id);
        return measurement.orElse(null);
    }

    @Transactional
    public void save(Measurement measurement) {
        measurement.setCreatedAt(LocalDate.now());

        // сохраняем
        repositories.save(measurement);
    }

    public Integer rainyDaysCount() {
        List<Measurement> measurements = findAll();

        Integer count = 0;
        for (Measurement measurement : measurements) {
            if (Boolean.TRUE.equals(measurement.getRaining()))
                count++;
        }

        return count;
    }
}
