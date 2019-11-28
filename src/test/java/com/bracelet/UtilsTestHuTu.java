package com.bracelet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.bracelet.util.StringUtil;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.io.resource.NoResourceException;
import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.text.StrFormatter;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.core.util.ZipUtil;
import junit.framework.Assert;

public class UtilsTestHuTu {

    public static void main(String[] args) {
    /*	int a = 1;
    	//aStr为"1"
    	String aStr = Convert.toStr(a);
    	System.out.println(aStr);
    	long[] b = {1,2,3,4,5};
    	//bStr为："[1, 2, 3, 4, 5]"
    	String bStr = Convert.toStr(b);
    	System.out.println(bStr);*/
    	
    	/*String a = "2017-05-06";
    	Date value = Convert.toDate(a);
    	System.out.println(value);*/
    	
    	
    /*	Object[] a = {"a", "你", "好", "", 1};
    	//从4.1.11开始可以这么用
    	List<?> list = Convert.toList(a);
    	System.out.println(list.get(0));*/
    	
    	
    	 //转为16进制（Hex）字符串

    	/*String a = "我是一个小小的可爱的字符串";
    	//结果："e68891e698afe4b880e4b8aae5b08fe5b08fe79a84e58fafe788b1e79a84e5ad97e7aca6e4b8b2"
    	String hex = Convert.toHex(a, CharsetUtil.CHARSET_UTF_8);
    	System.out.println(hex);
    	String raw = Convert.hexToStr(hex, CharsetUtil.CHARSET_UTF_8);
    	System.out.println(raw);*/
    
    	
    /*	System.out.println(DateUtil.now());
    	System.out.println(DateUtil.date());
    	System.out.println(DateUtil.dateSecond());
    	System.out.println(DateUtil.currentSeconds());
    	System.out.println(DateUtil.today());*/
    	
    /*	//当前时间
    	Date date = DateUtil.date();
    	//当前时间
    	Date date2 = DateUtil.date(Calendar.getInstance());
    	//当前时间
    	Date date3 = DateUtil.date(System.currentTimeMillis());
    	//当前时间字符串，格式：yyyy-MM-dd HH:mm:ss
    	String now = DateUtil.now();
    	//当前日期字符串，格式：yyyy-MM-dd
    	String today= DateUtil.today();
    	
    	System.out.println(date);
    	System.out.println(date2);
    	System.out.println(date3);
    	System.out.println(now);
    	System.out.println(today);*/
    	
    	
    	/*
    	 * yyyy-MM-dd HH:mm:ss
yyyy-MM-dd
HH:mm:ss
yyyy-MM-dd HH:mm
yyyy-MM-dd HH:mm:ss.SSS
    	 * */
    	
    	/*String dateStr = "2017-03-01";
    	Date date = DateUtil.parse(dateStr);
    	System.out.println(date);*/
    	
    	/*String dateStr = "2017-03-01";
    	Date date = DateUtil.parse(dateStr, "yyyy-MM-dd");
    	System.out.println(date);*/
    	
    	
    /*	Date date = DateUtil.date();
    	//获得年的部分
    	System.out.println(DateUtil.year(date));
    	//获得月份，从0开始计数
    	System.out.println(DateUtil.month(date));
    	//获得月份枚举
    	System.out.println(DateUtil.monthEnum(date));
    	
    	
    	//输出：31天1小时
    
    	
    	TimeInterval timer = DateUtil.timer();

    	//---------------------------------
    	//-------这是执行过程
    	//---------------------------------

    	timer.interval();//花费毫秒数
    	timer.intervalRestart();//返回花费时间，并重置开始时间
    	timer.intervalMinute();//花费分钟数
    	
    	
    	Console.log(timer.interval());
    	Console.log(timer.intervalRestart());
    	Console.log(timer.intervalMinute());*/
    	
    	//文件的复制
    /*	BufferedInputStream in = FileUtil.getInputStream("d:/test.txt");
    	BufferedOutputStream out = FileUtil.getOutputStream("d:/test2.txt");
    	long copySize = IoUtil.copy(in, out, IoUtil.DEFAULT_BUFFER_SIZE);
    	Console.log(copySize);*/
    	
    	
    	/*File file = FileUtil.file("d:/test.jpg");
    	String type = FileTypeUtil.getType(file);
    	//输出 jpg则说明确实为jpg文件
    	Console.log(type);*/
    	
    	//读取文件
    	/*FileReader fileReader = new FileReader("E:/javaMarProject/shoubiao-master/src/main/resources/db.properties");
    	String result = fileReader.readString();
    	Console.log(result);*/
    	
    	//文件写
    	/*FileWriter writer = new FileWriter("D:/db.properties");
    	writer.write("test");*/
    	
    	
   /* 	String fileName = StrUtil.removeSuffix("pretty_girl.jpg", ".jpg");
    	Console.log(fileName);*/

    	
    	/*字符串截取
    	 * String str = "abcdefgh";
    	String strSub1 = StrUtil.sub(str, 2, 3); //strSub1 -> c
    	String strSub2 = StrUtil.sub(str, 2, -3); //strSub2 -> cde
    	String strSub3 = StrUtil.sub(str, 3, 2); //strSub2 -> c
    	Console.log(strSub1);
    	Console.log(strSub2);
    	Console.log(strSub3);*/
    	
    	
    	/*String template = "{}爱{}，就像老鼠爱大米";
    	String str = StrUtil.format(template, "我", "你"); //str -> 我爱你，就像老鼠爱大米
    	Console.log(str);*/
    
/*    	String url = "http://www.hutool.cn//aaa/bbb";
    	// 结果为：http://www.hutool.cn/aaa/bbb
    	String normalize = URLUtil.normalize(url);
       	Console.log(normalize);
    	url = "http://www.hutool.cn//aaa/\\bbb?a=1&b=2";
    	// 结果为：http://www.hutool.cn/aaa/bbb?a=1&b=2
    	normalize = URLUtil.normalize(url);
    	Console.log(normalize);
    	
    	String body = "366466 - 副本.jpg";
    	// 结果为：366466%20-%20%E5%89%AF%E6%9C%AC.jpg
    	String encode = URLUtil.encode(body);
    	Console.log(encode);
    	*/
    	//网卡信息
    	/*String str = RuntimeUtil.execForStr("ipconfig");
    	Console.log(str);*/
    	
    /*	long c=299792458;//光速
    	String format = NumberUtil.decimalFormat(",###", c);//299,792,458
    	Console.log(format);*/

    	//生成的UUID是带-的字符串，类似于：a5c8a5e8-df2b-4706-bea4-08d0939410e3
    	/*String uuid = IdUtil.randomUUID();
    	Console.log(uuid);
    	//生成的是不带-的字符串，类似于：b17f24ff026d40949c85a24f4f375d42
    	String simpleUUID = IdUtil.simpleUUID();
    	Console.log(simpleUUID);*/
    	 
    	//参数1为终端ID
    	//参数2为数据中心ID
    	/*
    	 * 分布式系统中，有一些需要使用全局唯一ID的场景，有些时候我们希望能使用一种简单一些的ID，并且希望ID能够按照时间有序生成。Twitter的Snowflake 算法就是这种生成器。
    	 * //参数1为终端ID
//参数2为数据中心ID*/
    	/*Snowflake snowflake = IdUtil.createSnowflake(1, 1);
    	long id = snowflake.nextId();
    	Console.log(id);*/
    	
    	//将aaa目录下的所有文件目录打包到d:/aaa.zip
    
    	/*ZipUtil.zip("F:/weitechaotest", "F:/testweitechao/aaaaaaa.zip", true);
    	
    	ZipUtil.zip(FileUtil.file("d:/bbb/ccc.zip"), false, 
    		    FileUtil.file("d:/test1/file1.txt"),
    		    FileUtil.file("d:/test1/file2.txt"),
    		    FileUtil.file("d:/test2/file1.txt"),
    		    FileUtil.file("d:/test2/file2.txt")
    		);*/
    	
    	
    	//将test.zip解压到e:\\aaa目录下，返回解压到的目录
    //	File unzip = ZipUtil.unzip("E:\\aaa\\test.zip", "e:\\aaa");
    	
    	/*Dict dict = Dict.create()
    		    .set("key1", 1)//int
    		    .set("key2", 1000L)//long
    		    .set("key3", DateTime.now());//Date
    	dict.set("te", 123);
    	
    	Long v2 = dict.getLong("key2");//1000
    	Console.log(v2);
    	Console.log(dict.get("a"));*/
    	
    	/*字符串的空判断
    	 * String a="";
    	String b="cc";
    	Console.log(StrUtil.hasBlank(a));*/
   
    	/*	
     * 控制台的输出 console的封装
    	String[] a = {"abc", "bcd", "def"};
    	Console.log(a);//控制台输出：[abc, bcd, def]
    
    	Console.log("This is Console log for {}.", "test");
    	//控制台输出：This is Console log for test.
*/    
    	
    	//通常使用
    	String result1 = StrFormatter.format("this is {} for {}", "a", "b");
    	Assert.assertEquals("this is a for b", result1);

    	//转义{}
    	String result2 = StrFormatter.format("this is \\{} for {}", "a", "b");
    	Assert.assertEquals("this is {} for a", result2);

    	//转义\
    	String result3 = StrFormatter.format("this is \\\\{} for {}", "a", "b");
    	Assert.assertEquals("this is \\a for b", result3);
    	
    	Console.log(result1);
    	Console.log(result2);
    	Console.log(result3);
    	
    }

}
