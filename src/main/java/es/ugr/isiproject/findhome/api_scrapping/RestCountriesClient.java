package es.ugr.isiproject.findhome.datasource.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestCountriesClient {

  private final RestTemplate restTemplate;

  public RestCountriesClient(RestTemplateBuilder builder) {
    this.restTemplate = builder.build();
  }

  public List<CountryData> fetchAllCountries() {
    // Pedimos solo campos necesarios para reducir payload
    String url = "https://restcountries.com/v3.1/all?fields=name,cca2,cca3,population";
    
    @SuppressWarnings("unchecked")
    List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);

    List<CountryData> out = new ArrayList<>();
    if (response == null) return out;

    for (Map<String, Object> item : response) {
      String iso3 = (String) item.get("cca3");
      String iso2 = (String) item.get("cca2");

      // name es un objeto: { common: "...", official: "..." }
      String nombre = null;
      Object nameObj = item.get("name");
      if (nameObj instanceof Map<?, ?> nameMap) {
        Object common = ((Map<?, ?>) nameMap).get("common");
        if (common != null) nombre = common.toString();
      }

      Long poblacion = null;
      Object popObj = item.get("population");
      if (popObj instanceof Number n) poblacion = n.longValue();

      if (iso3 == null || iso3.isBlank()) continue; // claves imprescindibles
      if (iso2 == null || iso2.isBlank()) continue; 

      out.add(new CountryData(iso2, iso3, nombre, poblacion));
    }

    return out;
  }

  public record CountryData(String iso2, String iso3, String nombre, Long poblacion) {}
}