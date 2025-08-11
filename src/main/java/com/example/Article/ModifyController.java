package com.example.Article;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Controller
public class ModifyController {
    @Autowired
    ArticleRepository articleRepository;
    @GetMapping("/Change")
    public String Change(Model model, @RequestParam Long ModifyId) {
        Optional<Article> article = articleRepository.findById(ModifyId);
        if (article.get().getIsMode()) {
            model.addAttribute("MI",ModifyId);
            return "Modifyed";
        } else {
            return "redirect:/In";
        }
    }
    // 게시글 수정(PostMapping)
    @Transactional
    @PostMapping("/Modifying")
    public String Modify(ChanDto form,@RequestParam Long ModifyId, HttpSession session) {
        Article dummy = articleRepository.findByIdForUpdate(ModifyId);
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
        Optional<Article> now = articleRepository.findById(ModifyId); // 해당 아이디의 게시물 GET
        if (now.get().getUsId().equals(userObj)) {
            now.get().update(form.getRoomId(), form.getTitle(), form.getNews());
            articleRepository.save(now.get());
            System.out.println(articleRepository.findAll());
            int PageValue = articleRepository.findByRoomId(form.getRoomId()).size()/5;
            int PageValue2 = articleRepository.findByRoomId(form.getRoomId()).size()/5+1;
            if (articleRepository.findByRoomId(form.getRoomId()).size() % 5 == 0)
                return "redirect:/index?RoomId="+form.getRoomId()+"&Page="+PageValue;
            return "redirect:/index?RoomId="+form.getRoomId()+"&Page="+PageValue2;
        }
        int PageValue = articleRepository.findByRoomId(form.getRoomId()).size()/5;
        int PageValue2 = articleRepository.findByRoomId(form.getRoomId()).size()/5+1;
        if (articleRepository.findByRoomId(form.getRoomId()).size() % 5 == 0)
            return "redirect:/index?RoomId="+form.getRoomId()+"&Page="+PageValue;
        return "redirect:/index?RoomId="+form.getRoomId()+"&Page="+PageValue2;
    }
}


