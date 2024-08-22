package com.barber.BarberShop.Repository;

import com.barber.BarberShop.Model.HaircutApointment;
import com.barber.BarberShop.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface HaircutApointmentRepository extends JpaRepository<HaircutApointment, UUID> {

    public HaircutApointment findByDateTime(LocalDateTime dateTime);

    public Page<HaircutApointment> findByUserOrderByDateTimeDesc(User user, Pageable page);

    public List<HaircutApointment> findByDateTimeBetween(LocalDateTime start, LocalDateTime end);
}
