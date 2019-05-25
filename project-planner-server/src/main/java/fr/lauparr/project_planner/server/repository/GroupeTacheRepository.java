package fr.lauparr.project_planner.server.repository;

import fr.lauparr.project_planner.server.model.GroupeTache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupeTacheRepository extends JpaRepository<GroupeTache, Long> {

  @Modifying(flushAutomatically = true)
  @Query("update GroupeTache g set g.ordre = :ordre where g.id = :id")
  void updateGroupeOrderById(@Param("id") Long id, @Param("ordre") int ordre);

}
