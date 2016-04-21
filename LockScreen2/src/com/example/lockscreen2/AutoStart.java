package com.example.lockscreen2;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.WindowManager;


public class AutoStart extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		((KeyguardManager)context.getSystemService(Activity.KEYGUARD_SERVICE)).newKeyguardLock("IN").disableKeyguard();
		if(intent.getAction().equals("android.intent.action.SCREEN_OFF")) {
            Intent localIntent = new Intent(context, MainActivity.class);
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            localIntent.addFlags(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);
            context.startActivity(localIntent);
            
        }

	}

}
