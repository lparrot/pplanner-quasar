package fr.lauparr.project_planner.server.controller;

import fr.lauparr.project_planner.server.model.Projet;
import fr.lauparr.project_planner.server.model.ProjetFichier;
import fr.lauparr.project_planner.server.projections.ProjetDTO;
import fr.lauparr.project_planner.server.repository.ProjetFichierRepository;
import fr.lauparr.project_planner.server.repository.ProjetRepository;
import fr.lauparr.project_planner.server.service.ProjectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api/projets/fichiers")
public class ProjetFichierController {

  @Autowired
  private ProjetRepository projetRepository;
  @Autowired
  private ProjetFichierRepository projetFichierRepository;
  @Autowired
  private ProjectionService projectionService;

  @PostMapping("/{id}")
  public ResponseEntity postFichiers(@PathVariable Long id, MultipartHttpServletRequest request) {
    Projet projet = projetRepository.findById(id).orElse(null);
    if (projet == null) {
      return ResponseEntity.notFound().build();
    }
    List<String> nomFichiers = projetFichierRepository.findAllNomFichierByProjet(id);
    Iterator<String> filenames = request.getFileNames();
    while(filenames.hasNext()) {
      MultipartFile file = request.getFile(filenames.next());
      String fileName = StringUtils.cleanPath(file.getOriginalFilename());
      if (nomFichiers.stream().noneMatch(fileName::equals)) {
        try {
          ProjetFichier projetFichier = new ProjetFichier(fileName, file.getContentType(), file.getBytes());
          projet.addFichier(projetFichier);
          projetFichierRepository.save(projetFichier);
        } catch (IOException ignored) {
        }
      }
    }
    return ResponseEntity.ok(projectionService.convertToDto(projet, ProjetDTO.class));
  }

}
