/**
 * 
 */
package com.fan.hacker;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import com.sun.org.apache.bcel.internal.generic.RETURN;

import android.os.Environment;

/**
 * @author Administrator
 *��������ʵ����rootȨ�޵������ץ�����������Ը��ݷ���������������ʱ���У���������ͼƬ��·�����ظ���������ֱ�ӷ���ͼ
 */
public class ScreenCapture {
	
	public static String screenCap() {
		Process sh;
		try {
			sh = Runtime.getRuntime().exec("su", null,null);
		    OutputStream  os = sh.getOutputStream();
		    os.write(("/system/bin/screencap -p " + "/sdcard/omg.png").getBytes("ASCII"));
		    os.flush();
		    os.close();
		    try {
				sh.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String pathName =Environment.getExternalStorageDirectory()+ File.separator +"omg.png";//�õ��ļ���ȫ���ƣ�����׺
		return pathName;
		//����Ĵ��뽫png�ļ�תΪjpg�ļ�
//		Bitmap screen = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+         
//		File.separator +"img.png");
	//
//		//my code for saving
//		    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//		    screen.compress(Bitmap.CompressFormat.JPEG, 15, bytes);
	//
//		//you can create a new file name "test.jpg" in sdcard folder.
	//
//		File f = new File(Environment.getExternalStorageDirectory()+ File.separator + "test.jpg");
//	            try {
//					f.createNewFile();
//					//write the bytes in file
//				    FileOutputStream fo = new FileOutputStream(f);
//				    fo.write(bytes.toByteArray());
//				// remember close de FileOutput
	//
//				    fo.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}

	}
	
	
	

}
