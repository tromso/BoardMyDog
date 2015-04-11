package com.tromto.boardmydog;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by k on 2/19/15.
 */
public class BigPicture extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bigpicture);

        Bundle extras = this.getIntent().getExtras();
        String filename = extras.getString("filename");

        ImageView imageView = (ImageView)findViewById(R.id.imageView1);
        Picasso.with(getApplicationContext()).load("http://smileowl.com/Boardmydog/Uploads/Uploads/" + filename).into(imageView);
    }
}
