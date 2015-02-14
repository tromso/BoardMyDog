package com.tromto.boardmydog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by k on 2/3/15.
 */
public class DaycareDetail extends Activity {


    String name, address;
    private Button  button1;
    private static String urlNew = "http://smileowl.com/Boardmydog/reservation.php";
    EditText edittext1, edittext2;

    private int year;
    private int month;
    private int day;
    jParser2 parser = new jParser2();
    String startdate, enddate;

    static final int DATE_DIALOG_ID_1 = 999;
    static final int DATE_DIALOG_ID_2 = 998;

    private TextView checkin, checkout;
    private Button checkinbutton, checkoutbutton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daycaredetail);

        Bundle extras = this.getIntent().getExtras();
        name = extras.getString("name");
        address = extras.getString("address");

        checkin = (TextView) findViewById(R.id.tvDate);
        checkout = (TextView) findViewById(R.id.tvDate2);
        checkinbutton = (Button) findViewById(R.id.btnChangeDate);
        checkoutbutton = (Button) findViewById(R.id.btnChangeDate2);

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


    }
    class AddDaReservation extends AsyncTask<String, String, String> {


        String owner = edittext1.getText().toString();
        String numberdays = "69";



        @Override
        protected String doInBackground(String... args) {


                List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("daycare", name));
            params.add(new BasicNameValuePair("owner", owner));
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

        }
    };

}
