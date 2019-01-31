package com.ktlibrary.fileOperation;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class FileUtils {

    private static String TAG = "fileOperation";

    /* this function write file to given path */
    public static void writeFile(String path, String sFileName, String sBody) {
        try {
            File root = new File(path);
            if (!root.exists()) {
                root.mkdirs();
            }
            File file = new File(root, sFileName);
            if (!file.exists()) {
                FileWriter writer = new FileWriter(file);
                writer.append(sBody);
                writer.flush();
                writer.close();
//                if (IS_LOG) Log.d(TAG, "writeFile: File Saved");
            }
        } catch (IOException e) {
            e.printStackTrace();
//            if (IS_LOG) Log.d(TAG, "writeFile: exception : " + e.getLocalizedMessage());
        }
    }

    /* this function copy asset's folder file to given path */
    public static void copyAsset(Context context, String fileName, String path) {
        File dir = new File(path);
        if (!dir.exists()) dir.mkdirs();
        File file = new File(path + "/" + fileName);
        if (!file.exists()) {
            AssetManager assetManager = context.getAssets();
            InputStream in = null;
            OutputStream out = null;
            try {
                in = assetManager.open(fileName);
                out = new FileOutputStream(path + "/" + fileName);
                copyFile(in, out);
                in.close();
                in = null;
                out.flush();
                out.close();
                out = null;
//                if (IS_LOG) Log.d(TAG, "eventsManagement: file try condition.");
            } catch (Exception e) {
                e.printStackTrace();
//                if (IS_LOG)
//                    Log.d(TAG, "eventsManagement: file catch condition : " + e.getLocalizedMessage());
            }
        }
    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    /* this function delete file from given path */
    public static boolean fileDelete(String path) {
        File file = new File(path);
        if (file.exists()) {
            return file.delete();
        } else {
            return false;
        }
    }

    /* this function read file and return as string */
    public String fileRead(Context context, String filename) {
        try {
            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
    }
}
