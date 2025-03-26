package com.intishaka.easyexternaldirectoryandroid.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.intishaka.easyexternaldirectoryandroid.R;
import com.intishaka.eeda.helper.FGZip;

public class ZipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zip);

        findViewById(R.id.btn_encode_base64_md5_to_zip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zipAction();
            }
        });
    }

    private void zipAction() {
        //   /storage/emulated/0/MyLibsTesting/ExternalBase64Md5ToZip.zip
        String fileName = "/ExternalBase64Md5ToZip.zip";

        //dari file zip diubah jadi base64
        //https://base64.guru/converter/encode/file
        String base64EncodeFromFile = "UEsDBBQAAAAIAJK6+FDfGqHQdAEAAABAAAAZAAAARXh0ZXJuYWxCYXNlNjRNZDVUb1ppcC5kYu3aQU7CQBQG4BlKgJJgWZh042JsQgIBTNQLiKYhRCgIJYqbZqRj0tgWgXIAbuQJvIk3MC516xRMhLowLiX/l2nmvXnpm25f0sFV24sEu5/MAh6xU1IklJIzxgghqnzS5Fs6kVPyO5UcHTwVZKDsPRPN03S5AQAAAAAAAPzRtZLR63U6jvidL3joziae6wQi4i6PeDJPX/TNhm0yu3HeNlmyysr+ZMx9wWzzxq70Uhm9WqWjVeP51JczsjMX04UIx8lU2WqbKJZDHoiazCrLfZrVSyW6fFj35MGjL5wfcWqrm7FZMlg5rxqea6gtyzabZp9ZXZtZw3a7Js/jiww1/vjN416/1Wn0R+zSHJXjV1ljaHdblrykY1p2JV9ZzebaC9HetVe5AQAAAAAAAMB/U1AUcti8FV5oLQIxy6UUosfZSY5+Rcfx/E+1NyIXAAAAAAAAAOyEIlVKdPOfAmU9/38QuQAAAAAAAABgt2RpShehMxx8AlBLAQI/ABQAAAAIAJK6+FDfGqHQdAEAAABAAAAZACQAAAAAAAAAgAAAAAAAAABFeHRlcm5hbEJhc2U2NE1kNVRvWmlwLmRiCgAgAAAAAAABABgAgEsYXNZh1gH+eSEy1mHWASKHEDLWYdYBUEsFBgAAAAABAAEAawAAAKsBAAAAAA==";

        //dari file zip diubah jadi md5
        //https://emn178.github.io/online-tools/md5_checksum.html
        String md5EncodeFromFile = "966af03a49f85b0df0afd3d9a42d0264";

        //   /storage/emulated/0/MyLibsTesting/zipLocation
        String zipLocation = "/zipLocation";
        //atau
        //   /storage/emulated/0/MyLibsTesting/
        //String zipLocation = "/"; // jika tidak mau diletakan dalam folder

        boolean overwriteExistingFiles = true;

        //decode string menjadi file dan extrack ke tujuan zipLocation
        //   /storage/emulated/0/MyLibsTesting/zipLocation
        if (FGZip.initFileFromStringToZipToFile(fileName, zipLocation, base64EncodeFromFile, md5EncodeFromFile, overwriteExistingFiles)) {
            Toast.makeText(this, "Success extract file", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Gagal extract file", Toast.LENGTH_SHORT).show();
        }
    }
}