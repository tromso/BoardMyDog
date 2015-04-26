package com.tromto.boardmydog;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;



public class Deletedaycareuser extends Activity {

    String email;
    jParser parserget = new jParser();
    private static final String doguserurl = "http://smileowl.com/Boardmydog/deletedaycareuser.php";
    UserFunctions userFunctions;
    GoogleCloudMessaging gcm;


    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_doguserdetail);


        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        userFunctions = new UserFunctions();
        context = getApplicationContext();
        /*
        GoogleCloudMessaging gcm =
                GoogleCloudMessaging.getInstance (context);


        try {
            gcm.unregister();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
        new Deleteuser().execute();
        Log.v("execute", "this");

    }



    class Deleteuser extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected String doInBackground(String... args) {


                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("email", email));
                JSONObject json = parserget.makeHttpRequest(doguserurl, params);
            Log.v("hb","this?");
            userFunctions.logoutUser(getApplicationContext());

            if (VERSION_CODES.KITKAT <= VERSION.SDK_INT) {
                ((ActivityManager)context.getSystemService(ACTIVITY_SERVICE))
                        .clearApplicationUserData(); // note: it has a return value!
            } else {

                clearApplicationData();
            }

            Intent login2 = new Intent(getApplicationContext(), LoginActivity.class);
            login2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(login2);





            return null;

        }



    }
    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));

                }
            }
        }
    }
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }


    }
