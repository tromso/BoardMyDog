package com.tromto.boardmydog;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by k on 2/19/15.
 */
public class AddDog extends Activity {
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adddog);

        Bundle extras = this.getIntent().getExtras();
        email = extras.getString("email");
        Toast.makeText(getApplicationContext(), email, Toast.LENGTH_LONG).show();

    }
}
