package sgmv.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class UsuarioController {
    
    @RequestMapping("/")
    public String login1() {
        return "login/index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login/index";
    }

}
