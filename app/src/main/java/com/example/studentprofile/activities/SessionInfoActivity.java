package com.example.studentprofile.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.studentprofile.R;
import com.example.studentprofile.models.Activity;
import com.example.studentprofile.models.Session;
import com.example.studentprofile.requests.GetJsonObjectRequest;
import com.example.studentprofile.Services.Service;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SessionInfoActivity extends Menu{

    private ListView list;
    private Long sessionId;
    private Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_info);

        Bundle arguments = getIntent().getExtras();
        sessionId = Long.valueOf(arguments.get("sessionId").toString());
        userId = Long.valueOf(arguments.get("userId").toString());
        System.out.println("sessionId " + sessionId);
        System.out.println("userId " + userId);

        try {
            setSessionInfo();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void setSessionInfo() throws ExecutionException, InterruptedException {
        GetJsonObjectRequest getJsonObjectRequest = new GetJsonObjectRequest("getUser", userId);
        JsonObject userJson = getJsonObjectRequest.execute().get();
        Session session = Service.getSession(userJson, sessionId);
        assert session != null;
        System.out.println(session.getDate());

        TextView textViewDate = (TextView) findViewById(R.id.textViewDate2);
        TextView textViewHeight = (TextView) findViewById(R.id.textViewHeight2);
        TextView textViewWeight = (TextView) findViewById(R.id.textViewWeight2);
        textViewDate.setText(session.getDate().substring(1, 11));
        textViewHeight.setText(String.valueOf(session.getHeight()));
        textViewWeight.setText(String.valueOf(session.getWeight()));

        getActivities(session);
    }

    private void getActivities(Session session){
        @SuppressLint("CutPasteId")
        ListView listViewActivities = (ListView) findViewById(R.id.listViewActivities);
        ActivityAdapter adapter = new ActivityAdapter(this, (ArrayList) session.getActivities());
        listViewActivities.setAdapter(adapter);
    }

    class ActivityAdapter extends ArrayAdapter<Activity> {
        private Context mContext;
        private List<Activity> moviesList = new ArrayList<>();

        public ActivityAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<Activity> list) {
            super(context, 0, list);
            mContext = context;
            moviesList = list;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItem = convertView;
            if (listItem == null)
                listItem = LayoutInflater.from(mContext).inflate(R.layout.session_activity_list, parent, false);

            TextView textViewName = listItem.findViewById(R.id.textViewName);
            TextView textViewTime = listItem.findViewById(R.id.textViewTime);
            TextView textViewResult = listItem.findViewById(R.id.textViewResult);
            TextView textViewDistance = listItem.findViewById(R.id.textViewDistance);
            TextView textViewQuantity = listItem.findViewById(R.id.textViewQuantity);
            TextView textViewPulse = listItem.findViewById(R.id.textViewPulse);

            Activity currentMovie = moviesList.get(position);

            textViewName.setText(String.valueOf(currentMovie.getName()));
            textViewTime.setText(String.valueOf(currentMovie.getTime()));
            textViewResult.setText(String.valueOf(currentMovie.getResult()));
            textViewDistance.setText(String.valueOf(currentMovie.getDistance()));
            textViewQuantity.setText(String.valueOf(currentMovie.getQuantity()));
            textViewPulse.setText(String.valueOf(currentMovie.getPulse()));


            return listItem;
        }
    }
}
