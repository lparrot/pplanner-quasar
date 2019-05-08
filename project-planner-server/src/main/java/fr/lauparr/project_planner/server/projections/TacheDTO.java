package fr.lauparr.project_planner.server.projections;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface TacheDTO {

  Long getId();

  String getTitre();

  String getDescription();

  @Value("#{target.groupe.nom}")
  String getGroupe();

  Float getEstimation();

  List<String> getTags();

  MembreDTO getUtilisateur();

  StatutTacheDTO getStatut();
}
