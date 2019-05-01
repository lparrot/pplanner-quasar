package fr.lauparr.project_planner.server.repository;

import fr.lauparr.project_planner.server.model.GroupeTache;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupeTacheRepository extends JpaRepository<GroupeTache, Long> {
}
