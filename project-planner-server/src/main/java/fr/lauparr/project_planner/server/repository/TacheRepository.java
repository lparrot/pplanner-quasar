package fr.lauparr.project_planner.server.repository;

import fr.lauparr.project_planner.server.model.Tache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TacheRepository extends JpaRepository<Tache, Long> {

  @Modifying(flushAutomatically = true)
  @Query("update Tache t set t.ordre = :ordre where t.id = :id")
  void updateTacheOrderById(@Param("id") Long id, @Param("ordre") int ordre);

}
