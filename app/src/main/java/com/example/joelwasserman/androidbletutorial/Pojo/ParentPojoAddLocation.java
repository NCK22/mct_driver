package com.example.joelwasserman.androidbletutorial.Pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

public class ParentPojoAddLocation extends CommonParentPojo {


    @SerializedName("message")
    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
