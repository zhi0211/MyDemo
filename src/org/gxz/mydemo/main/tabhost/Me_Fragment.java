package org.gxz.mydemo.main.tabhost;

import org.gxz.mydemo.DemoApplication;
import org.gxz.mydemo.R;
import org.gxz.mydemo.spinner.SelectActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

public class Me_Fragment extends Fragment implements OnClickListener {

	private View view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((DemoApplication) getActivity().getApplication())
				.addActivity(getActivity());
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		((DemoApplication) getActivity().getApplication())
				.removeActivity(getActivity());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.me, container, false);
		Button btn = (Button) view.findViewById(R.id.button1);
		btn.setOnClickListener(this);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		TimePicker timePicker = (TimePicker) view
				.findViewById(R.id.timePicker1);
		timePicker.setIs24HourView(true);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.button1:
			for (int i = 0; i < 10; i++) {
				Log.d(getTag(), "add activity i:" + i);
				Intent intent = new Intent(this.getActivity(),
						SelectActivity.class);
				startActivity(intent);
			}
			Intent k = this
					.getActivity()
					.getApplicationContext()
					.getPackageManager()
					.getLaunchIntentForPackage(
							this.getActivity().getApplicationContext()
									.getPackageName());
			k.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(k);
			break;

		default:
			break;
		}

	}

}
