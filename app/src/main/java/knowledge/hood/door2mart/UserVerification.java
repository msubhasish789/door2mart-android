package knowledge.hood.door2mart;

import static android.content.Context.MODE_PRIVATE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import knowledge.hood.door2mart.Models.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserVerification extends AppCompatActivity {

    Button verify_btn;
    EditText txt1, txt2, txt3, txt4, txt5, txt6;
    TextView mobileno;
    String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_verification);

        String mobileNum = getIntent().getStringExtra("mobileNUm");
        otp = getIntent().getStringExtra("otp");

        ProgressBar progressBar = findViewById(R.id.progressBar);

        verify_btn = findViewById(R.id.verify_btn);
        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txt3 = findViewById(R.id.txt3);
        txt4 = findViewById(R.id.txt4);
        txt5 = findViewById(R.id.txt5);
        txt6 = findViewById(R.id.txt6);
        mobileno = findViewById(R.id.mobileNo);

        mobileno.setText(getIntent().getStringExtra("mobileNum"));

        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                verify_btn.setVisibility(View.GONE);
                if (!txt1.getText().toString().trim().isEmpty() &&
                        !txt2.getText().toString().trim().isEmpty() &&
                        !txt3.getText().toString().trim().isEmpty() &&
                        !txt4.getText().toString().trim().isEmpty() &&
                        !txt5.getText().toString().trim().isEmpty() &&
                        !txt6.getText().toString().trim().isEmpty()) {
                    String enteredCode = txt1.getText().toString().trim() +
                            txt2.getText().toString().trim() +
                            txt3.getText().toString().trim() +
                            txt4.getText().toString().trim() +
                            txt5.getText().toString().trim() +
                            txt6.getText().toString().trim();

                    if (enteredCode != null) {
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                                otp, enteredCode
                        );

                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                        //    Toast.makeText(UserVerification.this, "otp matched", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(UserVerification.this, MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            intent.putExtra("mobileNum", mobileno.getText().toString());
                                            SharedPreferences sharedPreferences = getSharedPreferences("credential", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("user_mobile", mobileno.getText().toString());
                                            editor.apply();
                                           // addUser(mobileno.getText().toString());
                                            UserVerification.this.startActivity(intent);
                                        } else {
                                            progressBar.setVisibility(View.GONE);
                                            verify_btn.setVisibility(View.VISIBLE);
                                            Toast.makeText(UserVerification.this, "Please enter correct otp", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else {
                        progressBar.setVisibility(View.GONE);
                        verify_btn.setVisibility(View.VISIBLE);
                        Toast.makeText(UserVerification.this, "Please Check internet connection", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    progressBar.setVisibility(View.GONE);
                    verify_btn.setVisibility(View.VISIBLE);
                    Toast.makeText(UserVerification.this, "Please fill all field", Toast.LENGTH_SHORT).show();
                }

            }
        });

        findViewById(R.id.resendOTP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + mobileno.getText().toString(),
                        90,
                        TimeUnit.SECONDS,
                        UserVerification.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                            }

                            @Override
                            public void onCodeSent(@NonNull String newotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                otp = newotp;
                                Toast.makeText(UserVerification.this, "OTP Resend Succesfully", Toast.LENGTH_SHORT).show();

                            }
                        }
                );
            }
        });

        numberotpmove();
    }

    private void numberotpmove() {
        txt1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty())
                    txt2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txt2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty())
                    txt3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txt3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty())
                    txt4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txt4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty())
                    txt5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        txt5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty())
                    txt6.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

//    private void addUser(String mobile) {
//        SharedPreferences sharedPreferences = getSharedPreferences("credential", MODE_PRIVATE);
//        Call<UserModel> call = apicontroler.getInstance().getapi().addUser(null, mobile, null, null, null, "1");
//        call.enqueue(new Callback<UserModel>() {
//            @Override
//            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
//                UserModel data = response.body();
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("user_id", data.getUser_id());
//                editor.apply();
//            }
//
//            @Override
//            public void onFailure(Call<UserModel> call, Throwable t) {
//                Toast.makeText(UserVerification.this, t.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
}