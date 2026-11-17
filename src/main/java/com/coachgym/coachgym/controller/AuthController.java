package com.coachgym.coachgym.controller;

import com.coachgym.coachgym.model.Usuario;
import com.coachgym.coachgym.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class AuthController {

    private final UsuarioRepository usuarioRepo;

    public AuthController(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String username,
                                @RequestParam String password,
                                HttpSession session,
                                Model model) {
        Optional<Usuario> opt = usuarioRepo.findByUsername(username.trim());
        if (opt.isPresent() && opt.get().getPassword().equals(password)) {
            session.setAttribute("usuarioLogueado", opt.get().getUsername());
            session.setAttribute("rol", opt.get().getRol());
            return "redirect:/";
        }
        model.addAttribute("error", "Usuario o contraseña incorrectos");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}