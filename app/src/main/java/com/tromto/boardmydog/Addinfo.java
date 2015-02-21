package com.tromto.boardmydog;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by k on 2/19/15.
 */
public class Addinfo extends Activity {
    String email;
    EditText editText1, editText2, editText3, editText4, editText5;
    jParser2 parser = new jParser2();
    String phone, secphone, address, emergency, emergencyphone;
    private Button button1;
    private static String urlNew = "http://smileowl.com/Boardmydog/daycareuserdetails.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addinfo);

        Bundle extras = this.getIntent().getExtras();
        email = extras.getString("email");
        //Toast.makeText(getApplicationContext(), email, Toast.LENGTH_LONG).show();
        editText1 = (EditText)findViewById(R.id.editText1);
        editText2 = (EditText)findViewById(R.id.editText2);
        editText3 = (EditText)findViewById(R.id.editText3);
        editText4 = (EditText)findViewById(R.id.editText4);
        editText5 = (EditText)findViewById(R.id.editText5);

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




                }
            });
        }
    }
}
