package com.intishaka.easyexternaldirectoryandroid.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.intishaka.easyexternaldirectoryandroid.databinding.ActivityFileBinding;
import com.intishaka.eeda.helper.FGDir;
import com.intishaka.eeda.helper.FGFile;

import java.util.List;

public class FileActivity extends AppCompatActivity {
    private ActivityFileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnCreateFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCreateFileAction();
            }
        });
        binding.btnIsFileExists.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnIsFileExistsAction();
            }
        });
        binding.btnDeleteFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDeleteFileAction();
            }
        });
        binding.btnDeleteAllFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDeleteAllFileAction();
            }
        });
        binding.btnReadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnReadFileAction();
            }
        });
        binding.btnAppenttext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAppenttextAction();
            }
        });
    }

    private void btnCreateFileAction() {
        //String[] data = {"Hallo GZeinNumer Again", "File Creating","File Created"};
        String[] data = new String[]{"Hallo GZeinNumer Again", "File Creating", "File Created"};

        //buat file dalam folder App
        //   /storage/emulated/0/MyLibsTesting/MyFile.txt
        String fileName = "/MyFile.txt";
        String saveTo = "/";
        if (FGFile.initFile(fileName, saveTo, data)) {
            Toast.makeText(this, "File MyFile.txt created", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "File MyFile.txt not created", Toast.LENGTH_SHORT).show();
        }
    }

    private void btnIsFileExistsAction() {
        boolean isExists = FGFile.isFileExists("/MyFile.txt");
        Toast.makeText(this, "File MyFile.txt exists : " + isExists, Toast.LENGTH_SHORT).show();
    }

    private void btnDeleteFileAction() {
        boolean isDeleted = FGFile.deleteDir("/MyFile.txt");
        Toast.makeText(this, "File MyFile.txt deleted : " + isDeleted, Toast.LENGTH_SHORT).show();
    }

    private void btnDeleteAllFileAction() {
        FGFile.deleteAllFile("/folder1");
        Toast.makeText(this, "/folder1 deleted : true", Toast.LENGTH_SHORT).show();
    }

    private void btnReadFileAction() {
        boolean isExists = FGFile.isFileExists("/MyFile.txt");
        if (isExists) {
            // /storage/emulated/0/MyLibsTesting/MyFile.txt
            List<String> list = FGFile.readFile("/MyFile.txt");
            String value_0 = list.get(0);
            Toast.makeText(this, "List size : " + list.size(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "File MyFile.txt not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void btnAppenttextAction() {
        //   /storage/emulated/0/MyLibsTesting/MyFile.txt
        String path = "/MyFile.txt";
        if (FGDir.isFileExists(path)) {
            String[] messages = {"Pesan ini akan ditambahkan ke file di line baru 1", "Pesan ini akan ditambahkan ke file di line baru 2"};
            //function untuk menambah text ke file yang sudah dibuat sebelumnya
            if (FGFile.appentText(path, messages)) {
                Toast.makeText(this, "Added new line in MyFile.txt", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error add new line", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "File MyFile.txt not found", Toast.LENGTH_SHORT).show();
        }
    }
}