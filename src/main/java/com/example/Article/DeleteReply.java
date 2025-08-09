package com.example.Article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class DeleteReply {
    @Autowired
    ReplyRepository replyRepository;

    @GetMapping("/DelRep")
    public String DelRep(@RequestParam Long id) {
        Optional<Reply> comment = replyRepository.findById(id);
        if (comment.get().getisMode()) {
            commentRepository.deleteById(id);
            return "redirect:/Rep?AcceptId="+id;
        }
        else {
            return "redirect:/404";
        }
    }
}







