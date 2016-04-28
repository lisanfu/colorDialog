package com.example.android_colordialog.custom;

import com.example.android_colordialog.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

public class ColoDialog extends Dialog implements View.OnClickListener{
	private ImageView mContentIv;
	private Bitmap mContentBitmap;
	private View mBtnGroupView,mDividerView,mBkgView,mDialogView;
	private TextView mTitleTv,mContentTv,mPositiveBtn,mNegativeBtn;
	private Drawable mDrawable;
	private AnimationSet mAnimIn, mAnimOut;
	private int mResId,mBackgroundColor,mTitleTextViewColor,mContentTextColor;
	private OnPositiveListener mPositiveListener;
	private OnNegativeListener mNegativeListener;
	private CharSequence mTitletext,mContentText,mPositiveText,mNegativeText;
	private boolean mIsShowAnim;
	
	public ColoDialog(Context context) {
		this(context,0);
		// TODO Auto-generated constructor stub
	}

	public ColoDialog(Context context, int theme) {
		super(context,R.style.color_dialog);
		init();
		// TODO Auto-generated constructor stub
	}
	private void callDismiss()
	{
		super.dismiss();
	}

	private void init() {
		// TODO Auto-generated method stub
		mAnimIn=AnimationLoader.getAnimation(getContext());
		mAnimOut=AnimationLoader.getOutAnimation(getContext());
		initAnimListener();
	}
	
	@Override
	public void setTitle(CharSequence title) {
		// TODO Auto-generated method stub
		mTitletext=title;
	}

	@Override
	public void setTitle(int titleId) {
		// TODO Auto-generated method stub
		setTitle(getContext().getText(titleId));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		View contentView =View.inflate(getContext(), R.layout.layout_colordialog, null);
		setContentView(contentView);
		
		mDialogView=getWindow().getDecorView().findViewById(android.R.id.content);
		mBkgView=contentView.findViewById(R.id.llBkg);
		mTitleTv=(TextView) contentView.findViewById(R.id.tvTitle);
		mContentTv=(TextView) contentView.findViewById(R.id.tvContent);
		mContentIv=(ImageView) contentView.findViewById(R.id.ivContent);
		
		mPositiveBtn =(TextView) contentView.findViewById(R.id.btnPositive);
		mNegativeBtn=(TextView) contentView.findViewById(R.id.btnNegative);
		
		mDialogView=contentView.findViewById(R.id.divider);
		mBtnGroupView=contentView.findViewById(R.id.llBtnGroup);
		mPositiveBtn.setOnClickListener(this);
		mNegativeBtn.setOnClickListener(this);
		
		mTitleTv.setText(mTitletext);
		mContentTv.setText(mContentText);
		mPositiveBtn.setText(mPositiveText);
		mNegativeBtn.setText(mNegativeText);
		
		
		if(null==mPositiveListener&&null==mNegativeListener)
		{
			mBtnGroupView.setVisibility(View.GONE);
		}
		else if(null==mPositiveListener&&null==mNegativeListener)
		{
			mPositiveBtn.setVisibility(View.GONE);
			mDividerView.setVisibility(View.GONE);
			mNegativeBtn.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.sel_def_gray));
			
		}
		else if(null!=mPositiveListener&&null==mNegativeListener)
		{
			mNegativeBtn.setVisibility(View.GONE);
			mDividerView.setVisibility(View.GONE);
			mPositiveBtn.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.sel_def_gray));
			
		}
		if(null!=mDrawable)
		{
			mContentIv.setBackgroundDrawable(mDrawable);
		}
		if(0!=mResId)
		{
			mContentIv.setBackgroundResource(mResId);
		}
		setTextColor();
		setBackgroundColor();
		setContentMode();
	}

	private void setContentMode() {
		// TODO Auto-generated method stub
		boolean isImageMode=(null!=mDrawable|null!=mContentBitmap|0!=mResId);
		boolean isTextMode=(!TextUtils.isEmpty(mContentText));
		
		if(isImageMode&&isTextMode)
		{
			FrameLayout.LayoutParams params= (LayoutParams) mContentTv.getLayoutParams();
			params.gravity=Gravity.BOTTOM;
			mContentTv.setLayoutParams(params);
			mContentTv.setBackgroundColor(Color.BLACK);
			mContentTv.getBackground().setAlpha(0x28);
			mContentTv.setVisibility(View.VISIBLE);
			mContentIv.setVisibility(View.VISIBLE);
			return;
		}
		if(isTextMode)
		{
			FrameLayout.LayoutParams params=(LayoutParams) mContentTv.getLayoutParams();
			params.gravity=Gravity.NO_GRAVITY;
			mContentTv.setLayoutParams(params);
			mContentTv.setVisibility(View.GONE);
			mContentTv.setVisibility(View.VISIBLE);
			return ;
			
		}
		if(isImageMode)
		{
			mContentTv.setVisibility(View.GONE);
			mContentTv.setVisibility(View.VISIBLE);
			return;
		}
		
	}

	private void setBackgroundColor() {
		// TODO Auto-generated method stub
		if(0==mBackgroundColor)
		{
			return ;
		}
		int radius=DisplayUtil.dp2px(getContext(), 6);
		float[] outerRadii=new float[]{radius,radius,radius,radius,0,0,0,0};
		RoundRectShape roundRectshape=new RoundRectShape(outerRadii,null,null);
		ShapeDrawable shapeDrawable=new ShapeDrawable(roundRectshape);
		shapeDrawable.getPaint().setColor(mBackgroundColor);
		shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
		mBkgView.setBackgroundDrawable(shapeDrawable);
		
	}

	private void setTextColor() {
		// TODO Auto-generated method stub
		if(0!=mTitleTextViewColor)
		{
			mTitleTv.setTextColor(mTitleTextViewColor);
		}
		if(0!=mContentTextColor)
		{
			mContentTv.setTextColor(mContentTextColor);
		}
	}

	private void initAnimListener() {
		// TODO Auto-generated method stub
		mAnimOut.setAnimationListener(new Animation.AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				mDialogView.post(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						callDismiss();
					}});
			}
		});
	}

	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		int id=view.getId();
		if(R.id.btnPositive==id)
		{
			mPositiveListener.onClick(this);
		}
		else if(R.id.btnNegative==id)
		{
			mNegativeListener.onClick(this);
		}
		else 
		{
			
		}
		
	}
	public ColoDialog setAnimationEnable(boolean enable)
	{
		mIsShowAnim=enable;
		return this;
	}
	public ColoDialog setAnimationIn(AnimationSet animIn)
	{
		mAnimIn=animIn;
		return this;
	}
	public ColoDialog setAnimationOut(AnimationSet animOut)
	{
		mAnimOut=animOut;
		initAnimListener();
		return this;
	}
	public ColoDialog setColor(int color)
	{
		mBackgroundColor=color;
		return this;
	}
	public ColoDialog setColor(String color)
	{
		try {
			setColor(Color.parseColor(color));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return this;
	}
	public ColoDialog setTitleTextColor(int color)
	{
		mTitleTextViewColor = color;
	        return this;
	}
	public ColoDialog setContentTextColor(int color)
	{
		mContentTextColor=color;
		return this;
	}
	public ColoDialog setContentTextColor(String color)
	{
		try {
			setContentTextColor(Color.parseColor(color));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return this;
	}
	public ColoDialog setPosisiveListener(CharSequence text,OnPositiveListener l)
	{
		mPositiveText=text;
		mPositiveListener=l;
		return this;
	}
	public ColoDialog setPositiveListener(int textId,OnPositiveListener l)
	{
		return setPosisiveListener(getContext().getText(textId), l);
	}
	public ColoDialog setNegativeListener(CharSequence text,OnNegativeListener l)
	{
		mNegativeText=text;
		mNegativeListener=l;
		return this;
	}
	public ColoDialog setNegativeListener(int textId,OnNegativeListener l)
	{
	
		return setNegativeListener(getContext().getText(textId), l);
	}
	public ColoDialog setContentText(CharSequence text)
	{
		mContentText=text;
		return this;
	}
	public ColoDialog setContenttext(int textId)
	{
		return setContentText(getContext().getText(textId));
	}
	public ColoDialog setContentImage(Drawable drawable)
	{
		mDrawable=drawable;
		return this;
		
		
	}
	public ColoDialog setContentImage(Bitmap bitmap)
	{
		mContentBitmap=bitmap;
		return this;
	}
	public CharSequence getContentText()
	{
		return mContentText;
	}
   

   public CharSequence getPositiveText() {
       return mPositiveText;
   }

   public CharSequence getNegativeText() {
       return mNegativeText;
   }

   public interface OnPositiveListener {
      
	void onClick(ColoDialog coloDialog);
   }

   public interface OnNegativeListener {
      

	void onClick(ColoDialog coloDialog);
   }
}
