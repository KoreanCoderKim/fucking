package com.example.Article;

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
public class ArticleController {
    // 게시물 관리소
    @Autowired
    private ArticleRepository articleRepository;
    // 방번호 저장소
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/Main")
    public String M() {
        return "mainindex";
    }
    @GetMapping("/SignUp")
    public String signMustache() {
        return "SignUp";
    }
    @PostMapping("/Board")
    public String ToBoard(UserDto form, Model model, HttpSession session) {
        // 중복 아이디 확인
        if (!userRepository.existsByUsId(form.toEntity().getUsId())) {
            session.setAttribute("user",form.toEntity().getUsId());
            session.setAttribute("pw",form.toEntity().getPassword());
            model.addAttribute("userId", form.toEntity().getUsId());
            userRepository.save(form.toEntity());
            return "List";
        }
        return "redirect:/SignUp";
    }
    @GetMapping("/Login")
    public String LoginMustache() {
        return "Login";
    }
    @PostMapping("/Board2")
    public String ToList(UserDto form, Model model, HttpSession session) {
        // 체크인
        if (userRepository.existsByUsId(form.toEntity().getUsId())) {
            List<User> finder = userRepository.findByUsId(form.toEntity().getUsId());
            if (finder.get(0).getPassword().equals(form.toEntity().getPassword())) {
                session.setAttribute("user",form.toEntity().getUsId());
                session.setAttribute("pw",form.toEntity().getPassword());
                model.addAttribute("userId", form.toEntity().getUsId());
                userRepository.save(form.toEntity());
                return "List";
            }
        }
        return "redirect:/Login";
    }
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
    public String GO(PosDto form) {
        if (roomRepository.existsByRoomId(form.getRoomId()))
            return "redirect:/index?RoomId="+form.getRoomId();
        System.out.println(articleRepository.findAll());
        return "redirect:/In";
    }
    // 보드 페이지(GetMapping)
    @GetMapping("/index")
    public String App(Model model, @RequestParam String RoomId) {
        if (roomRepository.existsByRoomId(RoomId)) {
            model.addAttribute("Data", articleRepository.findByRoomId(RoomId));
            model.addAttribute("Id", RoomId);
            return "index";
        }
        System.out.println(articleRepository.findAll());
        return "redirect:/In";
    }
    // 게시글 삭제(PostMapping)
    @PostMapping("/delete")
    public String deleted(@RequestParam Long deleteId, @RequestParam String RoomId, HttpSession session) {
        Optional<Article> now = articleRepository.findById(deleteId);
        if (now.get().getUsId().equals(session.getAttribute("user")) && now.get().getPassword().equals(session.getAttribute("pw"))) {
            articleRepository.deleteById(deleteId);
            System.out.println(articleRepository.findAll());
            return "redirect:/index?RoomId=" + RoomId;
        }
        return "redirect:/index?RoomId="+RoomId;
    }
    // 게시물 작성(GetMapping)
    @GetMapping("/new")
    public String Write(Pos pos, @RequestParam String RoomId, Model model) {
        if (roomRepository.existsByRoomId(RoomId)) {
            model.addAttribute("Id",RoomId);
            return "Wrote";
        }
        System.out.println(articleRepository.findAll());
        return "redirect:/In";
    }
    // 게시물 작성(PostMapping)
    @PostMapping("/RoomCommunity")
    public String OpenRoom(@RequestParam String RoomId, Model model, ArticleDto form, HttpSession session) {
        Article article = form.toEntity(RoomId, (String) session.getAttribute("user"), (String) session.getAttribute("pw"));
        articleRepository.save(article);
        System.out.println(articleRepository.findAll());
        model.addAttribute("Id", RoomId);
        return "redirect:/index?RoomId="+RoomId;
    }
    // 게시글 수정(GetMapping)
    @GetMapping("/Change")
    public String Change(Model model, @RequestParam Long ModifyId, HttpSession session) {
        Optional<Article> now = articleRepository.findById(ModifyId); // 해당 아이디의 게시물 GET
        if (now.get().getUsId().equals(session.getAttribute("user")) && now.get().getPassword().equals(session.getAttribute("pw"))) {
            model.addAttribute("MI",ModifyId);
            return "Modifyed";
        }
        return "redirect:/Board";
    }
    // 게시글 수정(PostMapping)
    @PostMapping("/Modifying")
    public String Modify(ChanDto form,@RequestParam Long ModifyId, HttpSession session) {
        Optional<Article> now = articleRepository.findById(ModifyId); // 해당 아이디의 게시물 GET
        now.get().update(form.getRoomId(),form.getTitle(),form.getNews());
        articleRepository.save(now.get());
        System.out.println(articleRepository.findAll());
        return "redirect:/index?RoomId="+form.getRoomId();
    }
}