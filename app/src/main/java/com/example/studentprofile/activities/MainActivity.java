package com.example.studentprofile.activities;

import androidx.annotation.RequiresApi;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.studentprofile.R;
import com.example.studentprofile.responses.Token;
import com.example.studentprofile.models.User;
import com.example.studentprofile.requests.AuthenticateRequest;
import com.google.gson.JsonObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends Menu {

    EditText editTextNickname, editTextPassword;
    Button buttonAddUpdate;
    List<User> usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usersList = new ArrayList<>();

        editTextNickname = (EditText) findViewById(R.id.editTextNickname);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonAddUpdate = (Button) findViewById(R.id.buttonAddUpdate);

        buttonAddUpdate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                try {
                    createUser();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createUser() throws ExecutionException, InterruptedException {

        String nickname = editTextNickname.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(nickname)) {
            editTextNickname.setError("Please enter name");
            editTextNickname.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter password");
            editTextPassword.requestFocus();
            return;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("login", nickname);
        params.put("password", password);

        AuthenticateRequest authenticateRequest = new AuthenticateRequest(params, "signup");
        JsonObject token = authenticateRequest.execute().get();
        Token.setInstance(token.get("token").toString());
        System.out.println(Token.getInstance());

    }


    public void  onButtonClick(View v){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

}