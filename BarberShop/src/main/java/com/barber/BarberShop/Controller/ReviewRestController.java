package com.barber.BarberShop.Controller;

import com.barber.BarberShop.Dtos.ReviewDto;
import com.barber.BarberShop.Model.Review;
import com.barber.BarberShop.Service.ReviewService;
import jakarta.persistence.Access;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ReviewRestController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/api/leaveReview")
    public ResponseEntity<String> leaveReview(@RequestBody ReviewDto data, HttpSession session) {
        if(session.getAttribute("user") == null) return ResponseEntity.status(401).body("User not logged in");
        Pair<Boolean, Integer> result = reviewService.leaveReview(session, data);
        switch (result.getSecond()) {
            case 0:
                return ResponseEntity.status(401).body("User not logged in");
            case 1:
                return ResponseEntity.status(404).body("Haircut apointment not found");
            case 2:
                return ResponseEntity.ok("Review left successfully");
            case 3:
                return ResponseEntity.status(400).body("Invalid rating");
            case 4:
                return ResponseEntity.status(400).body("Invalid data");
            default:
                return ResponseEntity.status(500).body("Error oqurred, try again");
        }
    }

    @DeleteMapping("/api/deleteReview")
    public ResponseEntity<String> deleteReview(@RequestBody ReviewDto data, HttpSession session) {
        if(session.getAttribute("user") == null) return ResponseEntity.status(401).body("User not logged in");
        Pair<Boolean, Integer> result = reviewService.deleteReview(session, data);
        switch (result.getSecond()) {
            case 0:
                return ResponseEntity.status(401).body("User not logged in");
            case 1:
                return ResponseEntity.status(404).body("Review not found");
            case 2:
                return ResponseEntity.ok("Review deleted successfully");
            case 4:
                return ResponseEntity.status(400).body("Invalid data");
            default:
                return ResponseEntity.status(500).body("Error oqurred, try again");
        }
    }

    @GetMapping("/api/getReview/{id}")
    public ResponseEntity<Review> getReview(@PathVariable UUID id) {
        Review review = reviewService.getReview(id);
        if (review == null) return ResponseEntity.status(404).body(null);
        else return ResponseEntity.ok(review);
    }

    @GetMapping("/api/getAllReviews")
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.getReviews();
        if (reviews == null) return ResponseEntity.status(404).body(null);
        else return ResponseEntity.ok(reviews);
    }

    @GetMapping("/api/getReviewsLeftByUser/{userId}")
    public ResponseEntity<List<Review>> getReviewsLeftByUser(@PathVariable UUID userId) {
        List<Review> reviews = reviewService.getReviewsLeftByUser(userId);
        if (reviews == null) return ResponseEntity.status(404).body(null);
        else return ResponseEntity.ok(reviews);
    }
}
