package com.egortroian.portal.controller;

import com.egortroian.portal.domain.Bike;
import com.egortroian.portal.domain.Booking;
import com.egortroian.portal.domain.User;
import com.egortroian.portal.repos.BikeRepo;
import com.egortroian.portal.repos.BookingRepo;
import com.egortroian.portal.repos.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private BikeRepo bikeRepo;
    @Autowired
    private UserRepo userRepo;

    @GetMapping
    public String bookingList(Model model,
                              @RequestParam(required = false) String startDate,
                              @RequestParam(required = false) String endDate) {

//        LocalDateTime sdateTime = LocalDateTime.parse(startDate);
//        LocalDateTime edateTime = LocalDateTime.parse(endDate);
//        log.info(sdateTime.toString());
//        log.info(edateTime.toString());
        if (startDate == null || endDate == null) {
            return "bookingList";
        }
        List<Booking> bookings = bookingRepo.
                findByStartDateBetweenOrEndDateBetween(LocalDateTime.parse(startDate),
                        LocalDateTime.parse(endDate),
                        LocalDateTime.parse(startDate),
                        LocalDateTime.parse(endDate));
        log.info("Bookings" + bookings.toString());
        List<Long> ids = bookings.stream().map(b -> b.getBike().getId()).toList();
        log.info("Bike id" + ids);
        List<Bike> bikes = bikeRepo.findByIdNotIn(ids.isEmpty() ? List.of(-1L) : ids);
        log.info("Bikes" + bikes);

        model.addAttribute("start", startDate);
        model.addAttribute("end", endDate);
        model.addAttribute("bikes", bikes);
        return "bookingList";
    }


//    @GetMapping("{booking}")
//    public String bookingEditForm(@PathVariable Booking booking, Model model) {
//        model.addAttribute("booking", booking);
//        return "bookingEdit";
//    }

    @GetMapping("/del/{booking}")
    public String bookingDelete(@PathVariable Booking booking) {
        bookingRepo.deleteById(booking.getId());
        return "redirect:/booking";
    }

    @GetMapping("/{bike}")
    public String bookingAddForm(@PathVariable Bike bike,
                                 @RequestParam String startDate,
                                 @RequestParam String endDate,
                                 Model model) {
        log.info(bike.toString());
        model.addAttribute("start", startDate);
        model.addAttribute("end", endDate);
        model.addAttribute("bike", bike);
        return "bookingAdd";
    }

    @PostMapping
    public String bookingSave(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam String email,
            @RequestParam("bikeId") Bike bike
    ) {
        Booking booking = new Booking();
        booking.setStartDate(LocalDateTime.parse(startDate));
        booking.setEndDate(LocalDateTime.parse(endDate));
        booking.setUser(userRepo.findByEmail(email).stream().findFirst().get());
        booking.setBike(bike);

        bookingRepo.save(booking);
        return "redirect:/booking";
    }
//    @PostMapping("{booking}")
//    public String bookingEdit(
//            @RequestParam LocalDateTime startDate,
//            @RequestParam LocalDateTime endDate,
//            @RequestParam ("userId") User user,
//            @RequestParam ("bikeId") Bike bike,
//            @RequestParam ("bookingId") Booking booking
//    ) {
//
//        booking.setStartDate(startDate);
//        booking.setEndDate(endDate);
//        booking.setUser(user);
//        booking.setBike(bike);
//
//
//        bookingRepo.save(booking);
//        return "redirect:/booking";
//    }


}
