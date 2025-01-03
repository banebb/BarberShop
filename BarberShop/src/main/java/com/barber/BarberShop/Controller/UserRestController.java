package com.barber.BarberShop.Controller;

import com.barber.BarberShop.Dtos.LoginDto;
import com.barber.BarberShop.Model.User;
import com.barber.BarberShop.Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        Pair<Integer, Boolean> registerStatus = userService.register(user.getUsername(), user.getPassword(), user.getEmail(), user.getName(), user.getSurname(), user.getPhoneNumber());

        switch (registerStatus.getFirst()) {
            case 0:
                return ResponseEntity.badRequest().body("User already exists");
            case 1:
                return ResponseEntity.badRequest().body("All fields must be filled");
            case 2:
                return ResponseEntity.ok("User registered");
            default:
                return ResponseEntity.internalServerError().body("Something went wrong, try again");
        }
    }

    @CrossOrigin
    @PostMapping("/api/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginData, HttpSession session) {
        Pair<Integer, Boolean> loginStatus = userService.login(loginData, session);

        switch (loginStatus.getFirst()) {
            case 0:
                return ResponseEntity.badRequest().body("Some data is missing");
            case 1:
                return ResponseEntity.badRequest().body("User with that username or mail or phone number doesn't exist");
            case 2:
                return ResponseEntity.badRequest().body("Wrong password");
            case 3:
                return ResponseEntity.ok("User successfully logged in");
            default:
                return ResponseEntity.internalServerError().body("Something went wrong, try again");
        }
    }

    @PostMapping("/api/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        if (userService.logout(session).getSecond()) {
            return ResponseEntity.ok("User successfully logged out");
        } else {
            return ResponseEntity.badRequest().body("User is not logged in");
        }
    }

    @CrossOrigin
    @GetMapping("/api/isLogged")
    public ResponseEntity<Boolean> isLogged(HttpSession session) {
        if(session.getAttribute("user") != null) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(false);
    }

    @GetMapping("/api/getName")
    public ResponseEntity<String> getName(HttpSession session) {
        if (userService.getName(session).getSecond()){
            return ResponseEntity.ok(userService.getName(session).getFirst());
        } else {
            return ResponseEntity.badRequest().body(userService.getName(session).getFirst());
        }
    }

    @GetMapping("/api/isBarber")
    public ResponseEntity<Boolean> isBarber(HttpSession session) {
        if (userService.isBarber(session).getSecond() && userService.isBarber(session).getFirst()) {
            return ResponseEntity.ok(true);
        }
        return new ResponseEntity<Boolean>(false, HttpStatus.NOT_FOUND);
    }

}
