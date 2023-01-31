package com.egortroian.portal.controller;

import com.egortroian.portal.domain.Bike;
import com.egortroian.portal.domain.Role;
import com.egortroian.portal.domain.User;
import com.egortroian.portal.repos.BikeRepo;
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
@RequestMapping("/bike")
public class BikeController {
    @Autowired
    private BikeRepo repo;

    @GetMapping
    public String bikeList(Model model) {
        model.addAttribute("bikes", repo.findAll());
        return "bikeList";
    }

    @GetMapping("{bike}")
    public String bikeEditForm(@PathVariable Bike bike, Model model) {
        model.addAttribute("bike", bike);
        return "bikeEdit";
    }

    @GetMapping("/del/{bike}")
    public String bikeDelete(@PathVariable Bike bike) {
        repo.deleteById(bike.getId());
        return "redirect:/bike";
    }
    @GetMapping("/add")
    public String bikeAddForm() {

        return "bikeAdd";
    }

    @PostMapping
    public String bikeSave(
            @RequestParam String bikeModel,
            @RequestParam String bikeBrand,
            @RequestParam int bikeEngine,
            @RequestParam String bikeImage
            ) {
        Bike bike = new Bike();
        bike.setBrand(bikeBrand);
        bike.setModel(bikeModel);
        bike.setEngine(bikeEngine);
        bike.setAvailable(true);
        bike.setImageURL(bikeImage);

        repo.save(bike);
        return "redirect:/bike";
    }
    @PostMapping("{bike}")
    public String bikeEdit(
            @RequestParam String bikeModel,
            @RequestParam String bikeBrand,
            @RequestParam int bikeEngine,
            @RequestParam boolean bikeAvailable,
            @RequestParam String bikeImage,
            @RequestParam ("bikeId") Bike bike
    ) {
        log.info("Before: " + bike);
        bike.setBrand(bikeBrand);
        bike.setModel(bikeModel);
        bike.setEngine(bikeEngine);
        bike.setAvailable(bikeAvailable);
        bike.setImageURL(bikeImage);
        log.info("After: " + bike);

        repo.save(bike);
        return "redirect:/bike";
    }
}
