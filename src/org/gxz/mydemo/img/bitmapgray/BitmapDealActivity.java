package org.gxz.mydemo.img.bitmapgray;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class BitmapDealActivity extends BaseActivity implements OnClickListener {

	private ImageView origImg0, origImg1, origImg2, origImg3;
	private ImageView workedImg;
	
	private int[] imgDraws={R.drawable.default_icon0,R.drawable.default_icon1,R.drawable.default_icon2,R.drawable.default_icon3};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bitmap_deal);

		origImg0 = (ImageView) findViewById(R.id.img_deal_orig0);
		origImg1 = (ImageView) findViewById(R.id.img_deal_orig1);
		origImg2 = (ImageView) findViewById(R.id.img_deal_orig2);
		origImg3 = (ImageView) findViewById(R.id.img_deal_orig3);
		workedImg = (ImageView) findViewById(R.id.img_deal_worked);

		origImg0.setImageResource(imgDraws[0]);
		origImg1.setImageResource(imgDraws[1]);
		origImg2.setImageResource(imgDraws[2]);
		origImg3.setImageResource(imgDraws[3]);

		origImg0.setOnClickListener(this);
		origImg1.setOnClickListener(this);
		origImg2.setOnClickListener(this);
		origImg3.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		BitmapDrawable bd = null;
		switch (v.getId()) {
		case R.id.img_deal_orig0:
			bd = (BitmapDrawable) getResources().getDrawable(imgDraws[0]);
			break;
		case R.id.img_deal_orig1:
			bd = (BitmapDrawable) getResources().getDrawable(imgDraws[1]);
			break;
		case R.id.img_deal_orig2:
			bd = (BitmapDrawable) getResources().getDrawable(imgDraws[2]);
			break;
		case R.id.img_deal_orig3:
			bd = (BitmapDrawable) getResources().getDrawable(imgDraws[3]);
			break;

		default:
			break;
		}
		if (bd != null)
			workedImg.setImageBitmap(ReadImage.toGrayImage(bd.getBitmap(), null));
	}

}
