package com.baiyu.mycalendar.ui.month;

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
public class MonthPageAdapter extends ListAdapter<DayCell, MonthPageAdapter.DayCellViewHolder> {
    //the list is in ListAdapter, use getItem()
    //the setData() is in ListAdapter namely submitList()
    private final OnDayCellClickListener listener;
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
    public MonthPageAdapter(OnDayCellClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    @NonNull
    @Override
    //create a new view holder and return it
    public DayCellViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDaycellBinding binding = ItemDaycellBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false);
        return new DayCellViewHolder(binding);
    }

    @Override
    //bind and set data from the list to view holder
    public void onBindViewHolder(@NonNull DayCellViewHolder holder, int position) {
        DayCell dayData = getItem(position);
        holder.binding.dayTv.setText(String.valueOf(dayData.date().getDayOfMonth()));
        //change the selected date
        holder.itemView.setOnClickListener(v -> { listener.onDayCellClick(dayData);
        });
        //must have else to update the view holder
        if (dayData.isDisplayedMonth()) {
            holder.itemView.setBackgroundResource(R.drawable.background_daycell_displayed_month);
            holder.binding.dayTv.setAlpha(1);
        }
        else {
            holder.itemView.setBackgroundResource(R.drawable.background_daycell_not_displayed_month);
            holder.binding.dayTv.setAlpha(0.5f);
        }
        if(dayData.isToday()){
            holder.binding.dayTv.setBackgroundResource(R.drawable.background_text_today);
            holder.binding.dayTv.setTextColor(0xFF000000);
        }
        else {
            holder.binding.dayTv.setBackground(null);
            holder.binding.dayTv.setTextColor(holder.defaultTextColor);
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
            defaultTextColor = binding.dayTv.getTextColors();
        }
    }
}
