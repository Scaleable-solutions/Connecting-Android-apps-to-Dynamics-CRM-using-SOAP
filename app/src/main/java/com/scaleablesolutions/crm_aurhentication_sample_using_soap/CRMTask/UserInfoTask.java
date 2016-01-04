package com.scaleablesolutions.crm_aurhentication_sample_using_soap.CRMTask;

import android.content.Context;
import android.os.AsyncTask;

import com.scaleablesolutions.crm_aurhentication_sample_using_soap.Helper.LocalStorage;
import com.scaleablesolutions.crm_aurhentication_sample_using_soap.Helper.ServiceHandler;

/**
 * Created by ajku-ajku on 1/1/2016.
 */
public class UserInfoTask extends AsyncTask<String,Void,String> {

    public interface UserInfoResponse{
        void userInfoProcessFinish(String fullName);
    }

    UserInfoResponse delegate = null;
    LocalStorage localStorage;

    public UserInfoTask(UserInfoResponse delegate,Context context) {
        this.delegate = delegate;
        this.localStorage = new LocalStorage(context);
    }

    @Override
    protected String doInBackground(String... params) {
        ServiceHandler handler = new ServiceHandler();
        return handler.getUserInfo(localStorage.getOrganizationURL(), localStorage.getCRMAuthHeader(), params[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        delegate.userInfoProcessFinish(s);
    }
}
