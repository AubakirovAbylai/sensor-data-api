package kz.abylai.spring.Project3.repository;

import kz.abylai.spring.Project3.models.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensorRepositories extends JpaRepository<Sensor, Integer> {
    Optional<Sensor> findByName(String name);
}
