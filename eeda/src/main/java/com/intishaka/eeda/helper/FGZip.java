package com.intishaka.eeda.helper;

import android.util.Base64;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static com.intishaka.eeda.helper.FGDir.logSystemFunctionGlobal;

public class FGZip {

    public static final String TAG = "FGZip";

    //start genFile
    //jika ingin replace file yang sudah ada, maka beri flag true, jika tidak maka false saja
    public static boolean initFileFromStringToZipToFile(String fileName, String zipLocation, String base64EncodeFromFile, String md5EncodeFromFile, boolean isNew) {
        if (fileName == null) {
            logSystemFunctionGlobal(TAG, "initFileFromStringToZipToFile", "FileName tidak boleh null", true);
            return false;
        }
        if (zipLocation == null) {
            logSystemFunctionGlobal(TAG, "initFileFromStringToZipToFile", "ZipLocation tidak boleh null", true);
            return false;
        }
        if (base64EncodeFromFile == null) {
            logSystemFunctionGlobal(TAG, "initFileFromStringToZipToFile", "Base64EncodeFromFile tidak boleh null", true);
            return false;
        }
        if (md5EncodeFromFile == null) {
            logSystemFunctionGlobal(TAG, "initFileFromStringToZipToFile", "Md5EncodeFromFile tidak boleh null", true);
            return false;
        }
        if (FGDir.appFolder.length() == 0) {
            logSystemFunctionGlobal(TAG, "initFileFromStringToZipToFile", "Folder External untuk aplikasi belum dideklarasi", true);
            return false;
        }
        if (!FGDir.isFileExists("")) {
            logSystemFunctionGlobal(TAG, "initFileFromStringToZipToFile", "Folder External untuk aplikasi tidak di temukan", false);
            if (FGDir.initFolder("")) {
                logSystemFunctionGlobal(TAG, "initFileFromStringToZipToFile", "Folder External sudah dibuat", false);
            } else {
                logSystemFunctionGlobal(TAG, "initFileFromStringToZipToFile", "Folder External gagal dibuat", false);
                return false;
            }
        }
        if (fileName.length() == 0) {
            logSystemFunctionGlobal(TAG, "initFileFromStringToZipToFile", "FileName tidak boleh kosong", true);
            return false;
        }
        if (!fileName.startsWith("/")) {
            fileName = "/" + fileName;
        }
        if (zipLocation.length() == 0) {
            logSystemFunctionGlobal(TAG, "initFileFromStringToZipToFile", "ZipLocation tidak boleh kosong", true);
            return false;
        }
        if (!zipLocation.startsWith("/")) {
            zipLocation = "/" + zipLocation;
        }
        if (base64EncodeFromFile.length() == 0) {
            logSystemFunctionGlobal(TAG, "initFileFromStringToZipToFile", "Base64EncodeFromFile tidak boleh kosong", true);
            return false;
        }
        if (md5EncodeFromFile.length() == 0) {
            logSystemFunctionGlobal(TAG, "initFileFromStringToZipToFile", "Md5EncodeFromFile tidak boleh kosong", true);
            return false;
        }
        if (FGDir.isFileExists(fileName) && !isNew) {
            logSystemFunctionGlobal(TAG, "initFileFromStringToZipToFile", "File sudah dibuat", true);
            return true;
        } else {
            if (converToZip(fileName, base64EncodeFromFile)) {
                if (compareMd5(fileName, md5EncodeFromFile)) {
                    try {
                        if (unzip(fileName, zipLocation)) {
                            logSystemFunctionGlobal(TAG, "initFileFromStringToZipToFile", "Success membuat file zip", false);
                            return true;
                        } else {
                            logSystemFunctionGlobal(TAG, "initFileFromStringToZipToFile", "Gagal membuat file zip", false);
                            return false;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        logSystemFunctionGlobal(TAG, "initFileFromStringToZipToFile", "Gagal membuat file zip " + e.getMessage(), true);
                        return false;
                    }
                } else {
                    logSystemFunctionGlobal(TAG, "initFileFromStringToZipToFile", "Gagal compareMd5 ", false);
                    return false;
                }
            } else {
                logSystemFunctionGlobal(TAG, "initFileFromStringToZipToFile", "Gagal converToZip ", false);
                return false;
            }
        }
    }

    private static boolean converToZip(String fileName, String base64) {
        final File fileZip = new File(FGDir.getStorageCard + FGDir.appFolder + fileName);
        byte[] pdfAsBytes;
        try {
            pdfAsBytes = Base64.decode(base64, 0);
            FileOutputStream os;
            try {
                os = new FileOutputStream(fileZip, false);
                os.write(pdfAsBytes);
                os.flush();
                os.close();
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                logSystemFunctionGlobal(TAG, "converToZip", "Gagal converToZip File Not Found " + e.getMessage(), true);
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                logSystemFunctionGlobal(TAG, "converToZip", "Gagal converToZip IOException " + e.getMessage(), true);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logSystemFunctionGlobal(TAG, "converToZip", "Gagal converToZip Exception " + e.getMessage(), true);
            return false;
        }
    }

    private static boolean compareMd5(String fileName, String md5OriginValue) {
        try {
            String filePath = FGDir.getStorageCard + FGDir.appFolder + fileName;
            FileInputStream fis = new FileInputStream(filePath);
            String md5Checksum = Md5Checksum.md5(fis);
            boolean isCompare = md5Checksum.equals(md5OriginValue);
            if (!isCompare)
                logSystemFunctionGlobal(TAG, "compareMd5", "Md5 Code Match " + false, true);
            return isCompare;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logSystemFunctionGlobal(TAG, "compareMd5", "Gagal compareMd5 FileNotFoundException " + e.getMessage(), true);
        }
        return false;
    }

    private static boolean unzip(String fileName, String zipLocation) throws IOException {
        String zipFile = FGDir.getStorageCard + FGDir.appFolder + fileName;
        String unzipLocation = FGDir.getStorageCard + FGDir.appFolder + zipLocation;

        //delete file
        if (new File(unzipLocation).exists())
            deleteRecursive(new File(unzipLocation));
        //

        ZipFile archive = new ZipFile(zipFile);
        Enumeration<? extends ZipEntry> e = archive.entries();
        while (e.hasMoreElements()) {
            ZipEntry entry = e.nextElement();
            File file = new File(unzipLocation, entry.getName());
            if (entry.isDirectory()) {
                file.mkdirs();
            } else {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                InputStream in = archive.getInputStream(entry);
                OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
                byte[] buffer = new byte[1024];
                int read;
                while ((read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, read);
                }
                in.close();

                // write the output file (You have now copied the file)
                out.flush();
                out.close();
                return true;
            }
        }
        return false;
    }
    //end genFile

    private static void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        fileOrDirectory.delete();
    }
}
