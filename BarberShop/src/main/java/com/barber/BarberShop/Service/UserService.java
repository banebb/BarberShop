package com.barber.BarberShop.Service;

import com.barber.BarberShop.Dtos.LoginDto;
import com.barber.BarberShop.Model.User;
import com.barber.BarberShop.Repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

    @Autowired
    private UserRepository userRepository;

    public Pair<Integer, Boolean> register(String username, String password, String email, String name, String surname, String phoneNumber) {

        if(username == null || password == null || email == null || name == null || surname == null || phoneNumber == null) {
            return Pair.of(1, false); //"All fields must be filled"
        }
        if(username.isEmpty() || password.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || name.isEmpty() || surname.isEmpty()) {
            return Pair.of(1, false);
        }

        if(userRepository.findByUsernameOrEmailOrPhoneNumber(username, email, phoneNumber) != null) {
            return Pair.of(0, false); //"User already exists"
        }

        String encryptedPassword = passwordEncryptor.encryptPassword(password);
        User registeredUser = new User(username, encryptedPassword, email, name, surname, phoneNumber, User.Role.CUSTOMER);

        userRepository.save(registeredUser);

        return Pair.of(2, true); //"User registered"
    }

    public Pair<Integer, Boolean> login(LoginDto loginData, HttpSession session) {

        if (loginData == null || loginData.getPassword() == null || loginData.getUsernameOrEmailOrPhoneNumber() == null) {
            return Pair.of(0, false); //"Some data is missing"
        }
        if(loginData.getPassword().isEmpty() || loginData.getUsernameOrEmailOrPhoneNumber().isEmpty()) {
            return Pair.of(0, false);
        }

        User user = userRepository.findByUsernameOrEmailOrPhoneNumber(loginData.getUsernameOrEmailOrPhoneNumber(), loginData.getUsernameOrEmailOrPhoneNumber(), loginData.getUsernameOrEmailOrPhoneNumber());

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

    public Pair<String, Boolean> getName(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user == null) return Pair.of("User has to be signed in first", false);
        return Pair.of(user.getName(), true);
    }

    public Pair<Boolean, Boolean> isBarber(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(user == null) {
            return Pair.of(false, false);
        }
        if (user.getRole() == User.Role.BARBER) { return Pair.of(true, true); }
        return Pair.of(false, true); //user is logged in  but it is either customer or admin
    }

}
