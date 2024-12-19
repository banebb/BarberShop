package com.barber.BarberShop.Service;

import com.barber.BarberShop.Dtos.ReviewDto;
import com.barber.BarberShop.Model.HaircutApointment;
import com.barber.BarberShop.Model.Review;
import com.barber.BarberShop.Model.User;
import com.barber.BarberShop.Repository.HaircutApointmentRepository;
import com.barber.BarberShop.Repository.ReviewRepository;
import com.barber.BarberShop.Repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private HaircutApointmentRepository haircutApointmentRepository;

    @Autowired
    private UserRepository userRepository;

    public Pair<Boolean, Integer> leaveReview(HttpSession session, ReviewDto data) {
        User customer = (User) session.getAttribute("user");
        if(customer == null) return Pair.of(false, 0); // 0 - user not logged in
        if(data == null) return Pair.of(false, 4); // 4 - invalid data
        if(data.getRating() < 1 || data.getRating() > 5) return Pair.of(false, 3); // 3 - invalid rating
        HaircutApointment apointment = haircutApointmentRepository.findById(data.getHaircut()).orElse(null);
        if(apointment == null) return Pair.of(false, 1); // 1 - haircut apointment not found
        if (apointment.getDateTime().isAfter(LocalDateTime.now())) return Pair.of(false, 5);
        Review review = new Review(data.getReviw(), data.getRating(), customer, apointment);
        reviewRepository.save(review);
        User barber = userRepository.findByRole(User.Role.BARBER);
        List<Review> reviews = getReviews();
        if(!reviews.isEmpty()){
            double sum = 0;
            for(Review i : reviews){
                sum += i.getRating();
            }
            barber.setAvgGrade(sum / reviews.size());
        } else barber.setAvgGrade(0);
        userRepository.save(barber);
        return Pair.of(true, 2); // 2 - review left successfully
    }

    public Pair<Boolean, Integer> deleteReview(HttpSession session, ReviewDto data) {
        User customer = (User) session.getAttribute("user");
        if(customer == null) return Pair.of(false, 0); // 0 - user not logged in
        if(data == null) return Pair.of(false, 4); // 4 - invalid data
        Review review = reviewRepository.findById(data.getId()).orElse(null);
        if(review == null) return Pair.of(false, 1); // 1 - review not found
        reviewRepository.delete(review);
        User barber = userRepository.findByRole(User.Role.BARBER);
        List<Review> reviews = getReviews();
        if(!reviews.isEmpty()){
            double sum = 0;
            for(Review i : reviews){
                sum += i.getRating();
            }
            barber.setAvgGrade(sum / reviews.size());
        } else barber.setAvgGrade(0);
        userRepository.save(barber);
        return Pair.of(true, 2); // 2 - review deleted successfully
    }

    public Review getReview(UUID id){
        return reviewRepository.findById(id).orElse(null);
    }

    public List<Review> getReviews(){
        return reviewRepository.findAll();
    }

    public List<Review> getReviewsLeftByUser(UUID userId) {
        if(userId == null) return null;
        User customer = userRepository.findById(userId).orElse(null);
        if(customer == null) return null;
        return reviewRepository.findByUserWhoLeftReview(customer);
    }

}
