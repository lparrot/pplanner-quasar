package fr.lauparr.project_planner.server.projections;

import fr.lauparr.project_planner.server.model.ProjetFichier;

import java.util.List;

public interface ProjetDTO {

  Long getId();

  String getNom();

  byte[] getLogo();

  List<GroupeTacheDTO> getGroupes();

  String getDescription();

  MembreDTO getProprietaire();

  List<MembreDTO> getUtilisateurs();

  List<ProjetFichier> getFichiers();

}
