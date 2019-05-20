package com.example.khanhho.kguide.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khanhho.kguide.Model.Tourist;
import com.example.khanhho.kguide.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText edt_email, edtName, edt_password, edt_password2, edtSurname;
    Button btn_Register;
    TextView link_register;
    String currentUser;

    // Firebase Setup
    FirebaseAuth mAuth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtName = findViewById(R.id.edt_name);
        edtSurname = findViewById(R.id.edt_surname);
        edt_email = findViewById(R.id.email);
        edt_password = findViewById(R.id.password);
        edt_password2 = findViewById(R.id.password2);
        btn_Register = findViewById(R.id.btn_register);
        link_register = findViewById(R.id.link_register);

        mAuth = FirebaseAuth.getInstance();

        btn_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_username = edtName.getText().toString();
                String txt_email = edt_email.getText().toString();
                String txt_password = edt_password.getText().toString();
                String txt_password2 = edt_password2.getText().toString();

                if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_email) ||
                        TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(txt_password2)) {

                    Toast.makeText(RegisterActivity.this, "All fields are required",
                            Toast.LENGTH_SHORT).show();

                } else if (txt_password.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password has to be min. 6 chars. long",
                            Toast.LENGTH_SHORT).show();

                } else {
                    if (!txt_password.equals(txt_password2)) {
                        Toast.makeText(RegisterActivity.this, "Passwords must be the same",
                                Toast.LENGTH_SHORT).show();
                        return;

                    } else {
                        registerUser(txt_username, txt_email, txt_password);
                    }
                }

            }
        });

    }

    private void registerUser(final String username, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            assert firebaseUser != null;
                            String userId = firebaseUser.getUid();
                            currentUser = mAuth.getCurrentUser().getUid();
                            String name = edtName.getText().toString();
                            String surname = edtSurname.getText().toString();
                            String email = edt_email.getText().toString();

                            reference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser);

//                            HashMap<String, String> hashMap = new HashMap<>();
////                            hashMap.put("id", userId);
////                            hashMap.put("username", username);


                            Map map = new HashMap();
                            map.put("email",email);
                            map.put("name",name);
                            map.put("surname",surname);
                            map.put("status", "newtourist");
                            map.put("gender", "");
                            map.put("country", "");
                            map.put("address", "");
                            map.put("dayofbirth", "");
                            map.put("phonenumber", "");
                            map.put("jobposition", "");
                            map.put("language", "");
                            map.put("image", "");
                            reference.updateChildren(map);
                            Toast.makeText(RegisterActivity.this, "Successful Registration!",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(RegisterActivity.this, "That email is already taken",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}
