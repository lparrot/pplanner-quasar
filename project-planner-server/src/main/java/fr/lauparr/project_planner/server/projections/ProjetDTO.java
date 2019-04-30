package fr.lauparr.project_planner.server.projections;

import fr.lauparr.project_planner.server.model.Projet;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

@Projection(name = "projetDTO", types = {Projet.class})
public interface ProjetDTO {

  Long getId();

  String getNom();

  byte[] getLogo();

  List<String> getTags();

  String getDescription();

  MembreDTO getProprietaire();

  List<MembreDTO> getUtilisateurs();

}
