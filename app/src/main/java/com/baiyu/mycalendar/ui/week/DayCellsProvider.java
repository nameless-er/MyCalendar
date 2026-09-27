package com.baiyu.mycalendar.ui.week;

import com.baiyu.mycalendar.model.DayCell;

import java.time.LocalDate;
import java.util.List;

//interface to viewmodel
public interface DayCellsProvider {
    List<DayCell> generateDayCells(LocalDate monday);
    List<DayCell> upDateSelectedDayCell(LocalDate newDate, List<DayCell> currentCells);
}
