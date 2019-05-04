package fr.lauparr.project_planner.server.projections;

import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.stream.Collectors;

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

  default String getInitiales() {
    return Arrays.stream(getNom().split(" ")).map(data -> Character.toString(data.charAt(0))).collect(Collectors.joining());
  }

}
