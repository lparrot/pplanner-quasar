package fr.lauparr.project_planner.server.repository;

import fr.lauparr.project_planner.server.model.StatutTache;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatutTacheRepository extends JpaRepository<StatutTache, Long> {

  StatutTache findStatutTacheByInitialTrue();

}
