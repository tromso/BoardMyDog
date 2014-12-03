package com.tromto.boardmydog;

import android.app.Fragment;
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
public class DaycareFragment extends Fragment {
    private ProgressDialog pDialog;
    private static final String smileowlurl = "http://smileowl.com/labloc/send_message.php";
    private static final String getappurl = "http://smileowl.com/labloc/getappartmentsbyflat.php";
    jParser parser3 = new JSONParser();
    jParser parser = new jParser();
    AlertDialogManager alert = new AlertDialogManager();
    JSONArray jArray = null;
    ArrayList<HashMap<String, String>> movies;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_daycare, container, false);
        Toast t = Toast.makeText(getActivity(),"The calculated value of your business is: ", Toast.LENGTH_LONG);
        t.show();
        return rootView;
    }
}
