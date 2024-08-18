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
    private boolean done;

    public HaircutApointment() { }

    public HaircutApointment(LocalDateTime dateTime, User user) {
        id = UUID.randomUUID();
        this.dateTime = dateTime;
        this.user = user;
        done = false;
    }

    public UUID getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public User getUser() {
        return user;
    }

    public boolean isDone() {
        return done;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
