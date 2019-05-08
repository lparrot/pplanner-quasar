package fr.lauparr.project_planner.server.projections;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface LogDTO {

  Long getId();

  String getMessage();

  @Value("#{target.utilisateur.details?.nom} #{target.utilisateur.details?.prenom}")
  String getUtilisateur();

  @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
  LocalDateTime getDateCreation();

}
