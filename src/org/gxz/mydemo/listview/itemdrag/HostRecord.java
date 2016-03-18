package org.gxz.mydemo.listview.itemdrag;

import android.graphics.Bitmap;

public class HostRecord {
	public String mac;
	public String jid;
	public String type;
	// public String ip;
	// public int level;
	public int state;
	public String name;
	public String nick;
	public int mode;
	public int subscription;
	public Bitmap img;
	public int hostType;

	public HostRecord() {
	}

	public HostRecord(String jid, String mac, String name, String type) {
		this.jid = jid;
		this.mac = mac;
		this.name = name;
		this.type = type;
	}

	public HostRecord(String jid, String mac, String type, String name,
			String nick, int state, int mode, int subscription, int hostType) {
		this.jid = jid;
		this.mac = mac;
		this.type = type;
		this.name = name;
		this.nick = nick;
		this.state = state;
		this.mode = mode;
		this.subscription = subscription;
		this.hostType = hostType;
	}

	public HostRecord(HostRecord record) {
		this.mac = record.mac;
		this.jid = record.jid;
		this.type = record.type;
		this.state = record.state;
		this.name = record.name;
		this.nick = record.nick;
		this.mode = record.mode;
		this.subscription = record.subscription;
		this.img = record.img;
	}
}