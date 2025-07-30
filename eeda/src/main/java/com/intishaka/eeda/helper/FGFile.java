package com.intishaka.eeda.helper;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;

import com.gzeinnumer.eeda.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static com.intishaka.eeda.helper.FGDir.logSystemFunctionGlobal;

public class FGFile {

    public static final String TAG = "FGFile";

    ImageLoadCallBack imageLoadCallBack;

    //create file
    public static boolean initFile(String fileName, String saveTo, String... text) {
        if (fileName == null) {
            logSystemFunctionGlobal(TAG, "initFile", "FileName tidak boleh null", true);
            return false;
        }
        if (saveTo == null) {
            logSystemFunctionGlobal(TAG, "initFile", "SaveTo tidak boleh null", true);
            return false;
        }
        if (text == null) {
            logSystemFunctionGlobal(TAG, "initFile", "Text tidak boleh null", true);
            return false;
        }
        if (FGDir.appFolder.length() == 0) {
            logSystemFunctionGlobal(TAG, "initFile", "Folder External untuk aplikasi belum di deklarasi", true);
            return false;
        }
        if (!FGDir.isFileExists("")) {
            logSystemFunctionGlobal(TAG, "initFile", "Folder External untuk aplikasi tidak di temukan", false);
            if (FGDir.initFolder("")) {
                logSystemFunctionGlobal(TAG, "initFile", "Folder External sudah dibuat", false);
            } else {
                logSystemFunctionGlobal(TAG, "initFile", "Folder External gagal dibuat", false);
                return false;
            }
        }
        if (!saveTo.startsWith("/")) {
            saveTo = "/" + saveTo;
        }
        if (!FGDir.isFileExists(saveTo)) {
            logSystemFunctionGlobal(TAG, "initFile", "Folder External untuk aplikasi tidak di temukan", false);
            String path = saveTo.substring(1);
            char someChar = '/';
            int count = 0;
            for (int i = 0; i < path.length(); i++) {
                if (path.charAt(i) == someChar) {
                    count++;
                }
            }
            if (count > 0) {
                String[] subFolder = saveTo.substring(1).split("/");
                String currentPath = "";
                for (String d : subFolder) {
                    if (!d.startsWith("/")) {
                        d = "/" + d;
                    }
                    currentPath = currentPath + d;
                    if (FGDir.initFolder(currentPath)) {
                        logSystemFunctionGlobal(TAG, "initFile", "Folder External sudah dibuat", false);
                    } else {
                        logSystemFunctionGlobal(TAG, "initFile", "Folder External gagal dibuat", false);
                        return false;
                    }
                }
            } else {
                if (FGDir.initFolder(path)) {
                    logSystemFunctionGlobal(TAG, "initFile", "Folder External sudah dibuat", false);
                } else {
                    logSystemFunctionGlobal(TAG, "initFile", "Folder External gagal dibuat", false);
                    return false;
                }
            }
        }
        if (fileName.length() == 0) {
            logSystemFunctionGlobal(TAG, "initFile", "FileName tidak boleh kosong", true);
            return false;
        }
        if (!fileName.startsWith("/")) {
            fileName = "/" + fileName;
        }
        File file = new File(FGDir.getStorageCard + FGDir.appFolder + saveTo + fileName);

        return processFile(file, text);
    }

    private static boolean processFile(File file, String... text) {
        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter writer = new PrintWriter(f);

            if (text.length > 0) {
                for (String d : text) {
                    writer.println(d);
                }
            }

            writer.flush();
            writer.close();
            f.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            logSystemFunctionGlobal(TAG, "processFile", "Gagal membuat file " + e.getMessage(), true);
            return false;
        }
    }

    public static List<String> readFile(String path) {
        List<String> list = new ArrayList<>();
        if (path == null) {
            logSystemFunctionGlobal(TAG, "readFile", "Path tidak boleh null", true);
            return list;
        }
        if (FGDir.appFolder.length() == 0) {
            logSystemFunctionGlobal(TAG, "readFile", "Folder External untuk aplikasi belum dideklarasi", true);
            return list;
        }
        if (path.length() == 0) {
            logSystemFunctionGlobal(TAG, "readFile", "Path tidak boleh kosong", true);
            return list;
        }
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        if (!FGDir.isFileExists(path)) {
            logSystemFunctionGlobal(TAG, "readFile", "File tidak ditemukan", true);
            return list;
        }

        File file = new File(FGDir.getStorageCard + FGDir.appFolder + path);
        Scanner input;

        try {
            input = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logSystemFunctionGlobal(TAG, "readFile", "Gagal membaca file", true);
            return list;
        }

        while (input.hasNextLine()) {
            list.add(input.nextLine());
        }
        return list;
    }

    public static boolean appentText(String path, String... msg) {
        if (path == null) {
            logSystemFunctionGlobal(TAG, "appentText", "Path tidak boleh null", true);
            return false;
        }
        if (msg == null) {
            logSystemFunctionGlobal(TAG, "appentText", "Path tidak boleh null", true);
            return false;
        }
        if (FGDir.appFolder.length() == 0) {
            logSystemFunctionGlobal(TAG, "appentText", "Folder External untuk aplikasi belum dideklarasi", true);
            return false;
        }
        if (!FGDir.isFileExists("")) {
            logSystemFunctionGlobal(TAG, "appentText", "Folder External untuk aplikasi tidak di temukan", false);
            if (FGDir.initFolder("")) {
                logSystemFunctionGlobal(TAG, "appentText", "Folder External sudah dibuat", false);
            } else {
                logSystemFunctionGlobal(TAG, "appentText", "Folder External gagal dibuat", false);
                return false;
            }
        }
        if (path.length() == 0) {
            logSystemFunctionGlobal(TAG, "appentText", "Path tidak boleh kosong", true);
            return false;
        }
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        if (!FGDir.isFileExists(path)) {
            logSystemFunctionGlobal(TAG, "appentText", "File tidak ditemukan", true);
            return false;
        }
        FileWriter fw;
        try {
            fw = new FileWriter(FGDir.getStorageCard + FGDir.appFolder + path, true);
            if (msg.length > 0) {
                for (String d : msg) {
                    fw.write(d + "\n");
                }
            }
            fw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            logSystemFunctionGlobal(TAG, "appentText", "Gagal mengapent text ke file " + e.getMessage(), true);
            return false;
        }
    }

    /**
     * @deprecated "There is abug in here, recommend to use initFileImageFromInternet(imgUrl, saveTo, filename, isNew, imageLoadCallBack)
     */
    @Deprecated
    public static void initFileImageFromInternet(final String imgUrl, final String saveTo, final String filename, final ImageView sendImageTo, final boolean isNew) {
        if (imgUrl == null) {
            logSystemFunctionGlobal(TAG, "initFileImageFromInternet", "ImgUrl tidak boleh null", true);
            return;
        }
        if (saveTo == null) {
            logSystemFunctionGlobal(TAG, "initFileImageFromInternet", "SaveTo tidak boleh null", true);
            return;
        }
        if (filename == null) {
            logSystemFunctionGlobal(TAG, "initFileImageFromInternet", "Filename tidak boleh null", true);
            return;
        }
        if (sendImageTo == null) {
            logSystemFunctionGlobal(TAG, "initFileImageFromInternet", "SendImageTo tidak boleh null", true);
            return;
        }
        if (FGDir.appFolder.length() == 0) {
            logSystemFunctionGlobal(TAG, "initFileImageFromInternet", "Folder External untuk aplikasi belum dideklarasi", true);
        }
        if (!FGDir.isFileExists("")) {
            logSystemFunctionGlobal(TAG, "initFileImageFromInternet", "Folder External untuk aplikasi tidak di temukan", false);
            if (FGDir.initFolder("")) {
                logSystemFunctionGlobal(TAG, "initFileImageFromInternet", "Folder External sudah dibuat", false);
            } else {
                logSystemFunctionGlobal(TAG, "initFileImageFromInternet", "Folder External gagal dibuat", false);
            }
        }
        File myDir = new File(FGDir.getStorageCard + FGDir.appFolder + saveTo);
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        if (filename.length() > 0) {
            myDir = new File(myDir, filename);
        } else {
            myDir = new File(myDir, new Date().toString() + ".jpg");
        }
        if (!myDir.exists() || isNew) { // file tidak ada or isNew : True
            final File finalMyDir = myDir;
            Picasso.get().load(imgUrl)
                    .placeholder(R.drawable.ic_baseline_sync_24)
                    .error(R.drawable.ic_baseline_broken_image_24)
                    .into(new Target() {
                              @Override
                              public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                  try {
                                      if (!finalMyDir.exists() || isNew) {
                                          //jika isNew true maka foto lama akan dihapus dan diganti dengan yang baru
                                          //jika file tidak ditemukan maka file akan dibuat
                                          logSystemFunctionGlobal(TAG, "initFileImageFromInternet", "Foto baru disimpan ke penyimpanan", false);
                                          FileOutputStream out = new FileOutputStream(finalMyDir);
                                          bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

                                          out.flush();
                                          out.close();
                                      } else {
                                          //jika isNew false maka akan load file lama di penyimpanan
                                          logSystemFunctionGlobal(TAG, "initFileImageFromInternet", "Foto lama di load dari penyimpanan", false);
                                          bitmap = BitmapFactory.decodeFile(finalMyDir.getAbsolutePath());
                                      }
                                      sendImageTo.setImageBitmap(bitmap);
                                  } catch (Exception e) {
                                      logSystemFunctionGlobal(TAG, "initFileImageFromInternet", e.getMessage(), true);
                                  }
                              }

                              @Override
                              public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                                  logSystemFunctionGlobal(TAG, "initFileImageFromInternet", e.getMessage(), true);
                              }

                              @Override
                              public void onPrepareLoad(Drawable placeHolderDrawable) {
                              }
                          }
                    );
        } else {
            logSystemFunctionGlobal(TAG, "initFileImageFromInternet", "Foto lama di load dari penyimpanan", false);
            Bitmap bitmap = BitmapFactory.decodeFile(myDir.getAbsolutePath());
            sendImageTo.setImageBitmap(bitmap);
        }
    }

    public static void initFileImageFromInternet(final Context context, final String imgUrl, final String saveTo, final String filename, final boolean isNew, final ImageLoadCallBack imageLoadCallBack) {
        if (imgUrl == null) {
            logSystemFunctionGlobal(TAG, "initFileImageFromInternet", "ImgUrl tidak boleh null", true);
            return;
        }
        if (saveTo == null) {
            logSystemFunctionGlobal(TAG, "initFileImageFromInternet", "SaveTo tidak boleh null", true);
            return;
        }
        if (filename == null) {
            logSystemFunctionGlobal(TAG, "initFileImageFromInternet", "Filename tidak boleh null", true);
            return;
        }
        if (imageLoadCallBack == null) {
            logSystemFunctionGlobal(TAG, "initFileImageFromInternet", "ImageLoadCallBack tidak boleh null", true);
            return;
        }
        if (FGDir.appFolder.length() == 0) {
            logSystemFunctionGlobal(TAG, "initFileImageFromInternet", "Folder External untuk aplikasi belum dideklarasi", true);
        }
        if (!FGDir.isFileExists("")) {
            logSystemFunctionGlobal(TAG, "initFileImageFromInternet", "Folder External untuk aplikasi tidak di temukan", false);
            if (FGDir.initFolder("")) {
                logSystemFunctionGlobal(TAG, "initFileImageFromInternet", "Folder External sudah dibuat", false);
            } else {
                logSystemFunctionGlobal(TAG, "initFileImageFromInternet", "Folder External gagal dibuat", false);
            }
        }
        File myDir = new File(FGDir.getStorageCard + FGDir.appFolder + saveTo);
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        if (filename.length() > 0) {
            myDir = new File(myDir, filename);
        } else {
            myDir = new File(myDir, new Date().toString() + ".jpg");
        }
        if (!myDir.exists() || isNew) { // file tidak ada or isNew : True
            final File finalMyDir = myDir;
            Picasso.get().load(imgUrl)
                    .placeholder(R.drawable.ic_baseline_sync_24)
                    .error(R.drawable.ic_baseline_broken_image_24)
                    .into(new Target() {
                              @Override
                              public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                  try {
                                      String msg;
                                      if (!finalMyDir.exists() || isNew) {
                                          //jika isNew true maka foto lama akan dihapus dan diganti dengan yang baru
                                          //jika file tidak ditemukan maka file akan dibuat
                                          logSystemFunctionGlobal(TAG, "initFileImageFromInternet", "Foto baru disimpan ke penyimpanan", false);
                                          FileOutputStream out = new FileOutputStream(finalMyDir);
                                          bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

                                          msg = "Save New Foto";

                                          out.flush();
                                          out.close();

                                      } else {
                                          //jika isNew false maka akan load file lama di penyimpanan
                                          logSystemFunctionGlobal(TAG, "initFileImageFromInternet", "Foto lama di load dari penyimpanan", false);
                                          bitmap = BitmapFactory.decodeFile(finalMyDir.getAbsolutePath());

                                          msg = "Load Old Foto";
                                      }

                                      imageLoadCallBack.onBitmapReturn(bitmap, finalMyDir.toString(), msg);
                                  } catch (Exception e) {
                                      logSystemFunctionGlobal(TAG, "initFileImageFromInternet", e.getMessage(), true);
                                  }
                              }

                              @Override
                              public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                                  logSystemFunctionGlobal(TAG, "initFileImageFromInternet", e.getMessage(), true);

                                  imageLoadCallBack.onBitmapReturn(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.ic_baseline_broken_image_24)), finalMyDir.toString(), e.getMessage());
                              }

                              @Override
                              public void onPrepareLoad(Drawable placeHolderDrawable) {
                                  imageLoadCallBack.onBitmapReturn(drawableToBitmap(ContextCompat.getDrawable(context, R.drawable.ic_baseline_sync_24)), finalMyDir.toString(), "onPrepareLoad");
                              }
                          }
                    );
        } else {
            logSystemFunctionGlobal(TAG, "initFileImageFromInternet", "Foto lama di load dari penyimpanan", false);
            Bitmap bitmap = BitmapFactory.decodeFile(myDir.getAbsolutePath());
            imageLoadCallBack.onBitmapReturn(bitmap, myDir.toString(), "Load Old Foto");
        }
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    //simpan data di dalam root folder sebagai temporary
//    public static File createImageFile(Context context, String fileName) throws IOException {
//        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_DCIM);
//        return File.createTempFile(fileName + "_", ".png", storageDir);
//    }

    public static File createImageFile(Context context, String fileName) throws IOException {
        // File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File  storageDir = new File(context.getFilesDir(), Environment.DIRECTORY_PICTURES);
        if (storageDir == null) {
            throw new IOException("getExternalFilesDir returned null");
        }
        if (!storageDir.exists()) {
            boolean created = storageDir.mkdirs();
            if (!created) {
                storageDir = new File(context.getFilesDir(), Environment.DIRECTORY_PICTURES);
                if (!storageDir.exists() && !storageDir.mkdirs()) {
                    throw new IOException("Failed to create internal directory: " + storageDir.getAbsolutePath());
                }
            }
        }

        return File.createTempFile(fileName + "_", ".png", storageDir);
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            assert cursor != null;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static boolean deleteDir(String path) {
        if (FGDir.appFolder.length() == 0) {
            logSystemFunctionGlobal(TAG, "deleteDir", "Folder External untuk aplikasi belum dideklarasi", true);
            return false;
        }
        return new File(FGDir.getStorageCard + FGDir.appFolder + path).delete();
    }

    public static void deleteAllFile(String path) {
        if (FGDir.appFolder.length() == 0) {
            logSystemFunctionGlobal(TAG, "deleteAllFile", "Folder External untuk aplikasi belum dideklarasi", true);
            return;
        }
        deleteAllFileProcess(new File(FGDir.getStorageCard + FGDir.appFolder + path));
        new File(FGDir.getStorageCard + FGDir.appFolder + path).mkdirs();
    }

    private static boolean deleteAllFileProcess(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (files != null && files.length > 0) {
                for (File aFile : files) {
                    deleteAllFileProcess(aFile);
                }
            }
        }
        return dir.delete();
    }

    public static boolean isFileExists(String path) {
        if (path == null) {
            logSystemFunctionGlobal(TAG, "isFileExists", "Path tidak boleh null", true);
            return false;
        }
        if (FGDir.appFolder.length() == 0) {
            logSystemFunctionGlobal(TAG, "deleteAllFile", "Folder External untuk aplikasi belum dideklarasi", true);
            return false;
        }
        File file = new File(FGDir.getStorageCard + FGDir.appFolder + path);
        return file.exists();
    }

    public interface ImageLoadCallBack {
        void onBitmapReturn(Bitmap bitmap, String path, String msg);
    }

}