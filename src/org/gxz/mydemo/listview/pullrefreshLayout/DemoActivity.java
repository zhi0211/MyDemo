package org.gxz.mydemo.listview.pullrefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;

public class DemoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pullrefresh_demo);

    }

    public void onListViewClick(View view) {
        startActivity(new Intent(this, ListViewActivity.class));
    }

    public void onRecyclerViewClick(View view) {
        startActivity(new Intent(this, RecyclerViewActivity.class));
    }

    public void onScrollViewClick(View view) {
        startActivity(new Intent(this, ScrollViewActivity.class));
    }

}
