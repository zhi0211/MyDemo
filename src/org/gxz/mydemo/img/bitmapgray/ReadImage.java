package org.gxz.mydemo.img.bitmapgray;

import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public class ReadImage {

	/**
	 * 
	 * @param bitmap
	 *            处理原图
	 * @param fileName
	 *            存储文件位置+名称
	 * @return 去色后的图片
	 */
	public static Bitmap toGrayImage(Bitmap bitmap, String fileName) {
		try {
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			Bitmap grayImg = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			//
			Canvas canvas = new Canvas(grayImg);

			Paint paint = new Paint();
			ColorMatrix colorMatrix = new ColorMatrix();
			colorMatrix.setSaturation(0);
			ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(colorMatrix);
			paint.setColorFilter(colorMatrixFilter);
			canvas.drawBitmap(bitmap, 0, 0, paint);
			// canvas.
			if (fileName != null) {
				File file = new File(fileName);
				if (file.createNewFile()) {
					FileOutputStream stream = new FileOutputStream(file);
					grayImg.compress(CompressFormat.JPEG, 100, stream);
					stream.flush();
					stream.close();
				}
			}
			return grayImg;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
