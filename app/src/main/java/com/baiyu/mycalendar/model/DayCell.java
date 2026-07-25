package com.baiyu.mycalendar.model;

import java.time.LocalDate;

public record DayCell(
        LocalDate date,
        boolean isToday,
        boolean isCurrentMonth
) {}