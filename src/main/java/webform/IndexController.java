package webform;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String redirect() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/add")
    public String add(Model model, @RequestParam(name = "invalid", required = false) String invalid) {
        model.addAttribute("user", new User());
        model.addAttribute("invalid", invalid);
        return "add";
    }

    @GetMapping("/get")
    public String get(Model model, @ModelAttribute("_user") User _user) {
        model.addAttribute("user", new User());
        model.addAttribute("_user", _user);
        return "get";
    }

    @RequestMapping("/update")
    public String update(Model model, @RequestParam(name = "invalid", required = false) String invalid) {
        model.addAttribute("user", new User());
        model.addAttribute("invalid", invalid);
        return "update";
    }

    @RequestMapping("/remove")
    public String remove(Model model, @RequestParam(name = "invalid", required = false) Boolean invalid) {
        model.addAttribute("user", new User());
        model.addAttribute("invalid", invalid);
        return "remove";
    }

}
