package com.scaleablesolutions.crm_aurhentication_sample_using_soap.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Date;

/**
 * Created by ajku-ajku on 1/1/2016.
 */
public class LocalStorage {

    SharedPreferences prefs;

    public LocalStorage(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void saveOrganizationURL(String url){
        prefs.edit().putString("org_url", url).apply();
    }

    public String getOrganizationURL(){
        return prefs.getString("org_url", "");
    }

    public void saveCRMAuthHeader(String authHeader){
        prefs.edit().putString("soap_header", authHeader).apply();
    }

    public String getCRMAuthHeader(){
        return prefs.getString("soap_header", "");
    }

    public void saveExpireTime(Date d){
        prefs.edit().putString("expire_time", d.toString()).apply();
    }

    public String getExpireTime(){
        return prefs.getString("expire_time", "");
    }
}
