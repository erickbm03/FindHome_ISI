package es.ugr.isiproject.findhome.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import es.ugr.isiproject.findhome.service.PaisSyncService;

@RestController
public class AdminController {

  private final PaisSyncService paisSyncService;

  public AdminController(PaisSyncService paisSyncService) {
    this.paisSyncService = paisSyncService;
  }

  @PostMapping("/admin/sync/restcountries")
  public ResponseEntity<String> syncRestCountries() {
    int count = paisSyncService.syncFromRestCountries();
    return ResponseEntity.ok("OK. Upserted countries: " + count);
  }

  @PostMapping("/admin/sync/worldbank")
  public ResponseEntity<String> syncWorldBank() {
    int count = paisSyncService.syncFromWorldBank();
    return ResponseEntity.ok("OK. Upserted countries: " + count);
  }

  @PostMapping("/admin/sync/numbeo")
  public ResponseEntity<String> syncNumbeo() {
    int count = paisSyncService.syncFromNumbeoScraper();
    return ResponseEntity.ok("OK. Upserted countries: " + count);
  }
}