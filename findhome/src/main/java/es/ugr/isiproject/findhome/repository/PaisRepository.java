package es.ugr.isiproject.findhome.repository;

import java.util.Optional;
import es.ugr.isiproject.findhome.model.Pais;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaisRepository extends JpaRepository<Pais, Integer> {
  Optional<Pais> findByIso3(String iso3);
}