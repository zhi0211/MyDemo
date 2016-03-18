package org.gxz.mydemo.test;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gxz on 2015/10/15.
 */
public class TestClass implements Parcelable, Serializable {

	public TestClass() {

	}

	public String a;
	public List<String> list = new ArrayList<String>();

	protected TestClass(Parcel in) {
		a = in.readString();
		list = in.createStringArrayList();
	}

	public static final Creator<TestClass> CREATOR = new Creator<TestClass>() {
		@Override
		public TestClass createFromParcel(Parcel in) {
			return new TestClass(in);
		}

		@Override
		public TestClass[] newArray(int size) {
			return new TestClass[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(a);
		dest.writeStringList(list);
	}

	@Override
	public String toString() {
		return a + list.toString();
	}
}
