package br.com.pj2.back.entrypoint.api.config;

import br.com.pj2.back.core.gateway.TokenGateway;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthFilter extends OncePerRequestFilter {
    private final TokenGateway tokenGateway;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String path = request.getServletPath();
        if (isPublicRoute(path)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (isNull(authHeader) || !authHeader.startsWith("Bearer ")) {
            log.error("Authorization header doesn't provided or is invalid.");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring("Bearer ".length());
            final String registration = tokenGateway.extractSubject(jwt);

            if (isNull(registration)) {
                log.error("It was not possible to extract user information from token.");
            }

            var userDetails = userDetailsService.loadUserByUsername(registration);
            if (!tokenGateway.isTokenValid(jwt, userDetails.getUsername()) || !tokenGateway.isAccessToken(jwt)) {
                log.error("The token provided is invalid or expired.");
                filterChain.doFilter(request, response);
                return;
            }

            final var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception ex) {
            log.error("Error during process of authentication.", ex);
        } finally {
            filterChain.doFilter(request, response);
        }
    }

    private boolean isPublicRoute(String path) {
        return path.startsWith("/auth/");
    }
}
