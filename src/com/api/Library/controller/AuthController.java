package com.api.Library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AuthController {

    // Custom login page
    @GetMapping("/login")
    public String login() {
        return "login";  // This will map to a login.html view in the templates folder
    }

    // Custom logout handler (optional if you want to handle logout in your controller)
    @RequestMapping("/logout-success")
    public String logoutPage(Authentication authentication) {
        if (authentication != null) {
            // Perform any additional logout logic here if needed
        }
        return "redirect:/login?logout";  // Redirects to login page with a "logged out" message
    }

    // OAuth2 login handler (optional for custom views)
    @GetMapping("/oauth2/authorization/github")
    public String oauth2LoginGithub() {
        return "redirect:/oauth2/authorization/github";  // Redirect to GitHub OAuth2 login
    }

    @GetMapping("/oauth2/authorization/google")
    public String oauth2LoginGoogle() {
        return "redirect:/oauth2/authorization/google";  // Redirect to Google OAuth2 login
    }
}
