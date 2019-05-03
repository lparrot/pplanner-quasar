package fr.lauparr.project_planner.server.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Projet implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  private Long id;
  @NotBlank
  private String nom;
  private String description;
  @Lob
  private byte[] logo;
  @ElementCollection(targetClass = String.class)
  private List<String> tags = new ArrayList<>();
  @OneToMany(mappedBy = "projet", cascade = CascadeType.PERSIST, orphanRemoval = true)
  @JsonManagedReference
  private List<GroupeTache> groupes = new ArrayList<>();
  @NotNull
  @ManyToOne
  @JsonManagedReference
  private Utilisateur proprietaire;
  @ManyToMany
  @JoinTable
  @JsonManagedReference
  private List<Utilisateur> utilisateurs = new ArrayList<>();

  public Projet(String nom, String description, @NotNull Utilisateur proprietaire) {
    this.nom = nom;
    this.description = description;
    this.proprietaire = proprietaire;
  }

  public Projet(@NotBlank String nom, String description, byte[] logo, @NotNull Utilisateur proprietaire) {
    this(nom, description, proprietaire);
    this.logo = logo;
  }

  public void addGroupe(GroupeTache groupeTache) {
    groupeTache.setProjet(this);
    this.groupes.add(groupeTache);
  }
}
