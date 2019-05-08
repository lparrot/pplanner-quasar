package fr.lauparr.project_planner.server.service;

import fr.lauparr.project_planner.server.model.Log;
import fr.lauparr.project_planner.server.model.Projet;
import fr.lauparr.project_planner.server.repository.LogRepository;
import fr.lauparr.project_planner.server.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LogService {

  @Autowired
  LogRepository logRepository;
  @Autowired
  UtilisateurRepository utilisateurRepository;

  public void creer(Projet projet, String message, Object... props) {
    Log log = new Log();
    log.setMessage(String.format(message, props));
    log.setProjet(projet);
    log.setUtilisateur(utilisateurRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
    logRepository.save(log);
  }

  public void creer(String message, Object... props) {
    this.creer(null, message, props);
  }

}
