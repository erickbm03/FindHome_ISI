package es.ugr.isiproject.findhome.controller;

import es.ugr.isiproject.findhome.repository.PaisRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  private final PaisRepository paisRepository;

  public HomeController(PaisRepository paisRepository) {
    this.paisRepository = paisRepository;
  }

  @GetMapping("/home")
  public String index(Model model) {
    model.addAttribute("paises", paisRepository.findAll());
    return "index"; // renderiza templates/index.html
  }
}