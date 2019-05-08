package fr.lauparr.project_planner.server.service;

import fr.lauparr.project_planner.server.model.Utilisateur;
import fr.lauparr.project_planner.server.repository.UtilisateurRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class JwtService {

  @Autowired
  UtilisateurRepository utilisateurRepository;

  private static final SignatureAlgorithm signature = SignatureAlgorithm.HS256;

  @Value("${app.jwt.token-prefix}")
  private String prefix;

  @Value("${app.jwt.salt-key}")
  private String saltKey;

  @Value("${app.jwt.header-name}")
  private String headerName;

  public String createToken(Utilisateur utilisateur) {
    return Jwts.builder().setClaims(utilisateur.getClaims()).signWith(signature, saltKey).compact();
  }

  public Utilisateur getUser(String token) {
    Claims claims = getClaims(token);
    return utilisateurRepository.findByUsername(claims.getSubject());
  }

  public Utilisateur getUser(HttpServletRequest request) {
    String token = request.getHeader(headerName);
    return getUser(token);
  }

  public Claims getClaims(String token) {
    if (token != null) {
      token = token.replace(String.format("%s ", prefix), "");
      return Jwts.parser().setSigningKey(saltKey).parseClaimsJws(token).getBody();
    }
    return null;
  }

  public Claims getClaims(HttpServletRequest request) {
    String token = request.getHeader(headerName);
    return getClaims(token);
  }

  public String getPrefix() {
    return prefix;
  }

  public String getSaltKey() {
    return saltKey;
  }

  public String getHeaderName() {
    return headerName;
  }
}
