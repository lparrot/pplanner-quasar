package fr.lauparr.project_planner.server.repository;

import fr.lauparr.project_planner.server.model.Tache;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TacheRepository extends JpaRepository<Tache, Long> {
}
