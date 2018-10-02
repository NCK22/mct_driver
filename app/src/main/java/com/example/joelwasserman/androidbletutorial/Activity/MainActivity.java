package com.example.joelwasserman.androidbletutorial.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joelwasserman.androidbletutorial.APIClient;
import com.example.joelwasserman.androidbletutorial.Adapter.StudentAdapter;
import com.example.joelwasserman.androidbletutorial.Interface.getChildListInterface;
import com.example.joelwasserman.androidbletutorial.Interface.setChildStatusActiveInterface;

import com.example.joelwasserman.androidbletutorial.Interface.setScannedByInterface;
import com.example.joelwasserman.androidbletutorial.Pojo.ChildPojoStudProf;
import com.example.joelwasserman.androidbletutorial.Pojo.CommonParentPojo;
import com.example.joelwasserman.androidbletutorial.Pojo.ParentPojoStudProf;
import com.example.joelwasserman.androidbletutorial.R;
import com.example.joelwasserman.androidbletutorial.Services.TrackLocService;
import com.example.joelwasserman.androidbletutorial.Storage.SPProfile;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final static int REQUEST_ENABLE_BT = 1;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    BluetoothManager btManager;
    BluetoothAdapter btAdapter;
    BluetoothLeScanner btScanner;
    Button startScanningButton;
    Button stopScanningButton;
    TextView peripheralTextView;
    RecyclerView rv_stud;
    ProgressDialog progressDialog;
    ArrayList<ChildPojoStudProf> mListItem=new ArrayList<ChildPojoStudProf>();
    ArrayList<ChildPojoStudProf> mListItem_bckp=new ArrayList<ChildPojoStudProf>();
    SPProfile spCustProfile;
    StudentAdapter adapter;
    public static ArrayList<String> list_macId=new ArrayList<String>();

    private boolean gps_enabled=false;
    public static AlertDialog.Builder builder;
    public static boolean builderFlag=false;


    Toolbar toolbar;
    TextView toolbar_textview;
    // Device scan callback.
    private ScanCallback leScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {

            // auto scroll for text view
            final int scrollAmount = peripheralTextView.getLayout().getLineTop(peripheralTextView.getLineCount()) - peripheralTextView.getHeight();
            // if there is no need to scroll, scrollAmount will be <=0
            if (scrollAmount > 0)
                peripheralTextView.scrollTo(0, scrollAmount);

            HashMap<String, Integer> txPowerLookupTable = new HashMap<String, Integer>();
            txPowerLookupTable.put(result.getDevice().getAddress(), new Integer(result.getRssi()));

            String macAddress = result.getDevice().getAddress();
            Integer txPower = txPowerLookupTable.get(macAddress);

            Log.e("txPower", "" + txPower);
            Log.e("Rssi", "" + result.getRssi());


//            𝑅𝑆𝑆𝐼𝑠𝑚𝑜𝑜𝑡h = 𝛼 ∗ 𝑅𝑆𝑆𝐼𝑛 + (1 − 𝛼) ∗ 𝑅𝑆𝑆𝐼𝑛−1

            Log.e("distance", "" + getDistance(result.getRssi(), txPower));
           /* if(!result.getDevice().getAddress().equalsIgnoreCase(""))
            {*/
           Log.e("macIdsize",""+list_macId.size());
                if(list_macId.isEmpty()|| !list_macId.contains(result.getDevice().getAddress())) {
                    list_macId.add(result.getDevice().getAddress());
                    Log.e("mListItem size",""+mListItem.size());
                    for(int i=0;i<mListItem.size();i++)
                    {
                        Log.e("MACId",mListItem.get(i).getChildMacID());
                        if(mListItem.get(i).getChildMacID().equalsIgnoreCase(result.getDevice().getAddress())
                                && !mListItem.get(i).getFound().equalsIgnoreCase("ignore"))
                            mListItem.get(i).setFound("true");
                    }
                }
           // }

            //if(!peripheralTextView.getText().toString().contains(result.getDevice().getAddress()))
            if(!list_macId.contains(result.getDevice().getAddress()))
            peripheralTextView.setText("MAC ADDRESS: " + result.getDevice().getAddress() + "\nRSSI: " + result.getRssi() + "\nBondState: " + result.getDevice().getBondState() + "\nDistance: " + calculateDistance(result.getRssi()) + "\n-----------------------------------------\n");
           if(adapter!=null)
                adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog=new ProgressDialog(this);
        spCustProfile=new SPProfile(this);

        toolbar=(Toolbar) findViewById(R.id.toolbar);
        toolbar_textview=(TextView)findViewById(R.id.toolbar_title);
        toolbar.setTitle("");
        toolbar_textview.setText("Scan");
        setSupportActionBar(toolbar);

        rv_stud=(RecyclerView)findViewById(R.id.rv_stud);
        rv_stud.setHasFixedSize(true);
        rv_stud.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        peripheralTextView = (TextView) findViewById(R.id.PeripheralTextView);
        peripheralTextView.setMovementMethod(new ScrollingMovementMethod());

        if(list_macId!=null)
            list_macId.clear();

        getStudentList();

        startScanningButton = (Button) findViewById(R.id.StartScanButton);
        startScanningButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startScanning();
            }
        });

        stopScanningButton = (Button) findViewById(R.id.StopScanButton);
        stopScanningButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                stopScanning();
            }
        });
        stopScanningButton.setVisibility(View.INVISIBLE);

        btManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        btAdapter = btManager.getAdapter();
        btScanner = btAdapter.getBluetoothLeScanner();


        if (btAdapter != null && !btAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }

        // Make sure we have access coarse location enabled, if not, prompt the user to enable it
        if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("This app needs location access");
            builder.setMessage("Please grant location access so this app can detect peripherals.");
            builder.setPositiveButton(android.R.string.ok, null);
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                }
            });
            builder.show();
        }


    //    EnableRuntimePermissionToAccessCamera();

     //   startService(new Intent(MainActivity.this, TrackLocService.class));
    }

    double getDistance(int rssi, int txPower) {
    /*
     * RSSI = TxPower - 10 * n * lg(d)
     * n = 2 (in free space)
     *
     * d = 10 ^ ((TxPower - RSSI) / (10 * n))
     */

        return Math.pow(10d, ((double) txPower - rssi) / (10 * 2));
    }


    double calculateDistance(int rssi) {

        int txPower = -59; //hard coded power value. Usually ranges between -59 to -65

        if (rssi == 0) {
            return -1.0;
        }

        double ratio = rssi * 1.0 / txPower;
        if (ratio < 1.0) {
            return Math.pow(ratio, 10);
        } else {
            double distance = (0.89976) * Math.pow(ratio, 7.7095) + 0.111;
            return distance;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    System.out.println("coarse location permission granted");
                    startService(new Intent(MainActivity.this, TrackLocService.class));
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }

                    });
                    builder.show();
                }
                return;
            }

            case 2: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // {Some Code}

                    startService(new Intent(MainActivity.this, TrackLocService.class));
                }
            }

            case 3:
                        Log.e("test", "onActivityResult");
                        if(gps_enabled)
                            finish();
                        else
                            isLocationEnabled();
                        // broadcastFlag=false;
                        break;




        }
    }

    public void startScanning() {
        if(list_macId!=null)
            list_macId.clear();
        System.out.println("start scanning");
        peripheralTextView.setText("");
        startScanningButton.setVisibility(View.INVISIBLE);
        stopScanningButton.setVisibility(View.VISIBLE);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                if(btScanner!=null)
                    btScanner.startScan(leScanCallback);
                else {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MainActivity.this, "Please try again", Toast.LENGTH_SHORT).show();
                            stopScanningButton.setVisibility(View.INVISIBLE);
                            startScanningButton.setVisibility(View.VISIBLE);
                        }
                    });
                }
            }
        });
    }

    public void stopScanning() {
        System.out.println("stopping scanning");
        peripheralTextView.append("Stopped Scanning");
        startScanningButton.setVisibility(View.VISIBLE);
        stopScanningButton.setVisibility(View.INVISIBLE);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                btScanner.stopScan(leScanCallback);
            }
        });

        for(int i=0;i<mListItem.size();i++)
        {
            if(mListItem.get(i).getFound().equalsIgnoreCase("true"))

          //  setChildStatusActive(mListItem.get(i).getChild_id(),mListItem.get(i).getChildMacID());
                setScannedBy(mListItem.get(i).getChildMacID());
          //  setChildStatusActive(mListItem.get(i).getChild_id(),mListItem.get(i).getChildMacID());

        }

    }

    public void getStudentList(){

        progressDialog.show();

if(mListItem!=null)
    mListItem.clear();

        getChildListInterface getResponse = APIClient.getClient().create(getChildListInterface.class);
        Call<ParentPojoStudProf> call = getResponse.doGetListResources(spCustProfile.getDriver_id());
        call.enqueue(new Callback<ParentPojoStudProf>() {
            @Override
            public void onResponse(Call<ParentPojoStudProf> call, Response<ParentPojoStudProf> response) {

                Log.e("Inside","onResponse");
                // Log.e("response body",response.body().getStatus());
                //Log.e("response body",response.body().getMsg());
                ParentPojoStudProf parentPojoStudProf =response.body();
                if(parentPojoStudProf !=null){
                    if(parentPojoStudProf.getStatus().equalsIgnoreCase("true")){
                        mListItem=parentPojoStudProf.getObjProfile();
                        mListItem_bckp=parentPojoStudProf.getObjProfile();
                        //  noOfTabs=list_child.size();
                        Log.e("Response","Success");

                        if(mListItem.size()>0){
                            displayData();
                        }



                        //      Log.e("objsize", ""+ parentPojoProfile.getObjProfile().size());

                        //setHeader();

                    }
                }
                else
                    Log.e("parentpojotabwhome","null");
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ParentPojoStudProf> call, Throwable t) {

                Log.e("throwable",""+t);
                progressDialog.dismiss();
            }
        });

    }

    public void setChildStatusActive(String child_id,String mac_id){

        progressDialog.show();

        if(mListItem!=null)
            mListItem.clear();

        setChildStatusActiveInterface getResponse = APIClient.getClient().create(setChildStatusActiveInterface.class);
        Call<CommonParentPojo> call = getResponse.doGetListResources(child_id,"1",mac_id);
        call.enqueue(new Callback<CommonParentPojo>() {
            @Override
            public void onResponse(Call<CommonParentPojo> call, Response<CommonParentPojo> response) {

                Log.e("Inside","onResponse");
                // Log.e("response body",response.body().getStatus());
                //Log.e("response body",response.body().getMsg());
                CommonParentPojo CommonParentPojo =response.body();
                if(CommonParentPojo !=null){
                    if(CommonParentPojo.getStatus().equalsIgnoreCase("true")){

                        //  noOfTabs=list_child.size();
                        Log.e("Response","Success");


                        //      Log.e("objsize", ""+ parentPojoProfile.getObjProfile().size());

                        //setHeader();

                    }
                }
                else
                    Log.e("parentpojotabwhome","null");
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<CommonParentPojo> call, Throwable t) {

                Log.e("throwable",""+t);
                progressDialog.dismiss();
            }
        });

    }


    public void setScannedBy(String child_mac_id){

        progressDialog.show();

       /* if(mListItem!=null)
            mListItem.clear();*/

        setScannedByInterface getResponse = APIClient.getClient().create(setScannedByInterface.class);
        Call<CommonParentPojo> call = getResponse.doGetListResources(child_mac_id,spCustProfile.getDriver_id(),"driver");
        call.enqueue(new Callback<CommonParentPojo>() {
            @Override
            public void onResponse(Call<CommonParentPojo> call, Response<CommonParentPojo> response) {

                Log.e("Inside","onResponse");
                // Log.e("response body",response.body().getStatus());
                //Log.e("response body",response.body().getMsg());
                CommonParentPojo CommonParentPojo =response.body();
                if(CommonParentPojo !=null){
                    if(CommonParentPojo.getStatus().equalsIgnoreCase("true")){

                        //  noOfTabs=list_child.size();
                        Log.e("Response","Success");


                        //      Log.e("objsize", ""+ parentPojoProfile.getObjProfile().size());

                        //setHeader();

                    }
                }
                else
                    Log.e("parentpojotabwhome","null");
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<CommonParentPojo> call, Throwable t) {

                Log.e("throwable",""+t);
                progressDialog.dismiss();
            }
        });

    }


    private void displayData() {

        adapter = new StudentAdapter(MainActivity.this, mListItem);
        rv_stud.setAdapter(adapter);

        /*if (adapter.getItemCount() == 0) {
            lyt_not_found.setVisibility(View.VISIBLE);
        } else {
            lyt_not_found.setVisibility(View.GONE);
        }*/
    }

    public void EnableRuntimePermissionToAccessCamera(){

        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA) &&
                (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) &&
                (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) &&
                (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED))
        {

            // Printing toast message after enabling runtime permission.
            //   Toast.makeText(this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 2);

        }
        progressDialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isLocationEnabled();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_logout:
                logout();
                break;

            case R.id.menu_profile:
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                break;


            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void logout() {

        new android.support.v7.app.AlertDialog.Builder(MainActivity.this)
                .setTitle(getString(R.string.menu_logout))
                .setMessage(getString(R.string.logout_msg))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        stopScanning();
                        //MyApp.saveIsLogin(false);
                        spCustProfile.setIsLogin("false");
                        spCustProfile.setDriver_id("");
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

    public void isLocationEnabled()
    {
        builderFlag=true;
        LocationManager lm = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);


        if (!gps_enabled) {

            if(builder!=null)
                builder=null;

            builder =
                    new AlertDialog.Builder(this);
            final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
            final String message = "Disabling location will stop tracking"
                    + " Do you still want to turn off location?";

            builder.setMessage(message)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface d, int id) {
                                    //context.this.startActivityForResult(new Intent(action),1);
                                    d.dismiss();
                                    isLocationEnabled();
                                    //   builderFlag=false;
                                //    getCurrentVisit();
                                    //  broadcastFlag=false;
                                }
                            })
                    .setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface d, int id) {
                                    d.cancel();
                                    MainActivity.this.startActivityForResult(new Intent(action),3);
                                    // isLocationEnabled();
                                }
                            });
            builder.setCancelable(false);
            builder.create().show();

        }
        else
            startService(new Intent(MainActivity.this, TrackLocService.class));
    }
}
