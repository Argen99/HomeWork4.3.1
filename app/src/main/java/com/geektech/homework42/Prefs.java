package com.geektech.homework42;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;

public class Prefs {

    private SharedPreferences preferences;

    public Prefs(Context context){

        preferences = context.getSharedPreferences("settings",Context.MODE_PRIVATE);

    }

    public void saveImage(String image){
        preferences.edit().putString("key",image).apply();
    }

    public String getImageUri(){
        return preferences.getString("key",null);
    }




    public void saveState(String name){

        // preferences.edit().putBoolean("isShown",true).apply();
        preferences.edit().putString("name", name);
    }

    public boolean isShown(){
        return preferences.getBoolean("isShown",false);
    }
}
