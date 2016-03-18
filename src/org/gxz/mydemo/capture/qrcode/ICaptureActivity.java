package org.gxz.mydemo.capture.qrcode;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;
import org.gxz.mydemo.capture.qrcode.view.ViewfinderView;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.os.Handler;

import com.google.zxing.Result;

/**
 * 二维码父类
 */
public class ICaptureActivity extends BaseActivity {

	private CaptureSettings captureSettings = CaptureSettings.getInstace();

	/**
	 * 清空view中先前扫描成功的图片
	 */
	public void drawViewfinder() {
	}

	/**
	 * 二维码扫描结果
	 * 
	 * @param rawResult
	 *            二维码数据
	 * @param barcode
	 */
	public void handleDecode(Result rawResult, Bitmap barcode) {
	}

	/**
	 * 返回处理解码结果的handler
	 */
	public Handler getHandler() {
		return null;
	}

	/**
	 * 返回显示的view
	 */
	public ViewfinderView getViewfinderView() {
		return null;
	}

	/**
	 * 重新扫描
	 * 
	 * @param handler
	 * @param delay
	 */
	protected void restartPreviewAfterDelay(Handler handler, long delay) {
		handler.sendEmptyMessageDelayed(R.id.restart_preview, delay);
	}

	/**
	 * 声音文件读取
	 * 
	 * @return
	 */
	protected AssetFileDescriptor openRawResourceFd() {
		return getResources().openRawResourceFd(R.raw.beep);
	}

	/**
	 * 设置屏幕方向(必须与Activity设置一致)
	 * 
	 * @param isPortrait
	 *            true为竖屏
	 */
	protected void setOrientation(boolean isPortrait) {
		captureSettings.setPortrait(isPortrait);
	}
	
	
	protected void setCaputureStype(int stype){
		captureSettings.setCaptureStype(stype);
	}

}
