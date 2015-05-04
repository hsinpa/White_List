package com.hsin.whitelist.Phone;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.hsin.whitelist.R;

/**
 * Created by hsinpaul on 2015/4/30.
 */
public class TopBarSwitch extends Fragment {
    private Switch switchButton;
    private SharedPreferences pref;

    public TopBarSwitch() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.topbar_switch, container, false);
        pref = getActivity().getSharedPreferences("save",0);
        ContactData contactData = new ContactData(getActivity());
        contactData.getContact();

        switchButton = (Switch) v.findViewById(R.id.appSwitchEnable);
        switchButton.setChecked(pref.getBoolean("appIsEnable", false));
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pref.edit().putBoolean("appIsEnable", isChecked).apply();
            }
        });
        return v;
    }
}
