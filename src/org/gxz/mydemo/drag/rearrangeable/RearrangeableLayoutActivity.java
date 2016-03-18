package org.gxz.mydemo.drag.rearrangeable;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;

public class RearrangeableLayoutActivity extends BaseActivity {
	private static final String TAG = "DEMO-REARRANGEABLE-LOUT";
	private RearrangeableLayout root;
	private View.OnLongClickListener longClickListener;
	private View.OnClickListener onClickListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rearrangeablelayout);

		root = (RearrangeableLayout) findViewById(R.id.rearrangeable_layout);
		root.setChildPositionListener(new RearrangeableLayout.ChildPositionListener() {
			@Override
			public void onChildMoved(View childView, Rect oldPosition, Rect newPosition) {
				Log.d(TAG, childView.toString());
				Log.d(TAG, oldPosition.toString() + " -> " + newPosition.toString());
				Log.d(TAG, "now pos>left:" + childView.getLeft() + ",top:" + childView.getTop() + ",right:" + childView.getRight() + ",bottom:" + childView.getBottom());
				Log.d(TAG, "view wh>width:" + childView.getWidth() + ",height:" + childView.getHeight());
//				index++;
//				childView.setTag(index);
			}
		});
		longClickListener = new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				if (root.isDrag()) {
					root.setIsLongPressed(true);
				}
				return true;
			}
		};

//        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                Log.d(TAG, "onGlobalLayout");
//                Log.d(TAG, root.toString());
//            }
//        });
//        root.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                //Log.d(TAG, "onPreDraw");
//                //Log.d(TAG, root.toString());
//                return true;
//            }
//        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_rearrangeablelayout, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			root.setIsDrag(!root.isDrag());
			return true;
		} else if (id == R.id.action_add) {
			index++;
			root.addView(getView());
			return true;
		} else if (id == R.id.action_log) {
			StringBuffer stringBuffer = new StringBuffer();
			for (int i = 0; i < root.getChildCount(); i++) {
				TextView view = (TextView) root.getChildAt(i);
				stringBuffer.append(",(TAG=").append(view.getTag()).append(")");
//						append(",TEXTVIEW").append(view.getText().toString()).append(")");

			}
			Log.d(TAG, "log=" + stringBuffer.toString());
			return true;
		}

		return super.onOptionsItemSelected(item);
	}


	private int index = 0;

	private View getView() {
		RearrangeableLayout.LayoutParams params = new RearrangeableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		TextView textView = new TextView(this);
		textView.setLayoutParams(params);
		textView.setText("新的TEXTVIEW" + index);
		textView.setTextSize(14);
		textView.setBackgroundColor(Color.YELLOW);
		textView.setTag(index);
		int padding = index * 10;
		textView.setPadding(padding, padding, padding, padding);
		textView.setOnLongClickListener(longClickListener);
		return textView;
	}
}
