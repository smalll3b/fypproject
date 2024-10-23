package com.example.fypproject;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UploadData {
    FirebaseDatabase db = FirebaseDatabase.getInstance("https://se-fyp-project-default-rtdb.asia-southeast1.firebasedatabase.app");
    //FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase = db.getReference();

    public void createProduct(Product product) {
        //automatically generate productID
        //
        //
        mDatabase.child("products").child("0").setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
              //  success
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // failure
            }
        });
    }
}
