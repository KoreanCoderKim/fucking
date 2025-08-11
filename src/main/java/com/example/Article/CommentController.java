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
public class CommentController {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ArticleRepository articleRepository;
    @GetMapping("/Comment")
    public String commented(@RequestParam Long articleId, Model model) {
        model.addAttribute("ai",articleId);
        return "Comment";
    }
    @Transactional
    @PostMapping("/Commented")
    public String commneting(@RequestParam Long aid, CommentDto form, HttpSession session) {
        form.setUsName((String) session.getAttribute("user"));
        form.setArticleId(aid);
        Comment comment = form.toEntity();
        Comment dummy = commentRepository.findByIdForUpdate(aid);
        System.out.println(dummy);
        commentRepository.save(comment);
        Optional<Article> article = articleRepository.findById(aid);
        String RoomId = article.get().getRoomId();
        return "redirect:index?RoomId="+RoomId+"&Page=1";
    }
}
