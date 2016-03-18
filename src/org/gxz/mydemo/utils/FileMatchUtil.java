package org.gxz.mydemo.utils;


public class FileMatchUtil {

	private static String[] MUSIC_FILES = new String[] { "acc", "ogg", "mp3",
			"wma", "ape", "flac", "wav", "m4a", "m4r", "mmf", "amr", "mp2" };
	private static String[] DOC_FILES = new String[] { "docx", "doc", };
	private static String[] IMAGE_FILES = new String[] { "bmp", "gif", "jpeg",
			"png", "jpg", "ico", "tif", "pcx", "tga" };
	private static String[] PPT_FILES = new String[] { "ppt", "pps", "pptx",
			"ppsx", "pptm", "potx", "potm", "ppam" };
	private static String[] VIDEO_FILES = new String[] { "3gp", "avi", "mp4",
			"mkv", "mpg", "vob", "flv", "swf", "mov" };
	private static String[] XLS_FILES = new String[] { "xls", "xlsx" };
	private static String[] ZIP_FILES = new String[] { "zip", "rar" };
	private static String[] TEXT_FILES = new String[] { "xml", "txt" };

//	public static int getFileDrawbleId(Context ctx, String fileName) {
//		int index = fileName.lastIndexOf(".");
//		if (index < 0) {
//			return -1;
//		}
//		String typeName = fileName.substring(index + 1, fileName.length())
//				.toLowerCase();
//		if (typeName.equals(""))
//			return -1;
//		int resId = -1;
//		if (Arrays.asList(MUSIC_FILES).contains(typeName)) {
//			resId = R.drawable.file_icon_mp3;
//		} else if (Arrays.asList(IMAGE_FILES).contains(typeName)) {
//			resId = R.drawable.file_icon_jpg;
//		} else if (Arrays.asList(DOC_FILES).contains(typeName)) {
//			resId = R.drawable.file_icon_doc;
//		} else if (Arrays.asList(TEXT_FILES).contains(typeName)) {
//			resId = R.drawable.file_icon_txt;
//		} else if ("pdf".equals(typeName)) {
//			resId = R.drawable.file_icon_pdf;
//		} else if ("apk".equals(typeName)) {
//			resId = R.drawable.file_icon_apk;
//		} else if ("html".equals(typeName)) {
//			resId = R.drawable.file_icon_html;
//		} else if (Arrays.asList(PPT_FILES).contains(typeName)) {
//			resId = R.drawable.file_icon_ppt;
//		} else if (Arrays.asList(VIDEO_FILES).contains(typeName)) {
//			resId = R.drawable.file_icon_video;
//		} else if (Arrays.asList(XLS_FILES).contains(typeName)) {
//			resId = R.drawable.file_icon_xls;
//		} else if (Arrays.asList(ZIP_FILES).contains(typeName)) {
//			resId = R.drawable.file_icon_zip;
//		} else {
//			resId = R.drawable.file_icon_unknow;
//		}
//
//		return resId;
//
//	}
}
