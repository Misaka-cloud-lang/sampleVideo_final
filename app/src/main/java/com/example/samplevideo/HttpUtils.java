package com.example.samplevideo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtils {

	public static String getJsonContent(String path) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			URL url = new URL(path);
			//连接至视频源服务器
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.connect();
			//接受json格式的网站内容，准备解析视频源
			InputStream is = conn.getInputStream();
			byte[] buf = new byte[1024];
			int hasRead;
			//读取视频源
			while (true) {
				hasRead = is.read(buf);
				if (hasRead < 0)break;//读到头了
				baos.write(buf, 0, hasRead);
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return baos.toString();
	}
}
