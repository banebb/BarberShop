package com.barber.BarberShop.Dtos;

public class LoginDto
{
    private String usernameOrEmailOrPhoneNumber;
    private String password;

    public LoginDto() {
    }

    public LoginDto(String usernameOrEmailOrPhoneNumber, String password) {
        this.usernameOrEmailOrPhoneNumber = usernameOrEmailOrPhoneNumber;
        this.password = password;
    }

    public String getUsernameOrEmailOrPhoneNumber() {
        return usernameOrEmailOrPhoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setUsernameOrEmailOrPhoneNumber(String usernameOrEmailOrPhoneNumber) {
        this.usernameOrEmailOrPhoneNumber = usernameOrEmailOrPhoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
