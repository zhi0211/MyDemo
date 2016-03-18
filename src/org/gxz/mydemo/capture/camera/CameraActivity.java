package org.gxz.mydemo.capture.camera;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;
import org.gxz.mydemo.capture.qrcode.camera.CameraConfigurationManager;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by gxz on 2015/7/10.
 */
public class CameraActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Window window = getWindow();
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.activity_camera_lyt);

		surfaceView = (SurfaceView) findViewById(R.id.surfaceview);
		WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		surfaceView.getHolder().setFixedSize(manager.getDefaultDisplay().getWidth(), manager.getDefaultDisplay().getWidth());
		surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		surfaceView.getHolder().addCallback(new SurfaceCallback());
		configManager = new CameraConfigurationManager(this);
		findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				int cameraCount = 0;
				Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
				cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数

				for (int i = 0; i < cameraCount; i++) {
					Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
					if (cameraPosition == Camera.CameraInfo.CAMERA_FACING_BACK) {
						//现在是后置，变更为前置
						if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置  
							mCamera.stopPreview();//停掉原来摄像头的预览
							mCamera.release();//释放资源
							mCamera = null;//取消原来摄像头
							mCamera = Camera.open(i);//打开当前选中的摄像头
							try {
								update(surfaceView.getHolder());
							} catch (IOException e) {
								e.printStackTrace();
							}
							mCamera.startPreview();//开始预览
							cameraPosition = Camera.CameraInfo.CAMERA_FACING_FRONT;
							break;
						}
					} else {
						//现在是前置， 变更为后置
						if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置  
							mCamera.stopPreview();//停掉原来摄像头的预览
							mCamera.release();//释放资源
							mCamera = null;//取消原来摄像头
							mCamera = Camera.open(i);//打开当前选中的摄像头
							try {
								update(surfaceView.getHolder());
							} catch (IOException e) {
								e.printStackTrace();
							}
							mCamera.startPreview();//开始预览
							cameraPosition = Camera.CameraInfo.CAMERA_FACING_BACK;
							break;
						}
					}

				}
			}
		});
	}

	private SurfaceView surfaceView;
	private Camera mCamera;
	private boolean isPreview;
	private int cameraPosition = Camera.CameraInfo.CAMERA_FACING_BACK;
	private CameraConfigurationManager configManager;


	public void openDriver(SurfaceHolder holder) throws IOException {
		if (mCamera == null) {
			mCamera = Camera.open();
			if (mCamera == null) {
				throw new IOException();
			}
			update(holder);
		}
		mCamera.startPreview();
	}
	
	private void update(SurfaceHolder holder) throws IOException{
		mCamera.setPreviewDisplay(holder);
		if (!isPreview) {
			isPreview = true;
			configManager.initFromCameraParameters(mCamera);
		}
		configManager.setDesiredCameraParameters(mCamera);
	}

	private class SurfaceCallback implements SurfaceHolder.Callback {

		@Override
		public void surfaceCreated(SurfaceHolder surfaceHolder) {
			try {
				//设置参数
				openDriver(surfaceHolder);
			} catch (IOException e) {
				if (mCamera != null) mCamera.release();
				mCamera = null;
			}
		}

		@Override
		public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
			if (mCamera != null) {
				if (isPreview) {//如果正在预览 
					mCamera.stopPreview();
					mCamera.release();
				}
			}
		}
	}

	


	
}
