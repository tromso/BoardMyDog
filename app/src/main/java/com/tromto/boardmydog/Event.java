package com.tromto.boardmydog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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


public class Event extends Activity {

    String email, daycarename;
    jParser parserget = new jParser();
    private static final String reservationsurl = "http://smileowl.com/Boardmydog/getdareservations.php";
    JSONArray jArray = null;
    ArrayList<HashMap<String, String>> eventmap;

    private ImageAdapter mAdapter;
    ListView lstView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);



        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        daycarename = intent.getStringExtra("daycarename");
        eventmap = new ArrayList<HashMap<String, String>>();

        lstView1 = (ListView)findViewById(R.id.list2);

        //Toast.makeText(this, "daycare is " + daycarename, Toast.LENGTH_LONG).show();

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
                    String filename = c.getString("filename");
                    String breed = c.getString("breed");

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("dog", dog0);
                    map.put("email0", email0);
                    map.put("startdate", startdate);
                    map.put("enddate", enddate);
                    map.put("filename", filename);
                    map.put("breed", breed);
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


                    mAdapter = new ImageAdapter(Event.this, eventmap);
                    lstView1.setAdapter(mAdapter);

                    /*
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

                    */




                }
            });
        }

    }
    public class ImageAdapter extends BaseAdapter {

        private Context context;
        private ArrayList<HashMap<String, String>> movies = new ArrayList<HashMap<String, String>>();

        public ImageAdapter(Context c, ArrayList<HashMap<String, String>> list){
            context = c;
            movies = list;

        }

        @Override
        public int getCount() {
            return movies.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null){
                convertView = inflater.inflate(R.layout.reservations, null);
            }
            //colimage
            ImageView imageView = (ImageView) convertView.findViewById(R.id.image1);
            //imageView.getLayoutParams().height = 100;
            //imageView.getLayoutParams().width = 100;
            // imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //mImageFetcher.loadImage(movies.get(position).get("poster"),
            //imageView);

            if (movies.get(position).get("filename").length()>6){
                imageView.setVisibility(View.VISIBLE);
            }else{
                imageView.setVisibility(View.GONE);
            }
            //imageView.getLayoutParams().height = 100;
            //imageView.getLayoutParams().width = 100;
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Picasso.with(context).load("http://smileowl.com/Boardmydog/Dogprofilepicture/Uploads/" + movies.get(position).get("filename")).into(imageView);

            TextView txtPoster = (TextView) convertView.findViewById(R.id.textView2);
            //txtPoster.setPadding(10, 0, 0, 0);
            txtPoster.setText( "");


            TextView txtGenre = (TextView) convertView.findViewById(R.id.textView4);
            //txtGenre.setPadding(10, 0, 0, 0);
            txtGenre.setText( movies.get(position).get("dog") +" " + movies.get(position).get("breed") +
            "\n"+ "From " +movies.get(position).get("startdate") + " to " + movies.get(position).get("enddate"));



            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent i5 = new Intent(getApplicationContext(), Doguserdetail.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);;
                    i5.putExtra("email", eventmap.get(position).get("email0"));
                    i5.putExtra("dogname", eventmap.get(position).get("dog"));
                    startActivityForResult(i5,100);

                }
            });


            return convertView;
        }
    }
}
