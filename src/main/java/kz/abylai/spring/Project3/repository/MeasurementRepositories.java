package kz.abylai.spring.Project3.repository;

import kz.abylai.spring.Project3.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepositories extends JpaRepository<Measurement, Integer> {

}
