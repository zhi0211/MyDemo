package org.gxz.mydemo.listview.itemdrag;

import java.util.ArrayList;
import java.util.List;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;
import org.gxz.mydemo.listview.itemdrag.DragListAdapter.OnFunctionBtnListener;

import android.os.Bundle;

public class MyWeiXinListviewActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_wei_xin_listview);
		DragItemListView lv=(DragItemListView) findViewById(R.id.my_weixin_listview);
		DragListAdapter adapter=new DragListAdapter(this,lv, getData(), new ImpCallbackListener());
		lv.setAdapter(adapter);
	}

	private List<HostRecord> getData() {
		List<HostRecord> list = new ArrayList<HostRecord>();
		HostRecord hr;
		for (int i = 0; i < 20; i++) {
			hr = new HostRecord("2342342", "2d:3c:6b", DBConstants.HOST_TYPE_PAD, "test"+i, "test"+(20-i),
					i%2, i%2, 0, i%3);
			list.add(hr);
		}
		return list;
	}

	class ImpCallbackListener implements OnFunctionBtnListener{

		@Override
		public void onContentClick(HostRecord hr) {
			
		}

		@Override
		public void onForeverBtnClick(HostRecord hr) {
			
		}

		@Override
		public void onTempBtnClick(HostRecord hr) {
			
		}
		
	}
}
