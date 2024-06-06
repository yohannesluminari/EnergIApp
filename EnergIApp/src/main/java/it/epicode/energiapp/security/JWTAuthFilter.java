package it.epicode.energiapp.security;

import it.epicode.energiapp.entities.User;
import it.epicode.energiapp.exceptions.NotFoundException;
import it.epicode.energiapp.exceptions.UnauthorizedException;
import it.epicode.energiapp.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Please fill the Authorization Header with a proper token.");
        }

        String accessToken = authHeader.substring(7);
        jwtUtils.verifyToken(accessToken);

        int id = jwtUtils.extractIdFromToken(accessToken);

        Optional<User> userOptional = userService.getUserById(id);

        if(userOptional.isPresent()){
            User user = userOptional.get();

            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        else{
            throw new NotFoundException("User with id= " + id + " not found");
        }

        filterChain.doFilter(request, response);
    }

    @Override //permette di non effettuare l'autenticazione per usare i servizi di autenticazione
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        return new AntPathMatcher().match("/auth/**", request.getServletPath());

    }
}
