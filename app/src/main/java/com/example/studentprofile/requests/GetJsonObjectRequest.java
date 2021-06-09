package com.example.studentprofile.requests;

import android.os.AsyncTask;

import com.example.studentprofile.Services.ServiceGenerator;
import com.example.studentprofile.responses.Token;
import com.google.gson.JsonObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class GetJsonObjectRequest extends AsyncTask<Void, JsonObject, JsonObject> {

    String functionName;
    Long id;

    public GetJsonObjectRequest(String functionName, Long id) {
        this.functionName = functionName;
        this.id = id;
    }

    public GetJsonObjectRequest(String functionName) {
        this.functionName = functionName;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JsonObject doInBackground(Void... voids) {
        IRetrofit jsonGetService = ServiceGenerator.createService(IRetrofit.class);
        Call<JsonObject> call = null;
        if (functionName.equals("getUser")) {
            call = jsonGetService.getUser(Token.getInstance().toString(), id);
        }
        else if(functionName.equals(("getStatistic"))){
            call = jsonGetService.getStatistic(Token.getInstance().toString());
        }

        try{
            assert call != null;
            Response<JsonObject> response = call.execute();
            System.out.println("body   " + response.body());
            assert response.body() != null;
            return response.body().getAsJsonObject();

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