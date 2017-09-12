package cn.com.mumway.util;


import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.http.client.HttpClient;

public class HttpUtil {
	private static MultiThreadedHttpConnectionManager connectionManager = null;
	private static HttpClient httpClient = null;

	static {
		connectionManager = new MultiThreadedHttpConnectionManager();
		connectionManager.getParams().setMaxTotalConnections(10240); // 总连接数
		connectionManager.getParams().setDefaultMaxConnectionsPerHost(1280);// host
		connectionManager.getParams().setConnectionTimeout(1000 * 1 * 5);// 超时时间
		connectionManager.getParams().setSoTimeout(1000 * 1 * 5);// 读取
	}

	public static HttpClient getHttpClient() {
		if (httpClient == null) {
		//	httpClient = new HttpClient();
		}
		return httpClient;
	}

}
