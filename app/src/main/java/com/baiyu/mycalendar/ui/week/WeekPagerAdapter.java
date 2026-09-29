package com.baiyu.mycalendar.ui.week;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.baiyu.mycalendar.databinding.ItemWeekPageBinding;
import com.baiyu.mycalendar.model.DayCell;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//for week_vp2
public class WeekPagerAdapter extends RecyclerView.Adapter<WeekPagerAdapter.PageViewHolder>{

    //listener and provider go from WeekViewFragment to WeekPagerAdapter then to WeekPageAdapter
    private final OnDayCellClickListener listener;
    private final DayCellsProvider provider;
    private final Map<LocalDate, List<DayCell>> dayCellsByMonday = new HashMap<>(); //store dayCells for each Monday
    private final Map<LocalDate, WeekPageAdapter> adaptersByMonday = new HashMap<>(); //store adapter for each Monday

    public WeekPagerAdapter(OnDayCellClickListener listener, DayCellsProvider provider) {
        this.listener = listener;
        this.provider = provider;
    }

    @NonNull
    @Override
    public PageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemWeekPageBinding binding = ItemWeekPageBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false);
        binding.datesRv.setLayoutManager(new GridLayoutManager(parent.getContext(), 7));
        return new PageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PageViewHolder holder, int position) {
        //get date of monday in current week, the position it set from 1000
        LocalDate monday = LocalDate.now().with(DayOfWeek.MONDAY).plusWeeks(position - 1000);
        holder.setMonday(monday);
        //get existing list, or generate it if it doesn't exist
        List<DayCell> dayCells = dayCellsByMonday.get(monday);
        if (dayCells == null) {
            dayCells = provider.generateDayCells(monday);
            dayCellsByMonday.put(monday, dayCells);
        }
        //create the adapter for this week's RecyclerView
        WeekPageAdapter pageAdapter = new WeekPageAdapter(listener);
        adaptersByMonday.put(monday, pageAdapter);
        holder.binding.datesRv.setAdapter(pageAdapter);
        pageAdapter.submitList(dayCells);
        //disableRvAnimation
        disableRvAnimation(holder);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void onViewRecycled(@NonNull PageViewHolder holder) {

        if (holder.monday != null) {
            adaptersByMonday.remove(holder.monday);
        }
        super.onViewRecycled(holder);
    }
    public void disableRvAnimation(@NonNull PageViewHolder holder){
        RecyclerView.ItemAnimator animator = holder.binding.datesRv.getItemAnimator();
        if (animator instanceof androidx.recyclerview.widget.SimpleItemAnimator) {
            ((androidx.recyclerview.widget.SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
    }

    //PageViewHolder contains a recycler view (item_week_page.xml)
    class PageViewHolder extends RecyclerView.ViewHolder{
        ItemWeekPageBinding binding;
        LocalDate monday;

        public PageViewHolder(@NonNull ItemWeekPageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setMonday(LocalDate monday) {
            this.monday = monday;
        }
    }

    public void updateSelectedDate(LocalDate newDate) {

        for (Map.Entry<LocalDate, List<DayCell>> entry : dayCellsByMonday.entrySet()) {

            LocalDate monday = entry.getKey();
            List<DayCell> currentCells = entry.getValue();

            List<DayCell> updatedCells =
                    provider.upDateSelectedDayCell(
                            newDate,
                            currentCells
                    );

            entry.setValue(updatedCells);
            //get adapter of this week and update the week
            WeekPageAdapter adapter =
                    adaptersByMonday.get(monday);

            if (adapter != null) {
                adapter.submitList(updatedCells);
            }
        }
    }
}
