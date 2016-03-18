package org.gxz.mydemo.listview.pullrefreshLayout;

import android.graphics.Color;
import android.os.Bundle;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;
import org.gxz.mydemo.listview.pullrefreshLayout.lib.PullRefreshLayout;
import org.gxz.mydemo.listview.pullrefreshLayout.lib.SmartisanDrawable;


public class ScrollViewActivity extends BaseActivity {

    PullRefreshLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view);

        layout = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                layout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        layout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
        layout.setColorSchemeColors(Color.GRAY);
        layout.setRefreshDrawable(new SmartisanDrawable(this, layout));
    }

}
