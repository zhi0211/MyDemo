package org.gxz.mydemo.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

public class FileUtils {

	/**
	 * 创建目录
	 * 
	 * @param dirFile
	 *            创建文件名 如dirFile="xxx/download"
	 * @return true 创建成功 false 创建失败
	 */
	public static boolean createDirFile(String dirFile) {
		// 判断SD卡状态可用否.
		if (android.os.Environment.MEDIA_MOUNTED.equals(android.os.Environment
				.getExternalStorageState())) {

			File f = new File(Environment.getExternalStorageDirectory() + "/"
					+ dirFile);
			try {
				if (!f.exists())
					f.mkdirs();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * 检查存储空间大小
	 * 
	 * @param file
	 *            文件名（含路径的文件名)
	 * @param size
	 *            文件大小
	 * @return true 空间充足
	 */
	public static boolean hasSpace(String file, int size) {
		File data = Environment.getDataDirectory();
		StatFs sf = new StatFs(data.getPath());
		int availableBlocks = sf.getAvailableBlocks();
		int blockSize = sf.getBlockSize() / 1024;
		int availableSize = availableBlocks * blockSize;
		Log.d("hasSpace", "availableBlocks:" + availableBlocks
				+ ";  blockSize:" + blockSize + ";  availableSize:"
				+ availableSize);
		if (availableSize > (size / 1024 + 1))
			return true;
		else {
			return false;
		}
	}

	/**
	 * 文件是否存在
	 * 
	 * @param file
	 *            完整文件名(含路径)
	 * @return
	 */
	public static boolean isExists(Context ctx, String file) {
		File f = new File(file);
		if (f.exists()) {
			// 检查apk是否完整
			if (!getUninatllApkInfo(ctx, file)) {
				f.delete();
				Log.d("isExists", file + "has been deleted!");
				return false;
			}
			return true;
		} else {
			try {
				f.createNewFile();
			} catch (IOException e) {
				Log.w("isExists", "create file wrong,the dir is " + f);
				e.printStackTrace();
			}
			return false;
		}
	}

	/**
	 * 判断apk是否完整
	 * 
	 * @param context
	 * @param filePath
	 *            文件路径
	 * @return
	 */
	public static boolean getUninatllApkInfo(Context context, String filePath) {

		boolean result = false;
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo info = pm.getPackageArchiveInfo(filePath,
					PackageManager.GET_ACTIVITIES);
			if (info != null) {
				result = true;
			}
		} catch (Exception e) {
			result = false;
			Log.w("getUninatllApkInfo",
					"*****  解析未安装的 apk 出现异常 *****" + e.getMessage());
		}
		Log.d("getUninatllApkInfo", filePath
				+ (result == false ? "isn't a complete file."
						: "is a complete file."));
		return result;
	}

	/**
	 * apk下载
	 * @param ctx 上下文
	 * @param fileDir 存储目录
	 * @param fileName 文件名称 如xxx.apk
	 * @param downloadUrl 下载url
	 * @return 
	 * @throws IOException
	 * @throws Exception
	 */
	public static boolean downloadFile(Context ctx, String fileDir,
			String fileName, String downloadUrl) throws IOException, Exception {
		//创建存储目录
		createDirFile(fileDir);
		
		String file = fileDir + "/" + fileName;
		int totalSize;// 文件总大小
		
		URL url = new URL(downloadUrl);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url
				.openConnection();
		httpURLConnection.setConnectTimeout(3000);
		httpURLConnection.setReadTimeout(3000);
		// 获取下载文件的size
		totalSize = httpURLConnection.getContentLength();
		// 连接失败
		if (httpURLConnection.getResponseCode() == 404) {
			throw new Exception("fail!");
		}
		
		// 判断空间大小
		boolean hasSpace = hasSpace(file, totalSize);
		if (!hasSpace) {
			throw new Exception("out of free space.");
		}
		// 判断文件是否已经存在
		boolean isFileExit = isExists(ctx, file);
		if (isFileExit)
			throw new Exception("file exitsts.");
		int down_step = 2;// 提示step，百分之几后提示
		int downloadCount = 0;// 已经下载好的大小
		int updateCount = 0;// 已经上传的文件大小
		InputStream inputStream;
		OutputStream outputStream;

		inputStream = httpURLConnection.getInputStream();
		outputStream = new FileOutputStream(file, false);// 文件存在则覆盖掉
		byte buffer[] = new byte[1024];
		int readsize = 0;
		while ((readsize = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, readsize);
			downloadCount += readsize; // 时时获取下载到的大小
			if (updateCount == 0
					|| (downloadCount * 100 / totalSize - down_step) >= updateCount) {
				updateCount += down_step;
				// 此处可以通知UI改变百分比：updateCount+"%".
				// .....
			}
		}
		if (httpURLConnection != null) {
			httpURLConnection.disconnect();
		}
		inputStream.close();
		outputStream.close();

		return true;
	}
}
