package com.example.khanhho.kguide.Activities;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khanhho.kguide.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class DraffActivity extends AppCompatActivity {
    private Button UpdateProfile;
    private ImageView AvatarUser;
    private String currentUserID;
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private static final int GalleryPick = 1;
    private StorageReference UserProfileImageRef;
    private ProgressDialog loaddingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draff);
//        mAuth = FirebaseAuth.getInstance();
//        currentUserID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();
        UserProfileImageRef = FirebaseStorage.getInstance().getReference().child("Profile Images");
        InitializeField();
        GetUserInfo();

        AvatarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GalleryPick);
            }
        });
    }

    private void GetUserInfo() {
        RootRef.child("Users").child("73HVRyRQkQNsLpdidlOvWXdYZaf1")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if ((dataSnapshot.exists())&&(dataSnapshot.hasChild("name")&&dataSnapshot.hasChild("image"))){
                            String getDataUser = dataSnapshot.child("name").getValue().toString();
                            String getDataStatus = dataSnapshot.child("status").getValue().toString();
                            String getAvatarImage = dataSnapshot.child("image").getValue().toString();


                            Picasso.get().load(getAvatarImage).into(AvatarUser);
                        } else if ((dataSnapshot.exists())&&(dataSnapshot.hasChild("name"))){
                            String getDataUser = dataSnapshot.child("name").getValue().toString();
                            String getDataStatus = dataSnapshot.child("status").getValue().toString();


                        } else {
                            Toast.makeText(DraffActivity.this, "Please set & update your information ...",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
    }

    private void InitializeField() {
        UpdateProfile = findViewById(R.id.save);
//        EditInputUser = findViewById(R.id.editInputUser);
//        EditInputStatus = findViewById(R.id.editInputStatus);
        AvatarUser = findViewById(R.id.img_anh);
//        showUserName = findViewById(R.id.showUserName);
        loaddingBar = new ProgressDialog(this);
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
                    final StorageReference filePath = UserProfileImageRef.child("73HVRyRQkQNsLpdidlOvWXdYZaf1"+".jpg");
                    filePath.putFile(selectedImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){
                                filePath.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Uri> task) {
                                        final String downloadUrl = task.getResult().toString();
                                        RootRef.child("Users").child("73HVRyRQkQNsLpdidlOvWXdYZaf1").child("image")
                                                .setValue(downloadUrl)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){

                                                            Toast.makeText(DraffActivity.this,"Up ok",Toast.LENGTH_SHORT).show();
                                                            loaddingBar.dismiss();
                                                        }else {
                                                            String message = task.getException().toString();
                                                            Toast.makeText(DraffActivity.this,""+message,Toast.LENGTH_SHORT).show();
                                                            loaddingBar.dismiss();
                                                        }
                                                    }
                                                });
                                    }
                                });
                                loaddingBar.dismiss();
                            } else {
                                String message = task.getException().toString();
                                Toast.makeText(DraffActivity.this,""+message,Toast.LENGTH_SHORT).show();
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

}
