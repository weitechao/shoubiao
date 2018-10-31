package com.bracelet;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bracelet.dto.SocketBaseDto;

public class JsonTest {
    public static void main(String[] args) {
        // String json =
        // "{\"type\":1,\"no\":\"1234567\",\"timestamp\":1501123709,\"data\": {\"dv\":\"divNo.1\",\"sd\":\"sdV1\"}}";
        // JSONObject object = (JSONObject) JSON.parse(json);
        // System.out.println(object.getIntValue("type"));
        // JSONObject object2 = (JSONObject) object.get("data");
        // System.out.println(object2.getString("dv"));
        // String o = (String) object2.get("dv");
        // System.out.println(o);
        // System.out.println(object2);

        String json2 = "{\"type\": 10,\"no\": \"12312312\", \"timestamp\": 1501123709,\"data\": [{\"minHeartRate\":100, \"maxHeartRate\":120,\"timestamp\": 1501123700}]}";
        JSONObject object = (JSONObject) JSON.parse(json2);
        JSONArray jsonArray = object.getJSONArray("data");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object2 = (JSONObject) jsonArray.get(i);
            System.out.println(object2.getString("timestamp"));
            System.out.println(object2.getString("minHeartRate"));
            System.out.println(object2.getString("maxHeartRate"));
        }

        SocketBaseDto baseDto = new SocketBaseDto();
        baseDto.setStatus(200);
        baseDto.setMessage("成功");

        Map<String, Object> dataResp = new HashMap<>();
        dataResp.put("maxHeartPressure", 200);
        dataResp.put("minHeartPressure", 120);
        dataResp.put("timestamp", new Date().getTime());
        baseDto.setData(dataResp);

        System.out.println(JSON.toJSONString(baseDto));
    }
}
