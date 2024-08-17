package com.barber.BarberShop.Model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class HaircutApointment {
    @Id
    private UUID id;
    private LocalDateTime dateTime;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public HaircutApointment() { }

    public HaircutApointment(LocalDateTime dateTime, User user) {
        id = UUID.randomUUID();
        this.dateTime = dateTime;
        this.user = user;
    }
}
