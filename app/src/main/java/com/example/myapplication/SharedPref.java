package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SharedPref {


    // Singleton class to access the user "database" across multiple activities
    private static SharedPreferences mSharedPref;

    public SharedPref() {
    }

    // Initializer
    public static void init(Context context)
    {
        if(mSharedPref == null) {
            mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
        }
    }

    // Loads existing users from json string into a List
    public static List<Player> load() {
        Gson gson = new Gson();
        String json = mSharedPref.getString("player list",null);
        Type collectionType = new TypeToken<List<Player>>(){}.getType();
        List<Player> list = gson.fromJson(json,collectionType);

        return list;
    }

    // Saves the player List as json string in sharedPreferences
    public static void save(List<Player> players){
        SharedPreferences.Editor editor = mSharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(players);
        editor.putString("player list",json);
        editor.apply();
    }
}
