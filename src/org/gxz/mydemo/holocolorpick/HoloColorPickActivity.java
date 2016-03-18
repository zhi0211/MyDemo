package org.gxz.mydemo.holocolorpick;

import org.gxz.mydemo.R;
import org.gxz.mydemo.circle.CircularSeekBar;
import org.gxz.mydemo.holocolorpick.ColorPicker.OnColorChangedListener;

import android.app.Activity;
import android.os.Bundle;

public class HoloColorPickActivity extends Activity implements OnColorChangedListener {

	CircularSeekBar circularSeekbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_holocolor_pick);
		ColorPicker picker = (ColorPicker) findViewById(R.id.picker);
		SVBar svBar = (SVBar) findViewById(R.id.svbar);
		OpacityBar opacityBar = (OpacityBar) findViewById(R.id.opacitybar);
		SaturationBar saturationBar = (SaturationBar) findViewById(R.id.saturationbar);
		ValueBar valueBar = (ValueBar) findViewById(R.id.valuebar);

		picker.addSVBar(svBar);
		picker.addOpacityBar(opacityBar);
		picker.addSaturationBar(saturationBar);
		picker.addValueBar(valueBar);

		//To get the color
		picker.getColor();

		//To set the old selected color u can do it like this
		picker.setOldCenterColor(picker.getColor());
		// adds listener to the colorpicker which is implemented
		//in the activity
		picker.setOnColorChangedListener(this);

		//to turn of showing the old color
		picker.setShowOldCenterColor(false);

		//adding onChangeListeners to bars
//		opacityBar.setonosetOnOpacityChangeListener(new OnOpacityChangeListener());
//		valueBar.setOnValueChangeListener(this);
//		saturationBar.setOnSaturationChangeListener(this);
		

	}

	@Override
	public void onColorChanged(int color) {
		
	}
	
	
}
