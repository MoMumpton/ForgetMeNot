package com.sunypoly.ForgetMeNot;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button scanButton = findViewById(R.id.scannerButton);
        final Activity activity = this;
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.initiateScan();

            }
        });

        Button generateCodeScanBtn = findViewById(R.id.generateCodeScanBtn);
        generateCodeScanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qrCode = "https://www.qrcode-monkey.com/";
                Uri webaddress = Uri.parse(qrCode);

                Intent qrIntent = new Intent(Intent.ACTION_VIEW, webaddress);
                if (qrIntent.resolveActivity(getPackageManager()) !=null) {
                    startActivity(qrIntent);
                }
            }
        });
    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data){
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if(result != null){
                if(result.getContents()==null){
                    Toast.makeText(this, "Scan Cancelled", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
        }else {
                super.onActivityResult(requestCode, resultCode, data);
            }


    }
}