package com.example.studentprofile.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.example.studentprofile.models.User;
import com.example.studentprofile.Services.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserInfoActivity extends Menu {

    private ListView list;
    private Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        Bundle arguments = getIntent().getExtras();
        id = Long.valueOf(arguments.get("id").toString());
        System.out.println("id " + id);

        try {
            setUserInfo();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void setUserInfo() throws ExecutionException, InterruptedException {
        User user = Service.getUser(id);
        System.out.println("user " + user);

        TextView textViewLogin = (TextView) findViewById(R.id.textViewLogin);
        TextView textViewRole = (TextView) findViewById(R.id.textViewRole);

        TextView textViewName = (TextView) findViewById(R.id.textViewName);
        TextView textViewSurname = (TextView) findViewById(R.id.textViewSurname);
        TextView textViewSex = (TextView) findViewById(R.id.textViewSex);
        TextView textViewNumber = (TextView) findViewById(R.id.textViewNumber);
        TextView textViewSchool = (TextView) findViewById(R.id.textViewSchool);
        TextView textViewBirthday = (TextView) findViewById(R.id.textViewBirthday);

        textViewLogin.setText(user.getLogin());
        textViewRole.setText(user.getRole().getName());

        textViewName.setText(user.getInfo().getName());
        textViewSurname.setText(user.getInfo().getSurname());
        textViewSex.setText(user.getInfo().getSex());
        textViewNumber.setText(user.getInfo().getPhone_number());
        textViewSchool.setText(user.getInfo().getSchool());
        textViewBirthday.setText(user.getInfo().getBirthday());

        @SuppressLint("CutPasteId")
        ListView listViewSessions = (ListView) findViewById(R.id.listViewSessions);
        SessionAdapter adapter = new SessionAdapter(UserInfoActivity.this, (ArrayList) user.getSessions());
        listViewSessions.setAdapter(adapter);

    }

    class SessionAdapter extends ArrayAdapter<Session> {
        private Context mContext;
        private List<Session> moviesList = new ArrayList<>();

        public SessionAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<Session> list) {
            super(context, 0, list);
            mContext = context;
            moviesList = list;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItem = convertView;
            if (listItem == null)
                listItem = LayoutInflater.from(mContext).inflate(R.layout.session_list, parent, false);

            TextView textViewDate = listItem.findViewById(R.id.textViewDate);
            TextView textViewHeight = listItem.findViewById(R.id.textViewHeight);
            TextView textViewWeight = listItem.findViewById(R.id.textViewWeight);

            Session currentMovie = moviesList.get(position);

            textViewDate.setText(currentMovie.getDate().substring(1,11));
            textViewHeight.setText(String.valueOf(currentMovie.getHeight()));
            textViewWeight.setText(String.valueOf(currentMovie.getWeight()));


            textViewDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println(currentMovie.getId());
                    Intent intent = new Intent(UserInfoActivity.this, SessionInfoActivity.class);
                    intent.putExtra("sessionId", currentMovie.getId());
                    intent.putExtra("userId", id);
                    startActivity(intent);
                }
            });

            return listItem;
        }
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
