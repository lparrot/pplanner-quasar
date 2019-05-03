package fr.lauparr.project_planner.server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class GroupeTache implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotBlank
  private String nom;
  @ManyToOne
  @JsonBackReference
  private Projet projet;
  @OneToMany(mappedBy = "groupe", cascade = CascadeType.REMOVE, orphanRemoval = true)
  @JsonManagedReference
  private List<Tache> taches = new ArrayList<>();

  public void addTache(Tache tache) {
    tache.setGroupe(this);
    taches.add(tache);
  }

}
