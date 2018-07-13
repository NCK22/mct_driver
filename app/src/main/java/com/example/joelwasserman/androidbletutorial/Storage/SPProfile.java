package com.example.joelwasserman.androidbletutorial.Storage;

import android.content.Context;
import android.content.SharedPreferences;

public class SPProfile {

    public static SPProfile spInstance;
    public SharedPreferences preferences;
    public String prefName = "profdtl";

    public SPProfile(Context context)
    {
        preferences = context.getSharedPreferences(prefName, 0);
        spInstance=this;
    }


    public static synchronized SPProfile getSpInstance() {
        return spInstance;
    }


    public String getIsLogin()
    {
        return preferences.getString("is_login","");
    }

    public String getProfile_id() {

        return preferences.getString("profile_id", "");
    }
    public String getUser_id() {

        return preferences.getString("user_id", "");
    }
    public String getDriver_id() {

        return preferences.getString("driver_id", "");
    }


    public String getReligion() {
      //  preferences = this.getSharedPreferences(prefName, 0);
        return preferences.getString("religion", "");
    }

    public String getGender() {
        //preferences = this.getSharedPreferences(prefName, 0);
        return preferences.getString("gender", "");
    }

    public String getDob() {
        //preferences = this.getSharedPreferences(prefName, 0);
        return preferences.getString("dob", "");
    }

    public String getMobile() {
        //preferences = this.getSharedPreferences(prefName, 0);
        return preferences.getString("mobile", "");
    }

    public String getEmail() {
       // preferences = this.getSharedPreferences(prefName, 0);
        return preferences.getString("email", "");
    }

    public String getMatrimonyId() {
        // preferences = this.getSharedPreferences(prefName, 0);
        return preferences.getString("matrimony_id", "");
    }

    public String getShortListedProf() {
        // preferences = this.getSharedPreferences(prefName, 0);
        return preferences.getString("shortlistMy", "");
    }

    public String getProfilePhotoPath() {
        // preferences = this.getSharedPreferences(prefName, 0);
        return preferences.getString("profile_photo", "");
    }

    public void setProfilePhotoPath(String profile_photo) {
        //preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("profile_photo", profile_photo);
        editor.apply();
    }

    public void setShortListedProf(String shortlistMy) {
        //preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("shortlistMy", shortlistMy);
        editor.apply();
    }

    public void setReligion(String religion) {
        //preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("religion", religion);
        editor.apply();
    }

    public void setProfile_id(String profile_id) {
       // preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("profile_id", profile_id);
        editor.apply();
    }

    public void setUser_id(String user_id) {
        // preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("user_id", user_id);
        editor.apply();
    }

    public void setDriver_id(String driver_id) {
        // preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("driver_id", driver_id);
        editor.apply();
    }

    public void setGender(String gender) {
        //preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("gender", gender);
        editor.apply();
    }

    public void setDob(String dob) {
        //preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("dob", dob);
        editor.apply();
    }

    public void setMobile(String mobile) {
       // preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("mobile", mobile);
        editor.apply();
    }

    public void setEmail(String email) {
        //preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", email);
        editor.apply();
    }

    public void setMatrimonyId(String matrimony_id) {
        //preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("matrimony_id", matrimony_id);
        editor.apply();
    }

    public void setIsLogin(String is_login) {
        //preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("is_login", is_login);
        editor.apply();
    }
}
