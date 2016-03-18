package org.gxz.mydemo.main.mytab;

import java.util.List;
import java.util.Map;

import org.gxz.mydemo.R;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SMSAdapter extends BaseAdapter {

	private Context ctx;
	private Button curDel_btn;
	private MyTabSMSDao dao;
	private float ux, dx;
	private List<Map<String, Object>> listData;

	public SMSAdapter(Context ctx, List<Map<String, Object>> listData) {
		this.ctx = ctx;
		this.listData = listData;
		dao = new MyTabSMSDao(ctx);
	}

	@Override
	public int getCount() {
		return listData.size();
	}

	@Override
	public Object getItem(int arg0) {
		return listData.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int pos, View view, ViewGroup parent) {
		ViewHolder holder;
		if (null == view) {
			view = LayoutInflater.from(ctx).inflate(R.layout.tab_my_home_item, null);
			holder = new ViewHolder();
			holder.body = (TextView) view.findViewById(R.id.my_home_item_body);
			holder.brief = (TextView) view.findViewById(R.id.my_home_item_phone);
			// holder.date=(TextView)view.findViewById(R.id.my_home_item_date);
			holder.photo = (ImageView) view.findViewById(R.id.my_home_item_photo);
			holder.delBtn = (Button) view.findViewById(R.id.my_home_item_del_btn);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		Map<String, Object> m;
		m = listData.get(pos);
		String calling = m.get("calling").toString().trim();
		Bitmap photo = dao.getPhotoByNumber(calling);
		if (photo == null) {
			holder.photo.setImageResource(R.drawable.contactdefaultfigure);
		} else {
			holder.photo.setImageBitmap(photo);
		}
		holder.body.setText(m.get("last_body").toString());
		holder.brief.setText(m.get("brief").toString());
//		view.setOnTouchListener(new ItemOnTouchListener(parent,pos));
//		holder.delBtn.setOnClickListener(new DelBtnOnclickListener(pos,parent));
		view.setOnClickListener(new ItemClick(pos));
		return view;
	}

	private class ViewHolder {
		TextView brief, body, date;
		ImageView photo;
		Button delBtn;
	}

	private class ItemClick implements OnClickListener{
		
		private int pos;
		public ItemClick(int pos) {
			this.pos=pos;
		}
		@Override
		public void onClick(View v) {
			Intent i=new Intent(ctx,ConversationsActivity.class);
			i.putExtra("title", listData.get(pos).get("calling").toString());
			i.putExtra("tid", (Long)listData.get(pos).get("tid"));
			ctx.startActivity(i);
			((MyHomeActivity)ctx).overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		}
		
	}
	private class DelBtnOnclickListener implements OnClickListener {
		private int pos;
		private ViewGroup vg;
		public DelBtnOnclickListener(int pos,ViewGroup parent) {
			this.pos = pos;
			this.vg=parent;
		}

		@Override
		public void onClick(View v) {
			listData.remove(pos);
			curDel_btn.setVisibility(View.GONE);
			notifyDataSetChanged();
		}

	}

	private class ItemOnTouchListener implements OnTouchListener {
		private ViewGroup vg;
		private int pos;
		public ItemOnTouchListener(ViewGroup vg,int pos){
			this.vg=vg;
			this.pos=pos;
		}
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			final ViewHolder holder = (ViewHolder) v.getTag();
			// 当按下时处理
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				// 获取按下时的x轴坐标
				dx = event.getX();
				// 判断之前是否出现了删除按钮如果存在就隐藏
				if (curDel_btn!=null) {
					 curDel_btn.setVisibility(View.GONE);
					 curDel_btn=null;
					 return true;
				}
			}
			if(event.getAction()==MotionEvent.ACTION_MOVE){
			}
			if (event.getAction() == MotionEvent.ACTION_UP) {// 松开处理
				// 获取松开时的x坐标
				ux = event.getX();
				// 判断当前项中按钮控件不为空时
				if (holder.delBtn != null) {
					// 按下和松开绝对值差当大于20时显示删除按钮，否则不显示
					if (Math.abs(dx - ux) > 20) {
						holder.delBtn.setVisibility(View.VISIBLE);
						Animation btn_animation = AnimationUtils.loadAnimation(ctx, R.anim.btn_push_left_in);
						holder.delBtn.setAnimation(btn_animation);
						curDel_btn = holder.delBtn;
						return true;
					}
				}

			}
			return false;
		}

	}
}
