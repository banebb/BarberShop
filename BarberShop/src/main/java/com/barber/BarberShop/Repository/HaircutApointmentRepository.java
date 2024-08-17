package com.barber.BarberShop.Repository;

import com.barber.BarberShop.Model.HaircutApointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HaircutApointmentRepository extends JpaRepository<HaircutApointment, UUID> {
}
