package fr.lauparr.project_planner.server.controller;

import fr.lauparr.project_planner.server.model.Utilisateur;
import fr.lauparr.project_planner.server.repository.UtilisateurRepository;
import fr.lauparr.project_planner.server.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private JwtService jwtService;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private UtilisateurRepository utilisateurRepository;

  @PostMapping("/login")
  @ResponseBody
  public ResponseEntity login(@RequestBody Map<String, Object> body) {
    Utilisateur utilisateur = utilisateurRepository.findByUsername((String) body.get("username"));
    if (utilisateur == null || !passwordEncoder.matches((CharSequence) body.getOrDefault("password", ""), utilisateur.getPassword())) {
      return ResponseEntity.badRequest().body("Login ou mot de passe incorrect");
    }

    Map<String, Object> result = new HashMap<>();
    result.put("token", jwtService.createToken(utilisateur));
    return ResponseEntity.ok(result);
  }

  @GetMapping("/user")
  @ResponseBody
  public ResponseEntity user(HttpServletRequest request) {
    Map<String, Object> result = new HashMap<>();
    result.put("user", jwtService.getClaims(request));
    return ResponseEntity.ok(result);
  }

}
