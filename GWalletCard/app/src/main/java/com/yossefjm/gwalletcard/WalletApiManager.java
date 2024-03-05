package com.yossefjm.gwalletcard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.pay.Pay;
import com.google.android.gms.pay.PayApiAvailabilityStatus;
import com.google.android.gms.pay.PayClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.wallet.WalletConstants;
// comentarion en ingles
/**
 * Class to manage the Wallet API
 */
public class WalletApiManager {
    private static final int LOAD_CARD_DATA_REQUEST_CODE = 999;

    private final Context context;

    private final PayClient walletClient;

    /**
     * Constructor
     * @param context The context
     */
    public WalletApiManager(Context context) {
        this.context = context;
        walletClient = Pay.getClient(context);
    }

    /**
     * Check if the Wallet API is available
     * @param context The context
     * @param listener The listener
     */
    public void checkWalletApiAvailability(Context context, OnWalletApiAvailabilityListener listener) {
        walletClient.getPayApiAvailabilityStatus(PayClient.RequestType.SAVE_PASSES)
                .addOnSuccessListener(new OnSuccessListener<Integer>() {
                    @Override
                    public void onSuccess(Integer status) {
                        if (status == PayApiAvailabilityStatus.AVAILABLE) {
                            listener.onWalletApiAvailable();
                        } else {
                            listener.onWalletApiUnavailable();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onWalletApiUnavailable();
                    }
                });
    }

    /**
     * Save a pass to the wallet
     * @param passJson The pass JSON
     * @param activity The activity
     */
    public void savePassToWallet(String passJson, Activity activity) {
        walletClient.savePasses(passJson, activity, LOAD_CARD_DATA_REQUEST_CODE);
    }

    /**
     * Handle the result of the activity
     * @param requestCode The request code
     * @param resultCode The result code
     * @param data The data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOAD_CARD_DATA_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // La tarjeta se guardó correctamente
                Toast.makeText(context, "Tarjeta guardada correctamente", Toast.LENGTH_SHORT).show();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // El guardado de la tarjeta fue cancelado
                Toast.makeText(context, "Guardado de tarjeta cancelado", Toast.LENGTH_SHORT).show();
            } else if (resultCode == PayClient.SavePassesResult.SAVE_ERROR) {
                // Se produjo un error al guardar la tarjeta
                if (data != null) {
                    String errorMessage = data.getStringExtra(PayClient.EXTRA_API_ERROR_MESSAGE);
                    Toast.makeText(context, "Error al guardar la tarjeta: " + errorMessage, Toast.LENGTH_SHORT).show();
                    Log.e("WalletApiManager", "Error al guardar la tarjeta: " + errorMessage);
                }
            } else {
                // Se produjo un error inesperado
                Toast.makeText(context, "Error inesperado al guardar la tarjeta", Toast.LENGTH_SHORT).show();
                Log.e("WalletApiManager", "Error inesperado al guardar la tarjeta");
            }
        }
    }

    /**
     * Interface to listen for Wallet API availability
     */
    public interface OnWalletApiAvailabilityListener {
        void onWalletApiAvailable();
        void onWalletApiUnavailable();
    }
}
