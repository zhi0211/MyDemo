package org.gxz.mydemo.anim;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class WelcomeActivity extends BaseActivity {

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcomelogo);
		RelativeLayout layout=(RelativeLayout)findViewById(R.id.welcome_layout);
		//得到ImageView控件
        ImageView logo = (ImageView) findViewById(R.id.logo2);
        //使用xml中自定义的动画效果
        Animation logo_animation = AnimationUtils.loadAnimation(WelcomeActivity.this, 
        		R.anim.welcome_layout_scale);
        layout.setAnimation(logo_animation);
//        logo.setAnimation(logo_animation);
        logo_animation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				WelcomeActivity.this.finish();
			}
		});
	}

}
