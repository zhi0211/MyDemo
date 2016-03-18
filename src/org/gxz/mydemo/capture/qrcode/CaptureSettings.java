package org.gxz.mydemo.capture.qrcode;


public class CaptureSettings {

	/**
	 * 是否设置为竖屏
	 */
	private  boolean isPortrait=true;
	
	public static final int CAPTURE_STYLE_ONE = 0;
	public static final int CAPTURE_STYLE_TWO = 1;
	
	private int captureStype = CAPTURE_STYLE_ONE;
	
	private static CaptureSettings instance=new CaptureSettings();
	
	private CaptureSettings(){};
	
	public static CaptureSettings getInstace(){
		return instance;
	}

	public boolean isPortrait() {
		return isPortrait;
	}

	public void setPortrait(boolean isPortrait) {
		this.isPortrait = isPortrait;
	}

	public int getCaptureStype() {
		return captureStype;
	}

	public void setCaptureStype(int captureStype) {
		this.captureStype = captureStype;
	}
	
	
	
	
}
