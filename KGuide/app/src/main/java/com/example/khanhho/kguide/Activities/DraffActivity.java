package com.example.khanhho.kguide.Activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.khanhho.kguide.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;


public class DraffActivity extends AppCompatActivity {
    private TextView nhap;
    private ImageView imgAnh;
    private StorageReference mStorageRef;
    int Request_Code_Image = 1;
    public Uri imageUri;
    private StorageTask uploadTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draff);
        nhap = (TextView) findViewById(R.id.nhap);
        imgAnh =(ImageView) findViewById(R.id.img_anh);

        nhap.setText("hsaha");
        mStorageRef = FirebaseStorage.getInstance().getReference();


    }

    public void test(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        startActivityForResult(intent, Request_Code_Image);
    }

    public void save(View view) {
        if (uploadTask != null && uploadTask.isInProgress()){
            Toast.makeText(DraffActivity.this, "Uploading...",Toast.LENGTH_LONG).show();
        }else {
            fileUploader();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == Request_Code_Image && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            imgAnh.setImageURI(imageUri);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private String getExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));

    }

    private void fileUploader() {
        StorageReference Ref = mStorageRef.child(System.currentTimeMillis()+"."+getExtension(imageUri));
        uploadTask = Ref.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
//                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(DraffActivity.this, "image uplaod succesfully", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });

    }

}
