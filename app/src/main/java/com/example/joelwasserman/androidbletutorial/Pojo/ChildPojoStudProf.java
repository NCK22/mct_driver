package com.example.joelwasserman.androidbletutorial.Pojo;

import com.google.gson.annotations.SerializedName;

public class ChildPojoStudProf {

    @SerializedName("parent_id")
    String parent_id;

    @SerializedName("parentName")
    String parentName;

    @SerializedName("childName")
    String childName;

    @SerializedName("child_id")
    String child_id;

    @SerializedName("name")
    String name;

    @SerializedName("school_id")
    String school_id;

    @SerializedName("standard_id")
    String standard_id;

    @SerializedName("class_id")
    String class_id;

    @SerializedName("mac_id")
    String mac_id;

    @SerializedName("stauts")
    String stauts;


    public String getChild_id() {
        return child_id;
    }

    public String getChildName() {
        return childName;
    }

    public String getName() {
        return name;
    }

    public String getParent_id() {
        return parent_id;
    }

    public String getParentName() {
        return parentName;
    }

    public String getSchool_id() {
        return school_id;
    }

    public String getClass_id() {
        return class_id;
    }

    public String getMac_id() {
        return mac_id;
    }

    public String getStandard_id() {
        return standard_id;
    }

    public String getStauts() {
        return stauts;
    }

    public void setChild_id(String child_id) {
        this.child_id = child_id;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public void setStandard_id(String standard_id) {
        this.standard_id = standard_id;
    }

    public void setMac_id(String mac_id) {
        this.mac_id = mac_id;
    }

    public void setStauts(String stauts) {
        this.stauts = stauts;
    }
}
