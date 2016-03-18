package org.gxz.mydemo.main.mytab;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ConversationsActivity extends BaseActivity implements OnClickListener {

	private TextView title;
	private ListView lv;
	private String calling;
	private long tid;
	private MyTabSMSDao dao;
	private List<Map<String, Object>> listData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conversations);
		lv = (ListView) findViewById(R.id.sms_listview);
		title = (TextView) findViewById(R.id.conversations_name);
		ImageView callBtn = (ImageView) findViewById(R.id.sms_call_btn);
		callBtn.setOnClickListener(this);
		ImageView smsBtn = (ImageView) findViewById(R.id.sms_sent_btn);
		smsBtn.setOnClickListener(this);
		Intent i = getIntent();
		calling = i.getStringExtra("title");
		tid = i.getLongExtra("tid", -1);
		dao = new MyTabSMSDao(getApplicationContext());
		String name = dao.getNameByNumber(calling);
		if (name == null)
			title.setText(calling);
		else
			title.setText(name);
		listData = new ArrayList<Map<String, Object>>();
		listData = dao.getContentDetail(listData, tid);
		MyConversationAdapter a = new MyConversationAdapter(this, listData);
		lv.setAdapter(a);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sms_call_btn:
			Toast.makeText(this, "call:" + calling, 0).show();
			break;

		case R.id.sms_sent_btn:
			Toast.makeText(this, "send:" + calling, 0).show();
			break;
		}
	}

}
