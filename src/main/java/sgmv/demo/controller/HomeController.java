package sgmv.demo.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        // Passa os dados do usuário para a página
        model.addAttribute("usuarioNome", session.getAttribute("usuarioNome"));
        model.addAttribute("usuarioEmail", session.getAttribute("usuarioEmail"));
        return "home/index"; // sua página principal do dashboard
    }
}
