package org.gxz.mydemo.spinner;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.gxz.mydemo.BaseActivity;
import org.gxz.mydemo.R;
import org.textmining.text.extraction.WordExtractor;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

public class SelectActivity extends BaseActivity implements Callback,
		OnClickListener,TextWatcher {
	// PopupWindow对象
	private PopupWindow selectPopupWindow = null;
	// 自定义Adapter
	private OptionsAdapter optionsAdapter = null;
	// 下拉框选项数据源
	private ArrayList<String> datas = new ArrayList<String>();;
	// 下拉框依附组件
	private LinearLayout parent;
	// 下拉框依附组件宽度，也将作为下拉框的宽度
	private int pwidth;
	// 文本框
	private EditText et;
	// 下拉箭头图片组件
	private ImageView image;
	// 恢复数据源按钮
	private Button button;
	// 展示所有下拉选项的ListView
	private ListView listView = null;
	// 用来处理选中或者删除下拉项消息
	private Handler handler;
	// 是否初始化完成标志
	private boolean flag = false;
	
	
	private EditText mEditText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select);
		Button btn = (Button) findViewById(R.id.btn_doc_txt);
		btn.setOnClickListener(this);
		mEditText = (EditText) findViewById(R.id.edittext2);
		mEditText.addTextChangedListener(this);
		Button btn2 = (Button) findViewById(R.id.editbtn);
		btn2.setOnClickListener(this);
	}

	/**
	 * 没有在onCreate方法中调用initWedget()，而是在onWindowFocusChanged方法中调用，
	 * 是因为initWedget()中需要获取PopupWindow浮动下拉框依附的组件宽度，在onCreate方法中是无法获取到该宽度的
	 */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		while (!flag) {
			initWedget();
			flag = true;
		}

	}

	/**
	 * 初始化界面控件
	 */
	private void initWedget() {
		// 初始化Handler,用来处理消息
		handler = new Handler(SelectActivity.this);

		// 初始化界面组件
		parent = (LinearLayout) findViewById(R.id.parent);
		et = (EditText) findViewById(R.id.edittext);
		image = (ImageView) findViewById(R.id.btn_select);

		// 获取下拉框依附的组件宽度
		int width = parent.getWidth();
		pwidth = width;

		// 设置点击下拉箭头图片事件，点击弹出PopupWindow浮动下拉框
		image.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (flag) {
					// 显示PopupWindow窗口
					popupWindwShowing();
				}
			}
		});

		// 初始化PopupWindow
		initPopuWindow();

		button = (Button) findViewById(R.id.refresh);
		// 设置点击事件，恢复下拉框列表数据，没有什么作用，纯粹是为了方便多看几次效果而设置
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				initDatas();
				optionsAdapter.notifyDataSetChanged();
			}
		});
	}

	/**
	 * 初始化填充Adapter所用List数据
	 */
	private void initDatas() {

		datas.clear();

		datas.add("北京");
		datas.add("上海");
		datas.add("广州");
		datas.add("深圳");
		datas.add("重庆");
		datas.add("青岛");
		datas.add("石家庄");
	}

	/**
	 * 初始化PopupWindow
	 */
	private void initPopuWindow() {

		initDatas();

		// PopupWindow浮动下拉框布局
		View loginwindow = (View) this.getLayoutInflater().inflate(
				R.layout.options, null);
		listView = (ListView) loginwindow.findViewById(R.id.list);

		// 设置自定义Adapter
		optionsAdapter = new OptionsAdapter(this, handler, datas);
		listView.setAdapter(optionsAdapter);

		selectPopupWindow = new PopupWindow(loginwindow, pwidth,
				LayoutParams.WRAP_CONTENT, true);

		selectPopupWindow.setOutsideTouchable(true);

		// 这一句是为了实现弹出PopupWindow后，当点击屏幕其他部分及Back键时PopupWindow会消失，
		// 没有这一句则效果不能出来，但并不会影响背景
		// 本人能力极其有限，不明白其原因，还望高手、知情者指点一下
		selectPopupWindow.setBackgroundDrawable(new BitmapDrawable());
	}

	/**
	 * 显示PopupWindow窗口
	 * 
	 * @param popupwindow
	 */
	public void popupWindwShowing() {
		// 将selectPopupWindow作为parent的下拉框显示，并指定selectPopupWindow在Y方向上向上偏移3pix，
		// 这是为了防止下拉框与文本框之间产生缝隙，影响界面美化
		// （是否会产生缝隙，及产生缝隙的大小，可能会根据机型、Android系统版本不同而异吧，不太清楚）
		selectPopupWindow.showAsDropDown(parent, 0, -3);
	}

	/**
	 * PopupWindow消失
	 */
	public void dismiss() {
		selectPopupWindow.dismiss();
	}

	/**
	 * 处理Hander消息
	 */
	@Override
	public boolean handleMessage(Message message) {
		Bundle data = message.getData();
		switch (message.what) {
		case 1:
			// 选中下拉项，下拉框消失
			int selIndex = data.getInt("selIndex");
			et.setText(datas.get(selIndex));
			dismiss();
			break;
		case 2:
			// 移除下拉项数据
			int delIndex = data.getInt("delIndex");
			datas.remove(delIndex);
			// 刷新下拉列表
			optionsAdapter.notifyDataSetChanged();
			break;
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_doc_txt:
//			Log.d("doc解析", "==========开始解析");
//			String str = readDOC(Environment.getExternalStorageDirectory()
//					.getAbsolutePath() + "/aa.doc");
//			createTXTAndWriteDoc(str, Environment.getExternalStorageDirectory()
//					.getAbsolutePath() + "/aa.txt");
//			Log.d("doc解析", "==========解析结束");
			break;
		case R.id.editbtn:
			mEditText.setText("默认");
			break;
		default:
			break;
		}

	}

	public static String readDOC(String path) {
		// 创建输入流读取doc文件
		String text = null;
		// Environment.getExternalStorageDirectory().getAbsolutePath()+
		// "/aa.doc")
		FileInputStream fis = null;
		ByteArrayOutputStream byteOS = null;
		InputStream byteIS = null;
		try {
			byteOS = new ByteArrayOutputStream();
			fis = new FileInputStream(new File(path));
			byte[] by = new byte[512];
			int t = fis.read(by, 0, by.length);
			while (t > 0) {
				byteOS.write(by, 0, 512); // 这里别写成t，写够512，呵呵，SB的方法对付SB的java API
				t = fis.read(by, 0, by.length);
			}
			byteOS.close();
			System.out.println("=========="+new String(byteOS.toByteArray(), Charset.forName("gbk")));
			byteIS = new ByteArrayInputStream(byteOS.toByteArray());
			int a = byteIS.available();
			WordExtractor extractor = null;
			// 创建WordExtractor
			extractor = new WordExtractor();
			// 对doc文件进行提取
			text = extractor.extractText(byteIS);
			System.out.println("解析得到的东西" + text);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			if(fis!=null){
//				try {
//					fis.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			if(byteIS!=null){
//				try {
//					byteIS.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
		}
		if (text == null) {
			text = "解析文件出现问题";
		}
		return text;
	}

	static void createTXTAndWriteDoc(String text, String path) {
		FileOutputStream fos = null;
		FileOutputStream out = null;
		try {
			// 新建一输出文件流,如果文件存在先删除文件
			File f = new File(path);
			if (f.exists()) {
				f.delete();
			}

			fos = new FileOutputStream(f);
			out = new FileOutputStream(f);
			byte[] b = text.getBytes("GB2312");
			out.write(b);
			out.flush();

			System.out.println("文件生成...");
		} catch (Exception e) {
			System.out.println("出现异常: " + e);
		} finally {
			try {
				if (null != fos) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (null != out) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			fos = null;
			out = null;
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		Log.d(getTag(), "==beforeTextChanged==:"+s.toString()+" &start="+start+"&after="+after+"&count="+count);
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		Log.d(getTag(), "==onTextChanged==:"+s.toString()+" &start="+start+"&before="+before+"&count="+count);		
	}

	@Override
	public void afterTextChanged(Editable s) {
		Log.d(getTag(), "==afterTextChanged==:"+s.toString());			
	}

}
