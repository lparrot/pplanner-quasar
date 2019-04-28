package fr.lauparr.project_planner.server.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import fr.lauparr.project_planner.server.model.Utilisateur;
import fr.lauparr.project_planner.server.projections.UtilisateurDetailsDTO;
import fr.lauparr.project_planner.server.repository.UtilisateurRepository;
import fr.lauparr.project_planner.server.service.ProjectionService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/security")
public class SecurityController {

  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private Environment envs;
  @Autowired
  private UtilisateurRepository utilisateurRepository;
  @Autowired
  private ProjectionService projectionService;


  @GetMapping("/user")
  public Principal user(Principal principal) {
    return principal;
  }

  @GetMapping("/account")
  public ResponseEntity getAccount(@RequestParam("username") @NotNull String username) {
    Utilisateur utilisateur = utilisateurRepository.findByUsername(username);

    if (utilisateur == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(projectionService.convertToDto(utilisateur, UtilisateurDetailsDTO.class));
  }

  @PutMapping("/account/{id}")
  public ResponseEntity putAccount(@RequestBody PutAccountParams params, @PathVariable Long id) {
    Utilisateur utilisateur = utilisateurRepository.findById(id).orElse(null);
    if (utilisateur == null) {
      return ResponseEntity.notFound().build();
    }

    utilisateur.getDetails().setNom(params.getNom());
    utilisateur.getDetails().setPrenom(params.getPrenom());
    utilisateur.getDetails().setEmail(params.getEmail());
    utilisateur.getDetails().setCompagnie(params.getCompagnie());
    utilisateur.getDetails().setFixe(params.getFixe());
    utilisateur.getDetails().setPortable(params.getPortable());
    utilisateur.getDetails().setDateNaissance(params.getDateNaissance());

    utilisateur = utilisateurRepository.save(utilisateur);
    return ResponseEntity.ok(projectionService.convertToDto(utilisateur, UtilisateurDetailsDTO.class));
  }

  @Data
  static class PutAccountParams {
    String nom;
    String prenom;
    String email;
    String compagnie;
    String fixe;
    String portable;
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate dateNaissance;
  }
}
