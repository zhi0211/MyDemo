/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gxz.mydemo.capture.qrcode.decoding;

import java.util.Vector;

import org.gxz.mydemo.R;
import org.gxz.mydemo.capture.qrcode.ICaptureActivity;
import org.gxz.mydemo.capture.qrcode.camera.CameraManager;
import org.gxz.mydemo.capture.qrcode.view.ViewfinderResultPointCallback;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

/**
 * This class handles all the messaging which comprises the state machine for
 * capture. 这个handler其实就是用来处理相机捕获的结果
 * 
 * @author dswitkin@google.com (Daniel Switkin)
 */
public final class CaptureActivityHandler extends Handler {

	private static final String TAG = CaptureActivityHandler.class
			.getSimpleName();

	/**
	 * 捕获二维码的activity
	 */
	private final ICaptureActivity activity;
	/**
	 * 处理捕获到图片的解析线程（是一个Looper线程）
	 */
	private final DecodeThread decodeThread;
	/**
	 * 当前捕获的状态
	 */
	private State state;

	/**
	 * 状态枚举
	 */
	private enum State {
		PREVIEW, SUCCESS, DONE
	}

	public CaptureActivityHandler(ICaptureActivity activity,
			Vector<BarcodeFormat> decodeFormats, String characterSet) {
		this.activity = activity;
		/**
		 * 设置解码线程
		 */
		decodeThread = new DecodeThread(activity, decodeFormats, characterSet,
				new ViewfinderResultPointCallback(activity.getViewfinderView()));
		/**
		 * 解码线程开始执行
		 */
		decodeThread.start();
		/**
		 * 状态设置为成功
		 */
		state = State.SUCCESS;

		// Start ourselves capturing previews and decoding.
		/**
		 * 相机准备
		 */
		CameraManager.get().startPreview();
		restartPreviewAndDecode();
	}

	@Override
	public void handleMessage(Message message) {
		int what = message.what;
		if (what == R.id.auto_focus) {
			// Log.d(TAG, "Got auto-focus message");
			// When one auto focus pass finishes, start another. This is the
			// closest thing to
			// continuous AF. It does seem to hunt a bit, but I'm not sure
			// what else to do.
			if (state == State.PREVIEW) {
				/**
				 * 处理自动聚焦
				 */
				CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
			}
		} else if (what == R.id.restart_preview) {
			Log.d(TAG, "Got restart preview message");
			restartPreviewAndDecode();
		} else if (what == R.id.decode_succeeded) {
			Log.d(TAG, "Got decode succeeded message");
			state = State.SUCCESS;
			Bundle bundle = message.getData();
			Bitmap barcode = bundle == null ? null : (Bitmap) bundle
					.getParcelable(DecodeThread.BARCODE_BITMAP);
			activity.handleDecode((Result) message.obj, barcode);
		} else if (what == R.id.decode_failed) {
			// We're decoding as fast as possible, so when one decode fails,
			// start another.
			state = State.PREVIEW;
			CameraManager.get().requestPreviewFrame(decodeThread.getHandler(),
					R.id.decode);
		}
		/**
		 * case R.id.return_scan_result: Log.d(TAG,
		 * "Got return scan result message");
		 * activity.setResult(Activity.RESULT_OK, (Intent) message.obj);
		 * activity.finish(); break; case R.id.launch_product_query: Log.d(TAG,
		 * "Got product query message"); String url = (String) message.obj;
		 * Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		 * intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		 * activity.startActivity(intent); break;
		 **/
	}

	public void quitSynchronously() {
		state = State.DONE;
		/**
		 * 关闭相机
		 */
		CameraManager.get().stopPreview();
		/**
		 * 关闭looper解码线程
		 */
		Message quit = Message.obtain(decodeThread.getHandler(), R.id.quit);
		quit.sendToTarget();

		try {
			/**
			 * 这里join是用来等待解码线程的结束
			 */
			decodeThread.join();
		} catch (InterruptedException e) {
			// continue
		}

		// Be absolutely sure we don't send any queued up messages
		removeMessages(R.id.decode_succeeded);
		removeMessages(R.id.decode_failed);
	}

	private void restartPreviewAndDecode() {
		if (state == State.SUCCESS) {
			/**
			 * 设置状态为捕获准备，其实就是设置解码handler和显示图像的surface
			 */
			state = State.PREVIEW;
			CameraManager.get().requestPreviewFrame(decodeThread.getHandler(),
					R.id.decode);
			/**
			 * 设置相机自动聚焦
			 */
			CameraManager.get().requestAutoFocus(this, R.id.auto_focus);
			/**
			 * 清空view中的结果图片,就是让view再绘制那个中间的框框和红线
			 */
			activity.drawViewfinder();
		}
	}

}
