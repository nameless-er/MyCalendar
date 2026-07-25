package com.baiyu.mycalendar.model;

import java.time.LocalDate;

public class DayCell {
    private final LocalDate date;
    private final boolean isToday;
    private final boolean isCurrentMonth;

    public DayCell(LocalDate date, boolean isToday, boolean isCurrentMonth) {
        this.date = date;
        this.isToday = isToday;
        this.isCurrentMonth = isCurrentMonth;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isToday() {
        return isToday;
    }

    public boolean isCurrentMonth() {
        return isCurrentMonth;
    }
}
