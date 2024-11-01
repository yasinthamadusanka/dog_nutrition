package com.example.dognutrition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    private EditText username, email, address, password, confirmPassword,phone;
    private Button btnRegister;
    private TextView signInText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        confirmPassword = findViewById(R.id.c_password);
        btnRegister = findViewById(R.id.loginButton);
        ImageView passwordToggle = findViewById(R.id.password_toggle);
        signInText = findViewById(R.id.signInText);
        address = findViewById(R.id.address);

        passwordToggle.setOnClickListener(v -> {
            if (password.getInputType() == (android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                password.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                passwordToggle.setImageResource(R.drawable.ic_visibility); // Change to 'eye' open icon
            } else {
                password.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
                passwordToggle.setImageResource(R.drawable.ic_visibility_off); // Change to 'eye' closed icon
            }

            password.setSelection(password.getText().length());
        });

        btnRegister.setOnClickListener(v -> registerUser());

        signInText.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void registerUser() {
        String uName = username.getText().toString().trim();
        String uEmail = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String cPass = confirmPassword.getText().toString().trim();
        String uPhone = phone.getText().toString().trim();
        String uAddress = address.getText().toString().trim();

        if (TextUtils.isEmpty(uName)) {
            username.setError("Username is required");
            return;
        }
        if (TextUtils.isEmpty(uEmail) || !Patterns.EMAIL_ADDRESS.matcher(uEmail).matches()) {
            email.setError("Enter a valid email address");
            return;
        }
        if (TextUtils.isEmpty(uPhone) || !uPhone.matches("\\d{10}")) {
            phone.setError("Enter a valid phone number");
            return;
        }

        if (TextUtils.isEmpty(uAddress)) {
            address.setError("Address is required");
            return;
        }

        if (TextUtils.isEmpty(pass) || pass.length() < 6) {
            password.setError("Password must be at least 6 characters");
            return;
        }
        if (TextUtils.isEmpty(cPass) || !cPass.equals(pass)) {
            confirmPassword.setError("Passwords do not match");
            return;
        }


        mAuth.createUserWithEmailAndPassword(uEmail, pass)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String userId = user.getUid();

                            UserProfile newUser = new UserProfile(uName, uEmail, uPhone, uAddress);

                            databaseReference.child(userId).setValue(newUser)
                                    .addOnSuccessListener(aVoid -> {
                                        View rootView = findViewById(android.R.id.content);
                                        Snackbar.make(rootView, "User registered successfully", Snackbar.LENGTH_LONG)
                                                .setBackgroundTint(ContextCompat.getColor(this, R.color.green))
                                                .show();
                                        new android.os.Handler().postDelayed(() -> {
                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }, 500);

                                    })
                                    .addOnFailureListener(e -> {
                                        View rootView = findViewById(android.R.id.content);
                                        Snackbar.make(rootView, "Failed to add user data", Snackbar.LENGTH_LONG)
                                                .setBackgroundTint(ContextCompat.getColor(this, R.color.red))
                                                .show();
                                    });
                        }
                    } else {
                        View rootView = findViewById(android.R.id.content);
                        Snackbar.make(rootView, "Already registered", Snackbar.LENGTH_LONG)
                                .setBackgroundTint(ContextCompat.getColor(this, R.color.red))
                                .show();
                    }
                });
    }
}
