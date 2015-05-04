package com.hsin.whitelist.Phone;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;

import org.json.JSONArray;

import java.lang.reflect.Method;

/**
 * Created by hsinpaul on 2015/4/26.
 */
public class PhoneCallStateListener extends PhoneStateListener{

    private Context context;
    private SharedPreferences mPref;
    public PhoneCallStateListener(Context context){
        this.context = context;
        mPref = context.getSharedPreferences("save", 0);
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {

        switch (state) {

            case TelephonyManager.CALL_STATE_RINGING:
                if (mPref.getBoolean("appIsEnable", false)) {
                    AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                    //Turn ON the mute
                    audioManager.setStreamMute(AudioManager.STREAM_RING, true);
                    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    try {
                        JSONArray phoneList = new JSONArray(mPref.getString("contactList", "[]"));
                        int jsonLength = phoneList.length();
                        boolean pickThePhone = false;
                        Class clazz = Class.forName(telephonyManager.getClass().getName());
                        Method method = clazz.getDeclaredMethod("getITelephony");
                        method.setAccessible(true);
                        ITelephony telephonyService = (ITelephony) method.invoke(telephonyManager);

                        for (int i = 0; i < jsonLength; i++) {
                            if (incomingNumber == phoneList.get(i)) {
                                pickThePhone = true;
                            }
                        }

                        if (pickThePhone)
                            telephonyService.endCall();
                    } catch (Exception e) {
                        Log.d("HsiN", e.getMessage());
                    }

                    //Turn OFF the mute
                    audioManager.setStreamMute(AudioManager.STREAM_RING, false);
                }
                break;
            case PhoneStateListener.LISTEN_CALL_STATE:

        }
        super.onCallStateChanged(state, incomingNumber);
    }
}
