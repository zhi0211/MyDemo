package org.gxz.mydemo.ratingbar;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;
import org.gxz.mydemo.widgets.RatingBarView;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RatingBarViewActivity extends BaseActivity implements OnClickListener{

	private RatingBarView mRatingBar;
	private EditText et,et1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rating_bar_view);
		mRatingBar=(RatingBarView)findViewById(R.id.rating_bar_view_setting);
		et=(EditText)findViewById(R.id.rating_bar_view_edit);
		et1=(EditText)findViewById(R.id.rating_bar_view_edit1);
		Button btn=(Button)findViewById(R.id.rating_bar_view_btn);
		btn.setOnClickListener(this);
		Button btn1=(Button)findViewById(R.id.rating_bar_view_btn1);
		btn1.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rating_bar_view_btn:
			try{
			mRatingBar.setRating(Float.valueOf(et.getText().toString()));
			}catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(RatingBarViewActivity.this, "setRating"+e.getMessage(), Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.rating_bar_view_btn1:
			try{
				mRatingBar.setRatio(Float.valueOf(et1.getText().toString()));
				}catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(RatingBarViewActivity.this, "setRoatio"+e.getMessage(), Toast.LENGTH_SHORT).show();
				}
			break;

		default:
			break;
		}
		
	}

}
