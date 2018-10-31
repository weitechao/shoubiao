package com.bracelet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 测试用的 TCP 客户端
 * 
 */
public class TcpClient {

    public static void main(String[] args) throws IOException {
        Socket socket = null;
        OutputStream out = null;
        
        
        // IMEI： 869758001213076  对应token：1qaz
        // IMEI:869758001213084  对应token：2wsx
        String json = "{\"type\":1,\"imei\":\"123456789012380\", \"no\":\"1234567889\",\"timestamp\":1501123709,\"data\": {\"dv\":\"divNo.1\",\"sd\":\"sdV1\",}}\r\n{\"type\":32,\"a\":0,\"imei\":\"123456789012380\", \"no\":\"1234567889\",\"timestamp\":1501123709,\"data\": {\"dv\":\"divNo.1\",\"sd\":\"sdV1\",}}";

        String host = "47.94.221.139";
//        host = "127.0.0.1";
        try {
            socket = new Socket(host, 7777);
            out = socket.getOutputStream();

            // 请求服务器
            // String lines = "床前明月光\r\n疑是地上霜\r\n举头望明月\r\n低头思故乡\r\n";
            String lines = json + "\r\n";
            byte[] outputBytes = lines.getBytes("gbk");
            out.write(outputBytes);
            out.flush();
            InputStream inputStream = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            System.out.println("-----");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            out.close();
            socket.close();
        }

    }

}
