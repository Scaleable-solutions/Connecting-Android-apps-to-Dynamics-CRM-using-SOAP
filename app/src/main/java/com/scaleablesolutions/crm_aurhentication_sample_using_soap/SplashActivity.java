package com.scaleablesolutions.crm_aurhentication_sample_using_soap;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        WaitTask waitTask = new WaitTask();
        waitTask.execute();
    }

    private class WaitTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
        }
    }
}
