package fr.lauparr.project_planner.server.projections;

import java.util.List;

public interface ProjetDTO {

  Long getId();

  String getNom();

  byte[] getLogo();

  String getDescription();

  MembreDTO getProprietaire();

  List<MembreDTO> getUtilisateurs();

}
