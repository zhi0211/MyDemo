package org.gxz.mydemo.pie;

import java.util.ArrayList;
import java.util.List;

import org.gxz.mydemo.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PieActivity extends Activity implements OnClickListener {

	private PieChart pie;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_pie);

		pie = (PieChart) findViewById(R.id.pie_chart);
		pie.setData(getValues());
		pie.invalidate();
		Button pieBtn=(Button) findViewById(R.id.pie_btn2);
		pieBtn.setOnClickListener(this);

	}

	private List<TitleValueColorEntity> getValues() {
		int[] colors = new int[] { Color.RED, Color.GREEN, Color.BLUE,
				Color.YELLOW };
		String[] titles = new String[] { "红色", "绿色", "蓝色", "黄色" };
		
		float[] values=new float[]{2.0f,4.8f,7.9f,1f};
		
		List<TitleValueColorEntity> entitys=new ArrayList<TitleValueColorEntity>();
		for(int i=0;i<4;i++){
			TitleValueColorEntity value=new TitleValueColorEntity(titles[i], values[i], colors[i]);
			entitys.add(value);
		}
		return entitys;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.pie_btn2:
			Intent intent=new Intent(this,PieActivity2.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		
	}

}
