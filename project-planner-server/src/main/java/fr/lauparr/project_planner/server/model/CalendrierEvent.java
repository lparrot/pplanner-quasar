package fr.lauparr.project_planner.server.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
public class CalendrierEvent implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nom;
  private String description;
  private LocalDateTime debut;
  private LocalDateTime fin;
  @ManyToOne
  private Projet projet;

  public CalendrierEvent(String nom, String description, LocalDateTime debut, LocalDateTime fin, Projet projet) {
    this.nom = nom;
    this.description = description;
    this.debut = debut;
    this.fin = fin;
    this.projet = projet;
  }
}
