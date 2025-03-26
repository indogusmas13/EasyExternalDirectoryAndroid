package com.intishaka.easyexternaldirectoryandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.intishaka.easyexternaldirectoryandroid.activities.CameraActivity;
import com.intishaka.easyexternaldirectoryandroid.activities.DirectoryActivity;
import com.intishaka.easyexternaldirectoryandroid.activities.FileActivity;
import com.intishaka.easyexternaldirectoryandroid.activities.GaleryActivity;
import com.intishaka.easyexternaldirectoryandroid.activities.InternetActivity;
import com.intishaka.easyexternaldirectoryandroid.activities.ZipActivity;
import com.intishaka.easyexternaldirectoryandroid.databinding.ActivityMenuBinding;

public class MenuActivity extends AppCompatActivity {
    private ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnDirectory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DirectoryActivity.class));
            }
        });
        binding.btnFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FileActivity.class));
            }
        });
        binding.btnZip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ZipActivity.class));
            }
        });
        binding.btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CameraActivity.class));
            }
        });
        binding.btnGalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), GaleryActivity.class));
            }
        });
        binding.btnInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), InternetActivity.class));
            }
        });
    }
}