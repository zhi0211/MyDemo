package org.gxz.mydemo.contact;

import org.gxz.mydemo.R;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Contacts.Photo;
import android.provider.ContactsContract.Data;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

public class ContactCurAdapter extends CursorAdapter {

	private LayoutInflater mListContainer;
	private ContactItem mContactItem = null;

	@SuppressWarnings("deprecation")
	public ContactCurAdapter(Context context, Cursor c) {
		super(context, c);
		mListContainer = LayoutInflater.from(context);
	}

	@Override
	public void bindView(View v, Context ctx, Cursor c) {
		if (null != v) {
			mContactItem=(ContactItem) v.getTag();
			int photoId = c.getColumnIndex(Contacts.PHOTO_ID);
			if (!c.isNull(photoId)) {
				Cursor photoCur = ctx.getContentResolver().query(Data.CONTENT_URI, new String[] { Photo.PHOTO }, Data._ID + "=?",
						new String[] { String.valueOf(c.getLong(photoId)) }, null);
				if (photoCur.moveToFirst()) {
					byte[] buff = photoCur.getBlob(0);
					Bitmap bmp = BitmapFactory.decodeByteArray(buff, 0, buff.length);
					mContactItem.photo.setImageBitmap(bmp);
				}
				photoCur.close();
			} else {
				mContactItem.photo.setImageResource(R.drawable.contactdefaultfigure);
			}
			long cid = c.getLong(c.getColumnIndex(Contacts._ID));
			String lookupKey=c.getString(c.getColumnIndex(Contacts.LOOKUP_KEY));
			mContactItem.photo.assignContactUri(Contacts.getLookupUri(cid, lookupKey));
			int nameIndex = c.getColumnIndex(Contacts.DISPLAY_NAME_PRIMARY);
			if (!c.isNull(nameIndex)) {
				mContactItem.name.setText(c.getString(nameIndex));
			}
		}

	}

	@Override
	public View newView(Context ctx, Cursor c, ViewGroup parent) {
		View convertView = null;
		mContactItem = new ContactItem();
		convertView = mListContainer.inflate(R.layout.singlecontactlayout, null);
		mContactItem.leter = (TextView) convertView.findViewById(R.id.leter);
		mContactItem.callImg = (ImageView) convertView.findViewById(R.id.call);
		mContactItem.smsImg = (ImageView) convertView.findViewById(R.id.sendsms);
		mContactItem.name = (TextView) convertView.findViewById(R.id.contactUserName);
		mContactItem.photo = (QuickContactBadge) convertView.findViewById(R.id.contactFigure);
		convertView.setTag(mContactItem);
		return convertView;
	}
	
	

}
