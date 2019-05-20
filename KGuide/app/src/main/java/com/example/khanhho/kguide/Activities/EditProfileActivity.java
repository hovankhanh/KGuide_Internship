package com.example.khanhho.kguide.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khanhho.kguide.Model.Tour;
import com.example.khanhho.kguide.Model.Tourist;
import com.example.khanhho.kguide.R;
import com.google.android.gms.tasks.OnCompleteListener;
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
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {
   private FirebaseAuth mAuth;
   private DatabaseReference DBf;
   private Tourist tourist;
   CircleImageView nAvatar;
   private StorageReference UserProfileImageRef;
   public Uri imageUri;
   private String currentUser;
    private static final int GalleryPick = 1;
   private ProgressDialog loaddingBar;

   private EditText edName, edSurname, edGender, edCountry, edLanguage, edDayOfBirth, edAddress, edJobPosition, edPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        nAvatar = (CircleImageView) findViewById(R.id.civ_avatar_edit);
        edName = (EditText)findViewById(R.id.edt_name);
        edSurname = (EditText)findViewById(R.id.edt_surname);
        edGender = (EditText)findViewById(R.id.edt_gender);
        edCountry = (EditText)findViewById(R.id.edt_country);
        edLanguage = (EditText)findViewById(R.id.edt_language);
        edDayOfBirth = (EditText)findViewById(R.id.edt_dayofbirth);
        edAddress = (EditText)findViewById(R.id.edt_address);
        edJobPosition = (EditText)findViewById(R.id.edt_jobposition);
        edPhoneNumber = (EditText)findViewById(R.id.edt_phonenumber);
        loaddingBar = new ProgressDialog(this);

        UserProfileImageRef = FirebaseStorage.getInstance().getReference().child("Profile Images");
        DBf = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        GetUserInfo();

        nAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GalleryPick);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                loaddingBar.setTitle("Set Profile Image");
                loaddingBar.setMessage("Pleas wait, your profile image is updating ...");
                loaddingBar.setCanceledOnTouchOutside(false);
                loaddingBar.show();
                if (requestCode == GalleryPick) {
                    Uri selectedImageUri = data.getData();
                    final String path = getPathFromURI(selectedImageUri);
                    if (path != null) {
                        File f = new File(path);
                        selectedImageUri = Uri.fromFile(f);
                    }
                    final StorageReference filePath = UserProfileImageRef.child(currentUser+".jpg");
                    filePath.putFile(selectedImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){
                                filePath.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        final String downloadUrl = task.getResult().toString();
                                        DBf.child("Users").child(currentUser).child("image")
                                                .setValue(downloadUrl)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            Toast.makeText(EditProfileActivity.this,"Up ok",Toast.LENGTH_SHORT).show();
                                                            loaddingBar.dismiss();
                                                        }else {
                                                            String message = task.getException().toString();
                                                            Toast.makeText(EditProfileActivity.this,""+message,Toast.LENGTH_SHORT).show();
                                                            loaddingBar.dismiss();
                                                        }
                                                    }
                                                });
                                    }
                                });
                                loaddingBar.dismiss();
                            } else {
                                String message = task.getException().toString();
                                Toast.makeText(EditProfileActivity.this,""+message,Toast.LENGTH_SHORT).show();
                                loaddingBar.dismiss();
                            }
                        }
                    });

                }
            }
        } catch (Exception e) {
            Log.e("FileSelectorActivity", "File select error", e);
        }
    }

    public void Save(View view) {
        UpdateInfoProfile();
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    private void UpdateInfoProfile() {
        String nName = edName.getText().toString();
        String nSurname = edSurname.getText().toString();
        String nGender = edGender.getText().toString();
        String nCountry = edCountry.getText().toString();
        String nLanguage = edLanguage.getText().toString();
        String nDayOfBirth = edDayOfBirth.getText().toString();
        String nAddress = edAddress.getText().toString();
        String nJobPosition = edJobPosition.getText().toString();
        String nPhoneNumber = edPhoneNumber.getText().toString();
        loaddingBar = new ProgressDialog(this);


        if (TextUtils.isEmpty(nName)|| TextUtils.isEmpty(nSurname)
                || TextUtils.isEmpty(nGender) || TextUtils.isEmpty(nCountry)
                || TextUtils.isEmpty(nLanguage) || TextUtils.isEmpty(nDayOfBirth)
                || TextUtils.isEmpty(nAddress) || TextUtils.isEmpty(nJobPosition)
                || TextUtils.isEmpty(nPhoneNumber)){
            Toast.makeText(this, "Please enter your information ...", Toast.LENGTH_SHORT).show();
        } else {
            HashMap<String,Object> profileMap = new HashMap<>();
            profileMap.put("name", nName);
            profileMap.put("surname", nSurname);
            profileMap.put("gender", nGender);
            profileMap.put("country", nCountry);
            profileMap.put("language", nLanguage);
            profileMap.put("dayofbirth", nDayOfBirth);
            profileMap.put("address", nAddress);
            profileMap.put("jobposition", nJobPosition);
            profileMap.put("phonenumber", nPhoneNumber);
            profileMap.put("status", "tourist");
            currentUser = mAuth.getCurrentUser().getUid();
            DBf.child("Users").child(currentUser).updateChildren(profileMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(EditProfileActivity.this,"Update info successfully ...", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                                String message = task.getException().toString();
                                Toast.makeText(EditProfileActivity.this,"Error: "+message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    private void GetUserInfo() {
        final DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        DatabaseReference myRef = database.child("Users");

        myRef.child(currentUser).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tourist = dataSnapshot.getValue(Tourist.class);
                edName.setText(tourist.getName().toString());
                edSurname.setText(tourist.getSurname().toString());
                if (tourist.getImage().toString() != null) {
                    String getAvatarImage = tourist.getImage().toString();
                    Picasso.get().load(getAvatarImage).into(nAvatar);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void alertView( String message ) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle( "Confirm" )
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {

                    }
                }).setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }
}
