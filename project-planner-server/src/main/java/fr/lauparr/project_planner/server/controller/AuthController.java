package fr.lauparr.project_planner.server.controller;

import fr.lauparr.project_planner.server.model.Utilisateur;
import fr.lauparr.project_planner.server.repository.UtilisateurRepository;
import fr.lauparr.project_planner.server.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final JwtService jwtService;
  private final UtilisateurRepository utilisateurRepository;

  @Autowired
  public AuthController(UtilisateurRepository utilisateurRepository, JwtService jwtService) {
    this.utilisateurRepository = utilisateurRepository;
    this.jwtService = jwtService;
  }

  @PostMapping("/login")
  @ResponseBody
  public ResponseEntity login(@RequestBody Map body) {
    Utilisateur utilisateur = utilisateurRepository.findByUsername((String) body.get("username"));
    if (utilisateur == null) {
      return ResponseEntity.badRequest().body("Utilisateur introuvable");
    }

    Map<String, Object> result = new HashMap<>();
    result.put("token", jwtService.createToken(utilisateur));
    return ResponseEntity.ok(result);
  }

  @RequestMapping("/user")
  @ResponseBody
  public ResponseEntity user(HttpServletRequest request) {
    Map<String, Object> result = new HashMap<>();
    result.put("user", jwtService.getClaims(request));
    return ResponseEntity.ok(result);
  }

}
