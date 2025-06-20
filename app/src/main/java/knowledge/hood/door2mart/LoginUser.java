package knowledge.hood.door2mart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import knowledge.hood.door2mart.Models.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginUser extends AppCompatActivity {

    EditText mobileNum;
    Button login;
    TextView waittxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_user);
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this);
        }


     //   cheackLoginCredential();
        mobileNum = findViewById(R.id.mobileNum);
        login = findViewById(R.id.login);
        waittxt = findViewById(R.id.waittxt);
        String mobile = mobileNum.getText().toString();
        ProgressBar progressBar = findViewById(R.id.processbar_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((mobileNum.getText().toString().trim()).length() == 10) {
                    progressBar.setVisibility(View.VISIBLE);
                    login.setVisibility(View.GONE);
                    waittxt.setTextColor(Color.WHITE);
                    waittxt.setText("Please wait...");
                    waittxt.setVisibility(View.VISIBLE);

//                    PhoneAuthOptions options =
//                            PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
//                                    .setPhoneNumber("+91" + mobileNum.getText().toString().trim())
//                                    .setTimeout(60L, TimeUnit.SECONDS)
//                                    .setActivity(LoginUser.this)
//                                            .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                                                @Override
//                                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                                                    progressBar.setVisibility(View.GONE);
//                                                    login.setVisibility(View.VISIBLE);
//                                                    waittxt.setVisibility(View.GONE);
//                                                }
//
//                                                @Override
//                                                public void onVerificationFailed(@NonNull FirebaseException e) {
//                                                    progressBar.setVisibility(View.GONE);
//                                                    login.setVisibility(View.VISIBLE);
//                                                    waittxt.setText("Try Again");
//                                                    waittxt.setTextColor(Color.RED);
//                                                }
//
//                                                @Override
//                                                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                                                    super.onCodeSent(s, forceResendingToken);
//                                                    Intent intent = new Intent(LoginUser.this, UserVerification.class);
//                                                    intent.putExtra("mobileNum", mobileNum.getText().toString());
//                                                    intent.putExtra("otp", s);
//                                                    LoginUser.this.startActivity(intent);
//                                                }
//                                            }).build();

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91" + mobileNum.getText().toString().trim(),
                            90,
                            TimeUnit.SECONDS,
                            LoginUser.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    progressBar.setVisibility(View.GONE);
                                    login.setVisibility(View.VISIBLE);
                                    waittxt.setVisibility(View.GONE);
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    progressBar.setVisibility(View.GONE);
                                    login.setVisibility(View.VISIBLE);
                                    waittxt.setText("OTP Sending Failure! Kindly use another method.");
                                    waittxt.setTextColor(Color.RED);
                                   // waittxt.setText("Verification failed: " + e.getMessage());  // Show full error
                                    Log.e("OTP_ERROR", "onVerificationFailed: ", e);             // Logcat print
                                }

                                @Override
                                public void onCodeSent(@NonNull String otp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    Intent intent = new Intent(LoginUser.this, UserVerification.class);
                                    intent.putExtra("mobileNum", mobileNum.getText().toString());
                                    intent.putExtra("otp", otp);
                                    LoginUser.this.startActivity(intent);

                                }
                            }
                    );
                  //  PhoneAuthProvider.verifyPhoneNumber(options);

                } else {
                    waittxt.setTextColor(Color.RED);
                    waittxt.setText("Please enter 10 digit Number");
                    waittxt.setVisibility(View.VISIBLE);
                }

            }
        });

        findViewById(R.id.skipnow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginUser.this, MainActivity.class);
                LoginUser.this.startActivity(intent);
            }
        });
    }

//    private void cheackLoginCredential() {
//        SharedPreferences sharedPreferences = getSharedPreferences("credential",MODE_PRIVATE);
//        if (sharedPreferences.contains("user_mobile")) {
//            Intent intent = new Intent(LoginUser.this, MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            LoginUser.this.startActivity(intent);
//        }
//    }
}