package kz.abylai.spring.Project3.service;

import kz.abylai.spring.Project3.models.Sensor;
import kz.abylai.spring.Project3.repository.SensorRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {
    private SensorRepositories sensorRepositories;

    @Autowired
    public SensorService(SensorRepositories sensorRepositories) {
        this.sensorRepositories = sensorRepositories;
    }

    public List<Sensor> findAll() {
        return sensorRepositories.findAll();
    }

    public Sensor findById(Integer id) {
        Optional<Sensor> sensor = sensorRepositories.findById(id);
        return sensor.orElse(null);
    }

    public Optional<Sensor> findByName(String name) {
        Optional<Sensor> sensor = sensorRepositories.findByName(name);
        return sensor;
    }

    @Transactional
    public void save(Sensor sensor) {
        sensor.setCreatedAt(LocalDate.now());

        sensorRepositories.save(sensor);
    }

    @Transactional
    public void update(Integer id, Sensor sensor) {
        Sensor savedSensor = findById(id);

        savedSensor.setName(sensor.getName());
        savedSensor.setCreatedAt(LocalDate.now());
        sensorRepositories.save(savedSensor);
    }

    @Transactional
    public void delete(Sensor sensor) {
        sensorRepositories.delete(sensor);
    }
}
