package org.gxz.mydemo.listview.popuwindow;

import org.gxz.mydemo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NewQAAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private String[] data;

	public NewQAAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
	}

	public void setData(String[] data) {
		this.data = data;
	}

	@Override
	public int getCount() {
		return data.length;
	}

	@Override
	public Object getItem(int item) {
		return data[item];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.popu_list_item, null);

			holder = new ViewHolder();
			
			holder.mTitleText = (TextView) convertView.findViewById(R.id.t_name);
			holder.img=(ImageView)convertView.findViewById(R.id.popu_list_item_img);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if(position%4==0){
			holder.img.setImageResource(R.drawable.default_else);
		}
		else if(position%4==1){
			holder.img.setImageResource(R.drawable.default_pad);
		}
		else if(position%4==2){
			holder.img.setImageResource(R.drawable.default_pc);
		}
		else{
			holder.img.setImageResource(R.drawable.default_telephone);
		}
		holder.mTitleText.setText(data[position]);

		return convertView;
	}

	static class ViewHolder {
		TextView mTitleText;
		ImageView img;
	}
}