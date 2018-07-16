package com.example.joelwasserman.androidbletutorial.Pojo;

import com.google.gson.annotations.SerializedName;

public class ChildPojoStudProf {

    String found="false";

    @SerializedName("driverId")
    String driverId;

    @SerializedName("name")
    String name;

    @SerializedName("address")
    String address;

    @SerializedName("vehicleType")
    String vehicleType;

    @SerializedName("vehicleRegNo")
    String vehicleRegNo;

    @SerializedName("phone")
    String phone;

    @SerializedName("macId")
    String macId;

    @SerializedName("username")
    String username;

    @SerializedName("password")
    String password;

    @SerializedName("school_id")
    String school_id;

    @SerializedName("schoolName")
    String schoolName;

    @SerializedName("child_id")
    String child_id;

    @SerializedName("childName")
    String childName;

    @SerializedName("childMacID")
    String childMacID;

    @SerializedName("mac_id")
    String mac_id;

    @SerializedName("stauts")
    String stauts;


    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getFound() {
        return found;
    }

    public void setFound(String found) {
        this.found = found;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getVehicleRegNo() {
        return vehicleRegNo;
    }

    public void setVehicleRegNo(String vehicleRegNo) {
        this.vehicleRegNo = vehicleRegNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMacId() {
        return macId;
    }

    public void setMacId(String macId) {
        this.macId = macId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getChild_id() {
        return child_id;
    }

    public void setChild_id(String child_id) {
        this.child_id = child_id;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getChildMacID() {
        return childMacID;
    }

    public void setChildMacID(String childMacID) {
        this.childMacID = childMacID;
    }


}
