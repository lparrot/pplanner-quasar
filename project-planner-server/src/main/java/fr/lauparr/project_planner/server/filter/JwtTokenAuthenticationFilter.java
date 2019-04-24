package fr.lauparr.project_planner.server.filter;

import fr.lauparr.project_planner.server.service.JwtService;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

  private JwtService jwtService;

  public JwtTokenAuthenticationFilter(JwtService jwtService) {this.jwtService = jwtService;}

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
    String header = request.getHeader(jwtService.getHeaderName());

    if (header != null && header.startsWith(jwtService.getPrefix())) {
      Claims claims = jwtService.getClaims(request);

      try {
        String username = claims.getSubject();
        if (username != null) {
          @SuppressWarnings("unchecked") List<String> authorities = (List<String>) claims.get("roles");
          UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
          SecurityContextHolder.getContext().setAuthentication(auth);
        }

      } catch (Exception e) {
        SecurityContextHolder.clearContext();
      }
    }

    chain.doFilter(request, response);
  }
}
