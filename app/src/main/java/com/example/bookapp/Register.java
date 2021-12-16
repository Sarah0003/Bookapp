package com.example.bookapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
 EditText rgPassword, rgUsername,rgEmail;
 Button Register;
 FirebaseAuth fAuth;
    TextView login_btn;
ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        rgUsername=(EditText)findViewById(R.id.rgUsername);
        rgPassword=(EditText)findViewById(R.id.rgPassword);
        rgEmail=(EditText)findViewById(R.id.rgEmail);
        Register=(Button) findViewById(R.id.Register);
        login_btn=  findViewById(R.id.Already);
        progressBar=findViewById(R.id.progressBar);
        fAuth=FirebaseAuth.getInstance();

        //if current user is already regiesterd

        if(fAuth.getCurrentUser() != null){
           startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }





Register.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String Email = rgEmail.getText().toString();
        String Password = rgPassword.getText().toString();


        if (TextUtils.isEmpty(Email)) {
            rgEmail.setError("Email is required");
            return;
        }
        if (TextUtils.isEmpty(Password)) {
            rgPassword.setError("password is required");
            return;
        }
        if (Password.length() < 6) {
            rgPassword.setError("password should be >= 6");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        fAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Register.this, "Registering Succeeded", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                } else {
                    Toast.makeText(Register.this, "Registering Failed" +task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);

                }


            }

        });
    }
});


        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });


    }

}