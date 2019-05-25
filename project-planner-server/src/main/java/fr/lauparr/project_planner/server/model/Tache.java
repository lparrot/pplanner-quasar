package fr.lauparr.project_planner.server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Tache implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String titre;
  private String description;
  private Float estimation;
  @NotNull
  @ManyToOne
  private StatutTache statut;
  @ManyToOne
  private Utilisateur utilisateur;
  @ElementCollection(targetClass = String.class)
  private List<String> tags;
  @ManyToOne
  @JsonBackReference
  private GroupeTache groupe;
  private int ordre;

  public Tache(String titre, String description, @NotNull StatutTache statut) {
    this.titre = titre;
    this.description = description;
    this.statut = statut;
  }
}
