package fr.lauparr.project_planner.server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
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


}
