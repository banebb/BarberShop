package com.barber.BarberShop.Service;

import com.barber.BarberShop.Model.HaircutApointment;
import com.barber.BarberShop.Model.User;
import com.barber.BarberShop.Repository.HaircutApointmentRepository;
import com.barber.BarberShop.Repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HaircutApointmentService {

    @Autowired
    private HaircutApointmentRepository haircutApointmentRepository;

    @Autowired
    private UserRepository userRepository;

    public Pair<HaircutApointment, Integer> makeApointment(LocalDateTime dateTime, HttpSession session) {
        User customer = (User) session.getAttribute("user");
        if(customer == null) {
            return Pair.of(new HaircutApointment(), 0); // You need to be singed in to make an apointment
        }
        if(customer.isBlocked()){
            return Pair.of(new HaircutApointment(), 6); // You are blocked
        }
        if(dateTime == null) {
            return Pair.of(new HaircutApointment(), 1); // You must provide a date and time
        }
        if(dateTime.isBefore(LocalDateTime.now())) {
            return Pair.of(new HaircutApointment(), 2);  // You can't make an apointment in the past
        }
        if(haircutApointmentRepository.findByDateTime(dateTime) != null) {
            return Pair.of(new HaircutApointment(), 3); // There is already an apointment at that time
        }
        HaircutApointment haircutApointment = new HaircutApointment(dateTime, customer);
        haircutApointmentRepository.save(haircutApointment);
        return Pair.of(haircutApointment, 4); // Apointment made
    }

    public Pair<HaircutApointment, Integer> cancelApointment(LocalDateTime dateTime, HttpSession session) {
        User customer = (User) session.getAttribute("user");
        if(customer == null) {
            return Pair.of(new HaircutApointment(), 0); // You need to be singed in to cancel an apointment
        }
        if (customer.isBlocked()) {
            return Pair.of(new HaircutApointment(), 6); // You are blocked
        }
        if(dateTime == null) {
            return Pair.of(new HaircutApointment(), 1); // You must provide a date and time
        }
        if(dateTime.isBefore(LocalDateTime.now())) {
            return Pair.of(new HaircutApointment(), 2);  // You can't cancel an apointment in the past
        }
        HaircutApointment haircutApointment = haircutApointmentRepository.findByDateTime(dateTime);
        if(haircutApointment == null) {
            return Pair.of(new HaircutApointment(), 3); // There is no apointment at that time
        }
        if(!haircutApointment.getUser().getId().equals(customer.getId())) {
            return Pair.of(new HaircutApointment(), 4); // You can not cancel an apointment of another user
        }
        customer.setNumOfCancellations(customer.getNumOfCancellations() + 1);
        if (customer.getNumOfCancellations() >= 10) customer.setBlocked(true);
        userRepository.save(customer);
        haircutApointmentRepository.delete(haircutApointment);
        return Pair.of(haircutApointment, 5); // Apointment canceled
    }

    public Pair<HaircutApointment, Integer> apointmentDone(UUID appointmentId, HttpSession session) {
        HaircutApointment haircutApointment = haircutApointmentRepository.findById(appointmentId).orElse(null);
        User barber = (User) session.getAttribute("user");
        if(barber == null) {
            return Pair.of(new HaircutApointment(), 0); // You need to be singed in to mark an apointment as done
        }
        if (barber.getRole() != User.Role.BARBER) {
            return Pair.of(new HaircutApointment(), 1); // You must be a barber to mark an apointment as done
        }
        if(haircutApointment == null) {
            return Pair.of(new HaircutApointment(), 2); // You must provide an apointment
        }
        if(haircutApointment.getStatus() == HaircutApointment.Status.CANCELED) {
            return Pair.of(new HaircutApointment(), 3); // You can't mark an apointment as done if it was canceled
        }
        if(haircutApointment.getDateTime().isAfter(LocalDateTime.now())){
            return Pair.of(new HaircutApointment(), 5); // You can't mark an apointment as done if it is in the future
        }
        haircutApointment.setStatus(HaircutApointment.Status.DONE);
        haircutApointment.getUser().setNumOfHaircuts(haircutApointment.getUser().getNumOfHaircuts() + 1);
        userRepository.save(haircutApointment.getUser());
        haircutApointmentRepository.save(haircutApointment);
        return Pair.of(haircutApointment, 4); // Apointment marked as done
    }

    public Pair<HaircutApointment, Integer> setCustomerDidntShowup(UUID appointmentId, HttpSession session) {
        HaircutApointment haircutApointment = haircutApointmentRepository.findById(appointmentId).orElse(null);
        User barber = (User) session.getAttribute("user");
        if(barber == null) {
            return Pair.of(new HaircutApointment(), 0); // You need to be singed in to set that customer didn't showup
        }
        if (barber.getRole() != User.Role.BARBER) {
            return Pair.of(new HaircutApointment(), 1); // You must be a barber to set that customer didn't showup
        }
        if(haircutApointment == null) {
            return Pair.of(new HaircutApointment(), 2); // You must provide an apointment
        }
        if(haircutApointment.getStatus() == HaircutApointment.Status.CANCELED || haircutApointment.getStatus() == HaircutApointment.Status.DONE) {
            return Pair.of(new HaircutApointment(), 3); // You can't set that customer didn't showup if appointment was canceled or it was already done
        }
        if(haircutApointment.getDateTime().isAfter(LocalDateTime.now())){
            System.out.println(LocalDateTime.now());
            return Pair.of(new HaircutApointment(), 4); // You can't set that customer didn't showup if appointment is in the future
        }
        haircutApointment.setStatus(HaircutApointment.Status.DIDNT_SHOW_UP);
        haircutApointment.getUser().setNumOfDidntShowUps(haircutApointment.getUser().getNumOfDidntShowUps() + 1);
        if(haircutApointment.getUser().getNumOfDidntShowUps() > 5) haircutApointment.getUser().setBlocked(true);
        userRepository.save(haircutApointment.getUser());
        haircutApointmentRepository.save(haircutApointment);
        return Pair.of(haircutApointment, 5); // Apointment marked as done
    }

    public HaircutApointment getHaircutApointment(LocalDateTime dateTime) {
        return haircutApointmentRepository.findByDateTime(dateTime);
    }

    public List<HaircutApointment> getHaircutApointments() {
        return haircutApointmentRepository.findAll();
    }

    public List<HaircutApointment> getHaircutApointmentsByUser(HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user == null) return null;
        Page<HaircutApointment> pageOfAppointments = haircutApointmentRepository.findByUserOrderByDateTimeDesc(user, PageRequest.of(0, 5));
        List<HaircutApointment> appointments = pageOfAppointments.getContent();
        return appointments;
    }

    public List<HaircutApointment> getHaircutAppointmentsOnDay(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        return haircutApointmentRepository.findByDateTimeBetween(startOfDay, endOfDay);
    }

    public List<HaircutApointment> getHaircutAppointmentsOnMonth(int month){
        LocalDateTime startOfMonth = LocalDateTime.of(Year.now().getValue() ,month, 1, 0,0,0);
        if(month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            LocalDateTime endOfMonth = LocalDateTime.of(Year.now().getValue() ,month, 31, 23,59,59);
            return haircutApointmentRepository.findByDateTimeBetween(startOfMonth, endOfMonth);
        } else {
            LocalDateTime endOfMonth = LocalDateTime.of(Year.now().getValue() ,month, 30, 23,59,59);
            return haircutApointmentRepository.findByDateTimeBetween(startOfMonth, endOfMonth);
        }
    }

}
