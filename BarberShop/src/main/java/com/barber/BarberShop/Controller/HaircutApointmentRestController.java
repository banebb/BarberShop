package com.barber.BarberShop.Controller;

import com.barber.BarberShop.Model.HaircutApointment;
import com.barber.BarberShop.Service.HaircutApointmentService;
import jakarta.servlet.http.HttpSession;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class HaircutApointmentRestController {

    @Autowired
    private HaircutApointmentService haircutApointmentService;

    @PostMapping("/api/makeApointment")
    public ResponseEntity<HaircutApointment> makeApointment(@RequestBody LocalDateTime dateTime, HttpSession session) {
        Pair<HaircutApointment, Integer> apointment = haircutApointmentService.makeApointment(dateTime, session);
        switch (apointment.getSecond()) {
            case 0:
                return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);
            case 1:
                return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
            case 2:
                return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
            case 3:
                return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
            case 4:
                return new ResponseEntity(apointment.getFirst(), HttpStatus.OK);
            case 6:
                return new ResponseEntity(null, HttpStatus.FORBIDDEN);
            default:
                return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/api/cancelApointment")
    public ResponseEntity<String> cancelApointment(@RequestBody LocalDateTime dateTime, HttpSession session) {
        Pair<HaircutApointment, Integer> apointment = haircutApointmentService.cancelApointment(dateTime, session);
        switch (apointment.getSecond()) {
            case 0:
                return new ResponseEntity("You need to be singed in to cancel an appointment", HttpStatus.UNAUTHORIZED);
            case 1:
                return new ResponseEntity("You must provide a date and time", HttpStatus.BAD_REQUEST);
            case 2:
                return new ResponseEntity("You can't cancel an appointment in the past", HttpStatus.BAD_REQUEST);
            case 3:
                return new ResponseEntity("There is no appointment at that time", HttpStatus.BAD_REQUEST);
            case 4:
                return new ResponseEntity("You can not cancel an appointment of an other user", HttpStatus.UNAUTHORIZED);
            case 5:
                return new ResponseEntity("Appointment canceled", HttpStatus.OK);
            case 6:
                return new ResponseEntity("You are blocked", HttpStatus.FORBIDDEN);
            default:
                return new ResponseEntity("An error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/api/apointmentDone")
    public ResponseEntity<String> apointmentDone(@RequestBody HaircutApointment haircutApointment, HttpSession session) {
        Pair<HaircutApointment, Integer> apointment = haircutApointmentService.apointmentDone(haircutApointment, session);
        switch (apointment.getSecond()) {
            case 0:
                return new ResponseEntity<String>("You need to be singed in to mark an apointment as done", HttpStatus.UNAUTHORIZED);
            case 1:
                return new ResponseEntity<String>("You must be a barber to mark an apointment as done", HttpStatus.BAD_REQUEST);
            case 2:
                return new ResponseEntity<String>("You must provide an apointment", HttpStatus.BAD_REQUEST);
            case 3:
                return new ResponseEntity<String>("You can't mark an apointment as done if it's not finished, or it was canceled", HttpStatus.BAD_REQUEST);
            case 4:
                return new ResponseEntity<String>("You marked appointment as done", HttpStatus.OK);
            default:
                return new ResponseEntity<String>("An error occurred, try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/api/getAppointment/{dateTime}")
    public ResponseEntity<HaircutApointment> getAppointment(@PathVariable LocalDateTime dateTime) {
        HaircutApointment apointment = haircutApointmentService.getHaircutApointment(dateTime);
        if(apointment == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok(apointment);
    }

    @GetMapping("/api/getAllAppointments")
    public ResponseEntity<List<HaircutApointment>> getAllAppointments() {
        List<HaircutApointment> appointments = haircutApointmentService.getHaircutApointments();
        if(appointments == null) return ResponseEntity.notFound().build();
        if (appointments.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/api/getAppointmentsByUser")
    public ResponseEntity<List<HaircutApointment>> getAppointmentsByUser(HttpSession session) {
        List<HaircutApointment> appointments = haircutApointmentService.getHaircutApointmentsByUser(session);
        if(appointments == null) return ResponseEntity.notFound().build();
        if (appointments.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/api/getAppointmentsOnDate/{date}")
    public ResponseEntity<List<HaircutApointment>> getAppointmentsOnDate(@PathVariable LocalDate date) {
        List<HaircutApointment> appointments = haircutApointmentService.getHaircutAppointmentsOnDay(date);
        if(appointments == null) return ResponseEntity.notFound().build();
        if (appointments.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/api/getAppointmentsOnMonth/{month}")
    public ResponseEntity<List<HaircutApointment>> getAppointmentsOnMonth(@PathVariable int month) {
        if(month <= 0 || month > 12) return ResponseEntity.badRequest().build();
        List<HaircutApointment> appointments = haircutApointmentService.getHaircutAppointmentsOnMonth(month);
        if(appointments == null) return ResponseEntity.notFound().build();
        if (appointments.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(appointments);
    }
}
