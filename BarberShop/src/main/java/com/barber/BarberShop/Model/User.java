package com.barber.BarberShop.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.UUID;

@Entity
public class User implements Serializable {

    @Id
    private UUID id;
    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    private String name;
    private String surname;
    private Long numOfHaircuts;
    private Long numOfCancellations;
    private Long numOfDidntShowUps;
    private boolean blocked;
    @Column(unique = true)
    private String phoneNumber;
    private Role role;
    private double avgGrade;

    public User() { }

    public User(String username, String password, String email, String name, String surname, String phoneNumber, Role role) {
        id = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.numOfHaircuts = 0L;
        this.numOfCancellations = 0L;
        this.numOfDidntShowUps = 0L;
        blocked = false;
        this.phoneNumber = phoneNumber;
        this.role = role;
        avgGrade = 0;
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Long getNumOfHaircuts() {
        return numOfHaircuts;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setId(UUID id) { this.id = id; }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) { this.surname = surname; }

    public void setNumOfHaircuts(Long numOfHaircuts) {
        this.numOfHaircuts = numOfHaircuts;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() { return role; }

    public void setRole(Role role) { this.role = role; }

    public double getAvgGrade() { return avgGrade; }

    public void setAvgGrade(double avgGrade) { this.avgGrade = avgGrade; }

    public enum Role { ADMIN, CUSTOMER, BARBER }

    public Long getNumOfCancellations() {
        return numOfCancellations;
    }

    public void setNumOfCancellations(Long numOfCancellations) {
        this.numOfCancellations = numOfCancellations;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public Long getNumOfDidntShowUps() {
        return numOfDidntShowUps;
    }

    public void setNumOfDidntShowUps(Long numOfDidntShowUps) {
        this.numOfDidntShowUps = numOfDidntShowUps;
    }
}
