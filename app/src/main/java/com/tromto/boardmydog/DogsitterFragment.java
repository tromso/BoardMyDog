package com.tromto.boardmydog;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by k on 12/1/14.
 */
public class DogsitterFragment extends Fragment implements View.OnClickListener  {
//implements MainActivity.TabClickedListener2
    TextView textView1;
    Button button3;
    UserFunctions userFunctions;

   // private DaycareFragment.TabClickedListener listener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_dogsitter, container, false);

        //Bundle extras = getActivity().getIntent().getExtras();
        //String shit = extras.getString("newtext");

        textView1 = (TextView)rootView.findViewById(R.id.textView1);
        userFunctions = new UserFunctions();

        button3 = (Button)rootView.findViewById(R.id.button3);
        button3.setOnClickListener(this);

        return rootView;
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.button3:

                userFunctions.logoutUser(getActivity());
                Intent login = new Intent(getActivity(), LoginActivity.class);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(login);
                Toast t = Toast.makeText(getActivity(),"settings logout ", Toast.LENGTH_LONG);
                t.show();
                // Closing dashboard screen
                break;

        }

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
