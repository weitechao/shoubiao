package com.bracelet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.bracelet.util.Utils;

@Controller
@RequestMapping("/slb")
public class SlbController extends BaseController {
	//private Logger logger = LoggerFactory.getLogger(getClass());

	//这个接口 给设备分配ip和端口使用
	
	@ResponseBody
	@RequestMapping(value = "/getiport/{imei}", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
	public String getiport(@PathVariable String imei) {
		return Utils.IP+","+ Utils.PORT;
	}
}
