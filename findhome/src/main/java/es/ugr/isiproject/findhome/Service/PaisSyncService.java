package es.ugr.isiproject.findhome.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import es.ugr.isiproject.findhome.datasource.api.WorldBankClient;
import es.ugr.isiproject.findhome.datasource.api.RestCountriesClient;
import es.ugr.isiproject.findhome.datasource.api.NumbeoScraper;
import es.ugr.isiproject.findhome.model.Pais;
import es.ugr.isiproject.findhome.repository.PaisRepository;

@Service
public class PaisSyncService {

  private final PaisRepository paisRepository;
  private final RestCountriesClient restCountriesClient;
  private WorldBankClient worldBankClient;
  private final NumbeoScraper numbeoScraper;

  public PaisSyncService(PaisRepository paisRepository, RestCountriesClient restCountriesClient, 
                         WorldBankClient worldBankClient, NumbeoScraper numbeoScraper) {
    this.paisRepository = paisRepository;
    this.restCountriesClient = restCountriesClient;
    this.worldBankClient = worldBankClient;
    this.numbeoScraper = numbeoScraper;
  }

  @Transactional
  public int syncFromRestCountries() {
    List<RestCountriesClient.CountryData> countries = restCountriesClient.fetchAllCountries();
    int updated = 0;

    for (RestCountriesClient.CountryData c : countries) {
      // Requiere ambos: iso3 y iso2

      if (c.iso3() == null || c.iso3().isBlank() || c.iso2() == null || c.iso2().isBlank()) {

        continue;
      }

      Pais p = paisRepository.findByIso3(c.iso3()).orElseGet(Pais::new);

      p.setIso3(c.iso3());
      p.setIso2(c.iso2());

      if (c.nombre() != null) p.setNombre(c.nombre());
      if (c.poblacion() != null) p.setHabitantes(String.valueOf(c.poblacion()));

      paisRepository.save(p);
      updated++;
    }
    return updated;
  }

  @Transactional
  public int syncFromWorldBank() {
    List<WorldBankClient.CountryData> countries = worldBankClient.fetchAllCountries();
    int updated = 0;
    for (WorldBankClient.CountryData c : countries) {

        Pais p = paisRepository.findByIso2(c.iso2()).orElse(null);
        if (p == null) continue; // no crea nuevos países desde WorldBank, solo actualiza los existentes (worldbanck no da nombres)
        p.setIso2(c.iso2());

        if (c.ingresosMedios() != null){
          double original = c.ingresosMedios();
          double truncado = new java.math.BigDecimal(original)
                      .setScale(2, java.math.RoundingMode.DOWN)
                      .doubleValue();
          p.setIngresosMedios(truncado);
        }
        if (c.tasaEmpleo() != null)     p.setTasaEmpleo(c.tasaEmpleo());

        paisRepository.save(p);
        updated++;
    }
    return updated;
  }

  @Transactional
  public int syncFromNumbeoScraper() {

    List<NumbeoScraper.CountryData> countries;
    try {
        countries = numbeoScraper.fetchAllCountries();
    } catch (Exception e) {
        // Maneja el error (log, lanzar runtime, etc)
        e.printStackTrace();
        return 0;
    }

    int updated = 0;
    for (NumbeoScraper.CountryData c : countries) {

        Pais p = paisRepository.findByNombre(c.country()).orElse(null);
        if (p == null) continue; // no crea nuevos países desde WorldBank, solo actualiza los existentes (worldbanck no da nombres)

       
        if (c.costeVida() != null)     p.setCosteVida(c.costeVida());

        if (c.calidadVida() != null) {
          double calidad = c.calidadVida();
          int calidadVidaEstrellas = 0;

          if (calidad >= 60 && calidad < 100) {
            calidadVidaEstrellas = 1;
          } else if (calidad >= 100 && calidad < 160) {
            calidadVidaEstrellas = 2;
          } else if (calidad >= 160 && calidad < 190) {
            calidadVidaEstrellas = 3;
          } else if (calidad >= 190 && calidad < 210) {
            calidadVidaEstrellas = 4;
          } else if (calidad >= 210) {
            calidadVidaEstrellas = 5;
          }
          // 0 estrellas para menos de 60 o nulo

          p.setCalidadVidaEstrellas(calidadVidaEstrellas);
      }

        paisRepository.save(p);
        updated++;
    }
    return updated;
  }

}