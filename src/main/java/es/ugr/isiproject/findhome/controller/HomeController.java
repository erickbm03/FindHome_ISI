package es.ugr.isiproject.findhome.controller;

import es.ugr.isiproject.findhome.repository.PaisRepository;
import es.ugr.isiproject.findhome.model.PaisPuntuado;
import es.ugr.isiproject.findhome.model.Pais;
import es.ugr.isiproject.findhome.model.BusquedaCriterios;
import es.ugr.isiproject.findhome.service.RankingService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.Collectors;

@Controller
public class HomeController {

  private final PaisRepository paisRepository;
  private final RankingService rankingService;

  public HomeController(PaisRepository paisRepository, RankingService rankingService) {
    this.paisRepository = paisRepository;
    this.rankingService = rankingService;
  }

  @GetMapping("/home")
  public String index(Model model) {
    model.addAttribute("paises", paisRepository.findAll());
    return "index"; // renderiza templates/index.html
  }

  @PostMapping("/ranking")
  public String ranking(@ModelAttribute BusquedaCriterios criterios, Model model) {
    List<PaisPuntuado> ranking = rankingService.calcularRanking(criterios);

    // Obtener lista de iso3 ordenada
    List<String> iso3Ordenados = ranking.stream()
                                 .map(PaisPuntuado::getIso3)
                                 .collect(Collectors.toList());

    // Buscar los objetos Pais según ese orden
    List<Pais> paisesOrdenados = iso3Ordenados.stream()
    .map(iso3 -> paisRepository.findByIso3(iso3).orElse(null))
    .filter(Objects::nonNull)
    .collect(Collectors.toList());

    model.addAttribute("paises", paisesOrdenados);
    return "index";
  }
}