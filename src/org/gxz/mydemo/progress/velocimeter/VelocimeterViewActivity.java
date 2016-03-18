package org.gxz.mydemo.progress.velocimeter;

import org.gxz.mydemo.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SeekBar;

public class VelocimeterViewActivity extends Activity {

  private SeekBar seek;
  private VelocimeterView velocimeter2;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_velocimeter);
    seek = (SeekBar) findViewById(R.id.seek);
    seek.setOnSeekBarChangeListener(new SeekListener());
    velocimeter2 = (VelocimeterView) findViewById(R.id.velocimeter2);
  }

  private class SeekListener implements SeekBar.OnSeekBarChangeListener {

    @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
      velocimeter2.setValue(progress, true);
    }

    @Override public void onStartTrackingTouch(SeekBar seekBar) {
      //Empty
    }

    @Override public void onStopTrackingTouch(SeekBar seekBar) {
      //Empty
    }
  }
}
