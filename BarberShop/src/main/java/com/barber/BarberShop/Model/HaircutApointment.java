package com.barber.BarberShop.Model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class HaircutApointment implements Serializable {
    @Id
    private UUID id;
    private LocalDateTime dateTime;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private boolean nullObj;
    private Status status;

    public HaircutApointment() {
        nullObj = true;
    }

    public HaircutApointment(LocalDateTime dateTime, User user) {
        id = UUID.randomUUID();
        this.dateTime = dateTime;
        this.user = user;
        status = Status.NOT_THE_TIME;
        nullObj = false;
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

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isNullObj() {
        return nullObj;
    }

    public enum Status {
        DONE, NOT_THE_TIME, CANCELED, DIDNT_SHOW_UP
    }
}
