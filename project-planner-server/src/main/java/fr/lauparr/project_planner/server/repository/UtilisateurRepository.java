package fr.lauparr.project_planner.server.repository;

import fr.lauparr.project_planner.server.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

  Utilisateur findByUsername(String username);

  @Query("select u from Utilisateur u, Projet p where p.id = :idProjet and  u not member of p.utilisateurs and not (u = p.proprietaire)")
  List<Utilisateur> findAllMembre(@Param("idProjet") Long idProjet);

}
