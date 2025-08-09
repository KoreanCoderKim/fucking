package com.example.Article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class DeleteComment {
    @Autowired
    CommentRepository commentRepository;

    @PostMapping("/DelComm")
    public String DelComm(@RequestParam Long id) {
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.get().getisMode()) {
            comment.get().updateCV("삭제된 댓글");
            comment.get().NotAcceptedReply();
            commentRepository.save(new Comment(comment.get().getId(), comment.get().getArticleId(), comment.get().getUsName(), comment.get().getCommentValue(), comment.get().getisMode()));
            return "redirect:/In";
        }
        else {
            return "redirect:/404";
        }
    }
}

