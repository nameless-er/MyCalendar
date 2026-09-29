package com.baiyu.mycalendar.ui.year;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

public interface MonthCellsProvider {
    List<List<LocalDate>> generateMonthCells(Year year);
}
