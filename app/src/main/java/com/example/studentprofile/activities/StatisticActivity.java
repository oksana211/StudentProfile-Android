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
import com.example.studentprofile.models.User;
import com.example.studentprofile.requests.GetJsonObjectRequest;
import com.example.studentprofile.responses.PersonalStatistic;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StatisticActivity extends Menu{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        try {
            getSortedUsers();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getSortedUsers() throws ExecutionException, InterruptedException {

        GetJsonObjectRequest getJsonObjectRequest = new GetJsonObjectRequest("getStatistic");
        JsonObject statisticJson = getJsonObjectRequest.execute().get();
        System.out.println(statisticJson);

        TextView textViewGoldenRunning = (TextView) findViewById(R.id.textViewGoldenRunning);
        TextView textViewSilverRunning = (TextView) findViewById(R.id.textViewSilverRunning);
        TextView textViewBronzeRunning = (TextView) findViewById(R.id.textViewBronzeRunning);
        TextView textViewIronRunning = (TextView) findViewById(R.id.textViewIronRunning);

        TextView textViewGoldenPullUp = (TextView) findViewById(R.id.textViewGoldenPullUp);
        TextView textViewSilverPullUp = (TextView) findViewById(R.id.textViewSilverPullUp);
        TextView textViewBronzePullUp = (TextView) findViewById(R.id.textViewBronzePullUp);
        TextView textViewIronPullUp = (TextView) findViewById(R.id.textViewIronPullUp);

        textViewGoldenRunning.setText(statisticJson.get("goldenRunning").getAsString());
        textViewSilverRunning.setText(statisticJson.get("silverRunning").getAsString());
        textViewBronzeRunning.setText(statisticJson.get("bronzeRunning").getAsString());
        textViewIronRunning.setText(statisticJson.get("ironRunning").getAsString());

        textViewGoldenPullUp.setText(statisticJson.get("goldenPullUp").getAsString());
        textViewSilverPullUp.setText(statisticJson.get("silverPullUp").getAsString());
        textViewBronzePullUp.setText(statisticJson.get("bronzePullUp").getAsString());
        textViewIronPullUp.setText(statisticJson.get("ironPullUp").getAsString());

        JsonArray ps = statisticJson.getAsJsonArray("sortedUsers");
        ArrayList<PersonalStatistic> psList = new ArrayList<>();

        for(JsonElement statisticJsonElement: ps){
            JsonObject activityJson = statisticJsonElement.getAsJsonObject();
            User user = new User();
            user.setId(activityJson.get("user").getAsJsonObject().get("id").getAsLong());
            user.setLogin(activityJson.get("user").getAsJsonObject().get("login").getAsString());
            System.out.println(user.getId());

            PersonalStatistic personalStatistic = new PersonalStatistic(user, activityJson.get("iron").getAsInt(),
                    activityJson.get("bronze").getAsInt(), activityJson.get("silver").getAsInt(), activityJson.get("golden").getAsInt());
            System.out.println("rate " + personalStatistic.getRate());
            psList.add(personalStatistic);
        }
        System.out.println(psList.get(0));

        @SuppressLint("CutPasteId")
        ListView list = (ListView) findViewById(R.id.listViewStatistic);
        UserStatisticAdapter adapter = new UserStatisticAdapter(this, psList);
        list.setAdapter(adapter);
    }

    class UserStatisticAdapter extends ArrayAdapter<PersonalStatistic> {
        private Context mContext;
        private List<PersonalStatistic> moviesList = new ArrayList<>();

        public UserStatisticAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<PersonalStatistic> list) {
            super(context, 0, list);
            mContext = context;
            moviesList = list;
            System.out.println(list.get(0));
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listItem = convertView;
            if (listItem == null)
                listItem = LayoutInflater.from(mContext).inflate(R.layout.statistic_list, parent, false);

            TextView textViewName = listItem.findViewById(R.id.textViewName);

            TextView textViewIron = listItem.findViewById(R.id.textViewIron);
            TextView textViewBronze = listItem.findViewById(R.id.textViewBronze);
            TextView textViewSilver = listItem.findViewById(R.id.textViewSilver);
            TextView textViewGolden = listItem.findViewById(R.id.textViewGolden);
            TextView textViewRate = listItem.findViewById(R.id.textViewRate);

            PersonalStatistic currentMovie = moviesList.get(position);
            System.out.println(currentMovie.getUser().getId());

            textViewName.setText(currentMovie.getUser().getLogin());

            textViewIron.setText(String.valueOf(currentMovie.getIron()));
            textViewBronze.setText(String.valueOf(currentMovie.getBronze()));
            textViewSilver.setText(String.valueOf(currentMovie.getSilver()));
            textViewGolden.setText(String.valueOf(currentMovie.getGolden()));
            textViewRate.setText(String.valueOf(currentMovie.getRate()));

            textViewName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println(currentMovie.getUser().getId());
                    Intent intent = new Intent(StatisticActivity.this, UserInfoActivity.class);
                    intent.putExtra("id", currentMovie.getUser().getId());
                    startActivity(intent);
                }
            });

            return listItem;
        }
    }
}
