package fr.lauparr.project_planner.server.projections;

import org.springframework.beans.factory.annotation.Value;

public interface MembreDTO {

  Long getId();

  String getUsername();

  @Value("#{target.details?.nom} #{target.details?.prenom}")
  String getNom();

  @Value("#{target.details?.email}")
  String getEmail();

  @Value("#{target.details?.compagnie}")
  String getCompagnie();

  @Value("#{target.details?.fixe}")
  String getFixe();

  @Value("#{target.details?.portable}")
  String getPortable();

}
