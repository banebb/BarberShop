package com.barber.BarberShop.Service;

import com.barber.BarberShop.Model.HaircutApointment;
import com.barber.BarberShop.Model.User;
import com.barber.BarberShop.Repository.HaircutApointmentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HaircutApointmentService {

    @Autowired
    private HaircutApointmentRepository haircutApointmentRepository;

    public Pair<HaircutApointment, Integer> makeApointment(LocalDateTime dateTime, HttpSession session) {
        if(session.getAttribute("user") == null) {
            return Pair.of(null, 0);
        }
        if(dateTime == null) {
            return Pair.of(null, 1);
        }
        HaircutApointment haircutApointment = new HaircutApointment(dateTime, (User) session.getAttribute("user"));
        haircutApointmentRepository.save(haircutApointment);
        return Pair.of(haircutApointment, 2);
    }
}
