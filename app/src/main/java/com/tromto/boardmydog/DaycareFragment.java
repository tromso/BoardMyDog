package com.tromto.boardmydog;

import android.app.Activity;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
 * Created by k on 12/1/14.
 */
public class DaycareFragment extends ListFragment {

    //TabClickedListener listener;


    static EditText userNameEditText;
    static String username;

    public interface TabClickedListener {
        public void passParam(String var);
    }

    private TabClickedListener listener;

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        if (activity instanceof TabClickedListener) {
            //call the method in the parent activity (MainActivity.java)
            listener = (TabClickedListener)activity;
        } else {
            //if we forget to implement the interface, we get an error
            throw new ClassCastException(activity.toString() + "must implement loginFragInterface.");
        }
    }

    private ProgressDialog pDialog;
    private static final String getdaycare = "http://smileowl.com/Boardmydog/daycares.php";
    jParser parser = new jParser();
    JSONArray jArray = null;
    ArrayList<HashMap<String, String>> movies;

    String email;
    UserFunctions userFunctions;
    Boolean owner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_daycare, container, false);
        movies = new ArrayList<HashMap<String, String>>();

        userFunctions = new UserFunctions();
        HashMap map = new HashMap();
        map = userFunctions.getdauser(getActivity());
        email = (String) map.get("email");


        userNameEditText = (EditText)rootView.findViewById(R.id.fragment_name);

        Button button = (Button)rootView.findViewById(R.id.frag_button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                username = userNameEditText.getText().toString();
                listener.passParam(username);
            }
        });

        new GetDaDaycares().execute();

        //listener = TabClickedListener
        //listener.passParam(email);
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
                    String daycarename = c.getString("daycarename");
                    String address = c.getString("address");
                    String mail = c.getString("email");

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("daycarename", daycarename);
                    map.put("address", address);
                    map.put("mail", mail);

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
                            R.layout.list, new String[] {"daycarename", "address", "mail"},
                            new int[]{R.id.textView1, R.id.textView2, R.id.textView3});

                    setListAdapter(adapter);
                    ListView lv = getListView();
                    lv.setOnItemClickListener(new OnItemClickListener(){

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position,
                                                long id) {





                            String daycarename =movies.get(position).get("daycarename");
                            String address = movies.get(position).get("address");
                            String mail = movies.get(position).get("mail");

                            listener.passParam(address);
                            if (mail.equals(email)){
                                Toast t = Toast.makeText(getActivity(), "You are the owner", Toast.LENGTH_LONG);
                                t.show();
                                owner = true;
                            }
                            else{
                                Toast t1 = Toast.makeText(getActivity(), mail+email, Toast.LENGTH_LONG);
                                t1.show();
                            }


                            Intent i2 = new Intent(getActivity(), DaycareDetail.class);

                            i2.putExtra("name", daycarename);
                            i2.putExtra("address", address);

                            startActivityForResult(i2,100);

                        }

                    });


                }
            });
        }

    }
/*
    public void setTextChangeListener(TabClickedListener listener) {
        this.listener = listener;
    }

    private static TabClickedListener listener = new TabClickedListener() {
        @Override
        public void passparam(String var) {
        }
    };
*/
}
