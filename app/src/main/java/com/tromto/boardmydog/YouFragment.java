package com.tromto.boardmydog;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by k on 2/18/15.
 */
public class YouFragment extends Fragment implements View.OnClickListener {
    String email, name;
    UserFunctions userFunctions;
    TextView textView1;
    Button button1, button2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.you, container, false);

        userFunctions = new UserFunctions();
        HashMap map = new HashMap();
        map = userFunctions.getdauser(getActivity());
        email = (String) map.get("email");
        name = (String) map.get("name");


        textView1 = (TextView)rootView.findViewById(R.id.textView1);
        textView1.setText(email+"\n"+name);


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
}