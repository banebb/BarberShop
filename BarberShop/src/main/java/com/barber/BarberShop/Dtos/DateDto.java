package com.barber.BarberShop.Dtos;

import java.time.LocalDateTime;

public class DateDto {

    private LocalDateTime dateTimeStr;

    public DateDto() {
    }

    public DateDto(LocalDateTime dateTimeStr) {
        this.dateTimeStr = dateTimeStr;
    }

    public LocalDateTime getDateTimeStr() {
        return dateTimeStr;
    }

    public void setDateTimeStr(LocalDateTime dateTimeStr) {
        this.dateTimeStr = dateTimeStr;
    }
}
