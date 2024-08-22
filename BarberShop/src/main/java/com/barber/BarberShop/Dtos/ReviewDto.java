package com.barber.BarberShop.Dtos;

import java.util.UUID;

public class ReviewDto {

    private UUID id;
    private String reviw;
    private int rating;
    private UUID userWhoLeftReview;
    private UUID haircut;

    public ReviewDto() {
    }

    public ReviewDto(UUID id, String reviw, int rating, UUID userWhoLeftReview, UUID haircut) {
        this.id = id;
        this.reviw = reviw;
        this.rating = rating;
        this.userWhoLeftReview = userWhoLeftReview;
        this.haircut = haircut;
    }

    public String getReviw() {
        return reviw;
    }

    public int getRating() {
        return rating;
    }

    public UUID getUserWhoLeftReview() {
        return userWhoLeftReview;
    }

    public UUID getHaircut() {
        return haircut;
    }

    public void setReviw(String reviw) {
        this.reviw = reviw;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setUserWhoLeftReview(UUID userWhoLeftReview) {
        this.userWhoLeftReview = userWhoLeftReview;
    }

    public void setHaircut(UUID haircut) {
        this.haircut = haircut;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}

