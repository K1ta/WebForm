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
    public String index(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (!checkSession(cookies)) {
            return "login";
        }
        return "index";
    }

    @RequestMapping("/add")
    public String add(Model model, @RequestParam(name = "invalid", required = false) String invalid, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (!checkSession(cookies)) {
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
                      HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (!checkSession(cookies)) {
            return "login";
        }
        model.addAttribute("user", new UserData());
        model.addAttribute("_user", _userData);
        model.addAttribute("invalid", invalid);
        return "get";
    }

    @RequestMapping("/update")
    public String update(Model model, @RequestParam(name = "invalid", required = false) String invalid, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (!checkSession(cookies)) {
            return "login";
        }
        model.addAttribute("user", new UserData());
        model.addAttribute("invalid", invalid);
        return "update";
    }

    @RequestMapping("/remove")
    public String remove(Model model, @RequestParam(name = "invalid", required = false) String invalid, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (!checkSession(cookies)) {
            return "login";
        }
        model.addAttribute("user", new UserData());
        model.addAttribute("invalid", invalid);
        return "remove";
    }

    private boolean checkSession(Cookie[] cookies) {
        if (cookies == null) {
            return false;
        }
        String token = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                token = cookie.getValue();
            }
        }
        String dbToken = tokenRepository.findFirstToken(token);
        if (dbToken == null) {
            return false;
        }
        if (!dbToken.equals(token)) {
            return false;
        }
        return true;
    }

}
