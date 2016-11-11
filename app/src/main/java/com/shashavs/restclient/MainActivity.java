package com.shashavs.restclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.shashavs.restclient.mrest.RestFactory;
import com.shashavs.restclient.mrest.RestService;
import com.shashavs.restclient.mrest.mResponseClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private RestService service;

    private EditText login, password;
    private EditText name, age;
    private TextView answer;
    private CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);

        name = (EditText) findViewById(R.id.param);
        age = (EditText) findViewById(R.id.age);
        answer = (TextView) findViewById(R.id.answer); //get answer

        checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    checkBox.setText("JSON");
                } else {
                    checkBox.setText("STRING");
                }
                age.setEnabled(b);
            }
        });

        Button ok = (Button) findViewById(R.id.ok); //send data from form (POST)
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT);
                } else {
                    service = RestFactory.getRestService(false);
                    Call<String> call = service.methodPost(login.getText().toString(), password.getText().toString());
                    call.enqueue(callbackPost);
                }
            }
        });

        //---------------------------------------------------
        Button getData = (Button) findViewById(R.id.get); //get data with param (GET)
        getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                service = RestFactory.getRestService(checkBox.isChecked());

                if(checkBox.isChecked()) {
                    if(age.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Enter age!", Toast.LENGTH_SHORT);
                    } else {
                        Call<mResponseClient> call = service.methodGetJSON(name.getText().toString(), Integer.valueOf(age.getText().toString()));
                        call.enqueue(callbackGet);
                    }

                } else {
                    Call<String> call = service.methodGet(name.getText().toString());
                    call.enqueue(callbackGet);
                }
            }
        });
    }

    private Callback callbackPost = new Callback() {

        @Override
        public void onResponse(Call call, Response response) {
//            Log.d(TAG, "onResponse call " + call.request());
            String answerText = (String) response.body();
            answer.setText(answerText);
            Log.d(TAG, "onResponse Post call.request()" + call.request());
            Log.d(TAG, "onResponse Post response" + answerText);
        }

        @Override
        public void onFailure(Call call, Throwable t) {
            Log.d(TAG, "onFailure Post " + t);
        }
    };

    private Callback callbackGet = new Callback() {
        @Override
        public void onResponse(Call call, Response response) {
//            Log.d(TAG, "onResponse call " + call.request());
            String answerText;

            if(checkBox.isChecked()) {
                mResponseClient responseClient = (mResponseClient)response.body();
                answerText = responseClient.getName() + "/ age = " + responseClient.getAge();

            } else {
                answerText = (String) response.body();
            }

            answer.setText(answerText);
            Log.d(TAG, answerText);
        }

        @Override
        public void onFailure(Call call, Throwable t) {
            Log.d(TAG, "onFailure call.request()" + call.request());
            answer.setText("onFailure");
            Log.d(TAG, "onFailure Get " + t);
        }
    };
}
