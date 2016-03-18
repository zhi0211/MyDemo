package org.gxz.mydemo.capture.qrcode;

import java.io.IOException;
import java.util.Vector;

import org.gxz.mydemo.R;
import org.gxz.mydemo.capture.qrcode.camera.CameraManager;
import org.gxz.mydemo.capture.qrcode.decoding.CaptureActivityHandler;
import org.gxz.mydemo.capture.qrcode.decoding.InactivityTimer;
import org.gxz.mydemo.capture.qrcode.view.ViewfinderView;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

public class CaptureActivity extends ICaptureActivity implements Callback, OnClickListener
{

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private TextView txtResult;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	setOrientation(true);
    	int stype = getIntent().getIntExtra("position", CaptureSettings.CAPTURE_STYLE_ONE);
    	setCaputureStype(stype);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_main);
        // 初始化 CameraManager
        CameraManager.init(getApplication());

        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        txtResult = (TextView) findViewById(R.id.txtResult);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        Button rescan=(Button) findViewById(R.id.rescan_btn);
        rescan.setOnClickListener(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        /**
         * 这里就是一系列初始化相机view的过程
         */
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface)
        {
            initCamera(surfaceHolder);
        }
        else
        {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        /**
         * 这里就是看看是否是正常声音播放模式，如果是就播放声音，如果不是，则不播放
         */
        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL)
        {
            playBeep = false;
        }
        /**
         * 初始化声音
         */
        initBeepSound();
        /**
         * 是否震动
         */
        vibrate = true;
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        /**
         * 关掉相机，关掉解码线程，清空looper队列中message
         */
        if (handler != null)
        {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();// 关掉相机
    }

    @Override
    protected void onDestroy()
    {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * 初始化相机
     */
    private void initCamera(SurfaceHolder surfaceHolder)
    {
        try
        {
            CameraManager.get().openDriver(surfaceHolder);
        }
        catch (IOException ioe)
        {
            return;
        }
        catch (RuntimeException e)
        {
            return;
        }
        if (handler == null)
        {
            /**
             * 新建解码结果handler
             */
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height)
    {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        /**
         * 初始化相机
         */
        if (!hasSurface)
        {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        hasSurface = false;
    }

    /**
     * 返回显示的view
     */
    @Override
    public ViewfinderView getViewfinderView()
    {
        return viewfinderView;
    }

    /**
     * 返回处理解码结果的handler
     */
    @Override
    public Handler getHandler()
    {
        return handler;
    }

    /**
     * 清空view中先前扫描成功的图片
     */
    @Override
    public void drawViewfinder()
    {
        viewfinderView.drawViewfinder();

    }

    @Override
    public void handleDecode(Result obj, Bitmap barcode)
    {
        /**
         * 重新计时
         */
        inactivityTimer.onActivity();
        /**
         * 将结果绘制到view中
         */
        viewfinderView.drawResultBitmap(barcode);
        /**
         * 播放jeep声音
         */
        playBeepSoundAndVibrate();
        /**
         * 显示解码字符串
         */
        txtResult.setText(obj.getBarcodeFormat().toString() + ":"
                + obj.getText());
        Log.d("captureActivity", "BarcodeFormat="+obj.getBarcodeFormat().toString()+";text="+obj.getText());
        //Intent intent = new Intent(CaptureActivity.this, IndexActivity.class);
        //this.startActivity(intent);
        //this.finish();
    }

    private void initBeepSound()
    {
        if (playBeep && mediaPlayer == null)
        {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try
            {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            }
            catch (IOException e)
            {
                mediaPlayer = null;
            }
        }
    }

    /**
     * 震动时间
     */
    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate()
    {
        /**
         * 播放声音
         */
        if (playBeep && mediaPlayer != null)
        {
            mediaPlayer.start();
        }
        /**
         * 震动
         */
        if (vibrate)
        {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener()
    {
        public void onCompletion(MediaPlayer mediaPlayer)
        {
            mediaPlayer.seekTo(0);
        }
    };

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.rescan_btn:
			restartPreviewAfterDelay(handler, 0L);
			txtResult.setText("");
			break;

		default:
			break;
		}
		
	}

}