package com.tromto.boardmydog;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by k on 2/19/15.
 */
public class Updatedaycare extends Activity {
    String email, daycarename, daycareaddress, daycaredescription, daycarefilename;
    EditText editText1, editText2, editText3, editText4, editText5;
    jParser2 parser = new jParser2();
    private Button button1;
    private static String urlNew = "http://smileowl.com/Boardmydog/updatedaycare.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changedaycareinfo);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        daycarename = intent.getStringExtra("daycarename");
        daycareaddress = intent.getStringExtra("daycareaddress");
        daycaredescription = intent.getStringExtra("daycaredescription");
        daycarefilename = intent.getStringExtra("daycarefilename");


        editText1 = (EditText)findViewById(R.id.editText1);
        editText2 = (EditText)findViewById(R.id.editText2);
        editText3 = (EditText)findViewById(R.id.editText3);


        editText1.setText(daycarename);
        editText2.setText(daycareaddress);
        editText3.setText(daycaredescription);

        button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Changedaycareinfo().execute();
            }
        });

    }
    class Changedaycareinfo extends AsyncTask<String, String, String> {

        String daycareaddress = editText2.getText().toString();
        String daycaredescription = editText3.getText().toString();


        @Override
        protected String doInBackground(String... args) {


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("description", daycaredescription));
            params.add(new BasicNameValuePair("address", daycareaddress));


            @SuppressWarnings("unused")
            JSONObject json = parser.makeHttpRequest(urlNew, params);


            return null;
        }

        protected void onPostExecute(String zoom) {


            runOnUiThread(new Runnable() {
                public void run() {

                    Toast.makeText(getApplicationContext(), "Information was updated", Toast.LENGTH_LONG).show();
                    finish();



                }
            });
        }
    }
}
