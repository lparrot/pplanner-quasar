package fr.lauparr.project_planner.server.projections;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface CalendrierEventDTO {

  Long getId();

  String getNom();

  String getDescription();

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime getDebut();

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  LocalDateTime getFin();

}
