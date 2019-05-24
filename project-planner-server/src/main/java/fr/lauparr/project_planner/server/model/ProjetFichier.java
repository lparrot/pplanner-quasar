package fr.lauparr.project_planner.server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"data"})
public class ProjetFichier implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String nom;
  private String type;
  @Lob
  private byte[] data;
  @ManyToOne
  @JsonBackReference
  private Projet projet;

  public ProjetFichier(String nom, String type, byte[] data) {
    this.nom = nom;
    this.type = type;
    this.data = data;
  }
}
