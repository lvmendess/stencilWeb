package com.stencilwebclient.stencilweb.controller;

import jakarta.servlet.http.HttpServletRequest;
import com.stencilwebclient.stencilweb.service.UsuarioDetailsService;
import com.stencilwebclient.stencilweb.models.Usuario; // adjust if your model class name is different
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final UsuarioDetailsService userDetailsService;

    public LoginController(UsuarioDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("")
    public String landingPage() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            HttpServletRequest request,
            Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }

        if (error != null) {
            model.addAttribute("error", "Invalid username or password.");
        }

        if (logout != null) {
            model.addAttribute("message", "You have been logged out.");
        }

        CsrfToken csrf = (CsrfToken) request.getAttribute("_csrf");
        if (csrf != null) {
            model.addAttribute("_csrf", csrf);
        }

        return "login";
    }

    /**
     * Renders the logout confirmation page and computes a user-specific "homeUrl".
     * The logout POST is still handled by Spring Security.
     */
    @GetMapping("/logout")
    public String logoutPage(
            @RequestParam(value = "message", required = false) String message,
            HttpServletRequest request,
            Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean authenticated = auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken);
        model.addAttribute("authenticated", authenticated);

        // default fallback
        String homeUrl = "/login";

        if (authenticated) {
            String username = auth.getName();

            // professor -> professor menu
            if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_A"))) {
                homeUrl = "/admin/menu";
            }
            else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_T"))) {
                homeUrl = "/professor/menu";
            }
            // aluno -> redirect to /aluno/user/{userId}
            else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_S"))) {
                try {
                    Usuario usuario = userDetailsService.findUserByUsername(username);
                    if (usuario != null) {
                        // adjust method name if your Usuario model uses a different getter for id
                        Integer userId = usuario.getIdUsuario();
                        if (userId != null) {
                            homeUrl = "/aluno/user/" + userId;
                        }
                    }
                } catch (Exception e) {
                    // swallow and leave fallback homeUrl; optionally log
                }
            }
            // other roles can be handled here
        }

        model.addAttribute("homeUrl", homeUrl);

        if (message != null) {
            model.addAttribute("message", message);
        }

        CsrfToken csrf = (CsrfToken) request.getAttribute("_csrf");
        if (csrf != null) {
            model.addAttribute("_csrf", csrf);
        }

        return "logout";
    }
}
