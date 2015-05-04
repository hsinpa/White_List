package com.hsin.whitelist;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TimePicker;

/**
 * Created by hsinpaul on 2015/5/2.
 */
public class ClockCtrlFragment extends Fragment {
    private String[] weekSet = {"mo", "tu", "we", "th", "fr", "sa", "su"};
    private SharedPreferences pref;
    private TimePicker startTime;
    private TimePicker endTime;
    private Button saveButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.clock_ctrl, container, false);
        pref = getActivity().getSharedPreferences("save",0);
        startTime = (TimePicker)v.findViewById(R.id.timeStart);
        endTime = (TimePicker)v.findViewById(R.id.timeEnd);
        startTime.setIs24HourView(true);
        endTime.setIs24HourView(true);
        saveButton = (Button)v.findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref.edit().
                        putInt("startTimeMin", startTime.getCurrentMinute()).
                        putInt("startTimeHour", startTime.getCurrentHour()).
                        putInt("endTimeMin", endTime.getCurrentMinute()).
                        putInt("endTimeHour", endTime.getCurrentHour()).
                        apply();
               getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        loadClockSaveRecord();
        registerWeekClickEvent(v);
        return v;
    }

    private void registerWeekClickEvent(View v) {
        for ( final String week:weekSet) {
            int resId = getResources().getIdentifier(week + "_button", "id", getActivity().getPackageName());
            final ImageView weekButton = (ImageView) v.findViewById(resId);
            boolean isCheck = pref.getBoolean(week+"weekDateEnable", false);

            String uri = (isCheck) ? "drawable/"+week+"_on" : "drawable/"+week+"_off";
            int imageResource = getResources().getIdentifier(uri, null, getActivity().getPackageName());
            Drawable image = getResources().getDrawable(imageResource);
            changeWeekIcon(image, weekButton);


            //Click Event
            weekButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isCheck = pref.getBoolean(week+"weekDateEnable", false);
                    String uri = (isCheck) ? "drawable/"+week+"_off" : "drawable/"+week+"_on";
                    int imageResource = getResources().getIdentifier(uri, null, getActivity().getPackageName());
                    Drawable image = getResources().getDrawable(imageResource);
                    changeWeekIcon(image, weekButton);
                    pref.edit().putBoolean(week+"weekDateEnable", !isCheck).apply();
                }
            });
        }
    }

    private void changeWeekIcon(Drawable image, ImageView imageView) {
        imageView.setImageDrawable(image);
    }

    private void loadClockSaveRecord() {
        startTime.setCurrentHour(pref.getInt("startTimeHour", 0));
        startTime.setCurrentMinute(pref.getInt("startTimeMin", 0));
        endTime.setCurrentHour(pref.getInt("endTimeHour", 0));
        endTime.setCurrentMinute(pref.getInt("endTimeMin", 0));
    }
}
