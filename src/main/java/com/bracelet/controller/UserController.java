package com.bracelet.controller;

import com.alibaba.fastjson.JSON;
import com.bracelet.dto.HttpBaseDto;
import com.bracelet.dto.SocketLoginDto;
import com.bracelet.entity.BindDevice;
import com.bracelet.entity.NoticeInfo;
import com.bracelet.entity.UserInfo;
import com.bracelet.entity.VersionInfo;
import com.bracelet.entity.WatchAppVersionInfo;
import com.bracelet.exception.BizException;
import com.bracelet.service.IAuthcodeService;
import com.bracelet.service.IOpenDoorService;
import com.bracelet.service.IUserInfoService;
import com.bracelet.service.IVoltageService;
import com.bracelet.util.ChannelMap;
import com.bracelet.util.RanomUtil;
import com.bracelet.util.RespCode;
import com.bracelet.util.Utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	IUserInfoService userInfoService;
	@Autowired
	IAuthcodeService authcodeService;
	@Autowired
	IVoltageService voltageService;

	@Autowired
	IOpenDoorService openService;
	private Logger logger = LoggerFactory.getLogger(getClass());

	@ResponseBody
	@RequestMapping(value = "/getAuthCode/{tel}", method = RequestMethod.GET)
	public HttpBaseDto getAuthCode(@PathVariable String tel) {
		if (StringUtils.isEmpty(tel)) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}
		this.authcodeService.sendAuthCode(tel);
		HttpBaseDto dto = new HttpBaseDto();
		return dto;
	}

	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public HttpBaseDto register(@RequestParam String tel,
			@RequestParam String code, @RequestParam String pwd) {
		if (StringUtils.isAnyEmpty(tel, code, pwd)) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}
		if (this.authcodeService.verifyAuthCode(tel, code)) {
			UserInfo userInfo = userInfoService.getUserInfoByUsername(tel);
			if (userInfo != null && userInfo.getType() == 1) {
				logger.info("该手机号已经注册, tel:" + tel);
				throw new BizException(RespCode.U_ALREADY_REGED);
			}
			HttpBaseDto dto = new HttpBaseDto();
			if (userInfo == null) {

				if (this.userInfoService.saveUserInfo(tel, pwd, 1)) {
					userInfo = userInfoService.getUserInfoByUsername(tel);
					Map<String, Object> dataMap = new HashMap<>();
					String token = this.tokenInfoService.genToken(userInfo
							.getUser_id());
					dataMap.put("id", userInfo.getUser_id());
					dataMap.put("token", token);
					dto.setData(dataMap);
					return dto;
				} else {
					logger.info("用户注册保存数据库失败, tel:" + tel);
					throw new BizException(RespCode.SYS_ERR);
				}
			} else if (userInfo != null && userInfo.getType() == 0) {
				userInfo = userInfoService.getUserInfoByUsername(tel);
				userInfoService.updatePwdAndType(tel, pwd, 1);
				Map<String, Object> dataMap = new HashMap<>();
				String token = this.tokenInfoService.genToken(userInfo
						.getUser_id());
				dataMap.put("id", userInfo.getUser_id());
				dataMap.put("token", token);
				dto.setData(dataMap);
				return dto;
			} else {
				logger.info("保存用户密码数据，发生数据库失败, token:" + tel);
				throw new BizException(RespCode.SYS_ERR);
			}
		} else {
			// 验证码错误
			logger.info("验证码验证失败, tel:" + tel + ",code:" + code);
			throw new BizException(RespCode.U_AUTHCODE_NOTEXIST);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/register/pwd", method = RequestMethod.POST)
	public HttpBaseDto registerPwd(@RequestParam String tel,
			@RequestParam String password) {
		if (StringUtils.isAnyEmpty(tel, password)) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}
		UserInfo userInfo = userInfoService.getUserInfoByUsername(tel);
		if (userInfo != null && userInfo.getType() == 1) {
			logger.info("该手机号已注册, tel:" + tel);
			throw new BizException(RespCode.U_ALREADY_REGED);
		}
		HttpBaseDto dto = new HttpBaseDto();
		if (userInfo == null) {
			if (this.userInfoService.saveUserInfo(tel, password, 1)) {
				return dto;
			} else {
				logger.info("保存用户密码数据，发生数据库失败, token:" + tel + ",password:"
						+ password);
				throw new BizException(RespCode.SYS_ERR);
			}
		} else if (userInfo != null && userInfo.getType() == 0) {
			userInfoService.updatePwdAndType(tel, password, 1);
			return dto;
		} else {
			logger.info("保存用户密码数据，发生数据库失败, token:" + tel + ",password:"
					+ password);
			throw new BizException(RespCode.SYS_ERR);
		}
	}

	/**
	 * type: 0: 验证码， 1: 密码
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public HttpBaseDto login(@RequestParam String tel,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "password", required = false) String password) {
		if (StringUtils.isAnyEmpty(tel)) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}
		if (StringUtils.isEmpty(type)) {
			type = "0";
		}
		if ("0".equals(type)) {
			if (StringUtils.isAnyEmpty(code)) {
				throw new BizException(RespCode.NOTEXIST_PARAM);
			}
			if (this.authcodeService.verifyAuthCode(tel, code)) {
				UserInfo userInfo = userInfoService.getUserInfoByUsername(tel);
				if (userInfo == null) {
					logger.info("该手机号尚未注册, tel:" + tel);
					throw new BizException(RespCode.U_TEL_NOT_REGED);
				}
				String token = this.tokenInfoService.genToken(userInfo
						.getUser_id());
				HttpBaseDto dto = new HttpBaseDto();
				Map<String, Object> dataMap = new HashMap<>();
				dataMap.put("user_id", userInfo.getUser_id());
				dataMap.put("username", userInfo.getUsername());
				dataMap.put("admin", 0);
				dataMap.put("token", token);
				List<BindDevice> list = userInfoService
						.getBindInfoById(userInfo.getUser_id());
				if (list != null && !list.isEmpty()) {
					for (BindDevice wlInfo : list) {
						Integer status = wlInfo.getStatus();
						if (status == 1) {
							dataMap.put("admin", 1);
							break;
						}
					}
				}

				dto.setData(dataMap);
				return dto;
			} else {
				// 验证码错误
				logger.info("验证码验证失败, tel:" + tel + ",code:" + code);
				throw new BizException(RespCode.U_AUTHCODE_NOTEXIST);
			}
		} else if ("1".equals(type)) {
			if (StringUtils.isAnyEmpty(password)) {
				throw new BizException(RespCode.NOTEXIST_PARAM);
			}
			UserInfo userInfo = userInfoService.getUserInfoByUsername(tel);
			if (userInfo == null) {
				logger.info("该手机号尚未注册, tel:" + tel);
				throw new BizException(RespCode.U_TEL_NOT_REGED);
			}
			/*
			 * String genPassword = Utils.getMD5(password); if
			 * (genPassword.equalsIgnoreCase(userInfo.getPassword())) {
			 */
			if (password.equalsIgnoreCase(userInfo.getPassword())) {
				String token = this.tokenInfoService.genToken(userInfo
						.getUser_id());
				HttpBaseDto dto = new HttpBaseDto();
				Map<String, Object> dataMap = new HashMap<>();
				dataMap.put("user_id", userInfo.getUser_id());
				dataMap.put("username", userInfo.getUsername());
				dataMap.put("token", token);
				dataMap.put("admin", 0);

				List<BindDevice> list = userInfoService
						.getBindInfoById(userInfo.getUser_id());
				if (list != null && !list.isEmpty()) {
					for (BindDevice wlInfo : list) {
						Integer status = wlInfo.getStatus();
						if (status == 1) {
							dataMap.put("admin", 1);
							break;
						}
					}
				}
				dto.setData(dataMap);
				return dto;
			} else {
				logger.info("用户密码错误, tel:" + tel + ",password:" + password
						+ ",genPassword:" + password + ",userPwd:"
						+ userInfo.getPassword());
				throw new BizException(RespCode.U_USERNAME_PWD_ERR);
			}
		} else {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/saveUserInfo", method = RequestMethod.POST)
	public HttpBaseDto saveUserInfo(@RequestParam String token,
			@RequestParam String avatar, @RequestParam String nickname,
			@RequestParam String sex, @RequestParam String weight,
			@RequestParam String height,
			@RequestParam(value = "address", required = false) String address) {
		if (StringUtils.isAnyEmpty(avatar, nickname, sex, weight, height)) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}
		Long user_id = checkTokenAndUser(token);
		Integer intSex = Integer.parseInt(sex);
		if (this.userInfoService.updateUserInfo(user_id, avatar, nickname,
				intSex, weight, height, address)) {
			HttpBaseDto dto = new HttpBaseDto();
			return dto;
		} else {
			logger.info("更新用户数据，发生数据库失败, token:" + token + ",avatar:" + avatar
					+ ",nickname:" + nickname + ",sex:" + sex + ",weight:"
					+ weight + ",height:" + height + ",address:" + address);
			throw new BizException(RespCode.SYS_ERR);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/info/{token}", method = RequestMethod.GET)
	public HttpBaseDto getInfo(@PathVariable String token) {
		Long user_id = checkTokenAndUser(token);
		UserInfo userInfo = userInfoService.getUserInfoById(user_id);
		if (userInfo == null) {
			logger.info("获取用户信息，token对应的user不存在 token:" + token + ",userId:"
					+ user_id);
			throw new BizException(RespCode.U_NOT_EXIST);
		}
		HttpBaseDto dto = new HttpBaseDto();
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("id", userInfo.getUser_id());
		dataMap.put("username", userInfo.getUsername());
		dataMap.put("nickname", userInfo.getNickname());
		dataMap.put("head", userInfo.getHead());
		/*
		 * dataMap.put("avatar", userInfo.getAvatar()); dataMap.put("sex",
		 * userInfo.getSex()); dataMap.put("weight", userInfo.getWeight());
		 * dataMap.put("height", userInfo.getHeight()); dataMap.put("address",
		 * userInfo.getAddress()); dataMap.put("dv", userInfo.getDv());
		 * dataMap.put("sd", userInfo.getSd()); dataMap.put("imei",
		 * userInfo.getImei()); dataMap.put("createtime",
		 * userInfo.getCreatetime() != null ? userInfo
		 * .getCreatetime().getTime() : 0); dataMap.put("bindingtime",
		 * userInfo.getBindingtime() != null ? userInfo
		 * .getBindingtime().getTime() : 0);
		 */
		dto.setData(dataMap);
		
		return dto;
	}

	@ResponseBody
	@RequestMapping(value = "/device/{token}", method = RequestMethod.GET)
	public HttpBaseDto getDevice(@PathVariable String token) {
		Long user_id = checkTokenAndUser(token);
		List<BindDevice> list = userInfoService.getBindInfoById(user_id);
		List<Map<String, Object>> datalist = new LinkedList<Map<String, Object>>();
		if (list != null && !list.isEmpty()) {
			for (BindDevice wlInfo : list) {
				Map<String, Object> dataMap = new HashMap<>();
				dataMap.put("id", wlInfo.getId());
				dataMap.put("name", wlInfo.getName());
				dataMap.put("imei", wlInfo.getImei());
				Integer count = openService.getOpenCount(wlInfo.getImei());
				dataMap.put("count", count);
				dataMap.put("createtime", wlInfo.getCreatetime().getTime());
				dataMap.put("status", wlInfo.getStatus());
				BindDevice bindevice = userInfoService
						.getBindInfoByImeiAndStatus(wlInfo.getImei(), 1);
				if (bindevice != null) {
					dataMap.put("user_id", bindevice.getUser_id());
				}
				datalist.add(dataMap);
			}
		}
		HttpBaseDto dto = new HttpBaseDto();
		dto.setData(datalist);
		return dto;
	}

	@ResponseBody
	@RequestMapping(value = "/device/bind", method = RequestMethod.POST)
	public HttpBaseDto deviceBind(@RequestParam String token,
			@RequestParam String imei, @RequestParam Integer type,
			@RequestParam String name) {
		if (StringUtils.isEmpty(imei)) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}
		Long user_id = checkTokenAndUser(token);
		if (this.userInfoService.bindDevice(user_id, imei, type, name)) {
			HttpBaseDto dto = new HttpBaseDto();
			return dto;
		} else {
			logger.info("用户绑定设备失败, token:" + token + ",userId:" + user_id
					+ ",imei:" + imei);
			throw new BizException(RespCode.U_BINGDING_ERR);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/device/unbind", method = RequestMethod.POST)
	public HttpBaseDto deviceUnbind(@RequestParam String token,
			@RequestParam Integer id, @RequestParam String imei) {
		Long user_id = checkTokenAndUser(token);
		if (this.userInfoService.unbindDevice(user_id, id)) {
			openService.deleteByImei(imei);
			HttpBaseDto dto = new HttpBaseDto();
			return dto;
		} else {
			logger.info("用户解除绑定设备失败, token:" + token);
			throw new BizException(RespCode.SYS_ERR);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/device/updatename", method = RequestMethod.POST)
	public HttpBaseDto updateName(@RequestParam String token,
			@RequestParam Long id, @RequestParam String name) {
		Long user_id = checkTokenAndUser(token);
		if (this.userInfoService.updateName(id, name)) {
			HttpBaseDto dto = new HttpBaseDto();
			return dto;
		} else {
			logger.info("用户修改名称失败, token:" + token);
			throw new BizException(RespCode.SYS_ERR);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/ask/device/{token}", method = RequestMethod.GET)
	public HttpBaseDto askDevice(@PathVariable String token) {
		Long user_id = checkTokenAndUser(token);
		UserInfo userInfo = userInfoService.getUserInfoById(user_id);
		if (userInfo == null) {
			logger.info("askDevice error.no login.token:" + token);
			throw new BizException(RespCode.U_NOT_EXIST);
		}
		SocketLoginDto socketLoginDto = ChannelMap.getChannel(userInfo
				.getImei());
		if (socketLoginDto == null || socketLoginDto.getChannel() == null) {
			logger.info("socketLoginDto error.no login.token:" + token);
			throw new BizException(RespCode.DEV_NOT_LOGIN);
		}

		Map<String, Object> req = new HashMap<String, Object>();
		req.put("a", 0);
		req.put("type", 31);
		req.put("no", RanomUtil.getFixLenthString(10));
		req.put("timestamp", System.currentTimeMillis() / 1000);
		if (socketLoginDto.getChannel().isActive()) {
			socketLoginDto.getChannel().writeAndFlush(
					JSON.toJSONString(req) + "\r\n");
			logger.info("===request askDevice...ip:"
					+ socketLoginDto.getChannel().remoteAddress().toString()
					+ ",data:" + JSON.toJSONString(req));
		} else {
			logger.info("socketLoginDto error.no login.not active.token:"
					+ token);
			throw new BizException(RespCode.DEV_NOT_LOGIN);
		}

		HttpBaseDto dto = new HttpBaseDto();
		return dto;
	}

	@ResponseBody
	@RequestMapping(value = "/updatepwd", method = RequestMethod.POST)
	public HttpBaseDto updatepwd(@RequestParam String tel,
			@RequestParam String password, @RequestParam String code) {
		if (StringUtils.isAnyEmpty(tel, password, code)) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}
		UserInfo userInfo = userInfoService.getUserInfoByUsername(tel);
		if (userInfo == null) {
			logger.info("该手机号尚未注册, tel:" + tel);
			throw new BizException(RespCode.U_TEL_NOT_REGED);
		}
		if (this.authcodeService.verifyAuthCode(tel, code)) {
			if (this.userInfoService.saveUserPassword(userInfo.getUser_id(),
					password)) {
				HttpBaseDto dto = new HttpBaseDto();
				return dto;
			} else {
				logger.info("保存用户密码数据，发生数据库失败, tel:" + tel + ",password:"
						+ password);
				throw new BizException(RespCode.SYS_ERR);
			}
		} else {
			// 验证码错误
			logger.info("验证码验证失败, tel:" + tel + ",code:" + code);
			throw new BizException(RespCode.U_AUTHCODE_NOTEXIST);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/verifyphone", method = RequestMethod.POST)
	public HttpBaseDto verifyphone(@RequestParam String tel,
			@RequestParam String code) {
		if (StringUtils.isAnyEmpty(tel, code)) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}
		UserInfo userInfo = userInfoService.getUserInfoByUsername(tel);
		if (userInfo == null) {
			logger.info("该手机号尚未注册, tel:" + tel);
			throw new BizException(RespCode.U_TEL_NOT_REGED);
		}

		if (this.authcodeService.verifyAuthCode(tel, code)) {
			HttpBaseDto dto = new HttpBaseDto();
			return dto;
		} else {
			// 验证码错误
			logger.info("验证码验证失败, tel:" + tel + ",code:" + code);
			throw new BizException(RespCode.U_AUTHCODE_NOTEXIST);
		}
	}

	@ResponseBody
	@RequestMapping(value = "/updatemyself", method = RequestMethod.POST)
	public HttpBaseDto saveUserInfo(@RequestParam String token,
			@RequestParam String nickname,
			@RequestParam(value = "head", required = false) String head) {
		logger.info("修改个人信息token=" + token + "昵称=" + nickname + "头像=" + head);
		if (StringUtils.isAnyEmpty(nickname)) {
			throw new BizException(RespCode.NOTEXIST_PARAM);
		}
		Long user_id = checkTokenAndUser(token);
		if (!StringUtils.isEmpty(head)) {
			if (this.userInfoService.updateUserInfoHeadAndNickName(user_id,
					nickname, head)) {
				HttpBaseDto dto = new HttpBaseDto();
				return dto;
			} else {
				logger.info("更新用户数据，发生数据库失败");
				throw new BizException(RespCode.SYS_ERR);
			}
		} else {

			if (this.userInfoService.updateUserInfoHeadAndNickName(user_id,
					nickname)) {
				HttpBaseDto dto = new HttpBaseDto();
				return dto;
			} else {
				logger.info("更新用户数据，发生数据库失败");
				throw new BizException(RespCode.SYS_ERR);
			}

		}

	}

	// 版本升级
	@ResponseBody
	@RequestMapping(value = "/version/{token}", method = RequestMethod.GET)
	public HttpBaseDto version(@PathVariable String token) {
		Long user_id = checkTokenAndUser(token);
		UserInfo userInfo = userInfoService.getUserInfoById(user_id);
		if (userInfo == null) {
			logger.info("askDevice error.no login.token:" + token);
			throw new BizException(RespCode.U_NOT_EXIST);
		}
		VersionInfo vinfo = userInfoService.getVersionInfo();

		Map<String, Object> map = new HashMap<String, Object>();
		if (vinfo != null) {
			map.put("app_download", vinfo.getDownload_path() + "");
			map.put("app_version", vinfo.getVersion());
			map.put("createtime", vinfo.getCreatetime());
			map.put("description", vinfo.getDescription() + "");
		} else {
			map.put("app_download", "");
			map.put("app_version", "");
			map.put("createtime", "");
			map.put("description", "");
		}

		HttpBaseDto dto = new HttpBaseDto();
		dto.setData(map);
		return dto;
	}

	@ResponseBody
	@RequestMapping(value = "/noticeset", method = RequestMethod.POST)
	public HttpBaseDto noticeset(@RequestParam String token,
			@RequestParam Integer memberunlockswitch,
			@RequestParam Integer temporaryunlockswitch,
			@RequestParam Integer abnormalunlockswitch,
			@RequestParam Integer appupdateswitch) {
		logger.info("通知设置接口=");
		Long user_id = checkTokenAndUser(token);
		userInfoService.insertNoticeSet(user_id, memberunlockswitch,
				temporaryunlockswitch, abnormalunlockswitch, appupdateswitch);

		HttpBaseDto dto = new HttpBaseDto();
		return dto;
	}

	@ResponseBody
	@RequestMapping(value = "/noticesett/{token}/{memberunlockswitch}/{temporaryunlockswitch}/{abnormalunlockswitch}/{appupdateswitch}", method = RequestMethod.GET)
	public HttpBaseDto noticesett(@PathVariable String token,
			@PathVariable Integer memberunlockswitch,
			@PathVariable Integer temporaryunlockswitch,
			@PathVariable Integer abnormalunlockswitch,
			@PathVariable Integer appupdateswitch) {

		logger.info("通知设置接口=");
		Long user_id = checkTokenAndUser(token);
		userInfoService.insertNoticeSet(user_id, memberunlockswitch,
				temporaryunlockswitch, abnormalunlockswitch, appupdateswitch);

		HttpBaseDto dto = new HttpBaseDto();
		return dto;

	}

	@ResponseBody
	@RequestMapping(value = "/noticeget", method = RequestMethod.POST)
	public HttpBaseDto noticeget(@RequestParam String token) {
		logger.info("获取设置设置参数=");
		Long user_id = checkTokenAndUser(token);
		NoticeInfo vinfo = userInfoService.getNoticeSet(user_id);
		Map<String, Object> map = new HashMap<String, Object>();
		if (vinfo != null) {
			map.put("memberunlockswitch", vinfo.getMemberunlockswitch());
			map.put("temporaryunlockswitch", vinfo.getTemporaryunlockswitch());
			map.put("abnormalunlockswitch", vinfo.getAbnormalunlockswitch());
			map.put("appupdateswitch", vinfo.getAppupdateswitch());
		} else {
			map.put("memberunlockswitch", 1);
			map.put("temporaryunlockswitch", 1);
			map.put("abnormalunlockswitch", 1);
			map.put("appupdateswitch", 1);
		}

		HttpBaseDto dto = new HttpBaseDto();
		dto.setData(map);
		return dto;
	}

	// 版本升级
	@ResponseBody
	@RequestMapping(value = "/version/watchapp", method = RequestMethod.GET)
	public HttpBaseDto versionWatch() {

		WatchAppVersionInfo vinfo = userInfoService.getWatchAppVersionInfo();

		Map<String, Object> map = new HashMap<String, Object>();
		if (vinfo != null) {
			map.put("app_download", vinfo.getDownload_path() + "");
			map.put("app_version", vinfo.getVersion());
			map.put("createtime", vinfo.getCreatetime());
			map.put("description", vinfo.getDescription() + "");
		} else {
			map.put("app_download", "");
			map.put("app_version", "");
			map.put("createtime", "");
			map.put("description", "");
		}

		HttpBaseDto dto = new HttpBaseDto();
		dto.setData(map);
		return dto;
	}
	
	
	// 版本升级
		@ResponseBody
		@RequestMapping(value = "/version/itfy/watchapp", method = RequestMethod.GET)
		public HttpBaseDto versionItfyWatch() {

			WatchAppVersionInfo vinfo = userInfoService.getItfyVersionInfo();

			Map<String, Object> map = new HashMap<String, Object>();
			if (vinfo != null) {
				map.put("app_download", vinfo.getDownload_path() + "");
				map.put("app_version", vinfo.getVersion());
				map.put("createtime", vinfo.getCreatetime());
				map.put("description", vinfo.getDescription() + "");
			} else {
				map.put("app_download", "");
				map.put("app_version", "");
				map.put("createtime", "");
				map.put("description", "");
			}

			HttpBaseDto dto = new HttpBaseDto();
			dto.setData(map);
			return dto;
		}

}
