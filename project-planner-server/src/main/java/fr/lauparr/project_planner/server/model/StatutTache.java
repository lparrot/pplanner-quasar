package fr.lauparr.project_planner.server.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
public class StatutTache implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String nom;

  private String description;

  private String couleur;

  private boolean initial;

  public StatutTache(String nom, String description, String couleur, boolean initial) {
    this.nom = nom;
    this.description = description;
    this.couleur = couleur;
    this.initial = initial;
  }
}
