package com.tromto.boardmydog;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by k on 2/18/15.
 */
public class YouFragment extends Fragment implements View.OnClickListener {
    String email, name, message, senderemail, receiveremail, datesent, filename, daycareaddress, daycaredescription, daycarefilename;
    String daycarename = "";
    UserFunctions userFunctions;
    TextView textView1, textView2, textView3, textView4;

    private static final String getdogsurl = "http://smileowl.com/Boardmydog/getdadogs.php";
    jParser parserget = new jParser();
    JSONArray jArray = null;
    JSONArray jArray2 = null;
    JSONArray jArray3 = null;
    JSONArray jArray4 = null;
    ArrayList<HashMap<String, String>> dogshashmap, eventmap, messagemap;
    Button button1, button2, button3, button4, button5;
    private static final String TAG = "MyActivity";
    private static final String TAG_SUCCESS = "success";
    int success;

    private ImageAdapter mAdapter;
    ListView lstView1;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.you, container, false);

        userFunctions = new UserFunctions();
        HashMap map = new HashMap();
        map = userFunctions.getdauser(getActivity());
        email = (String) map.get("email");
        name = (String) map.get("name");

        dogshashmap = new ArrayList<HashMap<String, String>>();
        eventmap = new ArrayList<HashMap<String, String>>();
        messagemap = new ArrayList<HashMap<String, String>>();

        new GetDaDogs().execute();

        textView1 = (TextView)rootView.findViewById(R.id.textView1);
        textView2 = (TextView)rootView.findViewById(R.id.textView2);
        textView3 = (TextView)rootView.findViewById(R.id.textView3);
        textView4 = (TextView)rootView.findViewById(R.id.textView4);
        textView1.setText("Email: " + email+"\n"+"Name: " + name);
        textView2.setVisibility(View.GONE);
        textView3.setVisibility(View.GONE);
       // textView4.setVisibility(View.GONE);



        button1 = (Button)rootView.findViewById(R.id.button1);
        button2 = (Button)rootView.findViewById(R.id.button2);
        button3 = (Button)rootView.findViewById(R.id.button3);
        button4 = (Button)rootView.findViewById(R.id.button4);
        button5 = (Button)rootView.findViewById(R.id.button5);
        button1.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
        button3.setVisibility(View.GONE);
        button4.setVisibility(View.GONE);
        button5.setVisibility(View.GONE);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);


        lstView1 = (ListView)rootView.findViewById(R.id.list2);


        return rootView;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button1:

                Intent i = new Intent(getActivity(), Addinfo.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);;
                i.putExtra("email", email);
                i.putExtra("name", name);

                startActivityForResult(i,100);
                break;
            case R.id.button2:

                Intent i2 = new Intent(getActivity(), AddDog.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);;
                i2.putExtra("email", email);
                startActivityForResult(i2,100);
                break;

            case R.id.button5:

                Intent i5 = new Intent(getActivity(), Event.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);;
                i5.putExtra("email", email);
                i5.putExtra("daycarename", daycarename);

                //Toast.makeText(getActivity(), daycarename, Toast.LENGTH_LONG).show();
                startActivityForResult(i5,100);
                break;
        }

    }
    class GetDaDogs extends AsyncTask<String, String, String>

    {

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
            JSONObject json = parserget.makeHttpRequest(getdogsurl, params);
            jArray = json.getJSONArray("smileowlTable");
            success = json.getInt(TAG_SUCCESS);

            if (success == 1) {


                jArray3 = json.getJSONArray("messages");
                for (int i = 0; i < jArray3.length(); i++) {



                    JSONObject c = jArray3.getJSONObject(i);
                    message = c.getString("message");
                    senderemail = c.getString("senderemail");
                    receiveremail = c.getString("receiveremail");
                    datesent = c.getString("datesent");
                    filename = c.getString("filename");


                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("message", message);
                    map.put("senderemail", senderemail);
                    map.put("receiveremail", receiveremail);
                    map.put("datesent", datesent);
                    map.put("filename", filename);

                    messagemap.add(map);
                }
                for (int i = 0; i < jArray.length(); i++) {


                    JSONObject c = jArray.getJSONObject(i);
                    String dogname = c.getString("dogname");
                    daycarename = c.getString("daycarename");


                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("dogname", dogname);
                    map.put("daycarename", daycarename);

                    dogshashmap.add(map);
                }
                if (!"0".equals(daycarename)) {
                    jArray2 = json.getJSONArray("eventTable");

                    for (int i = 0; i < jArray2.length(); i++) {


                        JSONObject c = jArray2.getJSONObject(i);
                        String startdate = c.getString("startdate");
                        String enddate = c.getString("enddate");
                        String email0 = c.getString("email");
                        String dog0 = c.getString("dog");

                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("dog0", dog0);
                        map.put("email0", email0);
                        map.put("startdate", startdate);
                        map.put("enddate", enddate);
                        eventmap.add(map);
                        //Log.v(TAG, "hello" + startdate+i);
                    }


                }
                jArray4 = json.getJSONArray("updatedaycaredetails");


                    JSONObject c = jArray4.getJSONObject(0);
                    daycareaddress = c.getString("daycareaddress");
                    daycaredescription = c.getString("daycaredescription");
                    daycarefilename = c.getString("daycarefilename");
                    Log.v(TAG, "hello" + daycareaddress + daycaredescription);

            }
        } catch(JSONException e) {

            e.printStackTrace();
        }
        return null;
        //
    }
    protected void onPostExecute(String zoom){

        getActivity().runOnUiThread(new Runnable() {
            public void run() {


                if (daycarename.equals("0") || daycarename.equals("")) {
                    //not owner
                    // Toast.makeText(getActivity(), daycarename +"you are not owner, just a mortal", Toast.LENGTH_LONG).show();

                    textView4.setText("Your dog: ");

                    button1.setVisibility(View.VISIBLE);
                    button2.setVisibility(View.VISIBLE);
                    textView2.setVisibility(View.VISIBLE);
                    //textView4.setVisibility(View.VISIBLE);



                } else {

                    //owner
                    //Toast.makeText(getActivity(),email +"is the admin of: " +daycarename , Toast.LENGTH_LONG).show();

                    textView4.setText("Latest reservations: ");
                    button3.setVisibility(View.VISIBLE);
                    textView3.setVisibility(View.VISIBLE);
                    button5.setVisibility(View.VISIBLE);

                    button3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Toast.makeText(getActivity(), daycareaddress + "addressf: " + daycaredescription, Toast.LENGTH_LONG).show();


                            Intent i3 = new Intent(getActivity(), Updatedaycare.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);;
                            i3.putExtra("email", email);
                            i3.putExtra("daycarename", daycarename);
                            i3.putExtra("daycareaddress", daycareaddress);
                            i3.putExtra("daycaredescription", daycaredescription);
                            i3.putExtra("daycarefilename", daycarefilename);
                            startActivityForResult(i3,100);


                        }
                    });
                }
                if (success == 1) {


                    for (int i = 0; i < dogshashmap.size(); i++) {
                        // System.out.println(dogshashmap.get(i));

                        textView2.append(dogshashmap.get(i).get("dogname") + "\n");

                    }
                    for (int i = 0; i < eventmap.size(); i++) {
                        // System.out.println(dogshashmap.get(i));

                        textView3.append(eventmap.get(i).get("dog0")+" From "+ eventmap.get(i).get("startdate")
                                + " to "+eventmap.get(i).get("enddate") + "\n");

                    }


                }

                    mAdapter = new ImageAdapter(getActivity(), messagemap);
                    lstView1.setAdapter(mAdapter);




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
            // imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //mImageFetcher.loadImage(movies.get(position).get("poster"),
            //imageView);

            if (movies.get(position).get("filename").length()>6){
                imageView.setVisibility(View.VISIBLE);
            }else{
                imageView.setVisibility(View.GONE);
            }
            Picasso.with(context).load("http://smileowl.com/Boardmydog/Uploads/Uploads/" + movies.get(position).get("filename")).into(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //Toast.makeText(getActivity(),  "evrika" + movies.get(position).get("filename"), Toast.LENGTH_LONG).show();

                    Intent i5 = new Intent(getActivity(), BigPicture.class);
                    i5.putExtra("filename",  movies.get(position).get("filename"));
                    startActivityForResult(i5,100);


                }
            });
            TextView txtPoster = (TextView) convertView.findViewById(R.id.textView2);
            //txtPoster.setPadding(10, 0, 0, 0);
            txtPoster.setText( "Sent by: " + movies.get(position).get("senderemail") + " on " + movies.get(position).get("datesent"));


            TextView txtGenre = (TextView) convertView.findViewById(R.id.textView4);
            //txtGenre.setPadding(10, 0, 0, 0);
            txtGenre.setText( movies.get(position).get("message"));



            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent i5 = new Intent(getActivity(), Messagethread.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i5.putExtra("email", email);
                    i5.putExtra("from", messagemap.get(position).get("senderemail"));
                    startActivityForResult(i5,100);

                }
            });


            return convertView;
        }
    }


}