package com.bracelet;

import java.util.List;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

public class UtilsTestHuTuExcel {
	public static void main(String[] args) {
		ExcelReader reader = ExcelUtil.getReader("d:/20191203192209.xlsx");
		List<List<Object>> readAll = reader.read();
	}
}
