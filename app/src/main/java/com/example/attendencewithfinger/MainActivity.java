package com.example.attendencewithfinger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private TextView paraLabel;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private Executor executor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paraLabel = findViewById(R.id.paraLabel);

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                paraLabel.setText("Error");
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                paraLabel.setText("Success");
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                paraLabel.setText("Failed");
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Attendence Finger Print")
                .setNegativeButtonText("Cancel / Use Password")
                .setConfirmationRequired(false)
                .build();

    }
    public  void buttonAuthenticate(View view){

        BiometricManager biometricManager = BiometricManager.from(this);
        if(biometricManager.canAuthenticate() != BiometricManager.BIOMETRIC_SUCCESS){
            paraLabel.setText("Biometric not Supported");
            return;
        }
        biometricPrompt.authenticate(promptInfo);

    }
}