package fr.lauparr.project_planner.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;

@RestController
@RequestMapping("/api/security")
public class SecurityController {

  private final RestTemplate restTemplate;

  private final Environment envs;

  @Autowired
  public SecurityController(RestTemplate restTemplate, Environment envs) {
    this.restTemplate = restTemplate;
    this.envs = envs;
  }

  @GetMapping("/user")
  public Principal user(Principal principal) {
    return principal;
  }

}
