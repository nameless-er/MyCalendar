package com.baiyu.mycalendar.ui.month;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.baiyu.mycalendar.databinding.FragmentMonthViewBinding;
import com.baiyu.mycalendar.model.DayCell;
import com.baiyu.mycalendar.ui.main.MainViewModel;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class MonthViewFragment extends Fragment {

    private FragmentMonthViewBinding binding;
    private MainViewModel mainViewModel;
    private MonthViewViewModel monthViewViewModel;
    private MonthPagerAdapter pagerAdapter;

    public static MonthViewFragment newInstance() {
        return new MonthViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //bind
        binding = FragmentMonthViewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //set ViewModel
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        monthViewViewModel = new ViewModelProvider(this).get(MonthViewViewModel.class);
        //create the provider and listener
        DayCellsProvider provider = new DayCellsProvider() {
            @Override
            public List<DayCell> generateDayCells(YearMonth month) {
                return monthViewViewModel.generateDayCells(month, mainViewModel.getSelectedDate().getValue());
            }

            @Override
            public List<DayCell> upDateSelectedDayCell(LocalDate newDate, List<DayCell> currentCells) {
                return monthViewViewModel.upDateSelectedDayCell(newDate, currentCells);
            }
        };
        OnDayCellClickListener listener = dayCell -> mainViewModel.getSelectedDate().setValue(dayCell.date());
        //set ViewPager adapter and give it to ViewPager
        pagerAdapter = new MonthPagerAdapter(listener, provider);
        binding.monthVp2.setAdapter(pagerAdapter);
        //set current page position 1000
        binding.monthVp2.setCurrentItem(1000, false);
        //set swipe callback
        binding.monthVp2.registerOnPageChangeCallback(
                new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        YearMonth month = YearMonth.now().plusMonths(position - 1000);
                        monthViewViewModel.getDisplayedMonth().setValue(month);
                    }
                }
        );
        //set observers
        mainViewModel.getSelectedDate().observe(getViewLifecycleOwner(), newDate ->
        {
            pagerAdapter.updateSelectedDate(newDate);
        });
        monthViewViewModel.getDisplayedMonth().observe(getViewLifecycleOwner(), newMonth -> {
            String month = newMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
            binding.textView.setText(month);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}