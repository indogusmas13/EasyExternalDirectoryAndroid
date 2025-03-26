package com.intishaka.easyexternaldirectoryandroid.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.intishaka.easyexternaldirectoryandroid.databinding.ActivityDirectoryBinding;
import com.intishaka.eeda.helper.FGDir;

public class DirectoryActivity extends AppCompatActivity {
    private ActivityDirectoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDirectoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCreateFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFolder();
            }
        });

        binding.btnIsFolderExists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                existsFolder();
            }
        });

        binding.btnDeleteFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFolder();
            }
        });
    }

    private void createFolder() {
        // /storage/emulated/0/MyLibsTesting/folder1
        // /storage/emulated/0/MyLibsTesting/folder1/folder1_1
        // /storage/emulated/0/MyLibsTesting/folder2

        // String[] folders = {"/folder1","/folder1/folder1_1","/folder2"};
        String[] folders = new String[]{"/folder1", "/folder1/folder1_1", "/folder2"};

        if (FGDir.initFolder(folders)) {
            Toast.makeText(this, "Folder sudah dibuat dan ditemukan sudah bisa lanjut", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Permition Required", Toast.LENGTH_SHORT).show();
        }
    }

    private void existsFolder() {
        boolean isExists = FGDir.isFileExists("/folder1/folder1_1");
        Toast.makeText(this, "/folder1/folder1_1 exists : " + isExists, Toast.LENGTH_SHORT).show();
    }

    private void deleteFolder() {
        boolean isDeleted = FGDir.deleteDir("/folder1/folder1_1");
        Toast.makeText(this, "/folder1/folder1_1 delete : " + isDeleted, Toast.LENGTH_SHORT).show();
    }
}