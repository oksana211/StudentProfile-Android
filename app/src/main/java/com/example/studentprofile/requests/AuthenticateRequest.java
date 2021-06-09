package com.example.studentprofile.requests;

import android.os.AsyncTask;
import android.util.Log;

import com.example.studentprofile.Services.ServiceGenerator;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

public class AuthenticateRequest extends AsyncTask<Void, JsonObject, JsonObject> {

    HashMap<String, String> body;
    String functionName;

    public AuthenticateRequest(HashMap<String, String> body, String functionName) {
        this.body = body;
        this.functionName = functionName;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JsonObject doInBackground(Void... args) {

        JsonObject jsonObject = new JsonObject();

        for (HashMap.Entry<String, String> entry : body.entrySet()) {
            jsonObject.addProperty(entry.getKey(),entry.getValue());
        }

        Log.e("response-success", String.valueOf(jsonObject));

        IRetrofit jsonPostService = ServiceGenerator.createService(IRetrofit.class);
        Call<JsonObject> call = null;

        if (functionName.equals("signin")) {
            call = jsonPostService.signin(jsonObject);
        }
        else if(functionName.equals("signup")){
            call = jsonPostService.signup(jsonObject);
        }

        try{
            assert call != null;
            Response<JsonObject> response = call.execute();
            System.out.println("body   " + response.body());
            return Objects.requireNonNull(response.body()).getAsJsonObject();

        } catch (IOException e){
            System.out.println("unable to execute");
        }

        return null;
    }

    @Override
    protected void onPostExecute(JsonObject result) {
        super.onPostExecute(result);
    }
}