package es.ugr.isiproject.findhome.datasource.api;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class NumbeoScraper {

  public record CountryData(String country, Double costeVida, Double calidadVida) {}

  public List<CountryData> fetchAllCountries() throws Exception {

    // ----- Scrapeo Coste de Vida -----
    String urlCost = "https://www.numbeo.com/cost-of-living/rankings_by_country.jsp";
    Document docCost = Jsoup.connect(urlCost).get();

    Element tablaCost = docCost.selectFirst("table#t2");
    Map<String, Double> costePorPais = new HashMap<>();
    if (tablaCost != null) {
      Elements filas = tablaCost.select("tbody > tr");
      for (Element fila : filas) {
        Elements celdas = fila.select("td");
        if (celdas.size() < 3) continue;

        String pais = celdas.get(1).text();
        String indiceStr = celdas.get(2).text().replace(",", "");
        try {
          double coste = Double.parseDouble(indiceStr);
          costePorPais.put(pais, coste);
        } catch (NumberFormatException ignored) { }
      }
    }

    // ---- Scrapeo Calidad de Vida -----
    String urlQuality = "https://es.numbeo.com/calidad-de-vida/clasificaciones-por-pa%C3%ADs";
    Document docQuality = Jsoup.connect(urlQuality).get();

    Element tablaQuality = docQuality.selectFirst("table#t2");
    Map<String, Double> calidadPorPais = new HashMap<>();
    if (tablaQuality != null) {
      Elements filas = tablaQuality.select("tbody > tr");
      for (Element fila : filas) {
        Elements celdas = fila.select("td");
        if (celdas.size() < 3) continue;

        String pais = celdas.get(1).text();
        String indiceStr = celdas.get(2).text().replace(",", ".");
        try {
          double calidad = Double.parseDouble(indiceStr);
          calidadPorPais.put(pais, calidad);
        } catch (NumberFormatException ignored) { }
      }
    }

    // ---- Unión de datos por país ----
    Set<String> paises = new HashSet<>();
    paises.addAll(costePorPais.keySet());
    paises.addAll(calidadPorPais.keySet());

    List<CountryData> result = new ArrayList<>();
    for (String pais : paises) {
      Double coste = costePorPais.get(pais);
      Double calidad = calidadPorPais.get(pais);
      result.add(new CountryData(pais, coste, calidad));
    }

    return result;

  }
}