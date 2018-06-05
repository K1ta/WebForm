package webform.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import webform.Repositories.TokenRepository;
import webform.Entities.UserData;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private TokenRepository tokenRepository;

    @RequestMapping("/")
    public String redirect() {
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index(Model model,
                        @CookieValue(value = "token", required = false) Cookie token,
                        @CookieValue(value = "email", required = false) Cookie email,
                        HttpServletRequest request) {
        if (userIsNotLogged(token)) {
            return "login";
        }
        if (email != null) {
            model.addAttribute("email", email.getValue());
        }
        return "index";
    }

    @RequestMapping("/add")
    public String add(Model model,
                      @RequestParam(name = "invalid", required = false) String invalid,
                      @CookieValue(value = "token", required = false) Cookie token,
                      HttpServletRequest request) {
        if (userIsNotLogged(token)) {
            return "login";
        }
        model.addAttribute("user", new UserData());
        model.addAttribute("invalid", invalid);
        return "add";
    }

    @GetMapping("/get")
    public String get(Model model,
                      @ModelAttribute("_user") UserData _userData,
                      @RequestParam(name = "invalid", required = false) String invalid,
                      @CookieValue(value = "token", required = false) Cookie token,
                      HttpServletRequest request) {
        if (userIsNotLogged(token)) {
            return "login";
        }
        model.addAttribute("user", new UserData());
        model.addAttribute("_user", _userData);
        model.addAttribute("invalid", invalid);
        return "get";
    }

    @RequestMapping("/update")
    public String update(Model model,
                         @RequestParam(name = "invalid", required = false) String invalid,
                         @CookieValue(value = "token", required = false) Cookie token,
                         HttpServletRequest request) {
        if (userIsNotLogged(token)) {
            return "login";
        }
        model.addAttribute("user", new UserData());
        model.addAttribute("invalid", invalid);
        return "update";
    }

    @RequestMapping("/remove")
    public String remove(Model model,
                         @RequestParam(name = "invalid", required = false) String invalid,
                         @CookieValue(value = "token", required = false) Cookie token,
                         HttpServletRequest request) {
        if (userIsNotLogged(token)) {
            return "login";
        }
        model.addAttribute("user", new UserData());
        model.addAttribute("invalid", invalid);
        return "remove";
    }

    private boolean userIsNotLogged(Cookie token) {
        if (token == null) {
            return true;
        }
        String dbToken = tokenRepository.findFirstToken(token.getValue());
        if (dbToken == null) {
            return true;
        }
        return !dbToken.equals(token.getValue());
    }

}
