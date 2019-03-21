package com.bracelet;

import org.apache.http.util.TextUtils;

public class ToshiliuTo2 {
	public static String str2HexStr(String str) {
	    char[] chars = "0123456789ABCDEF".toCharArray();
	    StringBuilder sb = new StringBuilder("");
	    byte[] bs = str.getBytes();
	    int bit;
	    for (int i = 0; i < bs.length; i++) {
	        bit = (bs[i] & 0x0f0) >> 4;
	        sb.append(chars[bit]);
	        bit = bs[i] & 0x0f;
	        sb.append(chars[bit]);
	        // sb.append(' ');
	    }
	    return sb.toString().trim();
	}
	
	public static String hexStr2Str(String hexStr) {
	    String str = "0123456789ABCDEF";
	    char[] hexs = hexStr.toCharArray();
	    byte[] bytes = new byte[hexStr.length() / 2];
	    int n;
	    for (int i = 0; i < bytes.length; i++) {
	        n = str.indexOf(hexs[2 * i]) * 16;
	        n += str.indexOf(hexs[2 * i + 1]);
	        bytes[i] = (byte) (n & 0xff);
	    }
	    return new String(bytes);
	}
	
	public static String strTo16(String s) {
	    String str = "";
	    for (int i = 0; i < s.length(); i++) {
	        int ch = (int) s.charAt(i);
	        String s4 = Integer.toHexString(ch);
	        str = str + s4;
	    }
	    return str;
	}
	
	public static String parseByte2HexStr(byte buf[]) {
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < buf.length; i++) {
	        String hex = Integer.toHexString(buf[i] & 0xFF);
	        if (hex.length() == 1) {
	            hex = '0' + hex;
	        }
	            sb.append(hex.toUpperCase());
	        }
	        return sb.toString();
	    }
	

	
	/**
	 * 16进制直接转换成为字符串(无需Unicode解码)
	 * @param hexStr
	 * @return
	 */
	public static String hexStr2Strr(String hexStr) {
	    String str = "0123456789ABCDEF";
	    char[] hexs = hexStr.toCharArray();
	    byte[] bytes = new byte[hexStr.length() / 2];
	    int n;
	    for (int i = 0; i < bytes.length; i++) {
	        n = str.indexOf(hexs[2 * i]) * 16;
	        n += str.indexOf(hexs[2 * i + 1]);
	        bytes[i] = (byte) (n & 0xff);
	    }
	    return new String(bytes);
	}
	
	public static String binaryString2hexString(String bString) {
		if (bString == null || bString.equals(""))
		return null;
		StringBuffer tmp=new StringBuffer();
		int iTmp = 0;
		for (int i = 0; i < bString.length(); i += 4) {
		iTmp = 0;
		for (int j = 0; j < 4; j++) {
		iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
		}
		tmp.append(Integer.toHexString(iTmp));
		}
		return tmp.toString();
		}
	
	//二进制的字符串 111111110000  怎么转成16进制的
	public static void main(String[] args) {
		System.out.println(Integer.toHexString(Integer.parseInt("100011110000", 2)));
     Integer infoVibration=1;
     Integer info=0;
		StringBuffer setString = new StringBuffer("");
		setString.append(infoVibration).append(info);
		System.out.println(setString.toString());
		System.out.println(turn2to16("000011110000"));
	}

	private static String turn2to16(String str) {
		String sum="";
		
		int t=str.length()%4;
		if(t!=0){
			for(int i=str.length();i-4>=0;i=i-4){
				String s=str.substring(i-4,i);
				int tem=Integer.parseInt(String.valueOf(s), 2);
				sum=Integer.toHexString(tem).toUpperCase()+sum;
			}
			String st=str.substring(0,t);
			
			int tem=Integer.parseInt(String.valueOf(st), 2);
			sum=Integer.toHexString(tem).toUpperCase()+sum;
			
		}
		else{
			for(int i=str.length();i-4>=-1;i=i-4){
				String s=str.substring(i-4,i);
				int tem=Integer.parseInt(String.valueOf(s), 2);
				sum=Integer.toHexString(tem).toUpperCase()+sum;
			}
		}
		return sum;
	}
	
}
