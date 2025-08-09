package com.example.Article;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class DeleteReply {
    @Autowired
    ReplyRepository replyRepository;

    @PostMapping("/DelRep")
    public String DelRep(@RequestParam Long id) {
        Optional<Reply> comment = replyRepository.findById(id);
        if (comment.get().getisMode()) {
            comment.get().updateRV("삭제된 답글");
            comment.get().NotAcceptedReply();
            replyRepository.save(new Reply(comment.get().getId(), comment.get().getCommentId(), comment.get().getUsName(), comment.get().getReplyValue(), comment.get().getisMode()));
            return "redirect:/Inside?id={{id}}&Page=1";
        }
        else {
            return "redirect:/404";
        }
    }
}


