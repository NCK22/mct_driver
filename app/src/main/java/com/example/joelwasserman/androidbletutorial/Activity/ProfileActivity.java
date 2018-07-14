package com.example.joelwasserman.androidbletutorial.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.joelwasserman.androidbletutorial.R;
import com.example.joelwasserman.androidbletutorial.Storage.SPProfile;


public class ProfileActivity extends AppCompatActivity {

    TextView tvParentId,tvName,tvMacId,tvVehType,tvAddress,tvPhone,tvVehReg,tvUName,tvPwd,tvSchoolId;

    SPProfile spProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        spProfile=new SPProfile(this);

        tvParentId=(TextView)findViewById(R.id.tv_driver_id);
        tvName=(TextView)findViewById(R.id.tv_name);
        tvMacId=(TextView)findViewById(R.id.tv_mac_id);
        tvVehType=(TextView)findViewById(R.id.tv_veh_type);
        tvAddress=(TextView)findViewById(R.id.tv_address);
        tvPhone=(TextView)findViewById(R.id.tv_phone);
        tvVehReg=(TextView)findViewById(R.id.tv_veh_reg);
        tvUName=(TextView)findViewById(R.id.tv_uname);
        tvPwd=(TextView)findViewById(R.id.tv_pwd);
        tvSchoolId=(TextView)findViewById(R.id.tv_school_id);

        tvParentId.setText(spProfile.getDriver_id());
        tvName.setText(spProfile.getName());
        tvMacId.setText(spProfile.getMac_id());
        tvVehType.setText(spProfile.getVeh_type());
        tvAddress.setText(spProfile.getAddress());
        tvPhone.setText(spProfile.getMobile());
        tvVehReg.setText(spProfile.getVeh_Reg());
        tvUName.setText(spProfile.getUname());
        tvPwd.setText(spProfile.getPassword());
        tvSchoolId.setText(spProfile.getSchol_Id());
    }
}
