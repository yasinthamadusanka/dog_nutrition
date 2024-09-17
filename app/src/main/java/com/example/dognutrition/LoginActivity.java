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

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText email, password;
    private Button btnLogin;
    private ImageView passwordToggle;
    private TextView signUpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.loginButton);
        passwordToggle = findViewById(R.id.password_toggle);
        signUpText = findViewById(R.id.signupText);

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

        btnLogin.setOnClickListener(v -> loginUser());

        signUpText.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        // Get input values
        String uEmail = email.getText().toString().trim();
        String uPassword = password.getText().toString().trim();

        // Input validation
        if (TextUtils.isEmpty(uEmail) || !Patterns.EMAIL_ADDRESS.matcher(uEmail).matches()) {
            email.setError("Enter a valid email address");
            return;
        }
        if (TextUtils.isEmpty(uPassword)) {
            password.setError("Password is required");
            passwordToggle.setVisibility(View.INVISIBLE);
            return;
        } else {
            passwordToggle.setVisibility(View.VISIBLE);
        }

        mAuth.signInWithEmailAndPassword(uEmail, uPassword)
                .addOnCompleteListener(this, task -> {
                    View rootView = findViewById(android.R.id.content);
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            Snackbar.make(rootView, "Login successful!", Snackbar.LENGTH_LONG)
                                    .setBackgroundTint(ContextCompat.getColor(this, R.color.green))
                                    .show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Snackbar.make(rootView, "Credentials are incorrect: ", Snackbar.LENGTH_SHORT)
                                .setBackgroundTint(ContextCompat.getColor(this, R.color.red))
                                .show();
                    }
                });
        }
    }