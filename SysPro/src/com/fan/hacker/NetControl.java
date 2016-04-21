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

//��������ʵ�ֶ�����APP�Ŀ��ƣ�����Api����ʵ��
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
		checkPreferences();//�����ѡ���Ƿ�׼������,�����ڴ˺�����Ĭ�����óɺ�ģʽ
		getApplicationsAndResort();
		final DroidApp[] apps = Api.getApps(ctx);
		
		disableAppNet(apps,-10,unChecked);//�õ�apps�󣬸���UID��enableFlagȻ�����µ��ô˺������Ϳ������ã�Checked����ȡ������(unChecked)
		
	}
	public void disableAppNet(DroidApp[] apps,int appUid,Boolean enableFlag) {
		for (DroidApp app:apps) {
			if (app.uid ==appUid) {
				app.selected_3g =enableFlag;
				app.selected_wifi =enableFlag;
			}
		}
		//Ӧ�ù���
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
			editor.putString(Api.PREF_MODE, Api.MODE_BLACKLIST);//Ĭ�����óɺ�ģʽ
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
	//ʹ��droidwall
	public void enableDroidwall() {
		
		final SharedPreferences prefs = ctx.getSharedPreferences(Api.PREFS_NAME, 0);
		final boolean enabled = true;//!prefs.getBoolean(Api.PREF_LOGENABLED, false);
		final Editor editor = prefs.edit();
		Api.setEnabled(ctx, enabled);//ʹ�ܷ���ǽ
		editor.putBoolean(Api.PREF_LOGENABLED, enabled);//ʹ�ܼ�¼
		editor.commit();
	}
	//������о�������Ȩ�޵�Ӧ�ã�����ѡ�е�Ӧ�÷���ǰ��
	public void getApplicationsAndResort() {
		final Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				//���Apps�󣬽�������
//				final DroidApp[] apps = Api.getApps(ctx);
				// Sort applications - selected first, then alphabetically  ��Ӧ�ý��з��࣬����ʾѡ�еģ�Ȼ����ĸ˳����ʾ
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
				Api.getApps(ctx);//��һ��ִ�д˺���ʱ���ǳ���
				handler.sendEmptyMessage(0);//�õ�Ӧ����Ϣ�󣬷�����Ϣ��Handler��ִ��showApplications();
			}
		}.start();
	}
}
