package com.scaleablesolutions.crm_aurhentication_sample_using_soap.CRMTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.scaleablesolutions.crm_aurhentication_sample_using_soap.CRM.CRMAuthHeader;
import com.scaleablesolutions.crm_aurhentication_sample_using_soap.Helper.ServiceHandler;

/**
 * Created by ajku-ajku on 1/1/2016.
 */
public class CRMAuthenticationTask extends AsyncTask<String, Void, CRMAuthHeader> {

    public interface CRMAuthResponse {
        void processFinish(CRMAuthHeader output);

        void credentialError(String error);
    }

    Context context;
    CRMAuthResponse delegate = null;

    public CRMAuthenticationTask(CRMAuthResponse delegate,Context context) {
        this.delegate = delegate;
        this.context = context;
    }

    @Override
    protected CRMAuthHeader doInBackground(String... params) {
        if (params.length != 0 && !params[0].contentEquals("") && !params[1].contentEquals("") && !params[2].contentEquals("")) {
            ServiceHandler serviceHandler = new ServiceHandler();
            return serviceHandler.getAuthHeader(params[0], params[1], params[2]);
        } else {
            return null;
        }
    }

    @Override
    protected void onPostExecute(CRMAuthHeader crmAuthHeader) {
        if (crmAuthHeader != null) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            prefs.edit().putString("soap_header", crmAuthHeader.getHeader()).apply();
            prefs.edit().putString("expire", crmAuthHeader.getExpire().toString()).apply();
            delegate.processFinish(crmAuthHeader);
        } else {
            delegate.credentialError("Please provide Credentials");
        }
    }
}
