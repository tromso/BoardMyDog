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
    String email, name, message, senderemail, receiveremail, datesent, filename, daycareaddress,
            daycaredescription, daycarefilename, dogfilename;
    String daycarename = "";
    UserFunctions userFunctions;
    TextView textView2, textView3, textView4, textView6, textView7, textView8, textView9;

    private static final String getdogsurl = "http://smileowl.com/Boardmydog/getdadogs.php";
    jParser parserget = new jParser();
    JSONArray jArray = null;
    JSONArray jArray2 = null;
    JSONArray jArray3 = null;
    JSONArray jArray4 = null;
    ArrayList<HashMap<String, String>> dogshashmap, eventmap, messagemap;
    Button button1, button2, button3, button5;
    private static final String TAG = "MyActivity";
    private static final String TAG_SUCCESS = "success";
    int success;

    private ImageAdapter mAdapter;
    ListView lstView1;


    ImageView imageView2, imageView3, imageView4, imageView5;

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

       // textView2 = (TextView)rootView.findViewById(R.id.textView2);
        textView3 = (TextView)rootView.findViewById(R.id.textView3);
        textView4 = (TextView)rootView.findViewById(R.id.textView4);
        textView6 = (TextView)rootView.findViewById(R.id.textView6);
        textView7 = (TextView)rootView.findViewById(R.id.textView7);
        textView8 = (TextView)rootView.findViewById(R.id.textView8);
        textView9 = (TextView)rootView.findViewById(R.id.textView9);
        textView3.setVisibility(View.GONE);
       // textView4.setVisibility(View.GONE);



        button1 = (Button)rootView.findViewById(R.id.button1);
        button2 = (Button)rootView.findViewById(R.id.button2);
        button3 = (Button)rootView.findViewById(R.id.button3);
        button5 = (Button)rootView.findViewById(R.id.button5);
        button1.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);
        button3.setVisibility(View.GONE);
        button5.setVisibility(View.GONE);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button5.setOnClickListener(this);

        imageView2 = (ImageView)rootView.findViewById(R.id.image1);
        imageView3 = (ImageView)rootView.findViewById(R.id.image2);
        imageView4 = (ImageView)rootView.findViewById(R.id.image3);
        imageView5 = (ImageView)rootView.findViewById(R.id.image4);


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
                    dogfilename = c.getString("filename");
                    String breed = c.getString("breed");
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
                    map.put("daycarename", daycarename);
                    map.put("dogfilename", dogfilename);
                    map.put("breed", breed);
                    map.put("weight", weight);
                    map.put("gender", gender);
                    map.put("age", age);
                    map.put("neutered", neutered);
                    map.put("sociable", sociable);
                    map.put("other", other);
                    map.put("vet", vet);
                    map.put("vetphone", vetphone);


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
                    button1.setVisibility(View.VISIBLE);
                    button2.setVisibility(View.VISIBLE);


                } else {
                    //owner
                    textView4.setText("Latest reservations: ");
                    button3.setVisibility(View.VISIBLE);
                    textView3.setVisibility(View.VISIBLE);
                    button5.setVisibility(View.VISIBLE);

                    for (int i = 0; i < eventmap.size(); i++) {

                        textView3.append(eventmap.get(i).get("dog0")+" From "+ eventmap.get(i).get("startdate")
                                + " to "+eventmap.get(i).get("enddate") + "\n");

                    }

                    button3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            Intent i3 = new Intent(getActivity(), Updatedaycare.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i3.putExtra("email", email);
                            i3.putExtra("daycarename", daycarename);
                            i3.putExtra("daycareaddress", daycareaddress);
                            i3.putExtra("daycaredescription", daycaredescription);
                            i3.putExtra("daycarefilename", daycarefilename);
                            startActivityForResult(i3,100);


                        }
                    });
                }
                if (success == 1 && daycarename.equals("0") || daycarename.equals("")) {


                    for (int i = 0; i < dogshashmap.size(); i++) {


                        if (dogshashmap.get(i).get("dogname").length()>1){
                            imageView2.setVisibility(View.VISIBLE);
                            imageView3.setVisibility(View.VISIBLE);
                            imageView4.setVisibility(View.VISIBLE);
                            imageView5.setVisibility(View.VISIBLE);
                            textView6.setVisibility(View.VISIBLE);
                            textView7.setVisibility(View.VISIBLE);
                            textView8.setVisibility(View.VISIBLE);
                            textView9.setVisibility(View.VISIBLE);


                            if (i==0 ) {
                                imageView2.getLayoutParams().height = 120;
                                imageView2.getLayoutParams().width = 120;
                                imageView2.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                textView6.setText(dogshashmap.get(0).get("dogname"));
                                Picasso.with(getActivity()).load("http://smileowl.com/Boardmydog/Dogprofilepicture/Uploads/" + dogshashmap.get(0).get("dogfilename")).into(imageView2);

                                imageView2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        //Toast.makeText(getActivity(),  "evrika" + movies.get(position).get("filename"), Toast.LENGTH_LONG).show();

                                        Intent i5 = new Intent(getActivity(), Updatedog.class);
                                        i5.putExtra("filename",  dogshashmap.get(0).get("dogfilename"));
                                        i5.putExtra("dogname",  dogshashmap.get(0).get("dogname"));
                                        i5.putExtra("breed",  dogshashmap.get(0).get("breed"));
                                        i5.putExtra("weight",  dogshashmap.get(0).get("weight"));
                                        i5.putExtra("gender",  dogshashmap.get(0).get("gender"));
                                        i5.putExtra("age",  dogshashmap.get(0).get("age"));
                                        i5.putExtra("neutered",  dogshashmap.get(0).get("neutered"));
                                        i5.putExtra("sociable",  dogshashmap.get(0).get("sociable"));
                                        i5.putExtra("other",  dogshashmap.get(0).get("other"));
                                        i5.putExtra("vet",  dogshashmap.get(0).get("vet"));
                                        i5.putExtra("vetphone",  dogshashmap.get(0).get("vetphone"));
                                        i5.putExtra("email",  email);

                                        startActivityForResult(i5,100);


                                    }
                                });
                            }
                            if (i==1) {
                                imageView3.getLayoutParams().height = 120;
                                imageView3.getLayoutParams().width = 120;
                                imageView3.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                textView7.setText(dogshashmap.get(1).get("dogname"));
                                Picasso.with(getActivity()).load("http://smileowl.com/Boardmydog/Dogprofilepicture/Uploads/" + dogshashmap.get(1).get("dogfilename")).into(imageView3);

                                imageView3.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        //Toast.makeText(getActivity(),  "evrika" + movies.get(position).get("filename"), Toast.LENGTH_LONG).show();

                                        Intent i5 = new Intent(getActivity(), Updatedog.class);
                                        i5.putExtra("filename",  dogshashmap.get(1).get("dogfilename"));
                                        i5.putExtra("dogname",  dogshashmap.get(1).get("dogname"));
                                        i5.putExtra("breed",  dogshashmap.get(1).get("breed"));
                                        i5.putExtra("weight",  dogshashmap.get(1).get("weight"));
                                        i5.putExtra("gender",  dogshashmap.get(1).get("gender"));
                                        i5.putExtra("age",  dogshashmap.get(1).get("age"));
                                        i5.putExtra("neutered",  dogshashmap.get(1).get("neutered"));
                                        i5.putExtra("sociable",  dogshashmap.get(1).get("sociable"));
                                        i5.putExtra("other",  dogshashmap.get(1).get("other"));
                                        i5.putExtra("vet",  dogshashmap.get(1).get("vet"));
                                        i5.putExtra("vetphone",  dogshashmap.get(1).get("vetphone"));
                                        i5.putExtra("email",  email);

                                        startActivityForResult(i5,100);


                                    }
                                });
                            }
                            if (i==2) {
                                imageView4.getLayoutParams().height = 120;
                                imageView4.getLayoutParams().width = 120;
                                imageView4.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                textView8.setText(dogshashmap.get(2).get("dogname"));
                                Picasso.with(getActivity()).load("http://smileowl.com/Boardmydog/Dogprofilepicture/Uploads/" + dogshashmap.get(2).get("dogfilename")).into(imageView4);

                                imageView4.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        //Toast.makeText(getActivity(),  "evrika" + movies.get(position).get("filename"), Toast.LENGTH_LONG).show();

                                        Intent i5 = new Intent(getActivity(), Updatedog.class);
                                        i5.putExtra("filename",  dogshashmap.get(2).get("dogfilename"));
                                        i5.putExtra("dogname",  dogshashmap.get(2).get("dogname"));
                                        i5.putExtra("breed",  dogshashmap.get(2).get("breed"));
                                        i5.putExtra("weight",  dogshashmap.get(2).get("weight"));
                                        i5.putExtra("gender",  dogshashmap.get(2).get("gender"));
                                        i5.putExtra("age",  dogshashmap.get(2).get("age"));
                                        i5.putExtra("neutered",  dogshashmap.get(2).get("neutered"));
                                        i5.putExtra("sociable",  dogshashmap.get(2).get("sociable"));
                                        i5.putExtra("other",  dogshashmap.get(2).get("other"));
                                        i5.putExtra("vet",  dogshashmap.get(2).get("vet"));
                                        i5.putExtra("vetphone",  dogshashmap.get(2).get("vetphone"));
                                        i5.putExtra("email",  email);

                                        startActivityForResult(i5,100);


                                    }
                                });
                            }
                            if (i==3) {
                                imageView5.getLayoutParams().height = 120;
                                imageView5.getLayoutParams().width = 120;
                                imageView5.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                textView9.setText(dogshashmap.get(3).get("dogname"));
                                Picasso.with(getActivity()).load("http://smileowl.com/Boardmydog/Dogprofilepicture/Uploads/" + dogshashmap.get(3).get("dogfilename")).into(imageView5);

                                imageView5.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        //Toast.makeText(getActivity(),  "evrika" + movies.get(position).get("filename"), Toast.LENGTH_LONG).show();

                                        Intent i5 = new Intent(getActivity(), Updatedog.class);
                                        i5.putExtra("filename",  dogshashmap.get(3).get("dogfilename"));
                                        i5.putExtra("dogname",  dogshashmap.get(3).get("dogname"));
                                        i5.putExtra("breed",  dogshashmap.get(3).get("breed"));
                                        i5.putExtra("weight",  dogshashmap.get(3).get("weight"));
                                        i5.putExtra("gender",  dogshashmap.get(3).get("gender"));
                                        i5.putExtra("age",  dogshashmap.get(3).get("age"));
                                        i5.putExtra("neutered",  dogshashmap.get(3).get("neutered"));
                                        i5.putExtra("sociable",  dogshashmap.get(3).get("sociable"));
                                        i5.putExtra("other",  dogshashmap.get(3).get("other"));
                                        i5.putExtra("vet",  dogshashmap.get(3).get("vet"));
                                        i5.putExtra("vetphone",  dogshashmap.get(3).get("vetphone"));
                                        i5.putExtra("email",  email);

                                        startActivityForResult(i5,100);


                                    }
                                });
                            }

                        }else{
                            imageView2.setVisibility(View.GONE);
                            imageView3.setVisibility(View.GONE);
                            imageView4.setVisibility(View.GONE);
                            imageView5.setVisibility(View.GONE);
                        }

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


            if (movies.get(position).get("filename").length()>6){
                imageView.setVisibility(View.VISIBLE);
            }else{
                imageView.setVisibility(View.GONE);
            }
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
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


            TextView messagedisplayed = (TextView) convertView.findViewById(R.id.textView4);
            if (movies.get(position).get("message")!="New picture"){
                messagedisplayed.setVisibility(View.VISIBLE);
                messagedisplayed.setText( movies.get(position).get("message"));
            }else{
                messagedisplayed.setVisibility(View.GONE);
            }



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