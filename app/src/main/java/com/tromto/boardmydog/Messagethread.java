package com.tromto.boardmydog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by k on 3/13/15.
 */
public class Messagethread extends Activity {

    String email, from, message, senderemail, receiveremail, datesent, filename;
    jParser parserget = new jParser();
    JSONArray jArray3 = null;
    jParser2 parser = new jParser2();
    ArrayList<HashMap<String, String>> messagemap;
    private static final String getthread = "http://smileowl.com/Boardmydog/getmessagethread.php";
    private static String smileowlurl = "http://smileowl.com/Boardmydog/send_messagetouser.php";
    private static final String TAG_SUCCESS = "success";
    int success;
    private ProgressDialog pDialog;
    EditText edittext1;

    private Button mTakePhoto, button2;
    String butt;

    private ImageAdapter mAdapter;
    ListView lstView1;

    UserFunctions userFunctions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagethread);

        lstView1 = (ListView)findViewById(R.id.list2);


        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        from = intent.getStringExtra("from");
        TextView tv1 = (TextView)findViewById(R.id.textView1);
        tv1.setText("Send message to "+from);

        TextView tv2 = (TextView)findViewById(R.id.textView2);
        tv2.setText("Message history with "+from);

        messagemap = new ArrayList<HashMap<String, String>>();

        new GetDaThread().execute();

        mTakePhoto = (Button) findViewById(R.id.button2);
        button2 = (Button) findViewById(R.id.button3);

        Button message = (Button) findViewById(R.id.button1);
        edittext1 = (EditText)findViewById(R.id.edittext1);

        userFunctions = new UserFunctions();

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

        mTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent i = new Intent(getApplicationContext(), Takepic.class);
                    i.putExtra("email", email);
                    i.putExtra("from", from);
                    butt = "1";
                    i.putExtra("butt", butt);
                    startActivity(i);

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent i = new Intent(getApplicationContext(), Takepic.class);
                    i.putExtra("email", email);
                    i.putExtra("from", from);
                    butt = "2";
                    i.putExtra("butt", butt);
                    startActivity(i);

            }
        });

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
        switch(item.getItemId()){
            case R.id.action_settings:
                userFunctions.logoutUser(getApplicationContext());
                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(login);
                //Log.v("ding","dong");
                //Toast.makeText(getApplicationContext(), "A your account", Toast.LENGTH_LONG);
                return true;
            case R.id.action_deleteaccount:
                //Toast.makeText(getApplicationContext(), "Deleted your account", Toast.LENGTH_LONG);


                new AlertDialog.Builder(this)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete your account? This deletes all " +
                                "the information about the dogs and your messages.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                Intent i = new Intent(getApplicationContext(), Deletedaycareuser.class);

                                i.putExtra("email", email);
                                startActivity(i);
                                //Log.v("fuck","cock");
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    class GetDaThread extends AsyncTask<String, String, String>

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
                params.add(new BasicNameValuePair("from", from));

                @SuppressWarnings("unused")
                JSONObject json = parserget.makeHttpRequest(getthread, params);
                success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                   // Log.v("Debig", "hello" + success);


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



                }
            } catch(JSONException e) {

                e.printStackTrace();
            }
            return null;
            //
        }
        protected void onPostExecute(String zoom){

            Messagethread.this.runOnUiThread(new Runnable() {
                public void run() {


                    if (success == 1) {


                        mAdapter = new ImageAdapter(getApplicationContext(), messagemap);
                        lstView1.setAdapter(mAdapter);

                        /*
                        ListAdapter adapter = new SimpleAdapter(Messagethread.this, messagemap,
                                R.layout.list, new String[]{"senderemail", "message", "datesent"},
                                new int[]{R.id.textView1, R.id.textView2, R.id.textView3});

                        //Toast.makeText(getActivity(), daycarename, Toast.LENGTH_LONG).show();

                        setListAdapter(adapter);
                        ListView lv = getListView();

                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position,
                                                    long id) {


                            }

                        });
                        */


                    }
                }
            });
        }

    }
    class AddDaMessage extends AsyncTask<String, String, String> {



        String mess = edittext1.getText().toString();
        //String msg = ms +"\n"+ getResources().getString(R.string.sentby)+ " " +yourusername + " " + getResources().getString(R.string.ap) + " "+yourap;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Messagethread.this);
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
                params.add(new BasicNameValuePair("from", from));
                params.add(new BasicNameValuePair("email", email));


                @SuppressWarnings("unused")
                JSONObject json = parser.makeHttpRequest(smileowlurl, params);
            }

            return null;
        }

        protected void onPostExecute(String zoom) {
            pDialog.dismiss();

            if (mess.length()>1) {
                Messagethread.this.runOnUiThread(new Runnable() {
                    public void run() {

                        Toast.makeText(getApplicationContext(), "message sent", Toast.LENGTH_LONG).show();
                        finish();


                    }
                });
            }else{

                Toast.makeText(getApplicationContext(), "You didn't write anything", Toast.LENGTH_SHORT).show();
                finish();
            }
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
                convertView = inflater.inflate(R.layout.threadadaptor, null);
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
/*
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //Toast.makeText(getActivity(),  "evrika" + movies.get(position).get("filename"), Toast.LENGTH_LONG).show();

                    Intent i5 = new Intent(getApplicationContext(), BigPicture.class);
                    i5.putExtra("filename",  movies.get(position).get("filename"));
                    startActivityForResult(i5,100);


                }
            });
            */
            DateFormat inputFormatter1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            inputFormatter1.setTimeZone(TimeZone.getTimeZone("PST"));
            Date date1 = null;
            try {
                date1 = inputFormatter1.parse(movies.get(position).get("datesent"));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            DateFormat outputFormatter1 = new SimpleDateFormat("hh:mm a MMM d yyyy");
            String output1 = outputFormatter1.format(date1);

            TextView txtPoster = (TextView) convertView.findViewById(R.id.textView2);
            //txtPoster.setPadding(10, 0, 0, 0);
            txtPoster.setText( "Sent by: " + movies.get(position).get("senderemail") + " on " + output1);


            TextView txtGenre = (TextView) convertView.findViewById(R.id.textView4);
            //txtGenre.setPadding(10, 0, 0, 0);
            txtGenre.setText( movies.get(position).get("message"));

            if (movies.get(position).get("filename").length()>6) {
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent i5 = new Intent(getApplicationContext(), BigPicture.class);
                        i5.putExtra("filename", movies.get(position).get("filename"));
                        startActivityForResult(i5, 100);

                    }
                });
            }

            return convertView;
        }
    }
}
