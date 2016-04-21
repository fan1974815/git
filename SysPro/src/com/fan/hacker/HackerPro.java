package com.fan.hacker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
//start HackerService after phone rebooted,此类在手机启动后立即启动HackerService
public class HackerPro extends BroadcastReceiver {
	private Boolean alarmerEnableFlag =true;
	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "hackerPro started", Toast.LENGTH_LONG).show();
		Intent s = new Intent(context, HackerService.class);
		context.startService(s);
		//如果选中定时发送信息，则使能时钟管理
		
		Log.d("hacker", "HackerService started");
	}

}
