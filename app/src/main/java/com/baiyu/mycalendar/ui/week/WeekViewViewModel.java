package com.baiyu.mycalendar.ui.week;

import androidx.lifecycle.ViewModel;

import com.baiyu.mycalendar.model.DayCell;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WeekViewViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    public List<DayCell> generateDayCells(LocalDate monday, LocalDate selectedDate) {
        List<DayCell> dayCells = new ArrayList<>();
        LocalDate today = LocalDate.now();// use once LocalDate() in case of latency
        for (int i = 0; i < 7; i++) {
            LocalDate date = monday.plusDays(i);
            boolean isDisplayedMonth = true;
            boolean isToday = date.equals(today);
            boolean isSelected = date.equals(selectedDate);
            dayCells.add(new DayCell(date, isToday, isDisplayedMonth, isSelected));
        }
        return dayCells;
    }

    public List<DayCell> upDateSelectedDayCell(LocalDate newDate, List<DayCell> currentCells){
        if (currentCells != null) {
            List<DayCell> updatedCells = new ArrayList<>(currentCells.size());

            // Just update the selection state of the existing cells
            for (DayCell cell : currentCells) {
                boolean isNowSelected = cell.date().equals(newDate);

                // Re-create the DayCell with the new selection state
                // (Assuming DayCell is a Java Record, the constructor looks like this)
                updatedCells.add(new DayCell(cell.date(), cell.isToday(), cell.isDisplayedMonth(), isNowSelected));
            }

            return updatedCells;
        }
        return currentCells;
    }
}