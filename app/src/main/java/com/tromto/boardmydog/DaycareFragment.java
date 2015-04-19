package com.tromto.boardmydog;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
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

/**
 * Created by k on 12/1/14.
 */
public class DaycareFragment extends Fragment {

    private ProgressDialog pDialog;
    private static final String getdaycare = "http://smileowl.com/Boardmydog/daycares.php";
    jParser parser = new jParser();
    JSONArray jArray = null;
    ArrayList<HashMap<String, String>> movies;

    String email;
    UserFunctions userFunctions;
    private ImageAdapter mAdapter;
    ListView lstView1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_daycare, container, false);
        movies = new ArrayList<HashMap<String, String>>();

        userFunctions = new UserFunctions();
        HashMap map = new HashMap();
        map = userFunctions.getdauser(getActivity());
        email = (String) map.get("email");

        lstView1 = (ListView)rootView.findViewById(R.id.list2);

        new GetDaDaycares().execute();

        return rootView;
    }


    class GetDaDaycares extends AsyncTask<String, String, String> {

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
                    String description = c.getString("description");
                    String filename = c.getString("filename");

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("daycarename", daycarename);
                    map.put("address", address);
                    map.put("mail", mail);
                    map.put("description", description);
                    map.put("filename", filename);


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



                    mAdapter = new ImageAdapter(getActivity(), movies);
                    lstView1.setAdapter(mAdapter);


                    /*
                    ListAdapter adapter = new SimpleAdapter(getActivity(), movies,
                            R.layout.list, new String[] {"daycarename", "address", "mail"},
                            new int[]{R.id.textView1, R.id.textView2, R.id.textView3});


                    setListAdapter(adapter);
                    ListView lv = getListView();
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position,
                                                long id) {





                            String daycarename =movies.get(position).get("daycarename");
                            String address = movies.get(position).get("address");
                            String mail = movies.get(position).get("mail");

                           // listener.passParam(address);
                            if (mail.equals(email)){
                                //Toast t = Toast.makeText(getActivity(), "You are the owner", Toast.LENGTH_LONG);
                                //t.show();
                                owner = true;
                            }
                            else{
                              //  //Toast t1 = Toast.makeText(getActivity(), mail+email, Toast.LENGTH_LONG);
                               // t1.show();
                            }


                            Intent i2 = new Intent(getActivity(), DaycareDetail.class);

                            i2.putExtra("name", daycarename);
                            i2.putExtra("address", address);
                            i2.putExtra("daycareadminemail", mail);

                            startActivityForResult(i2,100);

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
                convertView = inflater.inflate(R.layout.activity_daycarelist, null);
            }
            ImageView imageView = (ImageView) convertView.findViewById(R.id.image1);

            if (movies.get(position).get("filename").length()>6){
                imageView.setVisibility(View.VISIBLE);
            }else{
                imageView.setVisibility(View.GONE);
            }
            Picasso.with(context).load("http://smileowl.com/Boardmydog/Daycareimg/" + movies.get(position).get("filename")).into(imageView);


            TextView txtPoster = (TextView) convertView.findViewById(R.id.textView2);
            txtPoster.setPadding(10, 0, 0, 0);
           txtPoster.setText( movies.get(position).get("address")+"\n"+movies.get(position).get("description"));


            TextView txtGenre = (TextView) convertView.findViewById(R.id.textView4);
            txtGenre.setText( movies.get(position).get("daycarename"));
            txtGenre.setPadding(10, 0, 0, 0);


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String daycarename =movies.get(position).get("daycarename");
                    String address = movies.get(position).get("address");
                    String mail = movies.get(position).get("mail");

                    Intent i2 = new Intent(getActivity(), DaycareDetail.class);

                    i2.putExtra("name", daycarename);
                    i2.putExtra("address", address);
                    i2.putExtra("daycareadminemail", mail);

                    startActivityForResult(i2,100);



                }
            });


            return convertView;
        }
    }

}
