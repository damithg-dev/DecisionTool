package com.damithamarasinghe.oeidecisiontool.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.damithamarasinghe.oeidecisiontool.Activites.Util.UsefullData;
import com.damithamarasinghe.oeidecisiontool.R;

//
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//
//import com.android.volley.RequestQueue;
//import com.android.volley.toolbox.Volley;
//
public class CreateAccountActivity extends AppCompatActivity {
    private EditText et_name;
    private EditText et_password;
    private EditText et_retype;
    private EditText et_batch;
    private EditText et_date;
    private EditText et_ID;
    private EditText et_email;
    private Button submit;
    private RequestQueue mRequestQueue;
    private UsefullData usefullData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        et_name = (EditText) findViewById(R.id.et_name);
        et_password = (EditText) findViewById(R.id.et_password);
        et_retype = (EditText) findViewById(R.id.et_retype);
        et_email = (EditText) findViewById(R.id.et_email);
        submit = (Button) findViewById(R.id.btn_submit);
        usefullData = new UsefullData(this);
        mRequestQueue = Volley.newRequestQueue(CreateAccountActivity.this);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmit();
            }
        });
    }

    public void onSubmit() {
        if (et_name.getText().toString().trim().isEmpty()
                || et_password.getText().toString().trim().isEmpty()
                || et_date.getText().toString().trim().isEmpty()
                || et_batch.getText().toString().trim().isEmpty()
                || et_ID.getText().toString().trim().isEmpty()
                || et_email.getText().toString().trim().isEmpty()) {
            usefullData.displayMessage("Please fill out all the fields");
        }
        else if(!et_retype.getText().toString().equals(et_password.getText().toString()) ){
            usefullData.displayMessage("Passwords do not match!");
        }
        else if (et_password.getText().toString().length()<8){
            usefullData.displayMessage("Password must at least contain 8 characters");
        }else if (usefullData.isValidMail(et_email.getText().toString())){
            usefullData.displayMessage("Password must at least contain 8 characters");
        }
    else {
            usefullData.displayMessage("Account was successfully created");
            Intent i = new Intent(CreateAccountActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }

}
}
