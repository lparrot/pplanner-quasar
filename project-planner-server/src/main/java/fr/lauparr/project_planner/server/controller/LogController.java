package fr.lauparr.project_planner.server.controller;

import fr.lauparr.project_planner.server.projections.LogDTO;
import fr.lauparr.project_planner.server.repository.LogRepository;
import fr.lauparr.project_planner.server.repository.ProjetRepository;
import fr.lauparr.project_planner.server.service.ProjectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logs")
public class LogController {

  @Autowired
  ProjectionService projectionService;
  @Autowired
  ProjetRepository projetRepository;
  @Autowired
  LogRepository logRepository;

  @GetMapping("{id}")
  public ResponseEntity getLogs(@PathVariable Long id) {
    return ResponseEntity.ok(projectionService.convertToDto(logRepository.findAllByProjetIdOrderByDateCreationDesc(id), LogDTO.class));
  }

}
