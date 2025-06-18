package com.arnaud.p3.ChaTop.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request,
                       HttpServletResponse response,
                       AuthenticationException authException)
    throws IOException, ServletException {
    log.warn("Unauthorized error: {}", authException.getMessage());
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setHeader("X-Error-Message", "Vous devez etre administrateur pour effectuer cette action");
    response.setContentType("application/json");
    response.getWriter().write("{\"code\":\"UNAUTHORIZED\",\"message\":\"Vous devez être administrateur pour effectuer cette action\"}");


  }
}
