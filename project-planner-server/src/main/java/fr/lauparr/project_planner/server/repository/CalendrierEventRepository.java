package fr.lauparr.project_planner.server.repository;

import fr.lauparr.project_planner.server.model.CalendrierEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CalendrierEventRepository extends JpaRepository<CalendrierEvent, Long> {

  @Query("select c from CalendrierEvent c where c.projet.id = ?1 and c.debut >= debut and c.fin <= fin")
  List<CalendrierEvent> findAllByProjetBetweenDates(Long projetId, LocalDate debut, LocalDate fin);

}
