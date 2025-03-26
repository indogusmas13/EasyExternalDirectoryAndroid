package com.intishaka.eeda.helper;

import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.File;
import java.util.Objects;

public class FGDir {

    public static final String TAG = "FGDir";

//    public final static String getStorageCard = Environment.getExternalStorageDirectory().toString();
    public static String getStorageCard = "";
    public static String appFolder = "";

    private static MessageCallBack mCallBack;


    public static void logSystemFunctionGlobal(String tag, String function, String msg, boolean display) {
        Log.d("MyLibDirectory_Debug", function + "_" + msg);
        if (display) {
            if (mCallBack != null) {
                mCallBack.messageError(tag + "\n" + function + "\n" + msg);
            }
        }
    }

    public static void myLogD(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void setGetStorageCard(String getStorageCard) {
        FGDir.getStorageCard = getStorageCard;
    }

    public static void initExternalDirectoryName(String appFolder) {
        if (appFolder == null) {
            logSystemFunctionGlobal(TAG, "initExternalDirectoryName", "AppFolder tidak boleh null", true);
            return;
        }
        if (!appFolder.startsWith("/")) {
            appFolder = "/" + appFolder;
        }
        FGDir.appFolder = appFolder;
    }

    public static void initExternalDirectoryName(String appFolder, MessageCallBack callBack) {
        mCallBack = callBack;
        if (appFolder == null) {
            logSystemFunctionGlobal(TAG, "initExternalDirectoryName", "AppFolder tidak boleh null", true);
            return;
        }
        if (!appFolder.startsWith("/")) {
            appFolder = "/" + appFolder;
        }
        FGDir.appFolder = appFolder;
    }

    public static boolean initFolder(String... folderName) {
        if (folderName == null) {
            logSystemFunctionGlobal(TAG, "initFolder", "FolderName tidak boleh null", true);
            return false;
        }
        if (appFolder.length() == 0) {
            logSystemFunctionGlobal(TAG, "initFolder", "Folder External untuk aplikasi belum dideklarasi", true);
            return false;
        }
        File folder;

        if(Objects.equals(getStorageCard, "")){
            getStorageCard = Environment.getExternalStorageDirectory().toString();
        }
        // create appFolder
        folder = new File(getStorageCard + appFolder);
        if (!folder.exists()) {
            if (!creatingFolder(folder)) {
                logSystemFunctionGlobal(TAG, "initFolder", "Gagal membuat direktory External untuk aplikasi", false);
                return false;
            }
        }
        if (folderName.length > 0) {
            for (String s : folderName) {
                if (s.length() > 0) {
                    if (!s.startsWith("/")) {
                        s = "/" + s;
                    }
                    folder = new File(getStorageCard + appFolder + s);
                    if (!folder.exists()) {
                        if (!creatingFolder(folder)) {
                            logSystemFunctionGlobal(TAG, "initFolder", "Gagal membuat direktory " + s, false);
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    private static boolean creatingFolder(File folder) {
        try {
            if (folder.mkdirs()) {
                logSystemFunctionGlobal(TAG, "creatingFolder", "Success menjalankan mkdirs direktory External untuk aplikasi ", false);
            }
        } catch (Exception e) {
            logSystemFunctionGlobal(TAG, "creatingFolder", "Gagal menjalankan mkdirs direktory External untuk aplikasi " + e.getMessage(), true);
            return false;
        }
        return true;
    }

    public static boolean isFileExists(String path) {
        if (path == null) {
            logSystemFunctionGlobal(TAG, "isFileExists", "Path tidak boleh null", true);
            return false;
        }
        File file = new File(getStorageCard + appFolder + path);
        return file.exists();
    }

    public static boolean deleteDir(String path) {
        if (path == null) {
            logSystemFunctionGlobal(TAG, "deleteDir", "Path tidak boleh null", true);
            return false;
        }
        return new File(FGDir.getStorageCard + FGDir.appFolder + path).delete();
    }

    /**
     * @return Number of kilo bytes available on External storage
     */
    public static long getAvailableSpaceInKB() {
        final long SIZE_KB = 1024L;
        long availableSpace;
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        availableSpace = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
        return availableSpace / SIZE_KB;
    }

    /**
     * @return Number of Mega bytes available on External storage
     */
    public static long getAvailableSpaceInMB() {
        final long SIZE_KB = 1024L;
        final long SIZE_MB = SIZE_KB * SIZE_KB;
        long availableSpace;
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        availableSpace = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
        return availableSpace / SIZE_MB;
    }

    /**
     * @return Number of gega bytes available on External storage
     */
    public static long getAvailableSpaceInGB() {
        final long SIZE_KB = 1024L;
        final long SIZE_GB = SIZE_KB * SIZE_KB * SIZE_KB;
        long availableSpace;
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        availableSpace = (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
        return availableSpace / SIZE_GB;
    }

    public static boolean checkAvailableSpaceInKB(long requestSize) {
        return getAvailableSpaceInKB() > requestSize;
    }

    public static boolean checkAvailableSpaceInMB(long requestSize) {
        return getAvailableSpaceInMB() > requestSize;
    }

    public static boolean checkAvailableSpaceInGB(long requestSize) {
        return getAvailableSpaceInGB() > requestSize;
    }

    public interface MessageCallBack {
        void messageError(String msg);
    }
}
