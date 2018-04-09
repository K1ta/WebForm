package webform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/")
    public String redirect() {
        return "redirect:/login";
    }

    @PostMapping(value = "/index", params = "action=Log In")
    public String LogInIndex(@ModelAttribute User user) {
        User _user = userRepository.getUserByLoginAndPassword(user.getLogin(), user.getPassword());
        if(_user == null) {
            return "redirect:/login?msg=Error in login or password";
        }
        return "redirect:/index?id=" + _user.getId();
    }

    @PostMapping(value = "/index", params = "action=Sign Up")
    public String SignUpIndex(@ModelAttribute User user) {
        if (userRepository.countAllUsersByLogin(user.getLogin()) != 0) {
            return "redirect:/login?msg=Error! Login \"" + user.getLogin() + "\" is already used";
        }
        userRepository.addUser(user.getLogin(), user.getPassword());
        User _user = userRepository.getUserByLoginAndPassword(user.getLogin(), user.getPassword());
        return "redirect:/index?id=" + _user.getId();
    }

    @GetMapping("/index")
    public String getIndex(Model model, @RequestParam(name = "id", required = false) Long id) {
        if(id == null) {
            return "redirect:/login";
        }
        User user = userRepository.getUserById(id);
        model.addAttribute("user", user);
        return "index";
    }

    @GetMapping("/login")
    public String loginForm(Model model, @RequestParam(name = "msg", required = false) String msg) {
        model.addAttribute("user", new User());
        model.addAttribute("message", msg);
        return "login";
    }

}
