package com.baiyu.mycalendar.ui.month;

import com.baiyu.mycalendar.model.DayCell;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

//interface to viewmodel
public interface DayCellsProvider {
    List<DayCell> generateDayCells(YearMonth month);
    List<DayCell> upDateSelectedDayCell(LocalDate newDate, List<DayCell> currentCells);
}
