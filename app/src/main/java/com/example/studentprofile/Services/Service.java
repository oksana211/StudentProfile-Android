package com.example.studentprofile.Services;

import com.example.studentprofile.models.Activity;
import com.example.studentprofile.models.Role;
import com.example.studentprofile.models.Session;
import com.example.studentprofile.models.User;
import com.example.studentprofile.models.UserInfo;
import com.example.studentprofile.requests.GetJsonObjectRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Service {

    public static List<Session> getSessions(JsonObject userJson){
        List<Session> sessions = new ArrayList<>();
        JsonArray sessionsArray = userJson.getAsJsonArray("sessions");
        for(JsonElement sessionJsonElement: sessionsArray){

            JsonObject sessionJson = sessionJsonElement.getAsJsonObject();
            Session newSession = new Session();
            newSession.setId(Long.valueOf(sessionJson.get("id").toString()));
            newSession.setDate((sessionJson.get("date").toString()));
            newSession.setHeight(Double.valueOf(String.valueOf(sessionJson.get("height"))));
            newSession.setWeight(Double.valueOf(String.valueOf(sessionJson.get("weight"))));

            List<Activity> activities = new ArrayList<>();
            JsonArray activitiesArray = sessionJson.getAsJsonArray("activities");
            for(JsonElement activityJsonElement: activitiesArray){
                JsonObject activityJson = activityJsonElement.getAsJsonObject();
                Activity activity = new Activity();
                activity.setName(activityJson.get("name").toString());

                if (!activityJson.get("time").isJsonNull()) {
                    activity.setTime(Double.valueOf(activityJson.get("time").toString()));
                }
                if(!activityJson.get("quantity").isJsonNull()){
                    activity.setQuantity(Integer.valueOf(activityJson.get("quantity").toString()));
                }
                if(!activityJson.get("distance").isJsonNull()){
                    activity.setDistance(Double.valueOf(activityJson.get("distance").toString()));
                }
                if(!activityJson.get("pulse").isJsonNull()){
                    activity.setPulse(Double.valueOf(activityJson.get("pulse").toString()));
                }
                if(!activityJson.get("pressure").isJsonNull()){
                    activity.setPressure(activityJson.get("pressure").toString());
                }
                if(!activityJson.get("result").isJsonNull()){
                    activity.setResult(activityJson.get("result").toString());
                }

                activities.add(activity);
            }
            newSession.setActivities(activities);
            sessions.add(newSession);
        }
        return sessions;
    }

    public static Session getSession(JsonObject userJson, Long sessionId){
        List<Session> sessions = getSessions(userJson);
        int increment = 0;
        for(int i = 0; i < sessions.size(); i++){
            if(sessions.get(increment).getId().equals(sessionId)){
                return sessions.get(increment);
            }
        }
        return null;
    }

    public static User getUser(Long id) throws ExecutionException, InterruptedException {
        GetJsonObjectRequest getJsonObjectRequest = new GetJsonObjectRequest("getUser", id);
        JsonObject userJson = getJsonObjectRequest.execute().get();

        User newUser = new User();

        Role role = new Role();
        role.setName(userJson.getAsJsonObject("role").get("name").getAsString());


        newUser.setId(userJson.get("id").getAsLong());
        newUser.setLogin(userJson.get("login").getAsString());
        newUser.setRole(role);
        newUser.setInfo(getUserInfo(userJson));
        System.out.println("info " + newUser.getInfo().getSex());
        newUser.setSessions(Service.getSessions(userJson));
        return newUser;
    }

    public static UserInfo getUserInfo(JsonObject userJson){
        UserInfo userInfo = new UserInfo();
        userInfo.setName(userJson.getAsJsonObject("info").get("name").getAsString());
        userInfo.setSurname(userJson.getAsJsonObject("info").get("surname").getAsString());
        userInfo.setSex(userJson.getAsJsonObject("info").get("sex").getAsString());
        userInfo.setPhone_number(userJson.getAsJsonObject("info").get("phone_number").getAsString());
        userInfo.setSchool(userJson.getAsJsonObject("info").get("school").getAsString());
        userInfo.setBirthday(userJson.getAsJsonObject("info").get("birthday").getAsString());
        return userInfo;
    }
}
