package com.egortroian.portal.controller;
import com.egortroian.portal.domain.Message;
import com.egortroian.portal.domain.User;
import com.egortroian.portal.repos.MessageRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@Slf4j
public class MainController {
    @Autowired
    private MessageRepo repo;

    @GetMapping("/")
    public String greeting() {

        return "greeting";
    }

    @GetMapping("/main")
    public String index(@RequestParam (required = false) String filter, Model model) {
        Iterable<Message> messages = repo.findAll();
        if(filter != null && !filter.isEmpty()) {
            messages = repo.findByTag(filter);
        } else {
            messages = repo.findAll();
        }
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag,
            Map<String, Object> model) {
        log.info("Add message: " + text + ". By User: " + user.getUsername());
        Message message = new Message(text, tag, user);
        repo.save(message);
        Iterable<Message> messages = repo.findAll();

        model.put("messages", messages);
        return "main";
    }
//
//    @PostMapping("filter")
//    public String filter(@RequestParam String filter, Map<String, Object> model) {
//        Iterable<Message> messages;
//        if(!filter.isEmpty()) {
//            messages = repo.findByTag(filter);
//        } else {
//            messages = repo.findAll();
//        }
//
//        model.put("messages", messages);
//        return "main";
//    }

}