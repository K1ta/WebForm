package webform;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/api")
public class ApiController {
    @Autowired
    private UserRepository repository;

    @PostMapping("/add")
    public String add(User user) {
        if (repository.existsById(user.getId())) {
            return "redirect:/add?invalid=id";
        }
        String validationResult = user.validate();
        if (!validationResult.equals("ok")) {
            return "redirect:/add?invalid=" + validationResult;
        }
        repository.save(user);
        return "redirect:/add?invalid=ok";
    }

    @PostMapping("/get")
    public String get(User user, RedirectAttributes redirectAttributes) {
        User _user = repository.findById(user.getId()).orElse(null);
        redirectAttributes.addFlashAttribute("_user", _user);
        return "redirect:/get";
    }

    @PostMapping("/update")
    public String update(User user) {
        if (!repository.existsById(user.getId())) {
            return "redirect:/update?invalid=id";
        }
        String validationResult = user.validate();
        if (!validationResult.equals("ok")) {
            return "redirect:/update?invalid=" + validationResult;
        }
        User _user = repository.findById(user.getId()).get();
        _user.update(user);
        repository.deleteById(user.getId());
        repository.save(_user);
        return "redirect:/update?invalid=ok";
    }

    @PostMapping("/remove")
    public String remove(User user) {
        if (!repository.existsById(user.getId())) {
            return "redirect:/remove?invalid=true";
        }
        repository.deleteById(user.getId());
        return "redirect:/remove?invalid=false";
    }
}
