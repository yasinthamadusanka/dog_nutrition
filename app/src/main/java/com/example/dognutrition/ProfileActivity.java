package com.example.dognutrition;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private TextView textViewUsername1, textViewUsername2, textViewEmail1, textViewEmail2, textViewAddress, textViewPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewUsername1 = findViewById(R.id.textView);
        textViewUsername2 = findViewById(R.id.textView23);
        textViewEmail1 = findViewById(R.id.textView2);
        textViewEmail2 = findViewById(R.id.textView25);
        textViewAddress = findViewById(R.id.textView28);
        textViewPhoneNumber = findViewById(R.id.textView27);

        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        fetchUserData(userId);
    }

    private void fetchUserData(String userId) {
        // Get reference to Firebase database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        // Read data from Firebase
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Extract data from the snapshot
                String username = dataSnapshot.child("userName").getValue(String.class);
                String email = dataSnapshot.child("email").getValue(String.class);
                String address = dataSnapshot.child("address").getValue(String.class);
                String phoneNumber = dataSnapshot.child("phoneNumber").getValue(String.class);

                // Set the data to the TextViews
                textViewUsername1.setText(username);
                textViewUsername2.setText(username);
                textViewEmail1.setText(email);
                textViewEmail2.setText(email);
                textViewAddress.setText(address);
                textViewPhoneNumber.setText(phoneNumber);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
