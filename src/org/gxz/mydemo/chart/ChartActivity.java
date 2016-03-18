package org.gxz.mydemo.chart;

import java.util.HashMap;
import java.util.Random;

import org.gxz.mydemo.DemoApplication;
import org.gxz.mydemo.R;
import org.gxz.mydemo.widgets.ChartView;
import org.gxz.mydemo.widgets.ChartView.Mstyle;

import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import cn.jpush.android.api.InstrumentedActivity;

public class ChartActivity extends InstrumentedActivity {

	ChartView tu;
	HashMap<Double, Double> map;
	Double key = 8.0;
	Double value = 0.0;
	Random r = new Random();

	@Override
	protected void onDestroy() {
		super.onDestroy();
		((DemoApplication)getApplication()).removeActivity(this);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((DemoApplication)getApplication()).addActivity(this);

		map = new HashMap<Double, Double>();
		map.put(1.0, 120.0);
		map.put(2.0, 4.0);
		map.put(3.0, 1.0);
		map.put(4.0, 18.0);
		map.put(5.0, 160.0);
		map.put(6.0, 180.0);
		map.put(7.0, 133.0);
		map.put(8.0, 143.0);
		map.put(9.0, 153.0);
		map.put(10.0, 163.0);
		map.put(11.0, 173.0);
		map.put(12.0, 183.0);
		map.put(13.0, 193.0);
		map.put(14.0, 186.0);
		map.put(15.0, 195.0);
		map.put(16.0, 200.0);

		tu = new ChartView(this, map, 50, 50, "x", "y", false);
		// tu.setC(Color.CYAN);
		// tu.setResid(R.drawable.bbb);
		// tu.setBheight(200);
		tu.setTotalvalue(200);

		tu.setPjvalue(40);
		tu.setXstr("");
		tu.setYstr("");
		tu.setMargint(20);
		tu.setBackgroundColor(Color.WHITE);
		tu.setMarginb(50);
		tu.setMstyle(Mstyle.Line);
		RelativeLayout rela = getlayout(R.layout.activity_chart);
		rela.addView(tu);
		LayoutParams parm = new LayoutParams(1280, 720);
		parm.setMargins(0, 0, 0, 0);
		tu.setLayoutParams(parm);
		setContentView(rela);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_chart, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.menu_suiji) {
			HashMap<Double, Double> map = new HashMap<Double, Double>();
			map.put(1.0, getRandomDigit());
			map.put(2.0, getRandomDigit());
			map.put(3.0, getRandomDigit());
			map.put(4.0, getRandomDigit());
			map.put(5.0, getRandomDigit());
			map.put(6.0, getRandomDigit());
			map.put(7.0, getRandomDigit());
			map.put(8.0, getRandomDigit());
			map.put(9.0, getRandomDigit());
			map.put(10.0, getRandomDigit());
			map.put(11.0, getRandomDigit());
			map.put(12.0, getRandomDigit());
			map.put(13.0, getRandomDigit());
			map.put(14.0, getRandomDigit());
			map.put(15.0, getRandomDigit());
			map.put(16.0, getRandomDigit());
			map.put(17.0, getRandomDigit());
			map.put(18.0, getRandomDigit());
			map.put(19.0, getRandomDigit());
			map.put(20.0, getRandomDigit());
			tu.setTotalvalue(200);
			tu.setPjvalue(40);
			tu.setMap(map);
			tu.setMstyle(Mstyle.Line);
			tu.setIsylineshow(false);
			tu.postInvalidate();

		} else if (item.getItemId() == R.id.menu_ch) {
			HashMap<Double, Double> map = new HashMap<Double, Double>();
			map.put(1.0, 21.0);
			map.put(3.0, 25.0);
			map.put(4.0, 32.0);
			map.put(5.0, 31.0);
			map.put(6.0, 26.0);

			tu.setTotalvalue(40);
			tu.setPjvalue(10);
			tu.setMap(map);
			tu.setMstyle(Mstyle.scroll);
			tu.setIsylineshow(true);
			tu.postInvalidate();
		} else if (item.getItemId() == R.id.menu_ch2) {
			HashMap<Double, Double> map = new HashMap<Double, Double>();
			map.put(1.0, 41.0);
			map.put(3.0, 25.0);
			map.put(4.0, 32.0);
			map.put(5.0, 41.0);
			map.put(6.0, 16.0);
			map.put(7.0, 36.0);
			map.put(8.0, 26.0);
			tu.setTotalvalue(50);
			tu.setPjvalue(10);
			tu.setMap(map);
			tu.setMstyle(Mstyle.Line);
			tu.setIsylineshow(false);
			tu.postInvalidate();
		}
		return true;
	}

	public RelativeLayout getlayout(int r) {
		LayoutInflater inflater = (LayoutInflater) getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
		try {
			XmlResourceParser parser = getResources().getLayout(r);
			RelativeLayout layout = (RelativeLayout) inflater.inflate(parser,
					null);
			return layout;
		} catch (Exception e) {
		}
		return null;
	}

	private double getRandomDigit() {
		return r.nextInt(200);
	}

}
