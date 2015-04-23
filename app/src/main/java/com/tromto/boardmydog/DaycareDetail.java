package com.tromto.boardmydog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by k on 2/3/15.
 */
public class DaycareDetail extends Activity implements AdapterView.OnItemSelectedListener {


    private Button  button1;
    private static String urlNew = "http://smileowl.com/Boardmydog/reservation.php";
    private static String smileowlurl = "http://smileowl.com/Boardmydog/send_message.php";
    private static final String getdogsurl = "http://smileowl.com/Boardmydog/getdadogs.php";
    EditText edittext1, edittext2;

    private int year;
    private int month;
    private int day;
    jParser2 parser = new jParser2();
    String startdate, enddate;


    static final int DATE_DIALOG_ID_1 = 999;
    static final int DATE_DIALOG_ID_2 = 998;

    private TextView checkin, checkout, f1, f2;
    private Button checkinbutton, checkoutbutton, message;
    private ProgressDialog pDialog;

    UserFunctions userFunctions;
    String daycarename, address, dog, email, daycareadminemail;

    jParser parserget = new jParser();
    JSONArray jArray = null;
    ArrayList<HashMap<String, String>> dogshashmap;

    private Spinner spin;
    private ArrayList<Category> categoriesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daycaredetail);

        Bundle extras = this.getIntent().getExtras();
        daycarename = extras.getString("name");
        address = extras.getString("address");
        daycareadminemail = extras.getString("daycareadminemail");

        userFunctions = new UserFunctions();
        HashMap map = new HashMap();
        map = userFunctions.getdauser(getApplicationContext());
        email = (String) map.get("email");

        TextView tv1 = (TextView)findViewById(R.id.textview1);
        tv1.setText("Send message to " + daycarename);

        dogshashmap = new ArrayList<HashMap<String, String>>();

        new GetDaDogs().execute();

        checkin = (TextView) findViewById(R.id.tvDate);
        checkout = (TextView) findViewById(R.id.tvDate2);
        f1 = (TextView) findViewById(R.id.f1);
        f2 = (TextView) findViewById(R.id.f2);
        checkinbutton = (Button) findViewById(R.id.btnChangeDate);
        checkoutbutton = (Button) findViewById(R.id.btnChangeDate2);
        message = (Button) findViewById(R.id.message);
        edittext2 = (EditText)findViewById(R.id.edittext2);


        // Get current date by calender

        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);

        // Show current date

        checkin.setText(new StringBuilder().append(year)
                .append("-").append(month+1).append("-").append(day)
                .append(" "));

        checkout.setText(new StringBuilder().append(year)
                .append("-").append(month+1).append("-").append(day)
                .append(" "));

        f1.setText(new StringBuilder().append(month+1)
                .append("/").append(day).append("/").append(year)
                .append(" "));

        f2.setText(new StringBuilder().append(month+1)
                .append("/").append(day).append("/").append(year)
                .append(" "));

        // Button listener to show date picker dialog

        checkinbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // On button click show datepicker dialog
                showDialog(DATE_DIALOG_ID_1);

            }

        });
        checkoutbutton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // On button click show datepicker dialog
                showDialog(DATE_DIALOG_ID_2);

            }

        });




        spin = (Spinner) findViewById(R.id.spin);

        categoriesList = new ArrayList<Category>();
        spin.setOnItemSelectedListener(this);
        //dog = spin.getSelectedItem().toString();

        button1 = (Button)findViewById(R.id.button1);
        edittext1 = (EditText)findViewById(R.id.editText1);




        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startdate = checkin.getText().toString();
                enddate = checkout.getText().toString();

                new AddDaReservation().execute();
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            private boolean handledClick = false;
            @Override
            public void onClick(View view) {

                if (!handledClick) {
                    handledClick = true;

                    new AddDaMessage().execute();
                }
            }
        });


    }
    class AddDaReservation extends AsyncTask<String, String, String> {


        //String owner = edittext1.getText().toString();
        String numberdays = "69";



        @Override
        protected String doInBackground(String... args) {


                List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("daycare", daycarename));
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("dog", dog));
            params.add(new BasicNameValuePair("startdate", startdate));
            params.add(new BasicNameValuePair("enddate", enddate));
            params.add(new BasicNameValuePair("numberdays", numberdays));

                @SuppressWarnings("unused")
                JSONObject json = parser.makeHttpRequest(urlNew, params);


            return null;
        }

        protected void onPostExecute(String zoom) {


            runOnUiThread(new Runnable() {
                public void run() {

                    Toast t = Toast.makeText(getApplicationContext(), "The reservation was added", Toast.LENGTH_LONG);
                    t.show();
                }
            });
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID_1:

                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener, year, month,day);
            case DATE_DIALOG_ID_2:

                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener2, year, month,day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;

            // Show selected date
            checkin.setText(new StringBuilder().append(year)
                    .append("-").append(month+1).append("-").append(day)
                    .append(" "));
            f1.setText(new StringBuilder().append(month+1)
                    .append("/").append(day).append("/").append(year)
                    .append(" "));



        }
    };
    private DatePickerDialog.OnDateSetListener pickerListener2 = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;

            // Show selected date
            checkout.setText(new StringBuilder().append(year)
                    .append("-").append(month+1).append("-").append(day)
                    .append(" "));

            f2.setText(new StringBuilder().append(month+1)
                    .append("/").append(day).append("/").append(year)
                    .append(" "));

        }
    };


    class AddDaMessage extends AsyncTask<String, String, String> {


        String mess = edittext2.getText().toString();
        //String msg = ms +"\n"+ getResources().getString(R.string.sentby)+ " " +yourusername + " " + getResources().getString(R.string.ap) + " "+yourap;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(DaycareDetail.this);
            pDialog.setMessage("Loading. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        @Override
        protected String doInBackground(String... args) {

            if (mess.length()>1) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("message", mess));
                params.add(new BasicNameValuePair("daycareadminemail", daycareadminemail));
                params.add(new BasicNameValuePair("senderemail", email));


                @SuppressWarnings("unused")
                JSONObject json = parser.makeHttpRequest(smileowlurl, params);

            }
            return null;
        }

        protected void onPostExecute(String zoom) {
            pDialog.dismiss();

            if (mess.length()>1) {
                DaycareDetail.this.runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(getApplicationContext(), "Message sent", Toast.LENGTH_LONG).show();
                        finish();


                    }
                });
            }else{
                Toast.makeText(getApplicationContext(), "You didn't write anything", Toast.LENGTH_LONG).show();

            }
        }
    }
    class GetDaDogs extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected String doInBackground(String... args) {

            try {

                List<NameValuePair> params = new ArrayList<NameValuePair> ();
                params.add(new BasicNameValuePair("email", email));

                @SuppressWarnings("unused")
                JSONObject json = parserget.makeHttpRequest(getdogsurl, params);
                jArray = json.getJSONArray("smileowlTable");

                for (int i =0; i<jArray.length();i++){

                    JSONObject catObj = (JSONObject) jArray.get(i);
                    Category cat = new Category(i,
                            catObj.getString("dogname"));
                    categoriesList.add(cat);

                }

            } catch(JSONException e) {

                e.printStackTrace();
            }
            return null;
            //
        }
        protected void onPostExecute(String zoom){

                    DaycareDetail.this.runOnUiThread(new Runnable() {
                        public void run() {

                                    populateSpinner();


                        }
                    });
        }

    }
    private void populateSpinner() {

        List<String> lables = new ArrayList<String>();

        for (int i = 0; i < categoriesList.size(); i++) {
            lables.add(categoriesList.get(i).getName());
        }

        // Creating adapter for spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        spinnerAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spin.setAdapter(spinnerAdapter);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        /*
        Toast.makeText(
                getApplicationContext(),
                parent.getItemAtPosition(position).toString() + " Selected" ,
                Toast.LENGTH_LONG).show();
                */
        dog = spin.getSelectedItem().toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

    public class Category {

        private int id;
        private String name;

        public Category(){}

        public Category(int id, String name){
            this.id = id;
            this.name = name;
        }

        public void setId(int id){
            this.id = id;
        }

        public void setName(String name){
            this.name = name;
        }

        public int getId(){
            return this.id;
        }

        public String getName(){
            return this.name;
        }

    }
}
