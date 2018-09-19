package com.example.joelwasserman.androidbletutorial.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joelwasserman.androidbletutorial.APIClient;
import com.example.joelwasserman.androidbletutorial.Interface.getStudProfileInterface;
import com.example.joelwasserman.androidbletutorial.Pojo.ParentPojoGetStudProfile;
import com.example.joelwasserman.androidbletutorial.R;
import com.example.joelwasserman.androidbletutorial.Storage.SPProfile;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudDetailActivity extends AppCompatActivity  {

    TextView tvParentId,tvName,tvMacId,tvSchoolId,tvStd,tvClass,tvStatus;

    Toolbar toolbar;
    TextView toolbar_textview;
    DrawerLayout drawerLayout;
    private NavigationView navigationView;
    SPProfile spProfile;
    ProgressDialog progressDialog;
    String child_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_detail);

        toolbar=(Toolbar) findViewById(R.id.toolbar);
        toolbar_textview=(TextView)findViewById(R.id.toolbar_title);
        toolbar.setTitle("");
        toolbar_textview.setText("Student Detail");
        setSupportActionBar(toolbar);

        spProfile=new SPProfile(this);
        progressDialog=new ProgressDialog(this);

        tvParentId=(TextView)findViewById(R.id.tv_parent_id);
        tvName=(TextView)findViewById(R.id.tv_name);
        tvMacId=(TextView)findViewById(R.id.tv_mac_id);
        tvSchoolId=(TextView)findViewById(R.id.tv_school);
        tvStd=(TextView)findViewById(R.id.tv_std);
        tvClass=(TextView)findViewById(R.id.tv_class);

        tvStatus=(TextView)findViewById(R.id.tv_status);


    /*    navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView.getMenu().clear(); //clear old inflated items.
        navigationView.inflateMenu(R.menu.menu_drawer);
        navigationView.setCheckedItem(R.id.menu_go_profile);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        //  setHeader();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
*/

        Intent intent=getIntent();
        child_id=intent.getStringExtra("child_id");
        try {
            /*if(intent!=null) {
                JSONObject jsonObject = new JSONObject(intent.getStringExtra("response"));
                Log.e("json", String.valueOf(jsonObject));
                Log.e("parent_id", jsonObject.getString("parent_id"));
                tvParentId.setText(jsonObject.getString("parent_id"));
                tvName.setText(jsonObject.getString("name"));
                tvMacId.setText(jsonObject.getString("mac_id"));
                tvEmail.setText(jsonObject.getString("email"));
                tvAddress.setText(jsonObject.getString("address"));
                tvPhone.setText(jsonObject.getString("phone"));
                tvRelation.setText(jsonObject.getString("relation"));
                tvUName.setText(jsonObject.getString("username"));
                tvPwd.setText(jsonObject.getString("password"));
                tvStatus.setText(jsonObject.getString("status"));
            }
*/
        } catch (Exception e) {
            e.printStackTrace();
        }

        getProfile();
    }

/*    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        drawerLayout.closeDrawers();
        switch (item.getItemId()) {
            case R.id.menu_go_home:
                //   toolbar.setTitle(getString(R.string.menu_home));
                startActivity(new Intent(StudDetailActivity.this, TabViewHomeActivity.class));
                return true;


            case R.id.menu_go_profile:
//                toolbar.setTitle(getString(R.string.menu_matches));
                startActivity(new Intent(getApplicationContext(),StudDetailActivity.class).putExtra("tabFlag","profile"));
                finish();
                return true;

            case R.id.menu_go_logout:
                logout();
                return true;

            case R.id.menu_go_stud_prof:
                startActivity(new Intent(getApplicationContext(),StudentListActivity.class).putExtra("tabFlag","profile"));
                finish();
                return true;

        }
        return false;
    }*/

    private void logout() {

        new AlertDialog.Builder(StudDetailActivity.this)
                .setTitle(getString(R.string.menu_logout))
                .setMessage(getString(R.string.logout_msg))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //MyApp.saveIsLogin(false);
                        spProfile.setIsLogin("false");
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                //  .setIcon(R.drawable.ic_logout)
                .show();
    }

    public void getProfile(){


        //checkValidity();

        //if(flagAllValid==true) {
        Log.e("child_id",child_id);
        progressDialog.setMessage("Please wait");
            progressDialog.show();
            getStudProfileInterface getResponse = APIClient.getClient().create(getStudProfileInterface.class);
            Call<ParentPojoGetStudProfile> call = getResponse.doGetListResources(child_id);
            call.enqueue(new Callback<ParentPojoGetStudProfile>() {
                @Override
                public void onResponse(Call<ParentPojoGetStudProfile> call, Response<ParentPojoGetStudProfile> response) {

                    Log.e("Inside", "onResponse");
               /* Log.e("response body",response.body().getStatus());
                Log.e("response body",response.body().getMsg());*/
                    ParentPojoGetStudProfile parentPojoLogin = response.body();
                    if (parentPojoLogin != null) {
                        if (parentPojoLogin.getStatus().equalsIgnoreCase("true")) {
                            Log.e("Response", parentPojoLogin.getStatus());
                            try {
                                Log.e("json response 1",""+parentPojoLogin.getObjProfile().get(0).get("child_id"));

                                tvParentId.setText(parentPojoLogin.getObjProfile().get(0).get("child_id"));
                                tvName.setText(parentPojoLogin.getObjProfile().get(0).get("name"));
                                tvSchoolId.setText(parentPojoLogin.getObjProfile().get(0).get("school_id"));
                                tvClass.setText(parentPojoLogin.getObjProfile().get(0).get("class_id"));
                                tvStatus.setText(parentPojoLogin.getObjProfile().get(0).get("status"));
                                tvStd.setText(parentPojoLogin.getObjProfile().get(0).get("standard_id"));
                                tvMacId.setText(parentPojoLogin.getObjProfile().get(0).get("mac_id"));

                                tvStatus.setText(parentPojoLogin.getObjProfile().get(0).get("status"));
                            } catch (Exception e) {
                                Log.e("exception",""+e);
                                e.printStackTrace();
                            }


                        }


                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<ParentPojoGetStudProfile> call, Throwable t) {

                    Log.e("Throwabe ", "" + t);
                    progressDialog.dismiss();
                }
            });
        }

    public void showToast(String msg)
    {
        Toast.makeText(StudDetailActivity.this,msg,Toast.LENGTH_SHORT).show();
    }

}
