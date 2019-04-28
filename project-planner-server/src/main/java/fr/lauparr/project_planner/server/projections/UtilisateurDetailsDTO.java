package fr.lauparr.project_planner.server.projections;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

public interface UtilisateurDetailsDTO {
  String getId();

  @Value("#{target.details?.nom}")
  String getNom();

  @Value("#{target.details?.prenom}")
  String getPrenom();

  @Value("#{target.details?.email}")
  String getEmail();

  @Value("#{target.details?.compagnie}")
  String getCompagnie();

  @Value("#{target.details?.fixe}")
  String getFixe();

  @Value("#{target.details?.portable}")
  String getPortable();

  @Value("#{target.details?.dateNaissance}")
  LocalDate getDateNaissance();
}