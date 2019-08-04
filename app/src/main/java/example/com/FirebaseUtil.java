package example.com;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by KihiuFrank
 * Copyright © 2019
 * franklinekihiu@gmail.com
 */
public class FirebaseUtil {
    public static FirebaseDatabase firebaseDatabase;
    public static DatabaseReference databaseReference;
    public static FirebaseAuth firebaseAuth;
    public static FirebaseAuth.AuthStateListener authListener;
    private static FirebaseUtil firebaseUtil;
    public static ArrayList<TravelDeal> mDeals;
    private static final int RC_SIGN_IN = 123;
    private static Activity caller;

    private FirebaseUtil() { }

    public static void openFirebaseReference(String ref, final Activity callerActivity) {
        if (firebaseUtil == null) {
            firebaseUtil = new FirebaseUtil();
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseAuth = FirebaseAuth.getInstance();
            caller = callerActivity;
            authListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUtil.signIn();
                    Toast.makeText(callerActivity.getBaseContext(), "Welcome Back!", Toast.LENGTH_LONG).show();
                }
            };
        }
        mDeals = new ArrayList<TravelDeal>();
        databaseReference = firebaseDatabase.getReference().child(ref);
    }

    private static void signIn() {

        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build());

// Create and launch sign-in intent
        caller.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    public static void attachListener() {
        firebaseAuth.addAuthStateListener(authListener);
    }

    public static void detachListener() {
        firebaseAuth.removeAuthStateListener(authListener);
    }


}
