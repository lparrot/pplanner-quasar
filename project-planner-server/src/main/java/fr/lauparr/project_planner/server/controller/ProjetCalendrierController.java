package fr.lauparr.project_planner.server.controller;

import fr.lauparr.project_planner.server.model.CalendrierEvent;
import fr.lauparr.project_planner.server.projections.CalendrierEventDTO;
import fr.lauparr.project_planner.server.repository.CalendrierEventRepository;
import fr.lauparr.project_planner.server.service.ProjectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/projets/calendriers")
public class ProjetCalendrierController {

  @Autowired
  private CalendrierEventRepository calendrierEventRepository;
  @Autowired
  private ProjectionService projectionService;

  @GetMapping("/{id}/events")
  public ResponseEntity getCalendriersEvents(@PathVariable("id") Long id, @RequestParam("debut") String debut, @RequestParam("fin") String fin) {
    LocalDate finEvent;
    LocalDate debutEvent;
    try {
      debutEvent = LocalDate.parse(debut);
      finEvent = LocalDate.parse(fin);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body("Erreur sur les dates");
    }
    List<CalendrierEvent> events = calendrierEventRepository.findAllByProjetBetweenDates(id, debutEvent, finEvent);
    return ResponseEntity.ok(projectionService.convertToDto(events, CalendrierEventDTO.class));
  }

}
