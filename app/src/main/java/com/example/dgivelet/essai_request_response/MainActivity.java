package com.example.dgivelet.essai_request_response;



import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;




public class MainActivity extends AppCompatActivity {


    IntentIntegrator integrator;
    Web_Service web;
    //TextView textviewproduit;
    private String upcNumber;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        web=new Web_Service();
        //textviewproduit= (TextView) findViewById(R.id.textviewproduit);
        integrator=new IntentIntegrator(this);


    }


    public void initScan(View view) {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.CAMERA},
                1
        );

        /*Intent intent=new Intent(this,Web_Service.class);
        String upcNumber="pc";
        intent.putExtra("code",upcNumber);
        Log.e("lecture",upcNumber);
        startActivity(intent);*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {


            //textviewproduit.setText(data.getExtras().getString("SCAN_RESULT"));
            upcNumber = data.getExtras().getString("SCAN_RESULT");
            Intent intent=new Intent(this,Web_Service.class);
            intent.putExtra("code",upcNumber);
            Log.e("lecture",upcNumber);
            startActivity(intent);

        }
    }

    /*public void callBase(View view) {

            Intent intent=new Intent(this,Web_Service.class);
            intent.putExtra("code",upcNumber);
            Log.e("lecture",upcNumber);
            startActivity(intent);
    }*/

    public void generateCode(View view) {

        Intent intent=new Intent(this,GeneratecodeAvtivity.class);
        startActivity(intent);
    }





}