package com.tromto.boardmydog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Dogtoprofile extends Activity {


    private Button button1, mTakePhoto, button2;
    String butt, email, dogname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogtoprofile);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        dogname = intent.getStringExtra("dogname");


        button1 = (Button) findViewById(R.id.button1);
        mTakePhoto = (Button) findViewById(R.id.button2);
        button2 = (Button) findViewById(R.id.button3);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();

            }
        });

        mTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), Takeprofilepic.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("email", email);
                i.putExtra("dogname", dogname);
                butt = "1";
                i.putExtra("butt", butt);
                startActivity(i);

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), Takeprofilepic.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.putExtra("email", email);
                i.putExtra("dogname", dogname);
                butt = "2";
                i.putExtra("butt", butt);
                startActivity(i);

            }
        });
    }



}
