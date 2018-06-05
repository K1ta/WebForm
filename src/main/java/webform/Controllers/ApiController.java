package webform.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webform.BCrypt;
import webform.Entities.*;
import webform.Repositories.TokenRepository;
import webform.Repositories.UserDataRepository;
import webform.Repositories.UserRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private UserDataRepository userDataRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenRepository tokenRepository;

    @PostMapping("/add")
    public String add(UserData userData,
                      HttpServletRequest request,
                      @CookieValue(name = "token", required = false) Cookie token) {
        if (token == null) {
            return "redirect:/add?invalid=session";
        }
        String validationResult = userData.validate();
        if (!validationResult.equals("ok")) {
            return "redirect:/add?invalid=" + validationResult;
        }
        Long id;
        try {
            id = tokenRepository.findFirstId(token.getValue());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "redirect:/add?invalid=token";
        }
        userData.setUserId(id);
        userDataRepository.save(userData);
        return "redirect:/add?invalid=ok";
    }

    @PostMapping("/get")
    public String get(UserData user,
                      RedirectAttributes redirectAttributes,
                      HttpServletRequest request,
                      @CookieValue(value = "token", required = false) Cookie token) {
        if (token == null) {
            return "redirect:/add?invalid=session";
        }
        Long id;
        try {
            id = tokenRepository.findFirstId(token.getValue());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "redirect:/add?invalid=token";
        }
        UserData _userData = userDataRepository.findFirstByUseridAndName(id, user.getName());
        redirectAttributes.addFlashAttribute("_user", _userData);
        return "redirect:/get";
    }

    @PostMapping("/update")
    public String update(UserData user,
                         HttpServletRequest request,
                         @CookieValue(value = "token", required = false) Cookie token) {
        if (token == null) {
            return "redirect:/add?invalid=session";
        }
        Long id;
        try {
            id = tokenRepository.findFirstId(token.getValue());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "redirect:/add?invalid=token";
        }
        String validationResult = user.validate();
        if (!validationResult.equals("ok")) {
            return "redirect:/update?invalid=" + validationResult;
        }
        UserData _userData = userDataRepository.findFirstByUseridAndName(id, user.getName());
        if (_userData == null) {
            return "redirect:/update?invalid=user";
        }
        _userData.update(user);
        userDataRepository.save(_userData);
        return "redirect:/update?invalid=ok";
    }

    @PostMapping("/remove")
    public String remove(UserData userData) {
        if (!userDataRepository.existsByName(userData.getName())) {
            return "redirect:/remove?invalid=yes";
        }
        userDataRepository.removeByName(userData.getName());
        return "redirect:/remove?invalid=no";
    }

    @RequestMapping("/login")
    public void login(@RequestBody SessionRequest data,
                      HttpServletResponse response) {
        if (data.getEmail() == null || data.getPassword() == null) {
            response.setHeader("Error", "Error data");
            return;
        }
        User user = userRepository.findFirstByEmail(data.getEmail());
        if (user == null) {
            response.setHeader("Error", "Email is not registered");
            return;
        }
        if (BCrypt.checkpw(data.getPassword(), user.getPassword())) {
            String token = UUID.randomUUID().toString();
            tokenRepository.save(new Token(user.getId(), token));
            //save token in cookie
            Cookie tokenCookie = new Cookie("token", token);
            tokenCookie.setMaxAge(2500000);
            tokenCookie.setPath("/");
            tokenCookie.setHttpOnly(true);
            response.addCookie(tokenCookie);
            //save email in cookie
            Cookie emailCookie = new Cookie("email", user.getEmail());
            emailCookie.setMaxAge(2500000);
            emailCookie.setPath("/");
            emailCookie.setHttpOnly(true);
            response.addCookie(emailCookie);
        } else {
            response.setHeader("Error", "Wrong password");
        }
    }

    @RequestMapping("/signup")
    //public @ResponseBody
    //SessionResponse signup(@RequestBody SessionRequest data
    public void signup(@RequestBody SessionRequest data,
                       HttpServletResponse response) {
        if (data.getEmail() == null || data.getPassword() == null) {
            response.setHeader("Error", "Error data");
            return;
        }
        User user = userRepository.findFirstByEmail(data.getEmail());
        if (user != null) {
            response.setHeader("Error", "This email is already registered");
            return;
        }
        User newUser = new User();
        newUser.setEmail(data.getEmail());
        newUser.setPassword(BCrypt.hashpw(data.getPassword(), BCrypt.gensalt()));
        userRepository.save(newUser);
        Long id = userRepository.findFirstByEmail(newUser.getEmail()).getId();
        String token = UUID.randomUUID().toString();
        tokenRepository.save(new Token(id, token));
        //save token in cookie
        Cookie tokenCookie = new Cookie("token", token);
        tokenCookie.setMaxAge(2500000);
        tokenCookie.setPath("/");
        tokenCookie.setHttpOnly(true);
        response.addCookie(tokenCookie);
        //save email in cookie
        Cookie emailCookie = new Cookie("email", data.getEmail());
        emailCookie.setMaxAge(2500000);
        emailCookie.setPath("/");
        emailCookie.setHttpOnly(true);
        response.addCookie(emailCookie);
    }

    @RequestMapping("/quit")
    public void quit(HttpServletRequest request,
                     HttpServletResponse response,
                     @CookieValue(value = "token") Cookie cookie) {
        tokenRepository.removeAllByToken(cookie.getValue());
        //delete token cookie
        Cookie tokenCookie = new Cookie("token", "delete");
        tokenCookie.setMaxAge(0);
        tokenCookie.setPath("/");
        tokenCookie.setHttpOnly(true);
        response.addCookie(tokenCookie);
        //delete email cookie
        Cookie emailCookie = new Cookie("email", "delete");
        emailCookie.setMaxAge(0);
        emailCookie.setPath("/");
        emailCookie.setHttpOnly(true);
        response.addCookie(emailCookie);
    }

    @RequestMapping("/delete")
    public void delete(HttpServletRequest request,
                       HttpServletResponse response,
                       @CookieValue(value = "token") Cookie cookie) {
        String token = cookie.getValue();
        Long id = tokenRepository.findFirstId(token);
        userDataRepository.removeAllByUserid(id);
        userRepository.removeAllById(id);
        tokenRepository.removeAllByToken(token);
        //delete token cookie
        Cookie tokenCookie = new Cookie("token", "delete");
        tokenCookie.setMaxAge(0);
        tokenCookie.setPath("/");
        tokenCookie.setHttpOnly(true);
        response.addCookie(tokenCookie);
        //delete email cookie
        Cookie emailCookie = new Cookie("email", "delete");
        emailCookie.setMaxAge(0);
        emailCookie.setPath("/");
        emailCookie.setHttpOnly(true);
        response.addCookie(emailCookie);
    }

}
