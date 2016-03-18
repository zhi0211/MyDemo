package org.gxz.mydemo.contact;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ListView;

public class ContactListActivity extends BaseActivity implements OnItemClickListener {

	private ListView contactLv;
	private Context ctx;
	private CursorAdapter ca;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_contact_main);
		ctx=getApplicationContext();
		contactLv=(ListView)findViewById(R.id.list_result1);
		String[] projection = new String[] { Contacts._ID, // 0
				Contacts.DISPLAY_NAME_PRIMARY, // 1
				Contacts.SORT_KEY_PRIMARY, // 2
				Contacts.TIMES_CONTACTED, // 3
				Contacts.CONTACT_PRESENCE, // 4
				Contacts.PHOTO_ID, // 5
				Contacts.LOOKUP_KEY, // 6
				Contacts.HAS_PHONE_NUMBER, // 7
				Contacts.IN_VISIBLE_GROUP, // 8
		};
		Cursor cur=ctx.getContentResolver().query(Contacts.CONTENT_URI,projection, null, null, Contacts.SORT_KEY_PRIMARY );
		ca=new ContactCurAdapter(ContactListActivity.this,cur);
		contactLv.setAdapter(ca);
		contactLv.setOnItemClickListener(this);
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

		Uri uri = getContactUri(position);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}
	
	
	/**
	 * @return 根据参数得到这个position的联系人的lookupUri
	 */
	private Uri getContactUri(int position) {
		if (position == ListView.INVALID_POSITION) {
			throw new IllegalArgumentException("Position not in list bounds");
		}

		final Cursor cursor = (Cursor)ca.getItem(position);
		if (cursor == null) {
			return null;
		}
		final long contactId = cursor.getLong(cursor
				.getColumnIndex(Contacts._ID));
		final String lookupKey = cursor.getString(cursor
				.getColumnIndex(Contacts.LOOKUP_KEY));
		return Contacts.getLookupUri(contactId, lookupKey);
	}


}
