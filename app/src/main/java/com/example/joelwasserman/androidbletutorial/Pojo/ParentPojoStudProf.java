package com.example.joelwasserman.androidbletutorial.Pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ParentPojoStudProf {

    @SerializedName("status")
    public
    String status;

  /*  @SerializedName("message")
    String msg;*/

    @SerializedName("message")
    ArrayList<ChildPojoStudProf> objProfile;




    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<ChildPojoStudProf> getObjProfile() {
        return objProfile;
    }

    public void setObjProfile(ArrayList<ChildPojoStudProf> objProfile) {
        this.objProfile = objProfile;
    }
}
