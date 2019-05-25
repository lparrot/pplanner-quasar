package fr.lauparr.project_planner.server.controller;

import fr.lauparr.project_planner.server.repository.GroupeTacheRepository;
import fr.lauparr.project_planner.server.repository.TacheRepository;
import fr.lauparr.project_planner.server.service.ProjectionService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/groupes")
public class GroupeController {

  @Autowired
  private ProjectionService projectionService;
  @Autowired
  private GroupeTacheRepository groupeTacheRepository;
  @Autowired
  private TacheRepository TacheRepository;

  @Transactional
  @PutMapping("/ordre")
  public ResponseEntity putGroupeOrdre(@RequestBody List<GroupeTachePutOrdreParams> params) {
    for (GroupeTachePutOrdreParams groupe: params) {
      groupeTacheRepository.updateGroupeOrderById(groupe.getId(), groupe.getOrdre());
    }
    return ResponseEntity.ok(true);
  }

  @Transactional
  @PutMapping("/taches/ordre")
  public ResponseEntity putTacheOrdre(@RequestBody List<GroupeTachePutOrdreParams> params) {
    for (GroupeTachePutOrdreParams tache: params) {
      TacheRepository.updateTacheOrderById(tache.getId(), tache.getOrdre());
    }
    return ResponseEntity.ok(true);
  }

  @Data
  static class GroupeTachePutOrdreParams {
    Long id;
    int ordre;
  }

}
