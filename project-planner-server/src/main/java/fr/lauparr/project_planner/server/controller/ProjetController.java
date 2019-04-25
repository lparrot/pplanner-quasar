package fr.lauparr.project_planner.server.controller;

import fr.lauparr.project_planner.server.model.Projet;
import fr.lauparr.project_planner.server.projections.MembreDTO;
import fr.lauparr.project_planner.server.projections.ProjetDTO;
import fr.lauparr.project_planner.server.repository.ProjetRepository;
import fr.lauparr.project_planner.server.repository.UtilisateurRepository;
import fr.lauparr.project_planner.server.service.ProjectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/projets")
public class ProjetController {

  @Autowired
  private ProjetRepository projetRepository;
  @Autowired
  private UtilisateurRepository utilisateurRepository;
  @Autowired
  private ProjectionService projectionService;

  @Autowired
  public ProjetController() {
  }

  @GetMapping
  public ResponseEntity getProjets() {
    return ResponseEntity.ok(projectionService.convertToDto(projetRepository.findAll(), ProjetDTO.class));
  }

  @GetMapping("{id}/membres")
  public ResponseEntity getMembres(@PathVariable Long id) {
    Projet projet = projetRepository.findById(id).orElse(null);

    if (projet == null) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(projectionService.convertToDto(utilisateurRepository.findAllMembre(projet.getId()), MembreDTO.class));
  }

  @PutMapping("/{id}/membres")
  public ResponseEntity putMembres(@RequestBody List<Long> ids, @PathVariable Long id) {
    Projet projet = projetRepository.findById(id).orElse(null);
    if (projet == null) {
      return ResponseEntity.notFound().build();
    }
    projet.getUtilisateurs().addAll(utilisateurRepository.findAllById(ids));
    projet = projetRepository.save(projet);

    return ResponseEntity.ok(projectionService.convertToDto(projet, ProjetDTO.class));
  }

  @PutMapping("{id}/logo")
  public ResponseEntity putProjet(@RequestParam("file") MultipartFile file, @PathVariable Long id) throws IOException {
    Projet projet = projetRepository.findById(id).orElse(null);

    if (projet == null) {
      return ResponseEntity.notFound().build();
    }

    projet.setLogo(file.getBytes());

    projet = projetRepository.save(projet);
    return ResponseEntity.ok(projectionService.convertToDto(projet, ProjetDTO.class));
  }

  @DeleteMapping("/{idProjet}/membres/{idMembre}")
  public ResponseEntity deleteMembre(@PathVariable Long idProjet, @PathVariable Long idMembre) {
    Projet projet = projetRepository.findById(idProjet).orElse(null);

    if (projet == null) {
      return ResponseEntity.notFound().build();
    }

    boolean removed = projet.getUtilisateurs().removeIf(u -> u.getId().equals(idMembre));

    if (!removed) {
      return ResponseEntity.notFound().build();
    }

    projetRepository.save(projet);

    return ResponseEntity.ok(projectionService.convertToDto(projet, ProjetDTO.class));
  }

}
