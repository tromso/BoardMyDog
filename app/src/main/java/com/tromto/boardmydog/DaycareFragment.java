package com.tromto.boardmydog;

import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.app.ProgressDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tromto.boardmydog.jParser;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Created by k on 12/1/14.
 */
public class DaycareFragment extends ListFragment {
    private ProgressDialog pDialog;
    private static final String getdaycare = "http://smileowl.com/Boardmydog/daycares.php";
    jParser parser = new jParser();
    JSONArray jArray = null;
    ArrayList<HashMap<String, String>> movies;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_daycare, container, false);
        movies = new ArrayList<HashMap<String, String>>();

        new GetDaDaycares().execute();



        Toast t = Toast.makeText(getActivity(),"The calculated value of your business is: ", Toast.LENGTH_LONG);
        t.show();
        return rootView;
    }

    class GetDaDaycares extends AsyncTask<String, String, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }


        @Override
        protected String doInBackground(String... args) {

            String city = "orlando";
            try {

                List<NameValuePair> params = new ArrayList<NameValuePair> ();
                params.add(new BasicNameValuePair("city", city));

                @SuppressWarnings("unused")
                JSONObject json = parser.makeHttpRequest(getdaycare, params);
                jArray = json.getJSONArray("smileowlTable");

                for (int i =0; i<jArray.length();i++){

                    JSONObject c = jArray.getJSONObject(i);
                    String name = c.getString("name");
                    String address = c.getString("address");

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("name", name);
                    map.put("address", address);

                    movies.add(map);

                }

            } catch(JSONException e) {

                e.printStackTrace();
            }
            return null;
            //
        }

        protected void onPostExecute(String zoom){
            pDialog.dismiss();

            getActivity().runOnUiThread(new Runnable() {
                public void run() {


                    ListAdapter adapter = new SimpleAdapter(getActivity(), movies,
                            R.layout.list, new String[] {"name", "address"},
                            new int[]{R.id.textView1, R.id.textView2});

                    setListAdapter(adapter);
                    ListView lv = getListView();
                    lv.setOnItemClickListener(new OnItemClickListener(){

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position,
                                                long id) {





                            String name =movies.get(position).get("name");
                            String address = movies.get(position).get("address");



                            Intent i2 = new Intent(getActivity(), DaycareDetail.class);

                            i2.putExtra("name", name);
                            i2.putExtra("address", address);

                            startActivityForResult(i2,100);

                        }

                    });


                }
            });
        }

    }
}
