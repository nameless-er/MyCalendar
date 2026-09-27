package com.baiyu.mycalendar.ui.week;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.baiyu.mycalendar.R;
import com.baiyu.mycalendar.databinding.ItemDaycellBinding;
import com.baiyu.mycalendar.model.DayCell;

//for dates_rv
public class WeekPageAdapter extends ListAdapter<DayCell, WeekPageAdapter.DayCellViewHolder> {
    private final OnDayCellClickListener listener;
    private final DayCellsProvider provider;

    private static final DiffUtil.ItemCallback<DayCell> DIFF_CALLBACK = new DiffUtil.ItemCallback<DayCell>() {
        @Override
        //compare the id of the object (date)
        public boolean areItemsTheSame(@NonNull DayCell oldItem, @NonNull DayCell newItem) {
            return oldItem.date().equals(newItem.date());
        }

        @Override
        //compare the other content of the object
        public boolean areContentsTheSame(@NonNull DayCell oldItem, @NonNull DayCell newItem) {
            return oldItem.equals(newItem);
        }
    };

    public WeekPageAdapter(OnDayCellClickListener listener, DayCellsProvider provider) {
        super(DIFF_CALLBACK);
        this.listener = listener;
        this.provider = provider;
    }

    @NonNull
    @Override
    public DayCellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDaycellBinding binding = ItemDaycellBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new DayCellViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DayCellViewHolder holder, int position) {
        DayCell dayData = getItem(position);
        holder.binding.DayTv.setText(String.valueOf(dayData.date().getDayOfMonth()));
        //change the selected date
        holder.itemView.setOnClickListener(v -> { listener.onDayCellClick(dayData);
        });
        //must have else to update the view holder
        if (dayData.isDisplayedMonth()) {
            holder.itemView.setBackgroundResource(R.drawable.background_daycell_displayed_month);
            holder.binding.DayTv.setAlpha(1);
        }
        else {
            holder.itemView.setBackgroundResource(R.drawable.background_daycell_not_displayed_month);
            holder.binding.DayTv.setAlpha(0.5f);
        }
        if(dayData.isToday()){
            holder.binding.DayTv.setBackgroundResource(R.drawable.background_text_today);
            holder.binding.DayTv.setTextColor(0xFF000000);
        }
        else {
            holder.binding.DayTv.setBackground(null);
            holder.binding.DayTv.setTextColor(holder.defaultTextColor);
        }
        holder.binding.selectionFrame.setVisibility(
                dayData.isSelected() ? View.VISIBLE : View.GONE
        );
    }


    //define the view hold for the daycell
    static class DayCellViewHolder extends RecyclerView.ViewHolder {
        //extra attribute
        ItemDaycellBinding binding;
        ColorStateList defaultTextColor;
        public DayCellViewHolder(@NonNull ItemDaycellBinding binding) {
            //set viewItem
            super(binding.getRoot());
            this.binding = binding;
            defaultTextColor = binding.DayTv.getTextColors();
        }
    }
}
