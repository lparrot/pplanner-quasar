package fr.lauparr.project_planner.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/app")
public class ApplicationController {

  @GetMapping
  public Map getApp() {
    Map<String, String> config = new HashMap<String, String>();
    config.put("version", "1.0.0");
    return config;
  }

}
