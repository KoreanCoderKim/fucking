package com.example.Article;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Controller
public class DeleteReply {
    @Autowired
    ReplyRepository replyRepository;
    @Transactional
    @GetMapping("/DelRep")
    public String DelRep(@RequestParam Long id, HttpSession session) {
        Reply dummy = replyRepository.findByIdForUpdate(id);
        Optional<Reply> comment = replyRepository.findById(id);
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
            replyRepository.deleteById(id);
            return "redirect:/Rep?AcceptId="+id;
        }
        else {
            return "redirect:/404";
        }
    }
}













