package org.gxz.mydemo.spannablestringbuilder;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.widget.ImageView;
import android.widget.TextView;

public class SpanStringBuilderActivity extends BaseActivity {

	private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8;
	
	private Handler mhandler=new Handler();
	
	
	private String value="SpannableStringBuilder的使用方法 ";
	private int first=9;
	private int second=15;
	private int third=22;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_spanstringbuilder);
		tv1=(TextView)findViewById(R.id.spansb_text1);
		tv2=(TextView)findViewById(R.id.spansb_text2);
		tv3=(TextView)findViewById(R.id.spansb_text3);
		tv4=(TextView)findViewById(R.id.spansb_text4);
		tv5=(TextView)findViewById(R.id.spansb_text5);
		tv6=(TextView)findViewById(R.id.spansb_text6);
		tv7=(TextView)findViewById(R.id.spansb_text7);
		tv8=(TextView)findViewById(R.id.spansb_text8);
		
		
		tv1.setText("\n原先：\n");
		tv2.setText(value);
		
		tv3.setText("\n不同大小：\n");
		tv4.setText(getDiffText1(value));
		
		tv5.setText("\n下划线:\n");
		tv6.setText(getDiffText2(value));
		
		tv7.setText("\n不同颜色:\n");
		tv8.setText(getDiffText3(value));
		
		final ImageView imgGif=(ImageView)findViewById(R.id.image_gif);
		mhandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				((AnimationDrawable)imgGif.getDrawable()).start();
			}
		}, 50);
	}
	
	
	
	public SpannableStringBuilder getDiffText1(String s){
		SpannableStringBuilder ssb=new SpannableStringBuilder(s);
		ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, first, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ssb.setSpan(new AbsoluteSizeSpan(30,true), first, second,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ssb.setSpan(new AbsoluteSizeSpan(20, true), second, third, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ssb.setSpan(new AbsoluteSizeSpan(12, true), third, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return ssb;
		
	}
	
	
	public SpannableStringBuilder getDiffText2(String s){
		SpannableStringBuilder ssb=new SpannableStringBuilder(s);
		ssb.setSpan(new StyleSpan(Typeface.BOLD), 0, first, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ssb.setSpan(new AbsoluteSizeSpan(30,true), 0, first,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ssb.setSpan(new UnderlineSpan(), second, third, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ssb.setSpan(new AbsoluteSizeSpan(12, true), third, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return ssb;
		
	}
	
	
	public SpannableStringBuilder getDiffText3(String s){
		SpannableStringBuilder ssb=new SpannableStringBuilder(s);
		ssb.setSpan(new ForegroundColorSpan(Color.RED), 0, first, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ssb.setSpan(new ForegroundColorSpan(Color.GREEN), first, second,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ssb.setSpan(new ForegroundColorSpan(Color.BLUE), second, third, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		ssb.setSpan(new ForegroundColorSpan(Color.WHITE), third, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return ssb;
		
	}
	
	
}
