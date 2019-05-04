package com.example.khanhho.kguide.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khanhho.kguide.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    // Declaring our elements from our view
    EditText edt_email, edt_password;
    Button btn_login;
    TextView link_login;

    // Declaring the firebase lib.
    FirebaseAuth mAuth;
    DatabaseReference DBf;
    String currentUser;

    @Override
    protected void onStart() {
        super.onStart();

        // Automatic login
        if(mAuth.getCurrentUser() != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
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

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                                if (dataSnapshot.exists())
                                                {
                                                    String status = dataSnapshot.getValue().toString();
                                                    if(status.equals("guide")){
                                                        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        editor.putString("user", status);
                                                        editor.commit();
                                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent);

                                                    }else if (status.equals("tourist")){
                                                        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                                        editor.putString("user", status);
                                                        editor.commit();
                                                        Intent intent = new Intent(LoginActivity.this, EditProfileActivity.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        startActivity(intent);
                                                        Toast.makeText(LoginActivity.this, "Successful Login!", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

//                                        Toast.makeText(LoginActivity.this, "Successful Login!" + currentUser,
//                                                Toast.LENGTH_SHORT).show();
//                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(LoginActivity.this, "Email or Username aren't correct",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }});
                            }

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
}
