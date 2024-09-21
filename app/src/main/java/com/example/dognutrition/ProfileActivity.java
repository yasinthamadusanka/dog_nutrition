package com.example.dognutrition;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private TextView textViewUsername1, textViewUsername2, textViewEmail1, textViewEmail2, textViewAddress, textViewPhoneNumber, textViewChange;
    private Button editProfileButton;
    private ImageView imageView, imageView2;
    private FirebaseAuth firebaseAuth;
    private Spinner paymentSpinner;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }



        firebaseAuth = FirebaseAuth.getInstance();

        textViewUsername1 = findViewById(R.id.textView);
        textViewUsername2 = findViewById(R.id.textView23);
        textViewEmail1 = findViewById(R.id.textView2);
        textViewEmail2 = findViewById(R.id.textView25);
        textViewAddress = findViewById(R.id.textView28);
        textViewPhoneNumber = findViewById(R.id.textView27);
        editProfileButton = findViewById(R.id.edit_btn);
        imageView = findViewById(R.id.imageView5);
        textViewChange = findViewById(R.id.textView21);
        imageView2 = findViewById(R.id.imageView9);
        paymentSpinner = findViewById(R.id.paymentMethodSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.payment_methods, R.layout.spinner_item);

        adapter.setDropDownViewResource(R.layout.spinner_item);

        paymentSpinner.setAdapter(adapter);

        paymentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            private boolean isUserSelection = false;
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedPaymentMethod = parentView.getItemAtPosition(position).toString();

                if (isUserSelection) {
                    sendPaymentMethodToRealtimeDB(selectedPaymentMethod);
                } else {
                    isUserSelection = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

        textViewChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current user from FirebaseAuth
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (user != null) {
                    // Get the email address of the current user
                    String email = user.getEmail();

                    if (email != null && !email.isEmpty()) {
                        // Send password reset email
                        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(ProfileActivity.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(ProfileActivity.this, "No email address found for the current user", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProfileActivity.this, "No user is currently logged in", Toast.LENGTH_SHORT).show();
                }
            }
        });


        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        loadProfileImage();
        fetchUserData(userId);
    }

    private void sendPaymentMethodToRealtimeDB(String paymentMethod) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("users").child(userId);


        database.child("paymentMethod").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Update the existing payment method
                    database.child("paymentMethod").setValue(paymentMethod).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d("Firebase", "Payment method successfully updated");
                        } else {
                            Log.w("Firebase", "Failed to update payment method", task.getException());
                        }
                    });
                } else {
                    database.child("paymentMethod").setValue(paymentMethod).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d("Firebase", "Payment method successfully created");
                        } else {
                            Log.w("Firebase", "Failed to create payment method", task.getException());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error checking payment method: " + databaseError.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); 
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void loadProfileImage() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        databaseReference.child("profileImageUrl").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String imageUrl = dataSnapshot.getValue(String.class);
                    if (imageUrl != null) {
                        Glide.with(ProfileActivity.this)
                                .load(imageUrl)
                                .transform(new CircleTransformation())
                                .placeholder(R.drawable.profile_background)
                                .error(R.drawable.person)
                                .into(imageView);
                    }
                }else{
                    imageView.setImageResource(R.drawable.profile_background);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileActivity.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void fetchUserData(String userId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String username = dataSnapshot.child("userName").getValue(String.class);
                String email = dataSnapshot.child("email").getValue(String.class);
                String address = dataSnapshot.child("address").getValue(String.class);
                String phoneNumber = dataSnapshot.child("phoneNumber").getValue(String.class);
                String paymentMethod = dataSnapshot.child("paymentMethod").getValue(String.class);

                textViewUsername1.setText(username);
                textViewUsername2.setText(username);
                textViewEmail1.setText(email);
                textViewEmail2.setText(email);
                textViewAddress.setText(address);
                textViewPhoneNumber.setText(phoneNumber);

                if (paymentMethod != null) {
                    int spinnerPosition = ((ArrayAdapter<String>) paymentSpinner.getAdapter()).getPosition(paymentMethod);
                    paymentSpinner.setSelection(spinnerPosition);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
            }
        });
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to EditProfileActivity
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });
    }

}
