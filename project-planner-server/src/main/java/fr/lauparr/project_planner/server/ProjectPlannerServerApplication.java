package fr.lauparr.project_planner.server;

import fr.lauparr.project_planner.server.model.Projet;
import fr.lauparr.project_planner.server.model.Utilisateur;
import fr.lauparr.project_planner.server.model.UtilisateurDetails;
import fr.lauparr.project_planner.server.repository.ProjetRepository;
import fr.lauparr.project_planner.server.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ProjectPlannerServerApplication implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final ProjetRepository projetRepository;
    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public ProjectPlannerServerApplication(ProjetRepository projetRepository, UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        this.projetRepository = projetRepository;
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(ProjectPlannerServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Utilisateur user1 = creerUtilisateur("lparrot", "Parrot", "Laurent", "kestounet@gmail.com", LocalDate.of(1983, 9, 3), "CDAD-R", "06 48 09 11 32", "06 48 09 11 32");
        Utilisateur user2 = creerUtilisateur("aparrot", "Parrot", "Anne", "patesdegeek@gmail.com", LocalDate.of(1982, 7, 13), "Maison Rambouillet", "01 23 45 67 89", "01 23 45 67 89");
        Utilisateur user3 = creerUtilisateur("ebauduin", "Bauduin", "Edith", "edith.bauduin@gmail.com", LocalDate.of(1960, 3, 17), "Maison Guesnain", "98 76 54 32 10", "98 76 54 32 10");

        List<Projet> projets = Arrays.asList(
                new Projet("CCS", "Carte de Circulation Sécurisée", user1),
                new Projet("VDD", "Valise Diplomatique Défense", user1),
                new Projet("PRDV", "Eureka Module Prise de Rendez-Vous", user2)
        );

        projetRepository.saveAll(projets);
    }

    private Utilisateur creerUtilisateur(String username, String nom, String prenom, String email, LocalDate dateNaissance, String compagnie, String fixe, String portable) {
        Utilisateur utilisateur = new Utilisateur();
        UtilisateurDetails details = new UtilisateurDetails();
        utilisateur.setUsername(username);
        utilisateur.setPassword(passwordEncoder.encode("123"));
        details.setNom(nom);
        details.setPrenom(prenom);
        details.setEmail(email);
        details.setDateNaissance(dateNaissance);
        details.setCompagnie(compagnie);
        details.setFixe(fixe);
        details.setPortable(portable);
        utilisateur.setDetails(details);

        return utilisateurRepository.save(utilisateur);
    }
}
