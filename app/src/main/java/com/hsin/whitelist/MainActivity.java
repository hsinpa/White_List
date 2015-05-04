package com.hsin.whitelist;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.hsin.whitelist.Phone.TopBarSwitch;


public class MainActivity extends FragmentActivity {
    public static SharedPreferences mPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_holder);
        mPref = getSharedPreferences("save",0);

//        Intent intent = new Intent(this, PhoneBackgroundReceiver.class);
//        startService(intent);

        MainActivityFragment frag = new MainActivityFragment();
        addTopbar();
        changeFragment(frag);
    }



    public void addTopbar() {
        TopBarSwitch frag = new TopBarSwitch();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.topbar_holder, frag, "MainFragment");
        transaction.commit();
    }

    public void changeFragment(Fragment fragment) {
        // Create new fragment and transaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.main_fragment_holder, fragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    @Override
    public void onBackPressed(){
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        }
    }

}
