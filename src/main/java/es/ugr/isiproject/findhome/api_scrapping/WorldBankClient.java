package es.ugr.isiproject.findhome.datasource.api;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class WorldBankClient {

  private final RestTemplate restTemplate = new RestTemplate();

  public List<CountryData> fetchAllCountries() {
    Map<String, Double> ingresoMap = fetchIndicator("NY.GDP.PCAP.CD"); // PIB per cápita (USD)
    Map<String, Double> empleoMap = fetchIndicator("SL.TLF.CACT.ZS");  // Workforce participation rate (%)
    
    Set<String> allIso2 = new HashSet<>();
    allIso2.addAll(ingresoMap.keySet());
    allIso2.addAll(empleoMap.keySet());

    List<CountryData> result = new ArrayList<>();
    for (String iso2 : allIso2) {
      Double ingresosMedios = ingresoMap.get(iso2);
      Double tasaEmpleo = empleoMap.get(iso2);
      result.add(new CountryData(iso2, ingresosMedios, tasaEmpleo));
    }
    return result;
  }

  private Map<String, Double> fetchIndicator(String indicator) {
    String url = "https://api.worldbank.org/v2/country/all/indicator/" + indicator + "?date=2022&format=json&per_page=4000";
    List<?> response = restTemplate.getForObject(url, List.class);
    Map<String, Double> result = new HashMap<>();
    if (response == null || response.size() < 2) return result;
    List<Map<String, Object>> data = (List<Map<String, Object>>) response.get(1);
    for (var entry : data) {
      if (entry == null) continue;
      Map<String, Object> country = (Map<String, Object>) entry.get("country");
      String iso2 = country != null ? (String) country.get("id") : null;
      Double value = entry.get("value") != null ? parseDouble(entry.get("value")) : null;
      if (iso2 != null && value != null) {
        result.put(iso2, value);
      }
    }
    return result;
  }

  private Double parseDouble(Object o) {
    if (o instanceof Number n) return n.doubleValue();
    try { return Double.valueOf(o.toString()); } catch (Exception e) { return null; }
  }

  public record CountryData(String iso2, Double ingresosMedios, Double tasaEmpleo) {}

}