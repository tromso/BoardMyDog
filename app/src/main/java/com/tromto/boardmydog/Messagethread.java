package com.tromto.boardmydog;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

/**
 * Created by k on 3/13/15.
 */
public class Messagethread extends ListActivity{

    String email, from, message, senderemail, receiveremail, datesent;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messagethread);



        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        from = intent.getStringExtra("from");

        messagemap = new ArrayList<HashMap<String, String>>();

        new GetDaThread().execute();

        Button message = (Button) findViewById(R.id.button1);
        edittext1 = (EditText)findViewById(R.id.edittext1);
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
                    Log.v("Debig", "hello" + success);


                    jArray3 = json.getJSONArray("messages");
                    for (int i = 0; i < jArray3.length(); i++) {



                        JSONObject c = jArray3.getJSONObject(i);
                        message = c.getString("message");
                        senderemail = c.getString("senderemail");
                        receiveremail = c.getString("receiveremail");
                        datesent = c.getString("datesent");


                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("message", message);
                        map.put("senderemail", senderemail);
                        map.put("receiveremail", receiveremail);
                        map.put("datesent", datesent);

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

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("message", mess));
            params.add(new BasicNameValuePair("from", from));
            params.add(new BasicNameValuePair("email", email));


            @SuppressWarnings("unused")
            JSONObject json = parser.makeHttpRequest(smileowlurl, params);

            return null;
        }

        protected void onPostExecute(String zoom) {
            pDialog.dismiss();

            Messagethread.this.runOnUiThread(new Runnable() {
                public void run() {

                    Toast.makeText(getApplicationContext(), "message sent", Toast.LENGTH_LONG).show();
                    finish();


                }
            });
        }
    }
}
