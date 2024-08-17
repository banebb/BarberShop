package com.barber.BarberShop.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class User {

    @Id
    private UUID id;

    private String username;
    private String password;
    private String email;
    private String name;
    private String surname;
    private Long numOfHaircuts;
    private String phoneNumber;
    private Role role;

    public User() { }

    public User(String username, String password, String email, String name, String surname, Long numOfHaircuts, String phoneNumber, Role role) {
        id = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.numOfHaircuts = numOfHaircuts;
        this.phoneNumber = phoneNumber;
        this.role = role;
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

    public enum Role { ADMIN, USER, BARBER }
}
