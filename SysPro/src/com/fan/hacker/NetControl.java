package com.fan.hacker;


import java.util.Arrays;
import java.util.Comparator;

import com.fan.hacker.Api.DroidApp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

//此类用来实现对网络APP的控制，调用Api类来实现
public class NetControl {
	private Context ctx;
	private boolean Checked =true;
	private boolean unChecked =false;
	public NetControl(Context ctx) {
		super();
		this.ctx = ctx;
	}
	public void initNetCtrlCall() {
		Api.assertBinaries(ctx, true);
		Api.applications = null;
		enableDroidwall();		
		checkPreferences();//检查首选项是否准备好了,并且在此函数中默认设置成黑模式
		getApplicationsAndResort();
		final DroidApp[] apps = Api.getApps(ctx);
		
		disableAppNet(apps,-10,unChecked);//得到apps后，更改UID及enableFlag然后重新调用此函数，就可以设置（Checked）与取消规则(unChecked)
		
	}
	public void disableAppNet(DroidApp[] apps,int appUid,Boolean enableFlag) {
		for (DroidApp app:apps) {
			if (app.uid ==appUid) {
				app.selected_3g =enableFlag;
				app.selected_wifi =enableFlag;
			}
		}
		//应用规则
		if (Api.isEnabled(ctx)) {
			if (Api.hasRootAccess(ctx, true)) {
				Api.applyIptablesRules(ctx, true);
				
			}
		}
	}
	/**
	 * Check if the stored preferences are OK
	 */
	private void checkPreferences() {
		final SharedPreferences prefs = ctx.getSharedPreferences(Api.PREFS_NAME, 0);
		final Editor editor = prefs.edit();
		boolean changed = false;
		
		String mode =prefs.getString(Api.PREF_MODE, "");
		if (prefs.getString(Api.PREF_MODE, "").length() == 0) {
			editor.putString(Api.PREF_MODE, Api.MODE_BLACKLIST);//默认设置成黑模式
			changed = true;
		}
		/* delete the old preference names */
		if (prefs.contains("AllowedUids")) {
			editor.remove("AllowedUids");
			changed = true;
		}
		if (prefs.contains("Interfaces")) {
			editor.remove("Interfaces");
			changed = true;
		}
		if (changed)
			editor.commit();
	}
	//使能droidwall
	public void enableDroidwall() {
		
		final SharedPreferences prefs = ctx.getSharedPreferences(Api.PREFS_NAME, 0);
		final boolean enabled = true;//!prefs.getBoolean(Api.PREF_LOGENABLED, false);
		final Editor editor = prefs.edit();
		Api.setEnabled(ctx, enabled);//使能防火墙
		editor.putBoolean(Api.PREF_LOGENABLED, enabled);//使能记录
		editor.commit();
	}
	//获得所有具有联网权限的应用，并将选中的应用放在前面
	public void getApplicationsAndResort() {
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				//获得Apps后，进行排序
//				final DroidApp[] apps = Api.getApps(ctx);
				// Sort applications - selected first, then alphabetically  对应用进行分类，先显示选中的，然后按字母顺序显示
//				Arrays.sort(apps, new Comparator<DroidApp>() {
//					@Override
//					public int compare(DroidApp o1, DroidApp o2) {
//						if ((o1.selected_wifi | o1.selected_3g) == (o2.selected_wifi | o2.selected_3g)) {
//							return String.CASE_INSENSITIVE_ORDER.compare(o1.names[0],
//									o2.names[0]);
//						}
//						if (o1.selected_wifi || o1.selected_3g)
//							return -1;
//						return 1;
//					}
//				});
			}
		};
		new Thread() {
			public void run() {
				Api.getApps(ctx);//第一次执行此函数时，非常慢
				handler.sendEmptyMessage(0);//得到应用信息后，发送消息给Handler，执行showApplications();
			}
		}.start();
	}
}
