package com.tromto.boardmydog;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by k on 2/19/15.
 */
public class AddDog extends Activity {
    String email;
    EditText editText1, editText2, editText3, editText4, editText5, editText6, editText7, editText8;
    jParser2 parser = new jParser2();
    String dogname, breed, weight, gender, age, neutered, sociable, other, vet, vetphone;
    private Button button1;
    private RadioGroup radioSexGroup;
    private RadioGroup radiospayed;
    private static String urlNew = "http://smileowl.com/Boardmydog/insertdogs.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adddog);

        Bundle extras = this.getIntent().getExtras();
        email = extras.getString("email");
        //Toast.makeText(getApplicationContext(), email, Toast.LENGTH_LONG).show();
        editText1 = (EditText)findViewById(R.id.editText1);
        editText2 = (EditText)findViewById(R.id.editText2);
        editText3 = (EditText)findViewById(R.id.editText3);
        editText4 = (EditText)findViewById(R.id.editText4);
        editText5 = (EditText)findViewById(R.id.editText5);
        editText6 = (EditText)findViewById(R.id.editText6);
        editText7 = (EditText)findViewById(R.id.editText7);
        editText8 = (EditText)findViewById(R.id.editText8);

        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        radiospayed = (RadioGroup) findViewById(R.id.radiospayed);

        radioSexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioMale:
                        // do operations specific to this selection
                        gender = "male";
                        break;

                    case R.id.radioFemale:
                        gender = "female";
                        break;



                }


            }
        });

        radiospayed.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioyes:
                        // do operations specific to this selection
                        neutered = "yes";
                        break;

                    case R.id.radiono:
                        neutered = "no";
                        break;

                }


            }
        });

        button1 = (Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dogname = editText1.getText().toString();
                if(dogname.length()>2){
                    new AddDaDog().execute();
                }else{
                    Toast.makeText(getApplicationContext(),"Fill in the fields",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    class AddDaDog extends AsyncTask<String, String, String> {

        String dogname = editText1.getText().toString();
        String breed= editText2.getText().toString();
        String weight = editText3.getText().toString();
        String age = editText4.getText().toString();
        String sociable = editText5.getText().toString();
        String other = editText6.getText().toString();
        String vet = editText7.getText().toString();
        String vetphone = editText8.getText().toString();


        @Override
        protected String doInBackground(String... args) {


            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("email", email));
            params.add(new BasicNameValuePair("dogname", dogname));
            params.add(new BasicNameValuePair("breed", breed));
            params.add(new BasicNameValuePair("weight", weight));
            params.add(new BasicNameValuePair("age", age));
            params.add(new BasicNameValuePair("sociable", sociable));
            params.add(new BasicNameValuePair("other", other));
            params.add(new BasicNameValuePair("gender", gender));
            params.add(new BasicNameValuePair("neutered", neutered));
            params.add(new BasicNameValuePair("vet", vet));
            params.add(new BasicNameValuePair("vetphone", vetphone));


            @SuppressWarnings("unused")
            JSONObject json = parser.makeHttpRequest(urlNew, params);


            return null;
        }

        protected void onPostExecute(String zoom) {


            runOnUiThread(new Runnable() {
                public void run() {


                    Intent i = new Intent(AddDog.this, Dogtoprofile.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("email", email);
                    i.putExtra("dogname", dogname);
                    startActivityForResult(i,100);

                   // Toast t = Toast.makeText(getApplicationContext()," 00 "+ email + dogname + neutered + gender, Toast.LENGTH_LONG);
                    //t.show();


                }
            });
        }
    }
}
