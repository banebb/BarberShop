package com.barber.BarberShop.Repository;

import com.barber.BarberShop.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    public User findByUsername(String username);
    public User findByEmail(String email);
    public User findByPhoneNumber(String phoneNumber);
    public User findByUsernameOrEmailOrPhoneNumber(String usernameOrEmailOrPhoneNumber);
}
