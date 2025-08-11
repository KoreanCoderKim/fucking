package com.example.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.lang.Thread;
import org.springframework.dao.DataIntegrityViolationException;
@Controller
public class UserController {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Autowired
    UserRepository userRepository;
    @GetMapping("/SignUp")
    public String signMustache(Model model, @RequestParam String SessionState) {
        if (!SessionState.equals("SessionOut")) {
            model.addAttribute("State","세션 작동중");
        } else {
            model.addAttribute("State", SessionState);
        }
        return "SignUp";
    }
    @Transactional
    @PostMapping("/Board")
    public String ToBoard(UserDto form, Model model, HttpSession session) {
        String encodedPw;
        // 중복 아이디 확인
        if (!userRepository.existsByUsId(form.toEntity().getUsId())) {
            try {
                session.setAttribute("user",form.toEntity().getUsId());
                encodedPw = encoder.encode(form.toEntity().getPassword());
                session.setAttribute("pw",encodedPw);
            } catch (IllegalStateException e) {
                return "redirect:/SignUp?SessionState="+"SessionOut";
            }
            model.addAttribute("userId", form.toEntity().getUsId());
            User user = form.toEntity();
            user.setPassword(encodedPw);
            int count = 0;
            int maxRetries = 5;
            while (count < maxRetries) {
                try {
                    userRepository.save(user);
                    break;
                } catch (DataIntegrityViolationException e) {
                    count++;
                    if (count == maxRetries) {
                        return "";
                    }
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        return "";
                    }
                }
            }
            return "List";
        }
        return "redirect:/SignUp?SessionState=Good";
    }
    @GetMapping("/Login")
    public String LoginMustache(@RequestParam String SessionState, Model model) {
        if (!SessionState.equals("SessionOut")) {
            model.addAttribute("State","세션 작동중");
        } else {
            model.addAttribute("State", SessionState);
        }
        return "Login";
    }
    @Transactional
    @PostMapping("/Board2")
    public String ToList(UserDto form, Model model, HttpSession session) {
        String encodedPw;
        User dummy = userRepository.findByIdForUpdate(form.toEntity().getId());
        // 체크인
        if (userRepository.existsByUsId(form.toEntity().getUsId())) {
            List<User> finder = userRepository.findByUsId(form.toEntity().getUsId());
            if (encoder.matches(form.toEntity().getPassword(),finder.get(0).getPassword())) {
                try {
                    session.setAttribute("user",form.toEntity().getUsId());
                    encodedPw = encoder.encode(form.toEntity().getPassword());
                    session.setAttribute("pw",encodedPw);
                } catch (IllegalStateException e) {
                    return "redirect:/Login?SessionState="+"SessionOut";
                }
                model.addAttribute("userId", form.toEntity().getUsId());
                User user = form.toEntity();
                user.setPassword(encodedPw);
                userRepository.save(user);
                return "List";
            }
        }
        return "redirect:/Login?SessionState=Good";
    }
}






