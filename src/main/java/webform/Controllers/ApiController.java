package webform.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public String add(UserData userData, HttpServletRequest request) {
        String validationResult = userData.validate();
        if (!validationResult.equals("ok")) {
            return "redirect:/add?invalid=" + validationResult;
        }
        Long id = getIdByToken(request.getCookies());
        if (id == null) return "redirect:/add?invalid=session";
        userData.setUserId(id);
        userDataRepository.save(userData);
        return "redirect:/add?invalid=ok";
    }

    @PostMapping("/get")
    public String get(UserData user, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        Long id = getIdByToken(request.getCookies());
        if (id == null) return "redirect:/get?invalid=session";
        UserData _userData = userDataRepository.findFirstByUseridAndName(id, user.getName());
        redirectAttributes.addFlashAttribute("_user", _userData);
        return "redirect:/get";
    }

    @PostMapping("/update")
    public String update(UserData user, HttpServletRequest request) {
        Long id = getIdByToken(request.getCookies());
        if (id == null) return "redirect:/get?invalid=session";
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
    public String remove(UserData userData, HttpServletRequest request) {
        Long id = getIdByToken(request.getCookies());
        if (id == null) return "redirect:/get?invalid=session";
        if (!userDataRepository.existsByName(userData.getName())) {
            return "redirect:/remove?invalid=yes";
        }
        userDataRepository.removeByName(userData.getName());
        return "redirect:/remove?invalid=no";
    }

    @RequestMapping("/login")
    public @ResponseBody
    SessionResponse login(@RequestBody SessionRequest data) {
        User user = userRepository.findFirstByEmail(data.getEmail());
        if (user == null) {
            return new SessionResponse("Email is not registered", "");
        }
        if (BCrypt.checkpw(data.getPassword(), user.getPassword())) {
            String token = UUID.randomUUID().toString();
            tokenRepository.save(new Token(user.getId(), token));
            return new SessionResponse("", token);
        }
        return new SessionResponse("Error password", "");
    }

    @RequestMapping("/signup")
    public @ResponseBody
    SessionResponse signup(@RequestBody SessionRequest data) {
        User user = userRepository.findFirstByEmail(data.getEmail());
        if (user != null) {
            return new SessionResponse("This email is already registered", "");
        }
        User newUser = new User();
        newUser.setEmail(data.getEmail());
        newUser.setPassword(BCrypt.hashpw(data.getPassword(), BCrypt.gensalt()));
        userRepository.save(newUser);
        Long id = userRepository.findFirstByEmail(newUser.getEmail()).getId();
        String token = UUID.randomUUID().toString();
        tokenRepository.save(new Token(id, token));
        return new SessionResponse("", token);
    }

    @RequestMapping("/quit")
    @ResponseStatus(value = HttpStatus.OK)
    public void quit(@RequestHeader(value = "token") String token) {
        if (token != null) {
            tokenRepository.removeAllByToken(token);
        }
    }

    @RequestMapping("/delete")
    @ResponseStatus(value = HttpStatus.OK)
    public void delete(@RequestHeader(value = "token") String token) {
        if (token != null) {
            Long id = tokenRepository.findFirstId(token);
            userDataRepository.removeAllByUserid(id);
            userRepository.removeAllById(id);
            tokenRepository.removeAllByToken(token);
        }
    }

    private Long getIdByToken(Cookie[] cookies) {
        if (cookies == null) {
            return null;
        }
        String token = null;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                token = cookie.getValue();
            }
        }
        if (token == null) return null;
        return tokenRepository.findFirstId(token);
    }
}
