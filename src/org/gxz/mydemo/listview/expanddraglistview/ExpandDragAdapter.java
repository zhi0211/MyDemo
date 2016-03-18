package org.gxz.mydemo.listview.expanddraglistview;

import java.util.List;

import org.gxz.mydemo.R;
import org.gxz.mydemo.listview.draglist.DragListActivity;
import org.gxz.mydemo.listview.itemdrag.HostRecord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExpandDragAdapter extends BaseExpandableListAdapter {

	private Context ctx;
	private List<String> groups;
	private List<List<String>> childs;
	private int type;
	public  ExpandDragAdapter(Context ctx,List<String> groups, List<List<String>> childs,int type) {
		this.ctx=ctx;
		this.groups=groups;
		this.childs=childs;
	}
	@Override
	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return childs.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return childs.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		GroupViewholder holder;
//		if(convertView==null){
			convertView = LayoutInflater.from(ctx).inflate(R.layout.layout_group_item, null);
			holder=new GroupViewholder();
			holder.grouptv=(TextView) convertView.findViewById(R.id.group_tv);
			convertView.setTag(holder);
//		}else{
//			holder=(GroupViewholder) convertView.getTag();
//		}
		holder.grouptv.setText(getGroup(groupPosition).toString());
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		ChildViewholder holder;
		if(convertView==null){
			convertView = LayoutInflater.from(ctx).inflate(R.layout.drag_list_item, null);
			holder = new ChildViewholder();
			holder.name = (TextView) convertView.findViewById(R.id.drag_list_item_text);
			holder.forever = (TextView) convertView.findViewById(R.id.btn_forever);
			holder.temp = (TextView) convertView.findViewById(R.id.btn_temp);
			holder.left = (LinearLayout) convertView.findViewById(R.id.item_left);
			holder.right = (LinearLayout) convertView.findViewById(R.id.item_right);
			holder.img = (ImageView) convertView.findViewById(R.id.drag_item_img);
			holder.state = (ImageView) convertView.findViewById(R.id.drag_item_state);
			holder.clearLock = (TextView) convertView.findViewById(R.id.btn_clear_lock);
			holder.divider = (View) convertView.findViewById(R.id.right_divide);
			convertView.setTag(holder);
		}else{
			holder=(ChildViewholder) convertView.getTag();
		}
		if (type == DragListActivity.MENU_TYPE_NORMAL) {
			holder.temp.setVisibility(View.VISIBLE);
			holder.forever.setVisibility(View.VISIBLE);
			holder.divider.setVisibility(View.VISIBLE);
			holder.clearLock.setVisibility(View.GONE);
		} else if (type == DragListActivity.MENU_TYPE_FORBID) {
			holder.temp.setVisibility(View.GONE);
			holder.forever.setVisibility(View.GONE);
			holder.divider.setVisibility(View.GONE);
			holder.clearLock.setVisibility(View.VISIBLE);
		}
		holder.name.setText(childs.get(groupPosition).get(childPosition));
		if (childPosition % 4 == 0) {
			holder.img.setImageResource(R.drawable.default_pc);
			holder.state.setImageResource(R.drawable.icon_wifi_normal);
		} else if (childPosition % 4 == 1) {
			holder.img.setImageResource(R.drawable.default_pad);
			holder.state.setImageResource(R.drawable.icon_wifi_disabled);
		} else if (childPosition % 4 == 2) {
			holder.img.setImageResource(R.drawable.default_telephone);
			holder.state.setImageResource(R.drawable.icon_wired_disabled);
		} else {
			holder.img.setImageResource(R.drawable.default_else);
			holder.state.setImageResource(R.drawable.icon_wired_normal);
		}
//		holder.forever.setOnClickListener(new itemOnclick(position));
//		holder.temp.setOnClickListener(new itemOnclick(position));
//		holder.clearLock.setOnClickListener(new itemOnclick(position));
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	
	static class GroupViewholder{
		TextView grouptv;
	}
	static class ChildViewholder{
		TextView forever, temp, name, clearLock;
		LinearLayout left, right;
		ImageView img, state;
		View divider;
	}

}
