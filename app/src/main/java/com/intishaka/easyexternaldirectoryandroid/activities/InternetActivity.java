package com.intishaka.easyexternaldirectoryandroid.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.intishaka.easyexternaldirectoryandroid.R;
import com.intishaka.eeda.helper.FGFile;

public class InternetActivity extends AppCompatActivity {

    public static final String TAG = "InternetActivity";

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet);

        imageView = findViewById(R.id.img);

        findViewById(R.id.btn_download_image_internet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSuccessCheckPermitions();
            }
        });
    }


    private void onSuccessCheckPermitions() {
        String imgUrl = "https://avatars3.githubusercontent.com/u/45892408?s=460&u=94158c6479290600dcc39bc0a52c74e4971320fc&v=4";
        String saveTo = "/Foto_Download"; //   /storage/emulated/0/MyLibsTesting/Foto_Download
//        String saveTo = "/"; //   /storage/emulated/0/MyLibsTesting/     //Jika tidak mau diletakan dalam folder
        String fileName = "file name.jpg";

        // jika file name ada di akhir link seperti dibawah, maka kamu bsa gunakan cara seperti ini
        // imgUrl = "https://helpx.adobe.com/content/dam/help/en/stock/how-to/visual-reverse-image-search/jcr_content/main-pars/image/visual-reverse-image-search-v2_intro.jpg";
        // String fileName = imgUrl.substring(url.lastIndexOf('/') + 1, url.length());

        boolean isNew = false;
        //pilih 1 atau 2
        //1. jika isNew true maka file lama akan dihapus dan diganti dengan yang baru.
        //2. jika isNew false maka akan otomatis load file dan disimpan, tapi jika file belum ada, maka akan tetap didownload.
        FGFile.initFileImageFromInternet(getApplicationContext(), imgUrl, saveTo, fileName, isNew, new FGFile.ImageLoadCallBack() {
            @Override
            public void onBitmapReturn(Bitmap bitmap, String path, String msg) {
                imageView.setImageBitmap(bitmap);
                Toast.makeText(InternetActivity.this, "onBitmapReturn: " + path, Toast.LENGTH_SHORT).show();
                Toast.makeText(InternetActivity.this, "onBitmapReturn: " + msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}