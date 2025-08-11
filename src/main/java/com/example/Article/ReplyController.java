package com.example.Article;
import java.lang.Thread;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.transaction.annotation.Transactional;
@Controller
public class ReplyController {
    @Autowired
    ReplyRepository replyRepository;
    @GetMapping("/Reply")
    public String Replied(@RequestParam Long AcceptId, Model model) {
        model.addAttribute("Ci",AcceptId);
        return "Reply";
    }
    @Transactional
    @PostMapping("/Replied")
    public String Replying(@RequestParam Long commentId, ReplyDto form, HttpSession session) {
        Reply dummy = replyRepository.findByIdForUpdate(commentId);
        form.setUsName((String) session.getAttribute("user"));
        form.setCommentId(commentId);
        Reply reply = form.toEntity();
        int count = 0;
        int maxRetries = 5;
        while (count < maxRetries) {
            try {
                replyRepository.save(reply);
                break;
            } catch (DataIntegrityViolationException e) {
                count++;
                if (count == maxRetries) {
                    return "";
                }
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    return "";
                }
            }
        }
        return "redirect:index?RoomId="+(String)session.getAttribute("Room")+"&Page=1";
    }
}



