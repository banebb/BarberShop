package com.barber.BarberShop.Service;

import com.barber.BarberShop.Dtos.LoginDto;
import com.barber.BarberShop.Model.User;
import com.barber.BarberShop.Repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

    @Autowired
    private UserRepository userRepository;

    public Pair<Integer, Boolean> register(String username, String password, String email, String name, String surname, String phoneNumber) {
        if(userRepository.findByUsernameOrEmailOrPhoneNumber(username, email, phoneNumber) != null) {
            return Pair.of(0, false); //"User already exists"
        }
        if(username == null || password == null || email == null || name == null || surname == null || phoneNumber == null) {
            return Pair.of(1, false); //"All fields must be filled"
        }

        String encryptedPassword = passwordEncryptor.encryptPassword(password);
        User registeredUser = new User(username, encryptedPassword, email, name, surname, phoneNumber, User.Role.CUSTOMER);

        userRepository.save(registeredUser);

        return Pair.of(2, true); //"User registered"
    }

    public Pair<Integer, Boolean> login(LoginDto loginData, HttpSession session) {
        User user = userRepository.findByUsernameOrEmailOrPhoneNumber(loginData.getUsernameOrEmailOrPhoneNumber(), loginData.getUsernameOrEmailOrPhoneNumber(), loginData.getUsernameOrEmailOrPhoneNumber());
        if (loginData == null || loginData.getPassword() == null || loginData.getUsernameOrEmailOrPhoneNumber() == null) {
            return Pair.of(0, false); //"Some data is missing"
        }
        if(user == null) {
            return Pair.of(1, false); //"User with that username or mail or phone number doesn't exist"
        }
        if(!passwordEncryptor.checkPassword(loginData.getPassword(), user.getPassword())) {
            return Pair.of(2, false); //"Wrong password"
        }

        session.setAttribute("user", user);
        return Pair.of(3, true); //"User successfully logged in"
    }

    public Pair<Integer, Boolean> logout(HttpSession session) {
        if(session.getAttribute("user") == null) {
            return Pair.of(0, false); //"User is not logged in"
        }
        session.invalidate();
        return Pair.of(1, true) ; //"User successfully logged out"
    }

}
