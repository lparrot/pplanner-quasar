package fr.lauparr.project_planner.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/security")
public class SecurityController {

	@RequestMapping("/user")
	public Principal user(Principal principal) {
		return principal;
	}

}
