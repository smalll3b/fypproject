package com.example.fypproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class StorageActivity extends AppCompatActivity {

    private FirebaseStorage storage;
    private StorageReference storageRef;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_PERMISSION = 2;
    private ImageView imageView;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);

        // 初始化 Firebase Storage
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        // 初始化视图
        imageView = findViewById(R.id.imageView);
        Button selectImageButton = findViewById(R.id.selectImageButton);
        Button downloadImageButton = findViewById(R.id.downloadImageButton);

        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissions()) {
                    openImageSelector();
                } else {
                    requestPermissions();
                }
            }
        });

        downloadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadImageFromFirebase();
            }
        });

        // 处理从其他 Activity 传递过来的 Intent
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent != null && intent.getAction() != null && intent.getAction().equals(Intent.ACTION_SEND)) {
            Uri imageUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
            if (imageUri != null) {
                displayImage(imageUri);
                uploadImage(imageUri);
            }
        }
    }

    private void openImageSelector() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            displayImage(selectedImageUri);
            uploadImage(selectedImageUri);
        }
    }

    private void displayImage(Uri imageUri) {
        imageView.setImageURI(imageUri);
        imageView.setVisibility(View.VISIBLE);
    }

    private void uploadImage(Uri file) {
        // 创建一个文件引用
        StorageReference imagesRef = storageRef.child("images/" + Objects.requireNonNull(file.getLastPathSegment()));

        // 上传文件
        UploadTask uploadTask = imagesRef.putFile(file);

        // 注册监听器以获取上传进度
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // 上传成功
                Toast.makeText(StorageActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                Log.d("StorageActivity", "Image uploaded successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                // 上传失败
                Toast.makeText(StorageActivity.this, "Image upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("StorageActivity", "Image upload failed: " + exception.getMessage());
            }
        });
    }

    private void downloadImageFromFirebase() {
        // 创建存储引用
        StorageReference pathReference = storageRef.child("images/msf:18");

        // 获取下载 URL
        pathReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // 下载成功，显示图片
                Glide.with(StorageActivity.this)
                        .load(uri)
                        .into(imageView);
                imageView.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                // 下载失败
                Toast.makeText(StorageActivity.this, "Failed to download image: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("StorageActivity", "Failed to download image: " + exception.getMessage());
            }
        });
    }

    private boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImageSelector();
            } else {
                // Permission denied
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}