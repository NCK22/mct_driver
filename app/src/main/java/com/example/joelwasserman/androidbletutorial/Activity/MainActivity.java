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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.joelwasserman.androidbletutorial.APIClient;
import com.example.joelwasserman.androidbletutorial.Adapter.StudentAdapter;
import com.example.joelwasserman.androidbletutorial.Interface.getChildListInterface;
import com.example.joelwasserman.androidbletutorial.Pojo.ChildPojoStudProf;
import com.example.joelwasserman.androidbletutorial.Pojo.ParentPojoStudProf;
import com.example.joelwasserman.androidbletutorial.R;
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
    SPProfile spCustProfile;
    StudentAdapter adapter;
    public static ArrayList<String> list_macId=new ArrayList<String>();

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
            if(!list_macId.isEmpty())
            {
                if(!list_macId.contains(result.getDevice().getAddress()))
                    list_macId.add(result.getDevice().getAddress());
            }
           // if(peripheralTextView.getText().toString().equalsIgnoreCase(""))
            peripheralTextView.append("MAC ADDRESS: " + result.getDevice().getAddress() + "\nRSSI: " + result.getRssi() + "\nBondState: " + result.getDevice().getBondState() + "\nDistance: " + calculateDistance(result.getRssi()) + "\n-----------------------------------------\n");
           // adapter.notify();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog=new ProgressDialog(this);
        spCustProfile=new SPProfile(this);

        rv_stud=(RecyclerView)findViewById(R.id.rv_stud);
        rv_stud.setHasFixedSize(true);
        rv_stud.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        peripheralTextView = (TextView) findViewById(R.id.PeripheralTextView);
        peripheralTextView.setMovementMethod(new ScrollingMovementMethod());

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
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                }
            });
            builder.show();
        }

        getStudentList();
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
        }
    }

    public void startScanning() {
        System.out.println("start scanning");
        peripheralTextView.setText("");
        startScanningButton.setVisibility(View.INVISIBLE);
        stopScanningButton.setVisibility(View.VISIBLE);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                btScanner.startScan(leScanCallback);
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
    }

    public void getStudentList(){

        progressDialog.show();


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


    private void displayData() {
        adapter = new StudentAdapter(MainActivity.this, mListItem);

        rv_stud.setAdapter(adapter);

        /*if (adapter.getItemCount() == 0) {
            lyt_not_found.setVisibility(View.VISIBLE);
        } else {
            lyt_not_found.setVisibility(View.GONE);
        }*/
    }

}
