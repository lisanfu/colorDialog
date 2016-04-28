package com.example.android_colordialog.custom;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

public class DisplayUtil {

	public DisplayUtil() {
		// TODO Auto-generated constructor stub
	}
	/*���ݷֱ��ʴ�dp�ĵ�λת����px
	 * 
	 * 
	 * */
	public static int dp2px(Context context,float dpValue)
	{
		final float scale=context.getResources().getDisplayMetrics().density;
		return (int)(dpValue*scale+0.5f);
	}
	/*
	 * ���ݷֱ��ʴ�pxת����dp
	 * 
	 * 
	 * */
	/**
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2dp(Context context,float pxValue)
	{
		final float scale=context.getResources().getDisplayMetrics().density;
		return (int)(pxValue/scale+0.5f);
	}
	/*
	 * �����Ļ�ߴ�
	 * 
	 * */
	public static Point getScreenSize(Context context)
	{
		Point point=new Point();
		WindowManager wm=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getSize(point);
		return point;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
