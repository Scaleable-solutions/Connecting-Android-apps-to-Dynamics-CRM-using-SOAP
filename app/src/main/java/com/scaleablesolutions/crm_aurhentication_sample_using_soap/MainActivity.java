package com.scaleablesolutions.crm_aurhentication_sample_using_soap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.scaleablesolutions.crm_aurhentication_sample_using_soap.CRM.CRMAuthHeader;
import com.scaleablesolutions.crm_aurhentication_sample_using_soap.CRMTask.CRMAuthenticationTask;
import com.scaleablesolutions.crm_aurhentication_sample_using_soap.CRMTask.UserInfoTask;
import com.scaleablesolutions.crm_aurhentication_sample_using_soap.CRMTask.WhoAmITask;
import com.scaleablesolutions.crm_aurhentication_sample_using_soap.Helper.LocalStorage;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener, WhoAmITask.WhoAmIResponse, UserInfoTask.UserInfoResponse {

    EditText et_domain, et_username, et_password;
    Button btn_submit;
    LocalStorage storage;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        et_domain = (EditText) findViewById(R.id.et_domain);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);

        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(this);

        storage = new LocalStorage(getApplicationContext());
        relativeLayout = (RelativeLayout)findViewById(R.id.rl_progressbar);
    }

    private void showProgressBar(){
        et_domain.setEnabled(false);
        et_username.setEnabled(false);
        et_password.setEnabled(false);
        relativeLayout.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        et_domain.setEnabled(true);
        et_username.setEnabled(true);
        et_password.setEnabled(true);
        relativeLayout.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        showProgressBar();
        storage.saveOrganizationURL(et_domain.getText().toString());
        CRMAuthenticationTask task = new CRMAuthenticationTask(new CRMAuthenticationTask.CRMAuthResponse() {
            @Override
            public void processFinish(CRMAuthHeader output) {
                storage.saveCRMAuthHeader(output.getHeader());
                storage.saveExpireTime(output.getExpire());
                WhoAmITask whoAmITask = new WhoAmITask(MainActivity.this, getApplicationContext());
                whoAmITask.execute();
            }

            @Override
            public void credentialError(String error) {
                hideProgressBar();
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_LONG).show();
            }
        }, getApplicationContext());
        task.execute(et_domain.getText().toString(), et_username.getText().toString(), et_password.getText().toString());
    }

    @Override
    public void whoAmIProcessFinish(String id) {
        UserInfoTask userInfoTask = new UserInfoTask(this, getApplicationContext());
        userInfoTask.execute(id);
    }

    @Override
    public void userInfoProcessFinish(String fullName) {
        Intent intent = new Intent(this, NameActivity.class);
        intent.putExtra("fullname", fullName);
        startActivity(intent);
        hideProgressBar();
    }
}
