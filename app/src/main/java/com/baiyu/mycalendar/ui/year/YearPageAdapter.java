package com.baiyu.mycalendar.ui.year;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.baiyu.mycalendar.R;
import com.baiyu.mycalendar.databinding.ItemMonthcellBinding;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;

public class YearPageAdapter extends RecyclerView.Adapter<YearPageAdapter.MonthCellViewHolder> {
    private final OnMonthCellClickListener listener;
    private final Year year;
    private final List<List<LocalDate>> monthCells;

    public YearPageAdapter(OnMonthCellClickListener listener, Year year, List<List<LocalDate>> monthCells) {
        super();
        this.listener = listener;
        this.year = year;
        this.monthCells = monthCells;
    }

    @NonNull
    @Override
    public MonthCellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMonthcellBinding binding = ItemMonthcellBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new MonthCellViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MonthCellViewHolder holder, int position) {
        List<LocalDate> monthCell = monthCells.get(position);
        //text view of name of month
        YearMonth month = year.atMonth(position + 1);
        holder.binding.monthTv.setText(month.getMonth().toString().substring(0, 3));
        if(month.equals(YearMonth.now())){
            holder.binding.monthTv.setBackgroundResource(R.drawable.background_text_today);
            holder.binding.monthTv.setTextColor(0xFF000000);
        }
        else {
            holder.binding.monthTv.setBackground(null);
            holder.binding.monthTv.setTextColor(holder.defaultTextColor);
        }
        //set onclick listener
        holder.itemView.setOnClickListener(v -> listener.onMonthCellClick(month));
        //fill the grid with text view
        GridLayout grid = holder.binding.daysGrid;
        grid.removeAllViews();
        for(LocalDate date : monthCell){
            TextView dayTv = new TextView(grid.getContext());
            //centralize the dates
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = 0;
            //define the weight
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            int margin = (int) (1.6f * grid.getResources().getDisplayMetrics().density);
            params.setMargins(margin, margin, margin, margin);
            dayTv.setLayoutParams(params);
            dayTv.setGravity(Gravity.CENTER);
            dayTv.setTextSize(10);
            dayTv.setTypeface(dayTv.getTypeface(), Typeface.BOLD);
            dayTv.setIncludeFontPadding(false);
            if (date != null) {
                dayTv.setText(String.valueOf(date.getDayOfMonth()));
                dayTv.setBackgroundResource(R.drawable.background_daycell_displayed_month);
            }
            grid.addView(dayTv);

        }

    }

    @Override
    public int getItemCount() {
        return 12;
    }

    static class MonthCellViewHolder extends RecyclerView.ViewHolder{
        ItemMonthcellBinding binding;
        ColorStateList defaultTextColor;

        public MonthCellViewHolder(@NonNull ItemMonthcellBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            defaultTextColor = binding.monthTv.getTextColors();
        }
    }
}
