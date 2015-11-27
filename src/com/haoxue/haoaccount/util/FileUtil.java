package com.haoxue.haoaccount.util;

import static android.os.Environment.MEDIA_MOUNTED;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

/**
 * 说明:
 * 作者:Luoyangs
 * 时间:2015-10-8
 */
@SuppressLint("SdCardPath")
public class FileUtil {
	
	/**依据图片名字获取图片文件*/
	public static Drawable getDrawableByName(Context context,String name) {
		String packageName = context.getApplicationInfo().packageName;
		int id = context.getResources().getIdentifier(name, "drawable", packageName);
		return context.getResources().getDrawable(id);
	}
	
	/**
     * 获取/data/data/files目录
     * @param context
     * @return
     */
    public static File getFileDirectory(Context context) {
        File appCacheDir=null;
        if(appCacheDir == null) {
            appCacheDir=context.getFilesDir();
        }
        if(appCacheDir == null) {
            String cacheDirPath="/data/data/" + context.getPackageName() + "/files/";
            appCacheDir=new File(cacheDirPath);
        }
        return appCacheDir;
    }

    
    public static File getCacheDirectory(Context context, boolean preferExternal,String dirName) {
        File appCacheDir = null;
        if (preferExternal && MEDIA_MOUNTED
                .equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {
            appCacheDir = getExternalCacheDir(context,dirName);
        }
        if (appCacheDir == null) {
            appCacheDir = context.getCacheDir();
        }
        if (appCacheDir == null) {
            String cacheDirPath = "/data/data/" + context.getPackageName() + "/cache/";
            Log.w("Can't define system cache directory! '%s' will be used.", cacheDirPath);
            appCacheDir = new File(cacheDirPath);
        }
        return appCacheDir;
    }


    private static File getExternalCacheDir(Context context,String dirName) {
        File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
        File appCacheDir2 = new File(new File(dataDir, context.getPackageName()), "cache");
        File appCacheDir = new File(appCacheDir2, dirName);
        if (!appCacheDir.exists()) {
            if (!appCacheDir.mkdirs()) {
                Log.w(TAG,"Unable to create external cache directory");
                return null;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         
            }
            try {
                new File(appCacheDir, ".nomedia").createNewFile();
            } catch (IOException e) {
                Log.i(TAG,"Can't create \".nomedia\" file in application external cache directory");
            }
        }
        return appCacheDir;
    }
    private static final String TAG = "CacheUtils";
    private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";
    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
        return perm == PackageManager.PERMISSION_GRANTED;
    }
    
    public static boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		file.isFile();
		return file.exists();
	}
    
    public static File createSDDir(String dirName) throws IOException {
		File dir = new File(SDPATH + dirName);
		return dir;
	}
    
    public static String SDPATH = Environment.getExternalStorageDirectory() + "/Photo_NB/";
    public static void saveBitmap(Bitmap bm, String picName) {
		try {
			if (!isFileExist("")) {
				File tempf = createSDDir("");
			}
			File f = new File(SDPATH, picName + ".JPEG"); 
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
