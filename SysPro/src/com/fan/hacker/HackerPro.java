package com.fan.hacker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
//start HackerService after phone rebooted,�������ֻ���������������HackerService
public class HackerPro extends BroadcastReceiver {
	private Boolean alarmerEnableFlag =true;
	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "hackerPro started", Toast.LENGTH_LONG).show();
		Intent s = new Intent(context, HackerService.class);
		context.startService(s);
		//���ѡ�ж�ʱ������Ϣ����ʹ��ʱ�ӹ���
		
		Log.d("hacker", "HackerService started");
	}

}
