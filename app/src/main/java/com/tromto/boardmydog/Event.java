package com.tromto.boardmydog;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Event extends ListActivity {

    String email, daycarename;
    jParser parserget = new jParser();
    private static final String reservationsurl = "http://smileowl.com/Boardmydog/getdareservations.php";
    JSONArray jArray = null;
    ArrayList<HashMap<String, String>> eventmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);



        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        daycarename = intent.getStringExtra("daycarename");
        eventmap = new ArrayList<HashMap<String, String>>();

        Toast.makeText(this, "daycare is " + daycarename, Toast.LENGTH_LONG).show();

        new GetDaReservations().execute();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class GetDaReservations extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected String doInBackground(String... args) {

            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("daycarename", daycarename));

                @SuppressWarnings("unused")
                JSONObject json = parserget.makeHttpRequest(reservationsurl, params);
                jArray = json.getJSONArray("reservations");

                for (int i =0; i<jArray.length();i++){

                    JSONObject c = (JSONObject) jArray.get(i);
                    String startdate = c.getString("startdate");
                    String enddate = c.getString("enddate");
                    String email0 = c.getString("email");
                    String dog0 = c.getString("dog");

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("dog", dog0);
                    map.put("email0", email0);
                    map.put("startdate", startdate);
                    map.put("enddate", enddate);
                    eventmap.add(map);


                }

            } catch(JSONException e) {

                e.printStackTrace();
            }
            return null;
            //
        }
        protected void onPostExecute(String zoom){

            Event.this.runOnUiThread(new Runnable() {
                public void run() {


                    ListAdapter adapter = new SimpleAdapter(getApplicationContext(), eventmap,
                            R.layout.list, new String[]{"dog","startdate","enddate"},
                            new int[]{R.id.textView1, R.id.textView2, R.id.textView3});

                    setListAdapter(adapter);
                    ListView lv = getListView();
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position,
                                                long id) {

                            //Toast.makeText(getActivity(), messagemap.get(position).get("senderemail")+ "and receiver email is"+ email, Toast.LENGTH_LONG).show();

                            Intent i5 = new Intent(getApplicationContext(), Doguserdetail.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);;
                            i5.putExtra("email", eventmap.get(position).get("email0"));
                            i5.putExtra("dogname", eventmap.get(position).get("dog"));
                            startActivityForResult(i5,100);

                        }

                    });




                }
            });
        }

    }
}
