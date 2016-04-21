package com.fan.hacker;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import android.os.IBinder;

import android.util.Log;

import android.widget.Toast;


public class HackerService extends Service {
	public static final String ACTION = "com.fan.hacker.HackerService";
	
	private ConnectivityManager connectivityManager;
	private NetworkInfo info;
	 //
	 public static Boolean flag =true;

	@Override
	public IBinder onBind(Intent intent) {
		
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		//start the SendService
		Intent intent2 = new Intent(this, SendService.class);
		startService(intent2);
		Log.d("hacker", "onStartCommand");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onCreate() {
		//net change listerning
		IntentFilter mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mNetReceiver, mFilter);

        super.onCreate();
	}
	

	//����������ʱ������֪ͨ
	private BroadcastReceiver mNetReceiver = new BroadcastReceiver() {
		
        @Override
        public void onReceive(Context context, Intent intent) {
        	Log.d("hacker", "��������");
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
                info = connectivityManager.getActiveNetworkInfo();  
                if(info != null && info.isAvailable()) {
                    String name = info.getTypeName();
                    Toast.makeText(context, "�����Ѿ�����", Toast.LENGTH_LONG).show();
                    Log.d("mark", "��ǰ�������ƣ�" + name);
                    
                } else {
                    Log.d("mark", "û�п�������");
                }
            }
        }
    };
    
}