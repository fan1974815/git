package com.fan.hacker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReciever extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent intent2= new Intent(context, SendService.class);
		context.startService(intent2);
	}

}
