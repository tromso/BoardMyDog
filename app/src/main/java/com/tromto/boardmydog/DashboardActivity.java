/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package com.tromto.boardmydog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

public class DashboardActivity extends Activity {

	UserFunctions userFunctions;
    private ProgressDialog pDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new CheckLogin().execute();

    }
    class CheckLogin extends AsyncTask<String, String, String> {
        String bool;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DashboardActivity.this);
            pDialog.setMessage("Loading. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        @Override
        protected String doInBackground(String... args) {

            userFunctions = new UserFunctions();
            if(userFunctions.isUserLoggedIn(getApplicationContext())){

                bool = "A";
            }else{
                bool = "B";
            }
            return bool;

        }

        protected void onPostExecute(String zoom){
            pDialog.dismiss();

            DashboardActivity.this.runOnUiThread(new Runnable() {
                public void run() {

                    if (bool == "A") {

                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);

                        finish();


                    }else {
                        // user is not logged in show login screen
                        Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(login);
                        // Closing dashboard screen
                        finish();

                    }


                }
            });
        }
    }

}