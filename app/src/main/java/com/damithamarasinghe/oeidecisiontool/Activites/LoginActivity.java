package com.damithamarasinghe.oeidecisiontool.Activites;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.damithamarasinghe.oeidecisiontool.Activites.Util.Constants;
import com.damithamarasinghe.oeidecisiontool.Activites.Util.UsefullData;
import com.damithamarasinghe.oeidecisiontool.R;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText et_uname;
    private EditText et_password;
    private Button btn_login;
    private TextView tv_createnew;
    private RequestQueue mRequestQueue;
    private UsefullData usefullData;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_uname = (EditText) findViewById(R.id.et_uname);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        tv_createnew = (TextView) findViewById(R.id.tv_createnew);
        usefullData = new UsefullData(this);

        mRequestQueue = Volley.newRequestQueue(LoginActivity.this);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButtonClicked(); }
        });
        tv_createnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(in);
            }
        });

    }

    public void loginButtonClicked() {

        //comment for dev purposr
        Intent in = new Intent(LoginActivity.this, SelectionActivity.class);
        startActivity(in);
        finish();

        if (et_uname.getText().toString().trim().isEmpty() || et_password.getText().toString().trim().isEmpty()) {
            usefullData.displayMessage("Please enter email and password");
        } else if(!usefullData.isValidMail(et_uname.getText().toString().trim())) {
            usefullData.displayMessage("Please enter a valid Email");
        }else {
            if (usefullData.isNetworkConnected()) {
                loginApi(et_uname.getText().toString(),et_password.getText().toString());
            } else {
                usefullData.displayMessage("No internet connection");
            }
        }
    }

    public void loginApi(String username,String password){
        final String uName = username;
        final String pWord = password;
        progressDialog = ProgressDialog.show(LoginActivity.this,null, "Please wait...");

        //String loginUrl = "http://uat.fxhello.com/api/v1/auth/login";
        String loginUrl = Constants.BASE_URL + Constants.LOGIN_API;
        StringRequest request = new StringRequest(Request.Method.POST, loginUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("ela",""+response);
                        try{


                        }catch (Exception e){

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ela",""+error);
                        progressDialog.dismiss();
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            usefullData.displayMessage("No internet connection");
                        } else if (error instanceof AuthFailureError) {
                            usefullData.displayMessage("Invalid username or password");
                        } else if (error instanceof ServerError) {
                            usefullData.displayMessage("Server error occurred");
                        } else if (error instanceof NetworkError) {
                            usefullData.displayMessage("Connection error occurred");
                        } else if (error instanceof ParseError) {
                            usefullData.displayMessage("Parse error occurred");
                        }else{
                            usefullData.displayMessage("Unknown error occurred");
                        }
                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Accept", "application/json");

                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("email", uName);
                params.put("password", pWord);
                Log.e("hure parameters",""+uName+pWord);
                return params;
            }
        };

        mRequestQueue.add(request);
        progressDialog.show();

    }


}
