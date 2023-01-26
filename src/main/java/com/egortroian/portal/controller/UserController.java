package com.egortroian.portal.controller;

import com.egortroian.portal.domain.Role;
import com.egortroian.portal.domain.User;
import com.egortroian.portal.repos.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepo repo;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", repo.findAll());
        return "userList";
    }

    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam ("userId") User user
            ) {
        log.debug("New User name: " + username + " | " + "prev data: " + user);
        log.debug(form.keySet().toString());
        user.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for(String key : form.keySet()) {
            log.debug(key);
            if(roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        repo.save(user);
        return "redirect:/user";
    }
}
