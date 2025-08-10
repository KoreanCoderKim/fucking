package com.example.Article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class DeleteComment {
    @Autowired
    CommentRepository commentRepository;

    @GetMapping("/DelComm")
    public String DelComm(@RequestParam Long id, HttpSession session) {
        Optional<Comment> comment = commentRepository.findById(id);
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
        if (comment.get().getUsName().equals((String) userObj)) {
            commentRepository.deleteById(id);
            return "redirect:/Inside?id="+id+"&Page=1";
        }
        else {
            return "redirect:/404";
        }
    }
}








