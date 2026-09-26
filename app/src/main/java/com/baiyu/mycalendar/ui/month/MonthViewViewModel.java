package com.baiyu.mycalendar.ui.month;

import androidx.lifecycle.ViewModel;

import com.baiyu.mycalendar.model.DayCell;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class MonthViewViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    // The month currently displayed


    public MonthViewViewModel() {
    }

    //generate daycells based on a month
    public List<DayCell> generateDayCells(YearMonth month, LocalDate selectedDate) {
        DayOfWeek startWeekDay = month.atDay(1).getDayOfWeek();
        //check whether to create 35 cells or 42 cells
        int requiredCells = startWeekDay.getValue() + month.lengthOfMonth() - 1;
        int totalCells = requiredCells <= 35 ? 35 : 42;
        LocalDate today = LocalDate.now();// use once LocalDate() in case of latency
        LocalDate firstDayCell = month.atDay(1).minusDays(startWeekDay.getValue()-1);
        List<DayCell> dayCells = new ArrayList<>();
        for (int i = 0; i < totalCells; i++)
        {
            LocalDate date = firstDayCell.plusDays(i);
            boolean isDisplayedMonth = YearMonth.from(date).equals(month);
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