package com.example.Article;
import java.lang.Thread;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
@Controller
public class WriteController {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    RoomRepository roomRepository;
    @GetMapping("/new")
    public String Write(Pos pos, @RequestParam String RoomId, Model model) {
        if (roomRepository.existsByRoomId(RoomId)) {
            model.addAttribute("Id",RoomId);
            return "Wrote";
        }
        System.out.println(articleRepository.findAll());
        return "redirect:/In";
    }
    // 게시물 작성(PostMapping)
    @PostMapping("/RoomCommunity")
    public String OpenRoom(@RequestParam String RoomId, Model model, ArticleDto form, HttpSession session) {
        Object userObj = null;
        Object pwObj = null;
        try {
            userObj = session.getAttribute("user");
            pwObj = session.getAttribute("pw");
        } catch (IllegalStateException e) {
            return "redirect:/Login?SessionState="+"SessionOut";
        }

        if (userObj == null) {
            return "redirect:/Login?SessionState="+"SessionOut";
        }
        else if (pwObj == null) {
            return "redirect:/Login?SessionState="+"SessionOut";
        }
        String pww = (String) pwObj;
        Article article = form.toEntity(RoomId, (String) userObj, encoder.encode(pww));
        articleRepository.save(article);
        System.out.println(articleRepository.findAll());
        model.addAttribute("Id", RoomId);
        int PageValue = articleRepository.findByRoomId(RoomId).size()/5;
        int PageValue2 = articleRepository.findByRoomId(RoomId).size()/5+1;
        if (articleRepository.findByRoomId(RoomId).size() % 5 == 0)
            return "redirect:/index?RoomId="+RoomId+"&Page="+PageValue;
        return "redirect:/index?RoomId="+RoomId+"&Page="+PageValue2;
    }
}








