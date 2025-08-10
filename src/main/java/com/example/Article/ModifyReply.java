package com.example.Article;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import java.util.Optional;

@Controller
public class ModifyReply {
    @Autowired
    ReplyRepository replyRepository;
    @GetMapping("/Change3")
    public String Change3(Model model, @RequestParam Long ModifyId) {
        Optional<Reply> article = replyRepository.findById(ModifyId);
        model.addAttribute("MI",ModifyId);
        return "RCModify2";
    }
    // 게시글 수정(PostMapping)
    @PostMapping("/Modifying3")
    public String Modify3(ModifyDto form,@RequestParam Long ModifyId, HttpSession session) {
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
        Optional<Reply> now = replyRepository.findById(ModifyId); // 해당 아이디의 게시물 GET
        if (now.get().getUsName().equals(userObj)) {
            now.get().updateRV(form.getValue());
            replyRepository.save(now.get());
            return "redirect:/Rep?AcceptId="+ModifyId+"&Page=1";
        }
        return "redirect:/404";
    }
}



