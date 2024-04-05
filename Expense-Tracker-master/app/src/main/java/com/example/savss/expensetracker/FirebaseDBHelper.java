package com.example.savss.expensetracker;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseDBHelper {
    private static DatabaseReference myRef;
    static FirebaseDatabase database = FirebaseDatabase.getInstance();

    // Method to add a new user to the Firebase database
    public static void addUser(String name, String email, String number, String password) {
        // Set up a reference to the Firebase database
        myRef = database.getReference("message");

        // Add a ValueEventListener to listen for changes in the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Handle data changes
                String value = dataSnapshot.getValue(String.class);
                // This method will be triggered when data changes in the database
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database error
                // This method will be triggered if the database operation is cancelled
            }
        });

        // Create a child node in the "Users" node with the user's phone number as the key
        String child = number;
        myRef = database.getReference("Users").child(child);
        
        // Set values for the user attributes (Name, Email, Number, Password)
        myRef.child("Email").setValue(email);
        myRef.child("Name").setValue(name);
        myRef.child("Number").setValue(number);
        myRef.child("Password").setValue(password);
    }
}
