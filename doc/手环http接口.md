### 用户

### 获取短信验证码
GET /user/getAuthCode/{tel}

url参数

    tel: 手机号码

返回示例

    {
      "code":"0",
      "message":"操作成功！"
    } 


***

### 注册

POST /user/register

参数

    tel: 手机号码
    code: 短信验证码

返回示例

    {
        "code":0,
        "message":"成功",
        "data":{
            "id": 123,
            "username":"13800000000",
            "imei":"",
            "token":"1001r2"
        }
    }


***

### 凭短信验证码登录
POST /user/login

参数

    tel: 手机号码
    code: 短信验证码

返回示例 

    {
        "code":0,
        "message":"成功",
        "data":{
            "id": 123,
            "username":"13800000000",
            "imei":"",
            "token":"1001r2"
        }
    }


***

### 我的设备
GET /user/device/${token}

参数

    token: 用户token

返回示例 

    {
        "code":0,
        "message":"成功",
        "data":{
            "imei":"sdf23",
            "dv":"", //设备固件版本号
            "sd":"",  //软件版本号
            "bindingtime":1500123042125    //毫秒
        }
    }


***

### 绑定设备
POST /user/device/bind

参数

    token: 用户token
    imei: 设备imei

返回示例 

    {"code":0, "message":"成功"}


***

### 解除绑定设备
POST /user/device/unbind

参数

    token: 用户token
    imei: 设备imei

返回示例 

    {"code":0, "message":"成功"}


***



### 血压

### 上传血压信息(930后废弃)：
POST：/heartPressure/upload

参数：
	
	token:token 必填
	maxHeartPressure：高压 必填
	minHeartPressure：低压 必填

返回： 
	
	成功 {"code":0, "message":"成功"}
  	失败 {"code":1, "message":"系统异常"}

***

### 查询最近血压信息：
GET:/heartPressure/search/latest/${token}

参数:
	
	token:token 必填

返回：
 	
 	成功
 	{
 	    "code":0,
 	    "message":"成功",
 	    "data":{
 	        "maxHeartPressure":"120",
 	        "minHeartPressure":"100",
 	        "status": {code: 0, msg: "正常"},     // 0: 正常，1: 偏高, 2: 偏低
 	        "timestamp":1501123728
        }
    }
 	失败 {"code":1, "message":"系统异常"}
 
***



### 脉搏

### 上传脉搏信息(930后废弃)：
POST：/heartRate/upload

参数：
	
	token:token 必填
	heartRate：脉搏 必填

返回： 
	
	成功 {"code":0, "message":"成功"}
    失败 {"code":1, "message":"缺少某字段"}

***
  
### 查询最近脉搏信息：
GET:/heartRate/search/latest/${token}

参数:
	
	token:token 必填

返回：
    
    成功
    {
        "code":0,
        "message":"成功",
        "data":{
            "heartRate":"120",
            "status": {code: 0, msg: "正常"},     // 0: 正常，1: 偏高, 2: 偏低
            "timestamp":1501123728
        }
    }
    失败 {"code":1, "message":"系统异常"}

*** 



### 位置

### 查询最近位置信息：
GET:/location/search/latest/${token}

参数:
	
	token:token 必填

返回：
    
    成功 {"code":0, "message":"成功","data":{"lat":"12.2424","lng":"110.2424","timestamp":1501123728}}
    失败 {"code":1, "message":"系统异常"}

***

### 请求服务器调用手环获取位置：
GET：/location/ask/location/{token}

参数：

    token:token 必填

返回：
    
    {"code":0, "message":"成功"}
 
***

### 查询手环返回的实时位置数据：
GET：/location/search/realtime/{token}

参数：
	
	token:token 必填

返回：

    成功 {"code":0, "message":"成功","data":{"lat":"12.2424","lng":"110.2424","timestamp":1501123728}}
    失败 {"code":1, "message":"系统异常"}

***

### 查询手环轨迹数据：
GET：/location/search/footprint/{token}

参数：
	
	token: token 必填
	type: 【可选】时间类型，1：过去1小时，2：过去一天，不填默认是1

返回：

    成功
    {
        "code":0,
        "message":"成功",
        "data":[        // 按时间升序
            {"lat":"12.2424","lng":"110.2424","timestamp":1501123728},
            {"lat":"12.2424","lng":"110.2424","timestamp":1501123728},
            {"lat":"12.2424","lng":"110.2424","timestamp":1501123728},
            {"lat":"12.2424","lng":"110.2424","timestamp":1501123728},
            {"lat":"12.2424","lng":"110.2424","timestamp":1501123728}
        ]
    }
    失败 {"code":1, "message":"系统异常"}

***



### 安全
### 查询电子围栏：
GET：/security/fence/{token}

参数：
	
	token: token 必填

返回：

    成功
    {
        "code":0,
        "message":"成功",
        "data": {
            "id": 123,
            "lat": "39.1231",
            "lng": "110.1231",
            "radius": 500,
            "timestamp":1501123728,
            "fencelog": [
                {
                    "id": 123,
                    "imei": "1r12r12r",     // 发生的设备号
                    "lat": "39.1231",       // 设置的电子围栏坐标
                    "lng": "110.1231",      // 设置的电子围栏坐标
                    "radius": 500,          // 设置的电子围栏半径
                    "lat1": "39.1231",      // 设备当前坐标
                    "lng1": "110.1231",     // 设备当前坐标
                    "status": 1,            // 1:电子围栏内，2:电子围栏外
                    "content": "设备离开电子围栏"", // 文字说明
                    "timestamp": 15011237284123
                },{
                    "id": 123,
                    "imei": "1r12r12r",     // 发生的设备号
                    "lat": "39.1231",       // 设置的电子围栏坐标
                    "lng": "110.1231",      // 设置的电子围栏坐标
                    "radius": 500,          // 设置的电子围栏半径
                    "lat1": "39.1231",      // 设备当前坐标
                    "lng1": "110.1231",     // 设备当前坐标
                    "status": 1,            // 1:电子围栏内，2:电子围栏外
                    "content": "设备离开电子围栏"", // 文字说明
                    "timestamp": 15011237284123
                },{
                    "id": 123,
                    "imei": "1r12r12r",     // 发生的设备号
                    "lat": "39.1231",       // 设置的电子围栏坐标
                    "lng": "110.1231",      // 设置的电子围栏坐标
                    "radius": 500,          // 设置的电子围栏半径
                    "lat1": "39.1231",      // 设备当前坐标
                    "lng1": "110.1231",     // 设备当前坐标
                    "status": 1,            // 1:电子围栏内，2:电子围栏外
                    "content": "设备离开电子围栏"", // 文字说明
                    "timestamp": 15011237284123
                }
            ]
        }
    }
    失败 {"code":1, "message":"系统异常"}

***


### 设置电子围栏：
POST：/security/fence

参数：
	
	token: token 必填
	lat: 中心点纬度
	lng: 中心点经度
	radius: 半径，单位 米

返回：

    成功
    {
        "code":0,
        "message":"成功"
    }
    失败 {"code":1, "message":"系统异常"}

***

### 修改电子围栏：
POST：/security/fence/update

参数：
	
	token: token 必填
	id: 电子围栏数据id
	lat: 中心点纬度
	lng: 中心点经度
	radius: 半径，单位 米

返回：

    成功
    {
        "code":0,
        "message":"成功"
    }
    失败 {"code":1, "message":"系统异常"}

***

### 删除电子围栏：
POST：/security/fence/delete

参数：
	
	token: token 必填
	id: 电子围栏数据id

返回：

    成功
    {
        "code":0,
        "message":"成功"
    }
    失败 {"code":1, "message":"系统异常"}

***

### 查询电子围栏离开记录(3条)：
GET：/security/fence/leavelog/head/{token}

参数：
	
	token: token 必填

返回：

    成功
    {
        "code":0,
        "message":"成功",
        "data": [{
            "id": 123,
            "imei": "1r12r12r",     // 发生的设备号
            "lat": "39.1231",       // 设置的电子围栏坐标
            "lng": "110.1231",      // 设置的电子围栏坐标
            "radius": 500,          // 设置的电子围栏半径
            "lat1": "39.1231",      // 设备当前坐标
            "lng1": "110.1231",     // 设备当前坐标
            "status": 1,            // 1:电子围栏内，2:电子围栏外
            "content": "设备离开电子围栏"", // 文字说明
            "timestamp": 15011237284123
        },{
            "id": 123,
            "imei": "1r12r12r",     // 发生的设备号
            "lat": "39.1231",       // 设置的电子围栏坐标
            "lng": "110.1231",      // 设置的电子围栏坐标
            "radius": 500,          // 设置的电子围栏半径
            "lat1": "39.1231",      // 设备当前坐标
            "lng1": "110.1231",     // 设备当前坐标
            "status": 1,            // 1:电子围栏内，2:电子围栏外
            "content": "设备离开电子围栏"", // 文字说明
            "timestamp": 15011237284123
        },{
            "id": 123,
            "imei": "1r12r12r",     // 发生的设备号
            "lat": "39.1231",       // 设置的电子围栏坐标
            "lng": "110.1231",      // 设置的电子围栏坐标
            "radius": 500,          // 设置的电子围栏半径
            "lat1": "39.1231",      // 设备当前坐标
            "lng1": "110.1231",     // 设备当前坐标
            "status": 1,            // 1:电子围栏内，2:电子围栏外
            "content": "设备离开电子围栏"", // 文字说明
            "timestamp": 15011237284123
        }]
    }
    失败 {"code":1, "message":"系统异常"}

***

### 增加白名单信息接口：
POST: /sos/whitelist

参数：

	token:token 必填 String
    phone：电话 String  前端要验证是不是手机号
	name：名字String

返回：

    {
    "code": 0,
    "message": "成功"
    }

***

### 删除白名单信息接口：
POST:/sos/whitelist/del

参数：

	token:token 必填 String
	id：信息id  int

返回：

	{"code":0, "message":"成功"}

***

### 查询白名单信息接口：
GET:/sos/whitelist/{token}

参数：

	token:token 必填 String

返回：

    {
        "code":0,
        "message":"成功",
        "data":[
            {"phone":"18575508000","name":"123","id":15},
            {"phone":"18575508000","name":"123","id":15},
            {"phone":"18575508000","name":"123","id":15},
            {"phone":"18575508000","name":"123","id":15},
            {"phone":"18575508000","name":"123","id":15},
            {"phone":"18575508000","name":"123","id":15},
            {"phone":"18575508000","name":"123","id":15},
            {"phone":"18575508000","name":"123","id":15},
            {"phone":"18575508000","name":"123","id":15}
        ]
    }

***


### 首页
### 查询最近信息：
GET:/status/search/latest/${token}

参数：
	
	token:token 必填

返回：
	 
	{
        "code": 0,
        "message": "成功",
        "data": {
            "heartPressure": {
                "maxHeartPressure": "120",
                "minHeartPressure": "100",
                "status": {code: 0, msg: "正常"},     // 0: 正常，1: 偏高, 2: 偏低
                "timestamp": 1501123728
            },
            "heartRate": {
                "heartRate": "120",
                "status": {code: 0, msg: "正常"},     // 0: 正常，1: 偏高, 2: 偏低
                "timestamp": 1501123728
            },
            "location": {
                "lat": "12.2424",
                "lng": "110.2424",
                "status": "正常",                 // 没用
                "timestamp": 1501123728
            }
        }
    }

***

### 插入最近信息(已废弃)：
POST:/status/upload

参数：
	
	token:token 必填
	maxHeartPressure：高压
	minHeartPressure：低压
	heartRate：心率

返回：
	
	{"code":0, "message":"成功"}