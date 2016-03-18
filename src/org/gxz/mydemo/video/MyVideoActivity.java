package org.gxz.mydemo.video;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;

import android.app.Dialog;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MyVideoActivity extends BaseActivity implements OnErrorListener,
		OnCompletionListener {

	private static final String PATH = Environment
			.getExternalStorageDirectory() + "/11.mp4";
	private VideoView videoView;
	private int index = -1;

	private List<File> datas = new ArrayList<File>();

	//
	// private ImageView imageView;
	//
	// private TextView timeTextView;
	//
	// private TextView titleTextView;
	//
	// private TextView clockTextView;
	//
	// private ScheduledExecutorService scheduledExecutorService;
	//
	// private Handler handler;
	//
	// private SimpleDateFormat dateFormat;

	private Handler mHandler = new Handler();
	private Runnable mRunnable = new Runnable() {

		@Override
		public void run() {

			Log.v("VideoView", "perent:" + videoView.getBufferPercentage()
					+ ";current:" + videoView.getCurrentPosition()+";duration:"+videoView.getDuration());
			mHandler.postDelayed(mRunnable, 1000);
		}
	};
	@Override
	protected void onDestroy() {
		mHandler.removeCallbacks(mRunnable);
		super.onDestroy();
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_my_video);

		// this.dateFormat = new SimpleDateFormat("HH:mm:ss");

		// this.scheduledExecutorService = Executors.newScheduledThreadPool(2);
		// this.handler = new Handler();

		this.videoView = (VideoView) this.findViewById(R.id.videoView);
		// this.imageView = (ImageView) this.findViewById(R.id.image);
		// this.imageView.setImageResource(R.drawable.ad);

		// this.timeTextView = (TextView) this.findViewById(R.id.timeText);
		// this.titleTextView = (TextView) this.findViewById(R.id.videoTitle);
		// this.clockTextView = (TextView) this.findViewById(R.id.clockText);

		MediaController controller = new MediaController(this);
		this.videoView.setMediaController(controller);
		// this.videoView.setOnPreparedListener(new OnPreparedListener() {
		// @Override
		// public void onPrepared(MediaPlayer mp) {
		// // progressBar.setVisibility(View.GONE);
		// imageView.setVisibility(View.GONE);
		// timeTextView.setVisibility(View.VISIBLE);
		// titleTextView.setText("制作花絮");
		//
		// mp.start();
		//
		// scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
		//
		// @Override
		// public void run() {
		// handler.post(new Runnable() {
		// @Override
		// public void run() {
		// int position = videoView.getCurrentPosition();
		// int duration = videoView.getDuration();
		// timeTextView
		// .setText(getTimeFormatValue(position)
		// + " / "
		// + getTimeFormatValue(duration));
		// }
		// });
		//
		// }
		// }, 1000, 1000, TimeUnit.MILLISECONDS);
		//
		// scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
		// @Override
		// public void run() {
		// handler.post(new Runnable() {
		//
		// @Override
		// public void run() {
		// clockTextView.setText(dateFormat
		// .format(new Date()));
		// }
		// });
		//
		// }
		// }, 1000, 1000 * 10, TimeUnit.MILLISECONDS);
		// }
		// });
		// controller.setAnchorView(videoView);
		initVideoData();

	}

	// private static String getTimeFormatValue(long time) {
	// return MessageFormat.format(
	// "{0,number,00}:{1,number,00}:{2,number,00}",
	// time / 1000 / 60 / 60, time / 1000 / 60 % 60, time / 1000 % 60);
	// }

	@Override
	protected void onPause() {
		index = videoView.getCurrentPosition();
		videoView.stopPlayback();
		super.onPause();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		if (index >= 0) {
			videoView.seekTo(index);
			index = -1;
		}
		super.onResume();
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		this.finish();
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		return false;
	}

	/**
	 * 加载视频资源
	 */
	private void initVideoData() {
		Cursor cs = getContentResolver().query(
				MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null,
				MediaStore.Video.Media.DATE_MODIFIED + " desc");
		ArrayList<File> list = new ArrayList<File>();
		while (cs.moveToNext()) {
			File file = new File(cs.getString(cs
					.getColumnIndex(MediaStore.Audio.Media.DATA)));
			list.add(file);
		}
		cs.close();
		datas.clear();
		datas.addAll(list);
		if (datas.size() <= 0) {
			Toast.makeText(this, "没有视频文件", 0).show();
			finish();
		}

		final Dialog dialog = new Dialog(this);
		dialog.setTitle("视频选择");
		ListView listview = new ListView(this);
		LinearLayout.LayoutParams params = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		// listview.setLayoutParams(params);
		dialog.addContentView(listview, params);
		List<String> lists = new ArrayList<String>();
		for (File file : datas) {
			lists.add(file.getName());
		}
		ListAdapter mAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, lists);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				videoView.setVideoURI(Uri.parse(datas.get(position).getPath()));
				videoView.start();
				mHandler.post(mRunnable);
				dialog.dismiss();
			}
		});
		listview.setAdapter(mAdapter);
		dialog.show();

	}
}