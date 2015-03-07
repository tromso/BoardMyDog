package com.tromto.boardmydog;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by k on 12/1/14.
 */
public class DogsitterFragment extends Fragment  {
//implements MainActivity.TabClickedListener2
    TextView textView1;

   // private DaycareFragment.TabClickedListener listener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dogsitter, container, false);

        //Bundle extras = getActivity().getIntent().getExtras();
        //String shit = extras.getString("newtext");

        textView1 = (TextView)rootView.findViewById(R.id.textView1);
        //textView1.setText(shit);

       // Intent i2 = new Intent(getActivity(), DemoActivity.class);
        //startActivityForResult(i2,100);

        return rootView;
    }
    /*
    public void setTextChangeListener(DaycareFragment.TabClickedListener listener) {
        this.listener = listener;
    }


    @Override
    public void passParam2(String var2) {
        textView1.setText(var2);
    }

    */
}
