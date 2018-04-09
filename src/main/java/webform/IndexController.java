package webform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class IndexController {
    @Autowired
    private ChatRepository repository;

    @RequestMapping("/")
    public String redirect() {
        return "redirect:/login";
    }

    @PostMapping(value = "/index", params = "action=Log In")
    public String LogInIndex(@ModelAttribute User user) {
        if (repository.checkByLogin(user.getLogin()) != 0) {
            return "redirect:/login";
        }
        return "index";
    }

    @PostMapping(value = "/index", params = "action=Sign Up")
    public String SignUpIndex(@ModelAttribute User user) {
        if (repository.checkByLogin(user.getLogin()) != 0) {
            return "redirect:/login";
        }
        return "index";
    }

    @GetMapping("/index")
    public String getIndex(Model model) {
        model.addAttribute("user", new User());
        return "index";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

}
