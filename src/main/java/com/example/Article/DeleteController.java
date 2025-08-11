package com.example.Article;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class DeleteController {
    @Autowired
    ArticleRepository articleRepository;
    // 게시글 삭제(PostMapping)
    @Transactional
    @PostMapping("/delete")
    public String deleted(@RequestParam Long deleteId, @RequestParam String RoomId, HttpSession session) {
        Article dummy = articleRepository.findByIdForUpdate(deleteId);
        Optional<Article> article = articleRepository.findById(deleteId);
        if (!article.get().getIsMode()) {
            return "redirect:/In";
        }
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
        Optional<Article> now = articleRepository.findById(deleteId);
        if (now.get().getUsId().equals(userObj)) {
            articleRepository.deleteById(deleteId);
            System.out.println(articleRepository.findAll());
            int PageValue = articleRepository.findByRoomId(RoomId).size()/5;
            int PageValue2 = articleRepository.findByRoomId(RoomId).size()/5+1;
            if (articleRepository.findByRoomId(RoomId).size() % 5 == 0)
                return "redirect:/index?RoomId="+RoomId+"&Page="+PageValue;
            return "redirect:/index?RoomId="+RoomId+"&Page="+PageValue2;
        }
        int PageValue = articleRepository.findByRoomId(RoomId).size()/5;
        int PageValue2 = articleRepository.findByRoomId(RoomId).size()/5+1;
        if (articleRepository.findByRoomId(RoomId).size() % 5 == 0)
            return "redirect:/index?RoomId="+RoomId+"&Page="+PageValue;
        return "redirect:/index?RoomId="+RoomId+"&Page="+PageValue2;
    }
}
