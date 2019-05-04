package fr.lauparr.project_planner.server.projections;

import java.util.List;

public interface TacheDTO {

  Long getId();

  String getTitre();

  String getDescription();

  List<String> getTags();

  MembreDTO getUtilisateur();
}
