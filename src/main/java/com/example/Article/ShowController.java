package com.example.Article;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Optional;

@Controller
public class ShowController {
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
    @GetMapping("/Main")
    public String M() { return "mainindex"; }
    // 방 생성(GetMapping)
    @GetMapping("/Made")
    public String RoomMade() {
        return "sign";
    }
    // 방 생성(PostMapping)
    @PostMapping("/Make")
    public String RoomMake(RoomDto form) {
        if (!roomRepository.existsByRoomId(form.toEntity().getRoomId())) {
            roomRepository.save(form.toEntity());
            return "idx";
        }
        System.out.println(articleRepository.findAll());
        return "redirect:/Made";
    }
    // 방 입장(GetMapping)
    @GetMapping("/In")
    public String RoomIn() {
        return "main";
    }
    // 방 입장(PostMapping)
    @PostMapping("/Process")
    public String GO(PosDto form,HttpSession session) {
        int PageValue = articleRepository.findByRoomId(form.getRoomId()).size()/5;
        int PageValue2 = articleRepository.findByRoomId(form.getRoomId()).size()/5+1;
        if (roomRepository.existsByRoomId(form.getRoomId())) {
            session.setAttribute("Room",form.getRoomId());
            if (articleRepository.findByRoomId(form.getRoomId()).size() % 5 == 0)
                return "redirect:/index?RoomId="+form.getRoomId()+"&Page="+PageValue;
            return "redirect:/index?RoomId="+form.getRoomId()+"&Page="+PageValue2;
        }
        return "redirect:/In";
    }
    // 보드 페이지(GetMapping)
    @GetMapping("/index")
    public String App(Model model, @RequestParam String RoomId, @RequestParam int Page) {
        int PageValue = articleRepository.findByRoomId(RoomId).size() / 5 + 1;
        List<Integer> Pages = new java.util.ArrayList<>(List.of());
        if (articleRepository.findByRoomId(RoomId).size() % 5 == 0)
            PageValue -= 1;
        for (int i = 1; i <= PageValue; i++) {
            Pages.add(i);
        }
        List<Article> articles = List.of();
        if (articleRepository.findByRoomId(RoomId).size() <= 5) {
            if (roomRepository.existsByRoomId(RoomId)) {
                model.addAttribute("Data", articleRepository.findByRoomId(RoomId));
                model.addAttribute("Id", RoomId);
                model.addAttribute("Pager", Pages);
                return "index";
            }
            System.out.println(articles);
            return "redirect:/In";
        }
        if (articleRepository.findByRoomId(RoomId).size() - 1 < (Page * 5) - 1 && articleRepository.findByRoomId(RoomId).size() != 0)
            articles = articleRepository.findByRoomId(RoomId).subList((Page - 1) * 5, articleRepository.findByRoomId(RoomId).size());
        else
            articles = articleRepository.findByRoomId(RoomId).subList((Page - 1) * 5, Page * 5);
        if (roomRepository.existsByRoomId(RoomId)) {
            model.addAttribute("Data", articles);
            model.addAttribute("Id", RoomId);
            model.addAttribute("Pager", Pages);
            return "index";
        }
        System.out.println(articles);
        return "redirect:/In";
    }
    @GetMapping("/Inside")
    public String Inside(@RequestParam int Page, @RequestParam Long id, Model model) {
        Optional<Article> article = articleRepository.findById(id);
        List<Comment> comment = commentRepository.findByArticleId(id);
        List<Integer> Number = List.of();
        if (comment.size() <= 5) {
            Number.add(1);
        } else {
            for (int i = 1; i <= (comment.size() / 5); i++) {
                Number.add(i);
            }
        }
        List<Comment> comments;
        if (comment.size() - 1 < (Page * 5) - 1 && comment.size() != 0)
            comments = comment.subList((Page - 1) * 5, comment.size());
        else
            comments = comment.subList((Page - 1) * 5, Page * 5);
        model.addAttribute("ID", id);
        model.addAttribute("data", article.get().getNews());
        model.addAttribute("data2",comments);
        model.addAttribute("Pad", Number);
        return "Clip";
    }
    @GetMapping("/Rep")
    public String Reper(@RequestParam Long AcceptId, Model model) {
        model.addAttribute("Repping",replyRepository.findByCommentId(AcceptId));
        return "ReplyResult";
    }
}
