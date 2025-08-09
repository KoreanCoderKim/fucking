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
    public String DelComm(@RequestParam Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.get().getisMode()) {
            commentRepository.deleteById(id);
            return "redirect:/Inside?id="+id+"&Page=1";
        }
        else {
            return "redirect:/404";
        }
    }
}





