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
    public String M() { return "mainindex"; }
    @GetMapping("/SignUp")
    public String signMustache(Model model, @RequestParam String SessionState) {
        if (!SessionState.equals("SessionOut")) {
            model.addAttribute("State","세션 작동중");
        } else {
            model.addAttribute("State", SessionState);
        }
        return "SignUp";
    }
    @PostMapping("/Board")
    public String ToBoard(UserDto form, Model model, HttpSession session) {
        // 중복 아이디 확인
        if (!userRepository.existsByUsId(form.toEntity().getUsId())) {
            try {
                session.setAttribute("user",form.toEntity().getUsId());
                session.setAttribute("pw",form.toEntity().getPassword().hashCode());
            } catch (IllegalStateException e) {
                return "redirect:/SignUp?SessionState="+"SessionOut";
            }
            model.addAttribute("userId", form.toEntity().getUsId());
            userRepository.save(form.toEntity());
            return "List";
        }
        return "redirect:/SignUp?SessionState=Good";
    }
    @GetMapping("/Login")
    public String LoginMustache(@RequestParam String SessionState, Model model) {
        if (!SessionState.equals("SessionOut")) {
            model.addAttribute("State","세션 작동중");
        } else {
            model.addAttribute("State", SessionState);
        }
        return "Login";
    }
    @PostMapping("/Board2")
    public String ToList(UserDto form, Model model, HttpSession session) {
        // 체크인
        if (userRepository.existsByUsId(form.toEntity().getUsId())) {
            List<User> finder = userRepository.findByUsId(form.toEntity().getUsId());
            if (finder.get(0).getPassword().hashCode() == form.toEntity().getPassword().hashCode()) {
                try {
                    session.setAttribute("user",form.toEntity().getUsId());
                    session.setAttribute("pw",form.toEntity().getPassword().hashCode());
                } catch (IllegalStateException e) {
                    return "redirect:/Login?SessionState="+"SessionOut";
                }
                model.addAttribute("userId", form.toEntity().getUsId());
                userRepository.save(form.toEntity());
                return "List";
            }
        }
        return "redirect:/Login?SessionState=Good";
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
        int PageValue = articleRepository.findByRoomId(form.getRoomId()).size()/5;
        int PageValue2 = articleRepository.findByRoomId(form.getRoomId()).size()/5+1;
        if (roomRepository.existsByRoomId(form.getRoomId())) {
            if (articleRepository.findByRoomId(form.getRoomId()).size() % 5 == 0)
                return "redirect:/index?RoomId="+form.getRoomId()+"&Page="+PageValue;
            return "redirect:/index?RoomId="+form.getRoomId()+"&Page="+PageValue2;
        }
        return "redirect:/In";
    }
    // 보드 페이지(GetMapping)
    @GetMapping("/index")
    public String App(Model model, @RequestParam String RoomId, @RequestParam int Page) {
        int PageValue = articleRepository.findByRoomId(RoomId).size()/5+1;
        List<Integer> Pages = new java.util.ArrayList<>(List.of());
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
        if (articleRepository.findByRoomId(RoomId).size()-1 < (Page*5)-1 && articleRepository.findByRoomId(RoomId).size() != 0)
            articles = articleRepository.findByRoomId(RoomId).subList((Page-1)*5, articleRepository.findByRoomId(RoomId).size());
        else
            articles = articleRepository.findByRoomId(RoomId).subList((Page-1)*5, Page*5);
        if (roomRepository.existsByRoomId(RoomId)) {
            model.addAttribute("Data", articles);
            model.addAttribute("Id", RoomId);
            model.addAttribute("Pager", Pages);
            return "index";
        }
        System.out.println(articles);
        return "redirect:/In";
    }
    // 게시글 삭제(PostMapping)
    @PostMapping("/delete")
    public String deleted(@RequestParam Long deleteId, @RequestParam String RoomId, HttpSession session) {
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
        Optional<Article> now = articleRepository.findById(deleteId);
        if (now.get().getUsId().equals(userObj) &&  now.get().getPassword() == (int) pwObj) {
            articleRepository.deleteById(deleteId);
            System.out.println(articleRepository.findAll());
            int PageValue = articleRepository.findByRoomId(RoomId).size()/5;
            int PageValue2 = articleRepository.findByRoomId(RoomId).size()/5+1;
            if (articleRepository.findByRoomId(RoomId).size() % 5 == 0)
                return "redirect:/index?RoomId="+RoomId+"&Page="+PageValue;
            return "redirect:/index?RoomId="+RoomId+"&Page="+PageValue2;
        }
        int PageValue = articleRepository.findByRoomId(RoomId).size()/5;
        int PageValue2 = articleRepository.findByRoomId(RoomId).size()/5+1;
        if (articleRepository.findByRoomId(RoomId).size() % 5 == 0)
            return "redirect:/index?RoomId="+RoomId+"&Page="+PageValue;
        return "redirect:/index?RoomId="+RoomId+"&Page="+PageValue2;
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
        Article article = form.toEntity(RoomId, (String) userObj, (int) pwObj);
        articleRepository.save(article);
        System.out.println(articleRepository.findAll());
        model.addAttribute("Id", RoomId);
        int PageValue = articleRepository.findByRoomId(RoomId).size()/5;
        int PageValue2 = articleRepository.findByRoomId(RoomId).size()/5+1;
        if (articleRepository.findByRoomId(RoomId).size() % 5 == 0)
            return "redirect:/index?RoomId="+RoomId+"&Page="+PageValue;
        return "redirect:/index?RoomId="+RoomId+"&Page="+PageValue2;
    }
    // 게시글 수정(GetMapping)
    @GetMapping("/Change")
    public String Change(Model model, @RequestParam Long ModifyId) {
        model.addAttribute("MI",ModifyId);
        return "Modifyed";
    }
    // 게시글 수정(PostMapping)
    @PostMapping("/Modifying")
    public String Modify(ChanDto form,@RequestParam Long ModifyId, HttpSession session) {
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
        Optional<Article> now = articleRepository.findById(ModifyId); // 해당 아이디의 게시물 GET
        if (now.get().getUsId().equals(userObj) && now.get().getPassword() == (int) pwObj) {
            now.get().update(form.getRoomId(), form.getTitle(), form.getNews());
            articleRepository.save(now.get());
            System.out.println(articleRepository.findAll());
            int PageValue = articleRepository.findByRoomId(form.getRoomId()).size()/5;
            int PageValue2 = articleRepository.findByRoomId(form.getRoomId()).size()/5+1;
            if (articleRepository.findByRoomId(form.getRoomId()).size() % 5 == 0)
                return "redirect:/index?RoomId="+RoomId+"&Page="+PageValue;
            return "redirect:/index?RoomId="+RoomId+"&Page="+PageValue2;   
        }
        int PageValue = articleRepository.findByRoomId(form.getRoomId()).size()/5;
        int PageValue2 = articleRepository.findByRoomId(form.getRoomId()).size()/5+1;
        if (articleRepository.findByRoomId(form.getRoomId()).size() % 5 == 0)
            return "redirect:/index?RoomId="+form.getRoomId()+"&Page="+PageValue;
        return "redirect:/index?RoomId="+form.getRoomId()+"&Page="+PageValue2;
    }
}
