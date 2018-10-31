package com.bracelet.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedHashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class HttpClientGet {
	private static Logger logger = LoggerFactory.getLogger(HttpClientGet.class);
	private static final String defaultCharset = "utf-8";
	private static CloseableHttpClient httpClient = HttpClientBuilder.create()
			.build();
	private static RequestConfig requestConfig = null;
	static {
		requestConfig = RequestConfig.custom().setConnectionRequestTimeout(100)
				.setConnectTimeout(3000).setSocketTimeout(3000).build();
	}

	public static String get(String url) {
		HttpResponse httpResponse = null;
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader(
				"user-agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.131 Safari/537.36");
		// 添加head方法二
		httpGet.addHeader(new BasicHeader("Accept-Language", "zh-CN,zh;q=0.8"));
		httpGet.setConfig(requestConfig);
		String reponseStr = null;
		try {
			httpResponse = httpClient.execute(httpGet);
			reponseStr = EntityUtils.toString(httpResponse.getEntity(),
					defaultCharset);
		} catch (Exception e) {
			logger.error("http error.", e);
		}

		return reponseStr;
	}

	public static String urlReturnParamsAs(String urlNameString) {
		StringBuffer sb = new StringBuffer();
		BufferedReader in = null;
		HttpURLConnection httpConnect = null;
		urlNameString = urlNameString.replaceAll(" ", "%20");
		try {
			URL realUrl = new URL(urlNameString);
			httpConnect = (HttpURLConnection) realUrl.openConnection();
			httpConnect.setRequestProperty("accept", "*/*");
			httpConnect.setRequestProperty("connection", "Keep-Alive");
			httpConnect.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			httpConnect.connect();
			int code = httpConnect.getResponseCode();
			if (code == 200) {
				in = new BufferedReader(new InputStreamReader(
						httpConnect.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					sb.append(line);
				}
				in.close();

			} else {
				logger.info("httpConnect.getResponseCode: " + code);
				sb.append("-1");
			}

		} catch (Exception e) {
			sb.append("-1");
		} finally {
			try {
				if (httpConnect != null) {
					httpConnect.disconnect();
				}

				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static String sendGetToGaoDe(String url,
			LinkedHashMap<String, String> map) {
		String urlNameString = url + paramsTransToUrl(map);
		String returnParams = urlReturnParams(urlNameString);

		return returnParams;
	}

	public static String paramsTransToUrl(LinkedHashMap<String, String> map) {
		StringBuffer params = new StringBuffer("?");

		for (String key : map.keySet()) {
			if (!params.toString().equals("?")) {
				params.append("&");
			}
			params.append(key).append("=").append(map.get(key));
		}
		return params.toString();
	}

	public static String urlReturnParams(String urlNameString) {
		StringBuffer sb = new StringBuffer();
		BufferedReader in = null;
		try {
			URL realUrl = new URL(urlNameString);
			URLConnection connection = realUrl.openConnection();
			HttpURLConnection httpConnect = (HttpURLConnection) connection;
			httpConnect.setRequestProperty("accept", "*/*");
			httpConnect.setRequestProperty("connection", "Keep-Alive");
			httpConnect.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			httpConnect.connect();

			int code = httpConnect.getResponseCode();
			if (code == 200) {
				in = new BufferedReader(new InputStreamReader(
						connection.getInputStream()));
				String line;
				while ((line = in.readLine()) != null) {
					sb.append(line);
				}
				in.close();
			} else {
				sb.append("-1");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			sb.append("-1");
			// e.printStackTrace();
		} finally {
			return sb.toString();
		}
	}

}
