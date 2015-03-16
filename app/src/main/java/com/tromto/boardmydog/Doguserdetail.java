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
import android.widget.Toast;

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


    private ImageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doguserdetail);



        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        dogname = intent.getStringExtra("dogname");
        dogusermap = new ArrayList<HashMap<String, String>>();

        Toast.makeText(this, "email and dog " + email+ " dogname " +dogname, Toast.LENGTH_LONG).show();

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

                for (int i =0; i<jArray.length();i++){

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

                    HashMap<String, String> map = new HashMap<String, String>();

                    map.put("dogname", dogname);
                    map.put("email", email);
                    map.put("name", name);
                    map.put("phone", phone);
                    map.put("secphone", secphone);
                    map.put("address", address);
                    map.put("emergency", emergency);
                    map.put("emergencyphone", emergencyphone);


                    map.put("weight", weight);
                    map.put("gender", gender);
                    map.put("age", age);
                    map.put("neutered", neutered);
                    map.put("sociable", sociable);
                    map.put("other", other);
                    map.put("vet", vet);
                    map.put("vetphone", vetphone);
                    dogusermap.add(map);


                }

            } catch(JSONException e) {

                e.printStackTrace();
            }
            return null;
            //
        }
        protected void onPostExecute(String zoom){

            Doguserdetail.this.runOnUiThread(new Runnable() {
                public void run() {



                    ListView lstView1 = (ListView)findViewById(R.id.list2);
                    mAdapter = new ImageAdapter(getApplicationContext(), dogusermap);

                    lstView1.setAdapter(mAdapter);


                     /*
                    ListAdapter mListView1 = new SimpleAdapter(getApplicationContext(), dogusermap,
                            R.layout.list, new String[]{"dogname","name","email"},
                            new int[]{R.id.textView1, R.id.textView2, R.id.textView3});

                    //setListAdapter(adapter);
                    mListView1.setAdapter(mListAdapter1);

                    ListView lv = getListView();
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position,
                                                long id) {

                            //Toast.makeText(getActivity(), messagemap.get(position).get("senderemail")+ "and receiver email is"+ email, Toast.LENGTH_LONG).show();


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
                convertView = inflater.inflate(R.layout.activity_column, null);
            }
            //colimage
            ImageView imageView = (ImageView) convertView.findViewById(R.id.image1);
            //imageView.getLayoutParams().height = 100;
            //imageView.getLayoutParams().width = 100;
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //mImageFetcher.loadImage(movies.get(position).get("poster"),
                    //imageView);



            //colposition
            TextView txtPid = (TextView) convertView.findViewById(R.id.textView1);
            //txtPid.setPadding(10, 0, 0, 0);
            txtPid.setText(movies.get(position).get("dogname"));

            TextView txtPoster = (TextView) convertView.findViewById(R.id.textView2);
            //txtPoster.setPadding(10, 0, 0, 0);
            txtPoster.setText( movies.get(position).get("breed"));

            TextView txtName = (TextView) convertView.findViewById(R.id.textView3);
            //txtName.setPadding(10, 0, 0, 0);
            txtName.setText( movies.get(position).get("gender") + " (" +movies.get(position).get("weight")+")" );

            TextView txtGenre = (TextView) convertView.findViewById(R.id.textView4);
            //txtGenre.setPadding(10, 0, 0, 0);
            txtGenre.setText( movies.get(position).get("age") + "\n" + movies.get(position).get("neutered") + "\n" + "\n" + movies.get(position).get("description"));

            TextView upvote = (TextView) convertView.findViewById(R.id.textView5);
            //txtPid.setPadding(10, 0, 0, 0);
            upvote.setText(movies.get(position).get("name"));
            TextView downvote = (TextView) convertView.findViewById(R.id.textView6);
            //txtPid.setPadding(10, 0, 0, 0);
            downvote.setText(movies.get(position).get("address"));







            return convertView;
        }
    }
}
