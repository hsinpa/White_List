package com.hsin.whitelist.Phone;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;


/**
 * Created by hsinpaul on 2015/4/26.
 */
public class PhoneBackgroundReceiver extends Service{


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        try {
            StateListener phoneStateListener = new StateListener();
            TelephonyManager telephonymanager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
            telephonymanager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }catch(Exception e){

        }
    }

    class StateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch(state){
                case TelephonyManager.CALL_STATE_RINGING:
                    //Disconnect the call here...
                    try{
                        TelephonyManager manager = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
                        Class c = Class.forName(manager.getClass().getName());
                        Method m = c.getDeclaredMethod("getITelephony");
                        m.setAccessible(true);
                        ITelephony telephony = (ITelephony)m.invoke(manager);
                        telephony.endCall();
                    } catch(Exception e){
                        Log.d("", e.getMessage());
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
            }
        }
    };


    @Override
    public void onDestroy() {
    }
}