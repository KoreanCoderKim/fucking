package com.example.Article;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionPingController {

    @PostMapping("/session-ping")
    public ResponseEntity<String>  ping(HttpSession session) {
        session.setAttribute("lastPing",System.currentTimeMillis());
        return ResponseEntity.ok("pong");
    }
}
