package com.barber.BarberShop.Model;

import com.barber.BarberShop.Service.HaircutApointmentService;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
public class Review implements Serializable {

    @Id
    private UUID id;

    private String reviw;
    private double rating;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userWhoLeftReview;
    @OneToOne
    @JoinColumn(name = "haircut_id")
    private HaircutApointment haircut;

    public Review() {
    }

    public Review(String reviw, double rating, User userWhoLeftReview, HaircutApointment haircut) {
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

    public double getRating() {
        return rating;
    }

    public User getUserWhoLeftReview() {
        return userWhoLeftReview;
    }

    public void setReviw(String reviw) {
        this.reviw = reviw;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setUserWhoLeftReview(User userWhoLeftReview) {
        this.userWhoLeftReview = userWhoLeftReview;
    }
}
