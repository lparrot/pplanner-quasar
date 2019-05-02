package fr.lauparr.project_planner.server.projections;

import fr.lauparr.project_planner.server.model.GroupeTache;

import java.util.List;

public interface ProjetDTO {

  Long getId();

  String getNom();

  byte[] getLogo();

  List<String> getTags();

  List<GroupeTache> getGroupes();

  String getDescription();

  MembreDTO getProprietaire();

  List<MembreDTO> getUtilisateurs();

}
