package fr.lauparr.project_planner.server.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;

@Slf4j
@Data
@Embeddable
public class UtilisateurDetails implements Serializable {

  @NotBlank
  private String nom;
  @NotBlank
  private String prenom;
  @NotBlank
  private String email;
  @JsonFormat(pattern = "dd/MM/yyyy")
  private LocalDate dateNaissance;
  private String fixe;
  private String portable;

  public void reset() {
    Arrays.stream(this.getClass().getDeclaredFields()).forEach(f -> {
      try {
        f.setAccessible(true);
        f.set(this, null);
      } catch (IllegalAccessException e) {
        log.error(e.getMessage());
      }
    });
  }

}
