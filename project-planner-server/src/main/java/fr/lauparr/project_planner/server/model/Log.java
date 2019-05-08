package fr.lauparr.project_planner.server.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class Log implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String message;

  @ManyToOne
  Projet projet;

  @ManyToOne
  Utilisateur utilisateur;

  LocalDateTime dateCreation;

  @PrePersist
  private void prePersist() {
    dateCreation = LocalDateTime.now();
  }

}
