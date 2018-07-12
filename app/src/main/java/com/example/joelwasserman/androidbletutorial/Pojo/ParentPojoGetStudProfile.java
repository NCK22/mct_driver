package com.example.joelwasserman.androidbletutorial.Pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

public class ParentPojoGetStudProfile extends CommonParentPojo {


    @SerializedName("message")
    ArrayList<HashMap<String,String>> objProfile;

    public ArrayList<HashMap<String, String>> getObjProfile() {
        return objProfile;
    }

    public void setObjProfile(ArrayList<HashMap<String, String>> objProfile) {
        this.objProfile = objProfile;
    }

}
