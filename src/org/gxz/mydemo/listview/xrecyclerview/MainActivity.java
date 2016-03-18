package org.gxz.mydemo.listview.xrecyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xrecyclerview_main);
		ListView listView = (ListView) findViewById(R.id.listview);
		List<String> list = new ArrayList<String>();

		list.add("SysProgress");
		list.add("BallPulse");
		list.add("BallGridPulse");
		list.add("BallClipRotate");
		list.add("BallClipRotatePulse");
		list.add("SquareSpin");
		list.add("BallClipRotateMultiple");
		list.add("BallPulseRise");
		list.add("BallRotate");
		list.add("CubeTransition");
		list.add("BallZigZag");
		list.add("BallZigZagDeflect");
		list.add("BallTrianglePath");
		list.add("BallScale");
		list.add("LineScale");
		list.add("LineScaleParty");
		list.add("BallScaleMultiple");
		list.add("BallPulseSync");
		list.add("BallBeat");
		list.add("LineScalePulseOut");
		list.add("LineScalePulseOutRapid");
		list.add("BallScaleRipple");
		list.add("BallScaleRippleMultiple");
		list.add("BallSpinFadeLoader");
		list.add("LineSpinFadeLoader");
		list.add("TriangleSkewSpin");
		list.add("Pacman");
		list.add("BallGridBeat");
		list.add("SemiCircleSpin");
		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				int style = position - 1;
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, LinearActivity.class);
				intent.putExtra("style", style);
				startActivity(intent);

			}
		});
	}

	public void gotoLinearActivity(View v) {
		Intent intent = new Intent();
		intent.setClass(this, LinearActivity.class);
		startActivity(intent);
	}

	public void gotoGridActivity(View v) {
		Intent intent = new Intent();
		intent.setClass(this, GridActivity.class);
		startActivity(intent);
	}

	public void gotoStaggeredGridActivity(View v) {
		Intent intent = new Intent();
		intent.setClass(this, StaggeredGridActivity.class);
		startActivity(intent);
	}

	public void gotoProgress(View v) {
		Intent intent = new Intent();
		intent.setClass(this, ProgressStyles.class);
		startActivity(intent);
	}

}
