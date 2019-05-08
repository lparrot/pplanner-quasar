package fr.lauparr.project_planner.server.repository;

import fr.lauparr.project_planner.server.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogRepository extends JpaRepository<Log, Long> {

  List<Log> findAllByProjetIdOrderByDateCreationDesc(Long id);

}
