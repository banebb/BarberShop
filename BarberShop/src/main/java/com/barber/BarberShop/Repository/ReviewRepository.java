package com.barber.BarberShop.Repository;

import com.barber.BarberShop.Model.Review;
import com.barber.BarberShop.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

    public List<Review> findByUserWhoLeftReview(User userWhoLeftReview);
}
