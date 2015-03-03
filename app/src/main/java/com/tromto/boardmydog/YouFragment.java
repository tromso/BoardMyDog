package com.tromto.boardmydog;

import android.app.ListFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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
public class YouFragment extends ListFragment implements View.OnClickListener {
    String email, name;
    UserFunctions userFunctions;
    TextView textView1;
    Button button1, button2;
    private static final String getdogsurl = "http://smileowl.com/Boardmydog/getdadogs.php";
    jParser parserget = new jParser();
    JSONArray jArray = null;
    ArrayList<HashMap<String, String>> dogshashmap;

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

        new GetDaDogs().execute();




        textView1 = (TextView)rootView.findViewById(R.id.textView1);
        textView1.setText("Email: " + email+"\n"+"Name: " + name);


        Button button1 = (Button)rootView.findViewById(R.id.button1);
        Button button2 = (Button)rootView.findViewById(R.id.button2);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button1:

                Intent i = new Intent(getActivity(), Addinfo.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);;
                i.putExtra("email", email);
                startActivityForResult(i,100);
                break;
            case R.id.button2:

                Intent i2 = new Intent(getActivity(), AddDog.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);;
                i2.putExtra("email", email);
                startActivityForResult(i2,100);
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

            for (int i =0; i<jArray.length();i++){


                JSONObject c = jArray.getJSONObject(i);
                String dogname = c.getString("dogname");

                HashMap<String, String> map = new HashMap<String, String>();
                map.put("dogname", dogname);
                dogshashmap.add(map);




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


                ListAdapter adapter = new SimpleAdapter(getActivity(), dogshashmap,
                        R.layout.list, new String[]{"dogname"},
                        new int[]{R.id.textView1});

                setListAdapter(adapter);
                ListView lv = getListView();
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position,
                                            long id) {


                    }

                });


            }
        });
    }

}

    public void updateTextValue(String newText) {
        textView1.setText(newText);
    }
}