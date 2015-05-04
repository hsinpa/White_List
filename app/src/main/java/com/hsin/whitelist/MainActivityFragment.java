package com.hsin.whitelist;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    CheckBox clockChechbox;
    LinearLayout clockPanel;
    SharedPreferences pref;
    ImageView clockSetting;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main, container, false);
        pref = getActivity().getSharedPreferences("save",0);
        clockChechbox = (CheckBox)v.findViewById(R.id.clockCheckbox);
        clockPanel = (LinearLayout)v.findViewById(R.id.clockPanel);
        clockSetting = (ImageView)v.findViewById(R.id.clockSetting);
        clockChechbox.setChecked( pref.getBoolean("clockEnable", false) );

        clockSettingClickEvent();
        clockCheckboxEvent(v);

        return v;
    }


    private void clockCheckboxEvent(View v) {
        clockPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCheck = (pref.getBoolean("clockEnable", false)) ? true : false;
                allowClock(isCheck);
            }
        });

        clockChechbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                allowClock ( isChecked );
            }
        });

    }

    private void clockSettingClickEvent() {
        clockSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).changeFragment(new ClockCtrlFragment());
            }
        });
    }

    private void allowClock(boolean isCheck) {
        pref.edit().putBoolean("clockEnable", isCheck).apply();
    }

    private void loadClockAllowPref() {
        
    }
}