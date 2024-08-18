package com.barber.BarberShop.Controller;

import com.barber.BarberShop.Model.HaircutApointment;
import com.barber.BarberShop.Service.HaircutApointmentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class HaircutApointmentRestController {

    @Autowired
    private HaircutApointmentService haircutApointmentService;

    @PostMapping("/api/makeApointment")
    public ResponseEntity<HaircutApointment> makeApointment(@RequestBody LocalDateTime dateTime, HttpSession session) {
        Pair<HaircutApointment, Integer> apointment = haircutApointmentService.makeApointment(dateTime, session);
        switch (apointment.getSecond()) {
            case 0:
                return new ResponseEntity("User is not logged in", HttpStatus.UNAUTHORIZED); //"User is not logged in"
            case 1:
                return new  ResponseEntity("Date and time are missing", HttpStatus.BAD_REQUEST); //"Date and time are missing"
            case 2:
                return ResponseEntity.ok(apointment.getFirst()); //"Apointment successfully made"
            default:
                return new ResponseEntity("Something went wrong, try again", HttpStatus.INTERNAL_SERVER_ERROR); //"Something went wrong, try again"
        }
    }
}
