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
public class ModifyComment {
    @Autowired
    CommentRepository commentRepository;
    @GetMapping("/Change2")
    public String Change2(Model model, @RequestParam Long ModifyId) {
        Optional<Comment> article = commentRepository.findById(ModifyId);
        model.addAttribute("MI",ModifyId);
        return "RCModify";
    }
    // 게시글 수정(PostMapping)
    @Transactional
    @PostMapping("/Modifying2")
    public String Modify2(ModifyDto form,@RequestParam Long ModifyId, HttpSession session) {
        Comment dummy = commentRepository.findByIdForUpdate(ModifyId);
        System.out.println(dummy);
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
        Optional<Comment> now = commentRepository.findById(ModifyId); // 해당 아이디의 게시물 GET
        if (now.get().getUsName().equals(userObj)) {
            now.get().updateCV(form.getValue());
            commentRepository.save(now.get());
            return "redirect:/Inside?id="+ModifyId+"&Page=1";
        }
        return "redirect:/404";
    }

}


