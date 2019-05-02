package fr.lauparr.project_planner.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Entity
@JsonIgnoreProperties(value = {"password"})
public class Utilisateur implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  private Long id;
  @NotBlank
  private String username;
  private String password;
  @Embedded
  private UtilisateurDetails details;
  @ManyToMany(mappedBy = "utilisateurs")
  @JsonManagedReference
  private List<Projet> projets = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return AuthorityUtils.NO_AUTHORITIES;
  }

  public Claims getClaims() {
    Claims claims = Jwts.claims();
    claims.setSubject(username);
    claims.put("roles", getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
    if (details != null) {
      claims.put("nom", details.getNom());
      claims.put("prenom", details.getPrenom());
      claims.put("email", details.getEmail());
      if (details.getDateNaissance() != null) {
        claims.put("dateNaissance", details.getDateNaissance().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
      }
      claims.put("fixe", details.getFixe());
      claims.put("portable", details.getPortable());
      claims.put("fullname", details.getNom() + " " + details.getPrenom());
    }
    return claims;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
