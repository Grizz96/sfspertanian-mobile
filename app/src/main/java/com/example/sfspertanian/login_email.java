package com.example.sfspertanian;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class login_email extends AppCompatActivity {
    private EditText editEmail, editPassword;
    private Button btnLogin;
    private TextView txtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        FirebaseAuth firebaseAuth;
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);

        firebaseAuth = FirebaseAuth.getInstance();

        btnLogin = findViewById(R.id.btnLogin);
        editEmail = findViewById(R.id.etEmail);
        editPassword = findViewById(R.id.etPassword);
        btnLogin.setOnClickListener(v->{
            String email = editEmail.getText().toString();
            String password = editPassword.getText().toString();

            if(!email.isEmpty() && !password.isEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    if (user != null) {
                                        // Login berhasil, arahkan ke MainActivity
                                        Intent intent = new Intent(login_email.this, MainActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(login_email.this, "Gagal mendapatkan informasi pengguna.", Toast.LENGTH_SHORT).show();
                                    }
                                }else{
                                    Toast.makeText(login_email.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                }
            });
        }else{
                Toast.makeText(this, "Password tidak sesuai", Toast.LENGTH_SHORT).show();
            }
        });

    }

}