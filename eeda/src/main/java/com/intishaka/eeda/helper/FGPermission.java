package com.intishaka.eeda.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.intishaka.eeda.helper.model.PermissionsResult;

import java.util.ArrayList;
import java.util.List;

public class FGPermission {
    private static final String TAG = "Gbl_";
    private static final int PERMISSION_REQUEST_CODE = 100;

    public static void checkPermissions(Activity activity, String[] permissions) {
        List<String> permissionsNeeded = new ArrayList<>();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(permission);
            }
        }

        if (!permissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity, permissionsNeeded.toArray(new String[0]), PERMISSION_REQUEST_CODE);
        }
    }

    public static boolean getPermissionResult(Activity activity, String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static void getPermissionResult(Activity activity, String[] permissions, CallBackPermission callBackPermission) {
        List<PermissionsResult> list = new ArrayList<>();
        boolean allGranted = true;

        for (String permission : permissions) {
            boolean granted = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
            list.add(new PermissionsResult(granted ? "Granted" : "Denied", permission));
            if (!granted) allGranted = false;
        }

        callBackPermission.result(allGranted, list);
    }

    public interface CallBackPermission {
        void result(boolean isAllGranted, List<PermissionsResult> listPermissions);
    }
}
