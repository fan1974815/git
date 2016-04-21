package com.fan.hacker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.util.Log;

public class CallRecord {
	private static final String TAG ="CallRecord";
	private Context ctx;
	
	public CallRecord(Context ctx) {
		super();
		this.ctx = ctx;
	}
	public List callRecQuery() {
		ContentResolver contentResolver = ctx.getContentResolver();
		Cursor cursor = null;
		List<RecordEntity> mRecordList = new ArrayList<RecordEntity>();
		try {
			cursor = contentResolver.query(
					CallLog.Calls.CONTENT_URI, null, null, null,
					CallLog.Calls.DATE + " desc");
			if (cursor == null)
				return null;

			while (cursor.moveToNext())
			{
				RecordEntity record = new RecordEntity();
				record.name = cursor.getString(cursor
						.getColumnIndex(CallLog.Calls.CACHED_NAME));
				record.number = cursor.getString(cursor
						.getColumnIndex(CallLog.Calls.NUMBER));
				record.type = cursor.getInt(cursor
						.getColumnIndex(CallLog.Calls.TYPE));
				SimpleDateFormat sdFormat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				Date date= new Date(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.DATE))));
				record.sDate = sdFormat.format(date);
				record.duration = cursor.getString(cursor.getColumnIndexOrThrow(CallLog.Calls.DURATION));
				record._new = cursor.getInt(cursor
						.getColumnIndex(CallLog.Calls.NEW));
				Log.e(TAG, record.toString());
				//				int photoIdIndex = cursor.getColumnIndex(CACHED_PHOTO_ID);
				//				if (photoIdIndex >= 0) {
				//					record.cachePhotoId = cursor.getLong(photoIdIndex);
				//				}

				mRecordList.add(record);
			}
		}
		finally
		{
			if (cursor != null)
			{
				cursor.close();
			}
		}
		return mRecordList;
	}
	
	public class RecordEntity{
		String name;
		String number;
		int type;
		String sDate;
		String duration;
		int _new;
		@Override
		public String toString() {
			return "RecordEntity [toString()=" + name+"," + number+"," + type+"," + sDate+"," + duration+"," + name+"," + "]";
		}
	}

}
