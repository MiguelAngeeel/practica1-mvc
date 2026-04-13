package com.example.pr1;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.pr1.dao.UsuariosDAOInterface;
import com.example.pr1.dto.matc0007DTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class Control_matc0007 {

    @Autowired
    UsuariosDAOInterface usuariosDAO;


    // LOGIN PAGE
    @GetMapping("/login")
    public String showLogin(HttpSession session, Model model){

        if(session.getAttribute("username") != null){
            return "redirect:/articles";
        }

        return "login_matc0007";
    }


    // LOGIN PROCESS
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        // Intentar login desde MySQL (sesión 3)
        matc0007DTO usuario = usuariosDAO.login(username,password);

        if(usuario != null){

            session.setAttribute("username", username);

            if(username.equals("admin")){
                return "redirect:/admin";
            }

            return "redirect:/articles";
        }

        // Login hardcodeado (sesión 2)
        if(username.equals("admin") && password.equals("admin")){

            session.setAttribute("username", username);

            return "redirect:/admin";
        }

        if(username.equals("matc0007") && password.equals("1234")){

            session.setAttribute("username", username);

            return "redirect:/articles";
        }

        model.addAttribute("error", "User not registered");

        return "login_matc0007";
    }


    // ARTICLES PAGE (PROTEGIDA)
    @GetMapping("/articles")
    public String articles(HttpSession session, Model model){

        if(session.getAttribute("username") == null){
            return "redirect:/login";
        }

        model.addAttribute("username", session.getAttribute("username"));

        return "articles_matc0007";
    }


    // ADMIN PAGE (PROTEGIDA + SOLO ADMIN)
    @GetMapping("/admin")
    public String admin(HttpSession session, Model model){

        if(session.getAttribute("username") == null){
            return "redirect:/login";
        }

        if(!session.getAttribute("username").equals("admin")){
            return "redirect:/articles";
        }

        List<matc0007DTO> usuarios = usuariosDAO.leeUsuarios();

        model.addAttribute("usuarios", usuarios);

        return "admin_matc0007";
    }


    // se redirige a esta URL desde PayPal al confirmar el pago, 
    @GetMapping("/paymentconfirmed")
    public String paymentConfirmed() {
        return "redirect:/articles";
    }


    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session){

        session.invalidate();

        return "redirect:/login";
    }
}