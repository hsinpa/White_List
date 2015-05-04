package com.hsin.whitelist.Phone;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.hsin.whitelist.MainActivity;

import org.json.JSONArray;

/**
 * Created by hsinpaul on 2015/4/29.
 */
public class ContactData {
    Context mContext;

    public ContactData(Context context) {
        mContext = context;
    }

    public void getContact() {
        JSONArray phoneList = new JSONArray();
        ContentResolver cr = mContext.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    //Query phone here.  Covered next
                    if (Integer.parseInt(cur.getString(
                            cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                                new String[]{id}, null);
                        int phoneId = pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);

                        while (pCur.moveToNext()) {
                            // Do something with phones
                            //Log.d("Hsin", pCur.getString(phoneId) + ", " + name);
                            phoneList.put(pCur.getString(phoneId));
                        }
                        MainActivity.mPref.edit().putString("contactList", phoneList.toString()).apply();
                        pCur.close();
                    }

                }
            }
        }
    }

}
