package es.ugr.isiproject.findhome.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.ugr.isiproject.findhome.datasource.api.RestCountriesClient;
import es.ugr.isiproject.findhome.model.Pais;
import es.ugr.isiproject.findhome.repository.PaisRepository;

@Service
public class PaisSyncService {

  private final PaisRepository paisRepository;
  private final RestCountriesClient restCountriesClient;

  public PaisSyncService(PaisRepository paisRepository, RestCountriesClient restCountriesClient) {
    this.paisRepository = paisRepository;
    this.restCountriesClient = restCountriesClient;
  }

  @Transactional
  public int syncFromRestCountries() {
    List<RestCountriesClient.CountryData> countries = restCountriesClient.fetchAllCountries();

    int updated = 0;

    for (RestCountriesClient.CountryData c : countries) {
      Pais p = paisRepository.findByIso3(c.iso3()).orElseGet(Pais::new);

      p.setIso3(c.iso3());
      if (c.nombre() != null) p.setNombre(c.nombre());

      if (c.poblacion() != null) {
        p.setHabitantes(String.valueOf(c.poblacion()));
      }

      paisRepository.save(p);
      updated++;
    }

    return updated;
  }
}