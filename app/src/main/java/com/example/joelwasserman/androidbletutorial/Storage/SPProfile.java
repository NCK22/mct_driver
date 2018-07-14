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

    public String getDriver_id() {

        return preferences.getString("driver_id", "");
    }

    public String getName() {

        return preferences.getString("name", "");
    }
    public String getMac_id() {

        return preferences.getString("mac_id", "");
    }
    public String getVeh_type() {

        return preferences.getString("veh_type", "");
    }
    public String getVeh_Reg() {

        return preferences.getString("veh_reg", "");
    }

    public String getSchol_Id() {

        return preferences.getString("school_id", "");
    }


    public String getMobile() {
        //preferences = this.getSharedPreferences(prefName, 0);
        return preferences.getString("mobile", "");
    }

    public String getEmail() {
       // preferences = this.getSharedPreferences(prefName, 0);
        return preferences.getString("email", "");
    }

    public String getAddress() {
        // preferences = this.getSharedPreferences(prefName, 0);
        return preferences.getString("email", "");
    }

    public String getUname() {
        // preferences = this.getSharedPreferences(prefName, 0);
        return preferences.getString("username", "");
    }

    public String getPassword() {
        // preferences = this.getSharedPreferences(prefName, 0);
        return preferences.getString("password", "");
    }



    public void setUsername(String profile_id) {
        // preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("username", profile_id);
        editor.apply();
    }

    public void setPassword(String profile_id) {
        // preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("password", profile_id);
        editor.apply();
    }


    public void setProfile_id(String profile_id) {
       // preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("profile_id", profile_id);
        editor.apply();
    }



    public void setDriver_id(String driver_id) {
        // preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("driver_id", driver_id);
        editor.apply();
    }

    public void setMac_id(String mac_id) {
        // preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("mac_id", mac_id);
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


    public void setAddress(String address) {
        //preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("address", address);
        editor.apply();
    }
    public void setSchoolId(String email) {
        //preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("school_id", email);
        editor.apply();
    }
    public void setVehReg(String email) {
        //preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("veh_reg", email);
        editor.apply();
    }
    public void setVehType(String email) {
        //preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("veh_type", email);
        editor.apply();
    }
    public void setName(String email) {
        //preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name", email);
        editor.apply();
    }


    public void setIsLogin(String is_login) {
        //preferences = this.getSharedPreferences(prefName, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("is_login", is_login);
        editor.apply();
    }
}
