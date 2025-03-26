package com.intishaka.easyexternaldirectoryandroid.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.intishaka.easyexternaldirectoryandroid.R;
import com.intishaka.eeda.helper.imagePicker.FileCompressor;

import java.io.File;
import java.io.IOException;

public class GaleryActivity extends AppCompatActivity {

    //1
    static final int REQUEST_GALLERY_PHOTO = 3;
    File mPhotoFile;
    FileCompressor mCompressor;
    Button btnCamera;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galery);

        //2
        btnCamera = findViewById(R.id.btn_take_image_galery);
        imageView = findViewById(R.id.img);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchGalleryIntent();
            }
        });
    }

    //3
    private void dispatchGalleryIntent() {
        mCompressor = new FileCompressor(this);
        //   /storage/emulated/0/MyLibsTesting/Foto
        mCompressor.setDestinationDirectoryPath("/Foto");
        // int quality = 50;
        // mCompressor = new FileCompressor(this, quality);
        //diretori yang dibutuhkan akan lansung dibuatkan oleh fitur ini

        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO);
    }

    //4
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_GALLERY_PHOTO) {
                Uri selectedImage = data.getData();
                try {
                    mPhotoFile = mCompressor.compressToFile(selectedImage);
                    Glide.with(this).load(mPhotoFile).into(imageView);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}