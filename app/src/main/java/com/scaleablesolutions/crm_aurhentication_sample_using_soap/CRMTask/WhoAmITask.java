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
        return handler.getUserId(localStorage.getOrganizationURL(), localStorage.getCRMAuthHeader(),WhoAmIBody());
    }

    @Override
    protected void onPostExecute(String s) {
        delegate.whoAmIProcessFinish(s);
    }

    private String WhoAmIBody() {
        StringBuilder xml = new StringBuilder();
        xml.append("<s:Body>");
        xml.append("<Execute xmlns=\"http://schemas.microsoft.com/xrm/2011/Contracts/Services\">");
        xml.append("<request i:type=\"c:WhoAmIRequest\" xmlns:b=\"http://schemas.microsoft.com/xrm/2011/Contracts\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:c=\"http://schemas.microsoft.com/crm/2011/Contracts\">");
        xml.append("<b:Parameters xmlns:d=\"http://schemas.datacontract.org/2004/07/System.Collections.Generic\"/>");
        xml.append("<b:RequestId i:nil=\"true\"/>");
        xml.append("<b:RequestName>WhoAmI</b:RequestName>");
        xml.append("</request>");
        xml.append("</Execute>");
        xml.append("</s:Body>");
        return xml.toString();
    }
}
