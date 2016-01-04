package com.scaleablesolutions.crm_aurhentication_sample_using_soap.CRMTask;

import android.content.Context;
import android.os.AsyncTask;

import com.scaleablesolutions.crm_aurhentication_sample_using_soap.Helper.LocalStorage;
import com.scaleablesolutions.crm_aurhentication_sample_using_soap.Helper.ServiceHandler;

/**
 * Created by ajku-ajku on 1/1/2016.
 */
public class WhoAmITask extends AsyncTask<Void,Void,String> {

    public interface WhoAmIResponse{
        void whoAmIProcessFinish(String id);
    }

    WhoAmIResponse delegate = null;
    LocalStorage localStorage;

    public WhoAmITask(WhoAmIResponse delegate, Context context) {
        this.delegate = delegate;
        this.localStorage = new LocalStorage(context);
    }

    @Override
    protected String doInBackground(Void... params) {
        ServiceHandler handler = new ServiceHandler();
        return handler.getUserId(localStorage.getOrganizationURL(), localStorage.getCRMAuthHeader());
    }

    @Override
    protected void onPostExecute(String s) {
        delegate.whoAmIProcessFinish(s);
    }
}
