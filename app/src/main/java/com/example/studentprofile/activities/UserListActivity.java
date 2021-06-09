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
import com.example.studentprofile.models.Role;
import com.example.studentprofile.models.Session;
import com.example.studentprofile.models.User;
import com.example.studentprofile.requests.GetJsonArrayRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class UserListActivity extends Menu{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        try {
            getUsers();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getUsers() throws ExecutionException, InterruptedException {
        ListView list = (ListView) findViewById(R.id.listView);
        ArrayList<User> users = new ArrayList<>();

        GetJsonArrayRequest getJsonArrayRequest = new GetJsonArrayRequest("getUsers");
        JsonArray usersJson = getJsonArrayRequest.execute().get();
        for(JsonElement userJsonElement: usersJson){
            JsonObject userJson = userJsonElement.getAsJsonObject();
            User newUser = new User();

            Role role = new Role();
            role.setName(userJson.getAsJsonObject("role").get("name").getAsString());

            List<Session> sessions = new ArrayList<>();

            newUser.setId(userJson.get("id").getAsLong());
            newUser.setLogin(userJson.get("login").getAsString());
            newUser.setRole(role);
            newUser.setSessions(sessions);

            users.add(newUser);

        }

        @SuppressLint("CutPasteId")
        ListView productList = (ListView) findViewById(R.id.listView);
        UserAdapter adapter = new UserAdapter(this, users);
        productList.setAdapter(adapter);

    }

    public void onButtonClick(View view) {
        Intent intent = new Intent(UserListActivity.this, StatisticActivity.class);
        startActivity(intent);
    }


    class UserAdapter extends ArrayAdapter<User> {
        private Context mContext;
        private List<User> moviesList = new ArrayList<>();

        public UserAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<User> list) {
            super(context, 0, list);
            mContext = context;
            moviesList = list;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItem = convertView;
            if (listItem == null)
                listItem = LayoutInflater.from(mContext).inflate(R.layout.user_list, parent, false);

            TextView textViewName = listItem.findViewById(R.id.textViewName);
            TextView textViewCard = listItem.findViewById(R.id.textViewRole);
            TextView textViewId = listItem.findViewById(R.id.textViewId);

            User currentMovie = moviesList.get(position);

            textViewId.setText(String.valueOf(currentMovie.getId()));
            textViewName.setText(String.valueOf(currentMovie.getLogin()));
            textViewCard.setText(String.valueOf(currentMovie.getRole().getName()));

            textViewName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println(currentMovie.getId());
                    Intent intent = new Intent(UserListActivity.this, UserInfoActivity.class);
                    intent.putExtra("id", currentMovie.getId());
                    startActivity(intent);
                }
            });

            return listItem;
        }
    }
}
