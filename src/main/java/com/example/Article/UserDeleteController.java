package com.example.Article;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserDeleteController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Transactional
    @GetMapping("/DeleteUser")
    public String DeleteUser(HttpSession session) {
        Object usId = session.getAttribute("user");
        List<User> userdomain = userRepository.findByUsId((String) usId);
        User dummy = userRepository.findByIdForUpdate(userdomain.get(0).getId());
        List<Article> article = articleRepository.findByUsId((String) usId);
        List<Comment> comment = commentRepository.findByUsName((String) usId);
        List<Reply> reply = replyRepository.findByUsName((String) usId);
        System.out.println(userdomain);
        System.out.println(article);
        for (Article can : article) {
            can.NotAcceptedUser();
            can.setUserName("탈퇴한 회원");
        }
        for (Comment can : comment) {
            can.NotAcceptedReply();
            can.setUsName("탈퇴한 회원");
        }
        for (Reply can : reply) {
            can.NotAcceptedReply();
            can.setUsName("탈퇴한 회원");
        }
        userRepository.deleteById(userdomain.get(0).getId());
        return "redirect:/Main";
    }
}

