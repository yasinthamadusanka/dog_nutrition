package com.example.dognutrition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    private EditText editTextUserName, editTextEmail, editTextAddress, editTextPhoneNumber;
    private Button buttonUpdate;
    private DatabaseReference databaseReference;
    private String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView profileImageView;
    private Button changeProfileImageButton;
    private Uri imageUri;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editTextUserName = findViewById(R.id.editTextUserName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        profileImageView = findViewById(R.id.profileImageView);
        changeProfileImageButton = findViewById(R.id.changeProfileImageButton);
        storageReference = FirebaseStorage.getInstance().getReference("profile_pictures");

        changeProfileImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        fetchData();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateData();
                uploadImageToFirebase();
            }
        });
    }

    private void uploadImageToFirebase() {
        if (imageUri != null) {
            StorageReference fileReference = storageReference.child(System.currentTimeMillis() + ".jpg");

            fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();
                                    // Save this image URL to Firebase Realtime Database under the user's profile
                                    updateProfileImageUrl(imageUrl);
                                }
                            });
                        }
                    })
                    .addOnFailureListener(e -> {
                        // Handle the error
                    });
        }
    }

    private void updateProfileImageUrl(String imageUrl) {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userId);

        databaseReference.child("profileImageUrl").setValue(imageUrl).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Image URL updated successfully, you can show a toast or navigate back to the profile page
            } else {
                // Handle failure
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profileImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), PICK_IMAGE_REQUEST);
    }

    private void fetchData() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve existing data
                    String userName = dataSnapshot.child("userName").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);
                    String address = dataSnapshot.child("address").getValue(String.class);
                    String phoneNumber = dataSnapshot.child("phoneNumber").getValue(String.class);

                    // Set the data to the EditText fields
                    editTextUserName.setText(userName);
                    editTextEmail.setText(email);
                    editTextAddress.setText(address);
                    editTextPhoneNumber.setText(phoneNumber);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(EditProfileActivity.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateData() {
        // Get updated values from EditText fields
        String updatedUserName = editTextUserName.getText().toString().trim();
        String updatedEmail = editTextEmail.getText().toString().trim();
        String updatedAddress = editTextAddress.getText().toString().trim();
        String updatedPhoneNumber = editTextPhoneNumber.getText().toString().trim();

        // Create a HashMap to update the values
        Map<String, Object> updates = new HashMap<>();
        updates.put("userName", updatedUserName);
        updates.put("email", updatedEmail);
        updates.put("address", updatedAddress);
        updates.put("phoneNumber", updatedPhoneNumber);

        // Update the data in Firebase
        databaseReference.updateChildren(updates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(EditProfileActivity.this, "Profile updated successfully.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(EditProfileActivity.this, "Failed to update profile.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}