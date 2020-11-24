package com.sunypoly.ForgetMeNot;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
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
        getWindow().getDecorView().setBackgroundColor(Color.WHITE);

        // Button to scan codes
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

        // Button to bring user to code generator
        Button generateCodeScanBtn = findViewById(R.id.generateCodeScanBtn);
        generateCodeScanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qrCode = "https://qrcode.tec-it.com/en/vcard";
                Uri webaddress = Uri.parse(qrCode);

                Intent qrIntent = new Intent(Intent.ACTION_VIEW, webaddress);
                if (qrIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(qrIntent);
                }
            }
        });


        // Button to bring user to shop
        Button ShopBtn = findViewById(R.id.ShopBtn);
        ShopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Shop = "https://www.customink.com/ndx/?cid=ryb0-00cc-depr#/";
                Uri webaddress = Uri.parse(Shop);

                Intent shopIntent = new Intent(Intent.ACTION_VIEW, webaddress);
                if (shopIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(shopIntent);
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Scan Cancelled", Toast.LENGTH_LONG).show();
            } else {
                showResultDialogue(result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    // Display results of scan
    private void showResultDialogue(final String result) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }

        // scan result and location button
        builder.setTitle("You found me!")
                .setMessage(result)
                .setPositiveButton("Return", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // return to app
                        dialog.dismiss();
                    }
                })
                .show();
    }
}