package com.example.khanhho.kguide.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khanhho.kguide.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = null ;
    // Declaring our elements from our view
    EditText edt_email, edt_password;
    Button btn_login, btnFacebookLogin, btnGmailLogin;
    TextView link_login;
    static final int GOOGLE_SIGN =123;
    GoogleSignInClient mGoogleSignInClient;

    // Declaring the firebase lib.
    FirebaseAuth mAuth;
    DatabaseReference DBf;
    String currentUser;
    private SharedPreferences sharedPreferences;
    private DatabaseReference reference;
    String khanh = "2";

    @Override
    protected void onStart() {
        super.onStart();
        sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        if (sharedPreferences.getString("user", "").equals("guide")) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        } if (sharedPreferences.getString("user", "").equals("tourist")){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();


        edt_email = findViewById(R.id.email);
        edt_password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        link_login = findViewById(R.id.link_login);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        edt_password.setOnKeyListener(new View.OnKeyListener()
        {
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            btnClick();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnClick();
            }
        });

        link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, OptionActivity.class);
                startActivity(intent);
            }
        });
    }

    void btnClick() {
        String txt_email = edt_email.getText().toString();
        String txt_password = edt_password.getText().toString();

        if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
            Toast.makeText(LoginActivity.this, "All fields are required",
                    Toast.LENGTH_SHORT).show();

        } else {
            mAuth.signInWithEmailAndPassword(txt_email, txt_password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

//                                      read id of user
                                currentUser = mAuth.getCurrentUser().getUid();
                                DBf = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUser).child("status");
                                DBf.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.exists()) {
                                            String status = dataSnapshot.getValue().toString();
                                            if (status.equals("guide")) {
                                                SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString("user", status);
                                                editor.commit();
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);

                                            } else if (status.equals("newtourist")) {
                                                SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString("user", status);
                                                editor.commit();
                                                Intent intent = new Intent(LoginActivity.this, EditProfileActivity.class);
                                                startActivity(intent);
                                                Toast.makeText(LoginActivity.this, "Successful Login!", Toast.LENGTH_LONG).show();
                                            } else if (status.equals("tourist")) {
                                                SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString("user", status);
                                                editor.commit();
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                Toast.makeText(LoginActivity.this, "Successful Login!", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            } else {
                                Toast.makeText(LoginActivity.this, "Email or Username aren't correct",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, GOOGLE_SIGN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null){
            currentUser = mAuth.getCurrentUser().getUid();
            final String name = user.getDisplayName();
            final String email = user.getEmail();

            final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
            DatabaseReference myRef = database.child("Users");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                        if (messageSnapshot.getKey().equals(currentUser)){
                            khanh = "1";
                        }
                    }
                    if (khanh.equals("1")){
                        Log.d("khanhahha", "home");
                        Log.d("khanhahha", "ok");
                        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("user", "tourist");
                        editor.commit();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }else {
                        Log.d("khanhahha", "edit");
                        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("user", "tourist1");
                        editor.commit();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("email",email);
                        intent.putExtra("name",name);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            Toast.makeText(LoginActivity.this, "Successful Registration!",
                    Toast.LENGTH_SHORT).show();
        }else {
            Log.d("kiemtratoi", "khanh");
        }
    }



    public void FacebookLogin(View view) {

    }

    public void GmailLogin(View view) {
        signIn();
    }
}
