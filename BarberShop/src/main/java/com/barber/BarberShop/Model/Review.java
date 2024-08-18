package com.barber.BarberShop.Model;

import com.barber.BarberShop.Service.HaircutApointmentService;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Review {

    @Id
    private UUID id;

    private String reviw;
    private int rating;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userWhoLeftReview;
    @OneToOne
    @JoinColumn(name = "haircut_id")
    private HaircutApointment haircut;

    public Review() {
    }

    public Review(String reviw, int rating, User userWhoLeftReview, HaircutApointment haircut) {
        id = UUID.randomUUID();
        this.reviw = reviw;
        this.rating = rating;
        this.userWhoLeftReview = userWhoLeftReview;
        this.haircut = haircut;
    }

    public UUID getId() {
        return id;
    }

    public String getReviw() {
        return reviw;
    }

    public int getRating() {
        return rating;
    }

    public User getUserWhoLeftReview() {
        return userWhoLeftReview;
    }

    public void setReviw(String reviw) {
        this.reviw = reviw;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setUserWhoLeftReview(User userWhoLeftReview) {
        this.userWhoLeftReview = userWhoLeftReview;
    }
}
