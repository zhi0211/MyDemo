package org.gxz.mydemo.main.tabhost;

import org.gxz.mydemo.DemoApplication;
import org.gxz.mydemo.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;


public class MyFramentActivity extends FragmentActivity {

	private TabHost tabHost;
	private int CURRENT_TAB = 0;	//设置常量
	private Home_Fragment homeFragment;
	private Wall_Fragment wallFragment;
	private Message_Fragment messageFragment;
	private Me_Fragment meFragment;
	private android.support.v4.app.FragmentTransaction ft;
	private RelativeLayout tabIndicator1,tabIndicator2,tabIndicator4,tabIndicator5;

	@Override
	public void onDestroy() {
		super.onDestroy();
		((DemoApplication)getApplication()).removeActivity(this);
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DemoApplication)getApplication()).addActivity(this);
        setContentView(R.layout.activity_my_frament);
        findTabView();
        tabHost.setup();
        
        /** 监听*/
        TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener(){
			@Override
			public void onTabChanged(String tabId) {
				
				/**碎片管理*/
				android.support.v4.app.FragmentManager fm =getSupportFragmentManager();
				homeFragment = (Home_Fragment) fm.findFragmentByTag("home");
				wallFragment = (Wall_Fragment) fm.findFragmentByTag("wall");
				messageFragment = (Message_Fragment) fm.findFragmentByTag("message");
				meFragment = (Me_Fragment) fm.findFragmentByTag("me");
				ft = fm.beginTransaction();
				
				/** 如果存在Detaches掉 */
				if(homeFragment!=null)
					ft.detach(homeFragment);
				
				/** 如果存在Detaches掉 */
				if(wallFragment!=null)
					ft.detach(wallFragment);
				
				/** 如果存在Detaches掉 */
				if(messageFragment!=null)
					ft.detach(messageFragment);
				
				/** 如果存在Detaches掉 */
				if(meFragment!=null)
					ft.detach(meFragment);
				
				/** 如果当前选项卡是home */
				if(tabId.equalsIgnoreCase("home")){
					isTabHome();
					CURRENT_TAB = 1;
					
				/** 如果当前选项卡是wall */
				}else if(tabId.equalsIgnoreCase("wall")){	
					isTabWall();
					CURRENT_TAB = 2;
					
				/** 如果当前选项卡是message */
				}else if(tabId.equalsIgnoreCase("message")){	
					isTabMessage();
					CURRENT_TAB = 3;
					
				/** 如果当前选项卡是me */
				}else if(tabId.equalsIgnoreCase("me")){	
					isTabMe();
					CURRENT_TAB = 4;
				}else{
					switch (CURRENT_TAB) {
					case 1:
						isTabHome();
						break;
					case 2:
						isTabWall();
						break;
					case 3:
						isTabMessage();
						break;
					case 4:
						isTabMe();
						break;
					default:
						isTabHome();
						break;
					}		
					
				}
					ft.commit();	
			}
        	
        };
        //设置初始选项卡  
        tabHost.setCurrentTab(0);
        tabHost.setOnTabChangedListener(tabChangeListener);
        initTab();
         /**  设置初始化界面  */
        tabHost.setCurrentTab(0);

    }
    
    //判断当前
    public void isTabHome(){
    	
    	if(homeFragment==null){		
    		homeFragment=new Home_Fragment();
			ft.add(R.id.realtabcontent,homeFragment, "home");						
		}else{
			ft.attach(homeFragment);						
		}
    }
    
    public void isTabWall(){
    	
    	if(wallFragment==null){
    		wallFragment=new Wall_Fragment();
			ft.add(R.id.realtabcontent,wallFragment, "wall");						
		}else{
			ft.attach(wallFragment);						
		}
    }
    
    public void isTabMessage(){
    	
    	if(messageFragment==null){
    		messageFragment=new Message_Fragment();
			ft.add(R.id.realtabcontent,messageFragment, "message");						
		}else{
			ft.attach(messageFragment);						
		}
    }
    
    public void isTabMe(){
    	
    	if(meFragment==null){
    		meFragment=new Me_Fragment();
			ft.add(R.id.realtabcontent,meFragment, "me");						
		}else{
			ft.attach(meFragment);	
		}
    }
    /**
     * 找到Tabhost布局
     */
    public void findTabView(){
    	
    	 tabHost = (TabHost) findViewById(android.R.id.tabhost);
    	 TabWidget tw = (TabWidget) findViewById(android.R.id.tabs);
         
         tabIndicator1 = (RelativeLayout) LayoutInflater.from(this)
         		.inflate(R.layout.tab_indicator, tw, false);
         TextView tvTab1 = (TextView)tabIndicator1.findViewById(R.id.title);
         ImageView ivTab1 = (ImageView)tabIndicator1.findViewById(R.id.icon);
         ivTab1.setBackgroundResource(R.drawable.selector_mood_home);
         tvTab1.setText("首页");
         
         tabIndicator2 = (RelativeLayout) LayoutInflater.from(this)
         		.inflate(R.layout.tab_indicator, tw, false);
         TextView tvTab2 = (TextView)tabIndicator2.findViewById(R.id.title);
         ImageView ivTab2 = (ImageView)tabIndicator2.findViewById(R.id.icon);
         ivTab2.setBackgroundResource(R.drawable.selector_mood_wall);
         tvTab2.setText("心情墙");
         
//         tabIndicator3 = (RelativeLayout) LayoutInflater.from(this)
//         		.inflate(R.layout.tab_indicator_camera, tw, false);
//         TextView tvTab3 = (TextView)tabIndicator3.getChildAt(1);
//         ImageView ivTab3 = (ImageView)tabIndicator3.getChildAt(0);
//         ivTab3.setBackgroundResource(R.drawable.selector_mood_photograph);
//         tvTab3.setText(R.string.buttom_camera);
          
         tabIndicator4 = (RelativeLayout) LayoutInflater.from(this)
         		.inflate(R.layout.tab_indicator, tw, false);
         TextView tvTab4 = (TextView)tabIndicator4.findViewById(R.id.title);
         ImageView ivTab4 = (ImageView)tabIndicator4.findViewById(R.id.icon);
         ivTab4.setBackgroundResource(R.drawable.selector_mood_message);
         tvTab4.setText("信息");
         
         tabIndicator5 = (RelativeLayout) LayoutInflater.from(this)
         		.inflate(R.layout.tab_indicator, tw, false);
         TextView tvTab5 = (TextView)tabIndicator5.findViewById(R.id.title);
         ImageView ivTab5 = (ImageView)tabIndicator5.findViewById(R.id.icon);
         ivTab5.setBackgroundResource(R.drawable.selector_mood_my_wall);
         tvTab5.setText("我");
    }
    
    /** 
     * 初始化选项卡
     * 
     * */
    public void initTab(){
    	
    	DummyTabContent d1=new DummyTabContent(getBaseContext());
    	DummyTabContent d2=new DummyTabContent(getBaseContext());
    	DummyTabContent d3=new DummyTabContent(getBaseContext());
    	DummyTabContent d4=new DummyTabContent(getBaseContext());
        TabHost.TabSpec tSpecHome = tabHost.newTabSpec("home");
        tSpecHome.setIndicator(tabIndicator1);
        tSpecHome.setContent(d1);
        tabHost.addTab(tSpecHome);
        
        TabHost.TabSpec tSpecWall = tabHost.newTabSpec("wall");
        tSpecWall.setIndicator(tabIndicator2);        
        tSpecWall.setContent(d2);
        tabHost.addTab(tSpecWall);
        
//        TabHost.TabSpec tSpecCamera = tabHost.newTabSpec("camera");
//        tSpecCamera.setIndicator(tabIndicator3);        
//        tSpecCamera.setContent(new DummyTabContent(getBaseContext()));
//        tabHost.addTab(tSpecCamera);
//        
//        //拍照按钮监听事件，弹出dialog
//        tabIndicator3.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				
//				Dialog choose = new Dialog(MyFramentActivity.this,R.style.draw_dialog);
//		    	choose.setContentView(R.layout.camera_dialog);
//		    	// 设置背景模糊参数
//				WindowManager.LayoutParams winlp = choose.getWindow()
//						.getAttributes();
//				winlp.alpha = 0.9f; // 0.0-1.0
//				choose.getWindow().setAttributes(winlp);
//				choose.show();// 显示弹出框
//			}
//		});
        TabHost.TabSpec tSpecMessage = tabHost.newTabSpec("message");
        tSpecMessage.setIndicator(tabIndicator4);      
        tSpecMessage.setContent(d3);
        tabHost.addTab(tSpecMessage);
        
        TabHost.TabSpec tSpecMe = tabHost.newTabSpec("me");
        tSpecMe.setIndicator(tabIndicator5);        
        tSpecMe.setContent(d4);
        tabHost.addTab(tSpecMe);
        
    }
    
    @Override
    public void onBackPressed() {
    	if(messageFragment!=null&&messageFragment.getisDetail()){
    		messageFragment.setListView();
    		return;
    	}
    	super.onBackPressed();
    }
    
}
