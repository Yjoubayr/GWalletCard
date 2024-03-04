package com.yossefjm.gwalletcard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.pay.Pay;
import com.google.android.gms.pay.PayApiAvailabilityStatus;
import com.google.android.gms.pay.PayClient;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;

public class MainActivity extends AppCompatActivity {

    // walletClient is the client that will be used to interact with the google wallet api
    private PayClient walletClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // we will initialize the wallet client here
        walletClient = Pay.getClient(this);

        findViewById(R.id.addToGoogleWalletButton).setOnClickListener(v -> {
            // here we want to put a new card to the google wallet
            // we will use the google pay api to do that




        });
    }


    private void fetchCanUseGoogleWalletApi() {
        walletClient
                .getPayApiAvailabilityStatus(PayClient.RequestType.SAVE_PASSES)
                .addOnSuccessListener { status ->
                    if (status == PayApiAvailabilityStatus.AVAILABLE)
                        Toast.makeText(this, "Google Wallet API is available", Toast.LENGTH_SHORT).show()
                    }
                .addOnFailureListener {
                    // Hide the button and optionally show an error message
                    Toast.makeText(this, "Google Wallet API is not available", Toast.LENGTH_SHORT).show()
                }
            }
}