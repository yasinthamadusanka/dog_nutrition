package com.example.dognutrition;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    private EditText editTextName, editTextAddress, editTextPayment;
    private Button buttonSave;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        editTextName = findViewById(R.id.edit);
//        editTextAddress = findViewById(R.id.editTextAddress);
//        editTextPayment = findViewById(R.id.editTextPayment);
//        buttonSave = findViewById(R.id.buttonSave);
//
//        mAuth = FirebaseAuth.getInstance();
//        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
//
//        buttonSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveUserProfile();
//            }
//        });
//
//        // Load existing user profile information if needed
//        loadUserProfile();
    }

//    private void saveUserProfile() {
//        String userId = mAuth.getCurrentUser().getUid();
//        String name = editTextName.getText().toString().trim();
//        String address = editTextAddress.getText().toString().trim();
//        String paymentMethod = editTextPayment.getText().toString().trim();
//
//        if (name.isEmpty() || address.isEmpty() || paymentMethod.isEmpty()) {
//            Toast.makeText(ProfileActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        UserProfile userProfile = new UserProfile(name, address, paymentMethod);
//        databaseReference.child(userId).setValue(userProfile).addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                Toast.makeText(ProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(ProfileActivity.this, "Profile update failed", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

//    private void loadUserProfile() {
//        String userId = mAuth.getCurrentUser().getUid();
//        databaseReference.child(userId).get().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                UserProfile userProfile = task.getResult().getValue(UserProfile.class);
//                if (userProfile != null) {
//                    editTextName.setText(userProfile.getName());
//                    editTextAddress.setText(userProfile.getAddress());
//                    editTextPayment.setText(userProfile.getPaymentMethod());
//                }
//            }
//        });
//    }
}
