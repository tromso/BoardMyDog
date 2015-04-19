package com.tromto.boardmydog;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Doguserdetail extends Activity {

    String email, dogname;
    jParser parserget = new jParser();
    private static final String doguserurl = "http://smileowl.com/Boardmydog/getdoguserdetails.php";
    JSONArray jArray = null;
    ArrayList<HashMap<String, String>> dogusermap;
    TextView tv0, tv1, tv2, tv3, tv4, tv5, tv6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doguserdetail);


        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        dogname = intent.getStringExtra("dogname");
        dogusermap = new ArrayList<HashMap<String, String>>();

        //Toast.makeText(this, "daycare is " + daycarename, Toast.LENGTH_LONG).show();

        new GetDaDoguser().execute();

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

    class GetDaDoguser extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected String doInBackground(String... args) {

            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("email", email));
                params.add(new BasicNameValuePair("dogname", dogname));

                @SuppressWarnings("unused")
                JSONObject json = parserget.makeHttpRequest(doguserurl, params);
                jArray = json.getJSONArray("details");

                for (int i = 0; i < jArray.length(); i++) {

                    JSONObject c = (JSONObject) jArray.get(i);


                    String name = c.getString("name");
                    String phone = c.getString("phone");
                    String secphone = c.getString("secphone");
                    String address = c.getString("address");
                    String breed = c.getString("breed");
                    String emergency = c.getString("emergency");
                    String emergencyphone = c.getString("emergencyphone");

                    String weight = c.getString("weight");
                    String gender = c.getString("gender");
                    String age = c.getString("age");
                    String neutered = c.getString("neutered");
                    String sociable = c.getString("sociable");
                    String other = c.getString("other");
                    String vet = c.getString("vet");
                    String vetphone = c.getString("vetphone");
                    String filename = c.getString("filename");

                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put("dogname", dogname);
                    map.put("email", email);
                    map.put("name", name);
                    map.put("phone", phone);
                    map.put("secphone", secphone);
                    map.put("address", address);
                    map.put("emergency", emergency);
                    map.put("emergencyphone", emergencyphone);


                    map.put("breed", breed);
                    map.put("weight", weight);
                    map.put("gender", gender);
                    map.put("age", age);
                    map.put("neutered", neutered);
                    map.put("sociable", sociable);
                    map.put("other", other);
                    map.put("vet", vet);
                    map.put("vetphone", vetphone);
                    map.put("filename", filename);
                    dogusermap.add(map);


                }

            } catch (JSONException e) {

                e.printStackTrace();
            }
            return null;
            //
        }

        protected void onPostExecute(String zoom) {

            Doguserdetail.this.runOnUiThread(new Runnable() {
                public void run() {

                    ImageView imageView = (ImageView) findViewById(R.id.image1);
                    Picasso.with(getApplicationContext()).load("http://smileowl.com/Boardmydog/Dogprofilepicture/Uploads/" + dogusermap.get(0).get("filename")).into(imageView);
                    //final String xp = "http://smileowl.com/Boardmydog/Dogprofilepicture/Uploads/" + dogusermap.get(0).get("filename");
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            //Toast.makeText(getActivity(),  "evrika" + movies.get(position).get("filename"), Toast.LENGTH_LONG).show();

                            Intent i5 = new Intent(getApplicationContext(), BigPicture.class);
                            i5.putExtra("filenamedog",  dogusermap.get(0).get("filename"));
                            startActivityForResult(i5,100);


                        }
                    });

                    tv0 = (TextView) findViewById(R.id.textView0);
                    tv1 = (TextView) findViewById(R.id.textView1);
                    tv2 = (TextView) findViewById(R.id.textView2);
                    tv3 = (TextView) findViewById(R.id.textView3);
                    tv4 = (TextView) findViewById(R.id.textView4);
                    tv5 = (TextView) findViewById(R.id.textView5);
                    tv6 = (TextView) findViewById(R.id.textView6);

                    tv0.setText("Dog: " + dogusermap.get(0).get("dogname"));
                    tv1.setText("Breed: " + dogusermap.get(0).get("breed"));
                    tv2.setText("Gender: " + dogusermap.get(0).get("gender") +"\n" + " Age: " + dogusermap.get(0).get("age") +"\n" + " Weight: " + dogusermap.get(0).get("weight"));
                    tv3.setText("Neutered: " + dogusermap.get(0).get("neutered") +"\n" + " Sociable: " +
                            dogusermap.get(0).get("sociable") +"\n" + " Veterinarian: " + dogusermap.get(0).get("vet") +"\n" + " Veterinarian phone: " + dogusermap.get(0).get("vetphone"));

                    tv4.setText("Other dog info: " + dogusermap.get(0).get("other"));

                    tv5.setText("Owner: " + dogusermap.get(0).get("name") + "\n" +" Address: " +
                            dogusermap.get(0).get("address") + "\n" +" Email: " + dogusermap.get(0).get("email"));
                    tv6.setText("Owner phone: " + dogusermap.get(0).get("phone") + "\n" + " Owner phone 2: " +
                            dogusermap.get(0).get("secphone") + "\n" +" Emergency contact: " + dogusermap.get(0).get("emergency") + "\n" +" Emergency contact phone: " + dogusermap.get(0).get("emergencyphone"));


                }
            });
        }
    }

    }
