package org.gxz.mydemo.main.mytab;

import java.util.List;
import java.util.Map;

import org.gxz.mydemo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyConversationAdapter extends BaseAdapter {

	
	private Context ctx;
	private List<Map<String,Object>> list;
	public MyConversationAdapter(Context ctx,List<Map<String,Object>> list){
		this.list=list;
		this.ctx=ctx;
	}
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if(null==convertView){
			convertView=LayoutInflater.from(ctx).inflate(R.layout.sms_item, null);
			holder=new ViewHolder();
			holder.rlayout=(LinearLayout)convertView.findViewById(R.id.sms_recevied_layout);
			holder.rbody=(TextView)convertView.findViewById(R.id.sms_recevied_body);
			holder.rdate=(TextView)convertView.findViewById(R.id.sms_recevied_date);
			holder.slayout=(LinearLayout)convertView.findViewById(R.id.sms_sent_layout);
			holder.sbody=(TextView)convertView.findViewById(R.id.sms_sent_body);
			holder.sdate=(TextView)convertView.findViewById(R.id.sms_sent_date);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		if((Integer)list.get(position).get("type")==1){//收到
			holder.rlayout.setVisibility(View.VISIBLE);
			holder.slayout.setVisibility(View.GONE);
			holder.rbody.setText(list.get(position).get("body").toString());
			holder.rdate.setText(list.get(position).get("date").toString());
		}else{
			holder.rlayout.setVisibility(View.GONE);
			holder.slayout.setVisibility(View.VISIBLE);
			holder.sbody.setText(list.get(position).get("body").toString());
			holder.sdate.setText(list.get(position).get("date").toString());
		}
		return convertView;
	}
	
	static class ViewHolder{
		LinearLayout slayout,rlayout;
		TextView sbody,rbody,sdate,rdate;
	}

}
