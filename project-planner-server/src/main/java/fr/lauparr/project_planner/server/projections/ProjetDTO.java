package fr.lauparr.project_planner.server.projections;

import java.util.List;

public interface ProjetDTO {

  Long getId();

  String getNom();

  String getDescription();

  MembreDTO getProprietaire();

  List<MembreDTO> getUtilisateurs();

}
