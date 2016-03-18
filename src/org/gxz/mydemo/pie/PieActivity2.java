package org.gxz.mydemo.pie;

import org.gxz.mydemo.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

public class PieActivity2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pie2);
		PanelPieChartLabel pie=(PanelPieChartLabel) findViewById(R.id.pie);
		pie.setTextParam(15, Color.BLACK);
		pie.setBackgroundColor(Color.BLACK);
		pie.setStartAngle(270);
		pie.setBlankColor(0XA0FFFFFF);
		int[] colors=new int[]{Color.LTGRAY,Color.CYAN,Color.RED,Color.YELLOW};
		float[] angles=new float[]{120,40,50,10};
		pie.setPercentage(colors, angles);
		pie.invalidate();
	}

}