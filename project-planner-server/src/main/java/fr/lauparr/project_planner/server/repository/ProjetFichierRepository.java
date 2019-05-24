package fr.lauparr.project_planner.server.repository;

import fr.lauparr.project_planner.server.model.ProjetFichier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjetFichierRepository extends JpaRepository<ProjetFichier, Long> {

  @Query("select o.nom from ProjetFichier o where o.projet.id = :id")
  List<String> findAllNomFichierByProjet(@Param("id") Long id);

}
