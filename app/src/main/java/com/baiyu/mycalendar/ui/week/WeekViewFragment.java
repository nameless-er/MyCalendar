package com.baiyu.mycalendar.ui.week;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.baiyu.mycalendar.databinding.FragmentWeekViewBinding;
import com.baiyu.mycalendar.model.DayCell;
import com.baiyu.mycalendar.ui.main.MainViewModel;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class WeekViewFragment extends Fragment {

    private FragmentWeekViewBinding binding;
    private MainViewModel mainViewModel;
    private WeekViewViewModel weekViewViewModel;
    private WeekPagerAdapter pagerAdapter;

    public static WeekViewFragment newInstance() {
        return new WeekViewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //bind
        binding = FragmentWeekViewBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        weekViewViewModel = new ViewModelProvider(this).get(WeekViewViewModel.class);
        //create the provider and listener
        DayCellsProvider provider = new DayCellsProvider() {
            @Override
            public List<DayCell> generateDayCells(LocalDate monday) {
                return weekViewViewModel.generateDayCells(monday, mainViewModel.getSelectedDate().getValue());
            }

            @Override
            public List<DayCell> upDateSelectedDayCell(LocalDate newDate, List<DayCell> currentCells) {
                return weekViewViewModel.upDateSelectedDayCell(newDate, currentCells);
            }
        };
        OnDayCellClickListener listener = dayCell -> mainViewModel.getSelectedDate().setValue(dayCell.date());
        //set ViewPager adapter and give it to ViewPager
        pagerAdapter = new WeekPagerAdapter(listener, provider);
        binding.weekVp2.setAdapter(pagerAdapter);
        //set current page position 1000
        binding.weekVp2.setCurrentItem(1000, false);
        //set swipe callback
        binding.weekVp2.registerOnPageChangeCallback(
                new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageSelected(int position) {
                        super.onPageSelected(position);
                        LocalDate monday = LocalDate.now()
                                .with(DayOfWeek.MONDAY)
                                .plusWeeks(position - 1000);

                        LocalDate selectedDate = mainViewModel.getSelectedDate().getValue();

                        if (selectedDate == null) {
                            return;
                        }

                        LocalDate currentMonday = selectedDate.with(DayOfWeek.MONDAY);

                        if (currentMonday.equals(monday)) {
                            return;
                        }
                        DayOfWeek dow = selectedDate.getDayOfWeek();
                        mainViewModel.getSelectedDate().setValue(monday.with(dow));
                    }
                }
        );
        //set observers
        mainViewModel.getSelectedDate().observe(getViewLifecycleOwner(), newDate ->
        {
            pagerAdapter.updateSelectedDate(newDate);
            //change the page to the selected date based on date of Monday
            int position = (int) ChronoUnit.WEEKS.between(
                    LocalDate.now().with(DayOfWeek.MONDAY),
                    newDate.with(DayOfWeek.MONDAY)
            ) + 1000;
            if (binding.weekVp2.getCurrentItem() != position) {
                binding.weekVp2.setCurrentItem(position, false);
            }
            //change the textView
            String month = newDate.getMonth().toString();
            binding.textView.setText(month);
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}