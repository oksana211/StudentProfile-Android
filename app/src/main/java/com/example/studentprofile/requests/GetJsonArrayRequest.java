package com.example.studentprofile.requests;

import android.os.AsyncTask;

import com.example.studentprofile.Services.ServiceGenerator;
import com.example.studentprofile.responses.Token;
import com.google.gson.JsonArray;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

public class GetJsonArrayRequest extends AsyncTask<Void, JsonArray, JsonArray> {

    String functionName;

    public GetJsonArrayRequest(String functionName) {
        this.functionName = functionName;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected JsonArray doInBackground(Void... voids) {
        IRetrofit jsonGetService = ServiceGenerator.createService(IRetrofit.class);
        Call<JsonArray> call = null;
        if (functionName.equals("getUsers")) {
            call = jsonGetService.getUsers(Token.getInstance().toString());
        }

        try{
            assert call != null;
            Response<JsonArray> response = call.execute();
            System.out.println("body   " + response.body());
            return Objects.requireNonNull(response.body()).getAsJsonArray();

        } catch (IOException e){
            System.out.println("unable to execute");
        }

        return null;
    }

    @Override
    protected void onPostExecute(JsonArray result) {
        super.onPostExecute(result);
    }
}
