package com.tromto.boardmydog;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by k on 2/19/15.
 */
public class Addinfo extends Activity {
    String email, name;
    EditText editText1, editText2, editText3, editText4, editText5, editText6;
    jParser2 parser = new jParser2();
    String phone, secphone, address, emergency, emergencyphone;
    private Button button1;
    private static String urlNew = "http://smileowl.com/Boardmydog/daycareuserdetails.php";

    private static final String getuserurl = "http://smileowl.com/Boardmydog/getdaycareuserdetails.php";
    jParser parserget = new jParser();
    JSONArray jArray = null;
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addinfo);

        Bundle extras = this.getIntent().getExtras();
        email = extras.getString("email");
        name = extras.getString("name");

        new Getuserdetails().execute();

        //Toast.makeText(getApplicationContext(), email, Toast.LENGTH_LONG).show();
        editText1 = (EditText)findViewById(R.id.editText1);
        editText2 = (EditText)findViewById(R.id.editText2);
        editText3 = (EditText)findViewById(R.id.editText3);
        editText4 = (EditText)findViewById(R.id.editText4);
        editText5 = (EditText)findViewById(R.id.editText5);
        editText6 = (EditText)findViewById(R.id.editText6);

        editText6.setText(name);

        button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AddDaUserinfo().execute();
            }
        });

    }
    class AddDaUserinfo extends AsyncTask<String, String, String> {

        String phone = editText1.getText().toString();
        String secphone= editText2.getText().toString();
        String address = editText3.getText().toString();
        String emergency = editText4.getText().toString();
        String emergencyphone = editText5.getText().toString();



        @Override
        protected String doInBackground(String... args) {


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("phone", phone));
            params.add(new BasicNameValuePair("secphone", secphone));
            params.add(new BasicNameValuePair("address", address));
            params.add(new BasicNameValuePair("emergency", emergency));
            params.add(new BasicNameValuePair("emergencyphone", emergencyphone));

            @SuppressWarnings("unused")
            JSONObject json = parser.makeHttpRequest(urlNew, params);


            return null;
        }

        protected void onPostExecute(String zoom) {
            runOnUiThread(new Runnable() {
                public void run() {
                    finish();
                }
            });
        }
    }


    class Getuserdetails extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected String doInBackground(String... args) {

            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("email", email));

                @SuppressWarnings("unused")
                JSONObject json = parserget.makeHttpRequest(getuserurl, params);


                jArray = json.getJSONArray("smileowlTable");


                    JSONObject c = (JSONObject) jArray.get(0);


                    name = c.getString("name");
                    phone = c.getString("phone");
                    secphone = c.getString("secphone");
                    address = c.getString("address");
                    emergency = c.getString("emergency");
                    emergencyphone = c.getString("emergencyphone");
                    Log.v(TAG,address);




            } catch (JSONException e) {

                e.printStackTrace();
            }
            return null;
            //
        }

        protected void onPostExecute(String zoom) {

            Addinfo.this.runOnUiThread(new Runnable() {
                public void run() {

                    editText6.setText(name);
                    editText1.setText(phone);
                    editText2.setText(secphone);
                    editText3.setText(address);
                    editText4.setText(emergency);
                    editText5.setText(emergencyphone);


                }
            });
        }
    }
}
