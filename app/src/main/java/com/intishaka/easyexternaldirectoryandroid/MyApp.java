package com.intishaka.easyexternaldirectoryandroid;

import android.app.Application;
import android.view.Gravity;
import android.widget.Toast;

import com.intishaka.easyexternaldirectoryandroid.R;
import com.intishaka.eeda.helper.FGDir;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        String externalFolderName = getApplicationContext().getString(R.string.app_name); //MyLibsTesting
        FGDir.initExternalDirectoryName(externalFolderName, new FGDir.MessageCallBack() {
            @Override
            public void messageError(String msg) {
                Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0, 150);
                toast.show();
            }
        });
    }
}
