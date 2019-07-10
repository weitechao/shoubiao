package com.bracelet.google;

import org.json.JSONObject;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

public class Jsontest {
	/**
	 * 根据基站信息生成位置转换请求内容
	 * 
	 * @param cellid
	 * @param mnc
	 * @param mcc
	 * @param lac
	 * @return 请求串
	 */
	private String makeRequest(String cellid, int lac, int mcc, int mnc) {
		JSONObject request = new JSONObject();
		request.put("version", "1.1.0");
		request.put("host", "maps.google.com");
		// request.put("home_mobile_country_code", mcc);
		// request.put("home_mobile_network_code", mnc);
		// request.put("radio_type", "gsm");
		// request.put("carrier", "Vodafone");
		request.put("request_address", true);
		request.put("address_language", "zh_CN");

		JSONArray cell_towers = new JSONArray();
		JSONObject cellinfo = new JSONObject();
		cellinfo.put("cell_id", cellid);
		cellinfo.put("location_area_code", lac);
		cellinfo.put("mobile_country_code", mcc);
		cellinfo.put("mobile_network_code", mnc);
		cellinfo.put("age", 0);
		// cellinfo.put("signal_strength", -60);
		// cellinfo.put("timing_advance", 5555);

		cell_towers.put(cellinfo);
		request.put("cell_towers", cell_towers);

		return request.toString();
	}

	/**
	 * 根据经纬度信息生成位置转换请求内容
	 * 
	 * @param latitude
	 * @param longitude
	 * @return 请求串
	 */
	private String makeRequest(double latitude, double longitude) {
		JSONObject request = new JSONObject();
		request.put("version", "1.1.0");
		request.put("host", "maps.google.com");
		request.put("request_address", true);
		request.put("address_language", "zh_CN");

		JSONObject location = new JSONObject();
		location.put("latitude", latitude);
		location.put("longitude", longitude);

		request.put("location", location);

		return request.toString();
	}

	public static void main(String args[]) {
		Jsontest test = new Jsontest();
		URL url = null;
		HttpURLConnection conn = null;

		try {
			url = new URL("http://www.google.com/loc/json");

			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");

			String request = test.makeRequest("50554", 9513, 460, 1

			);
			// String request=test.makeRequest(26.080,119.280);
			System.out.println(request);

			conn.getOutputStream().write(request.getBytes());
			conn.getOutputStream().flush();
			conn.getOutputStream().close();

			int code = conn.getResponseCode();
			System.out.println("code   " + code);

			BufferedReader in = new BufferedReader(new InputStreamReader(conn
					.getInputStream()));
			String inputLine;
			inputLine = in.readLine();
			System.out.println(inputLine);
			in.close();

			// 解析结果
			JSONObject result = new JSONObject(inputLine);
			JSONObject location = result.getJSONObject("location");
			JSONObject address = location.getJSONObject("address");

			System.out.println("city = " + address.getString("city"));
			System.out.println("region = " + address.getString("region"));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				conn.disconnect();
		}
	}

}
