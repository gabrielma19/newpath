package com.example.user.newpath.DAO;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by FirebaseConfig on 30/05/2017.
 */

public class FirebaseConfig {

    private static DatabaseReference databaseReference;
    private static FirebaseDatabase firebaseDatabase;
    private static FirebaseAuth firebaseAuth;

    public static DatabaseReference getFirebase() {
        if (databaseReference == null ){
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }
    public static  FirebaseAuth getFirebaseAuth(){
        if (firebaseAuth == null ){
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }
}
