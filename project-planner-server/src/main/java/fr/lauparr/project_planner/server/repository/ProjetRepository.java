package fr.lauparr.project_planner.server.repository;

import fr.lauparr.project_planner.server.model.Projet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetRepository extends JpaRepository<Projet, Long> {
}
