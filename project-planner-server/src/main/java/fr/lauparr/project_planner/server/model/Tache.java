package fr.lauparr.project_planner.server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class Tache implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String titre;
  private String description;
  @ManyToOne
  @JsonBackReference
  private GroupeTache groupe;

}
