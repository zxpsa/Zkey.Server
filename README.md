[TOC]
# 狼人伽Api文档

> 版本：1.1

# 主要约定和说明

## 历史记录
| 日期         | 作者   | 内容                                       |
| ---------- | ---- | ---------------------------------------- |
| 2017-06-13 | 裴胜   | 新增接口文档                              |

## 说明

- 带删除线的接口为废弃接口，但仍在使用。不再使用的接口会直接从文档删除

## 接口公共属性说明

### 返回格式

| **字段** | **数据类型** | **备注**                                   |
| ------ | -------- | ---------------------------------------- |
| status | Integer  | 状态码：-1-系统错误 0-成功 1-参数错误 2-业务错误 3-权限错误 <br/>特殊跳转：<br/> 20001-需实名认证引导<br/> 20002-需视频认证引导<br/> 20003-需商家入驻引导<br/> 20004-需设置交易密码<br/> 20005-需设置融宝结算卡<br/> 20006-需设置易宝结算卡 |
| msg    | String   | 状态对应消息                                   |
| data   | Object   | 返回数据                                     |

格式一：无返回值

```json
{"status":0, "msg":"操作成功", "data":null}
```

格式二：返回对象

```json
{"status":0, "msg":"成功", "data":{"orderNo":"HZ16110102111166", "totalAmount":100.00},response:null}
```

格式三：返回列表

```json
{"status":0, "msg":"成功", "data":[

    {"id":1, "name":"商品1", "desc":"商品描述1"},

    {"id":2, "name":"商品2", "desc":"商品描述2"}

],response:null}
```

格式四：返回分页列表

```json
{"status":0, "msg":"成功","data":{
    "pageNo":1,
    "pageSize":20,
    "totalCount":0,
    "totalPage":0,
    "extraData":null,
    "data":[{
        "id":379681,
        "productId":8801,
        "name":"沣瑞祥翡翠",
        "pictures":[
            {"pictureId":154223,"pictureUrl":"GetPicture?PictureId=154223"},
            {"pictureId":154223,"pictureUrl":"GetPicture?PictureId=154223"}
        ]
    },{
        "id":379682,
        "productId":8802,
        "name":"沣瑞祥翡翠",
        "pictures":[
            {"PictureId":154223,"pictureUrl":"GetPicture?PictureId=154223"},
            {"PictureId":154223,"pictureUrl":"GetPicture?PictureId=154223"}
        ]
    }]
}}
```

### 数据类型

| **类型**   | **备注**                                |
| -------- | ------------------------------------- |
| String   | 字符串                                   |
| Long     | 长整型，注：所有long型数据均会以String的格式传递         |
| Integer  | 整型                                    |
| Double   | 双精度浮点型                                |
| Decimal  | 高精度小数表示                               |
| List     | 列表                                    |
| T        | 范型，KV结构数据                             |
| DateTime | 时间，默认为yyyy-MM-dd HH:mm:ss格式 特殊会备注对应格式 |

### <a id="webview_statute">WebView标准约定</a>

- 当url中出现``_zktg=_blank``时停止对当前Url加载并打开外部游览器打开该Url (3.1.0)
- 当url中出现``_zktg=_close_blank``时停止对当前Url加载并打开外部游览器打开该Url,并关闭当前webview (3.1.0)
- 当url中出现``_zktg=_self``时在当前webView中加载该Url (3.1.0)
- 默认内部打开 (3.1.0)
- 当是扫描进入的时候默认外部打开 (3.1.0)
- 标签规定行为覆盖默认行为



### 其它约定

- 发送的时间格式为yyyy-MM-dd HH:mm:ss
- 返回超过【long】型数字时请返回字符串（因js当前不支持超过int型的数字长度，过长会被自动截断补充0）
- 当请求参数集为空时请使用默认参数

## 规则要求

- 无
## 参考文档

- 无

## 项目结构

```sh
  xsf-hyapp-plugins/ #插件库
  	|-- trunk/ # 线上环境代码
  	| |-- TestProject/ # 插件开发测试项目
  	| | |-- CordovaHyApp/ # 插件开发测试项目
  	|-- tags/
  	|-- branches/ # 当前开发版本
  	|-- README.md/ # 说明文档
  	
  xsf-hyapp/ #标准蓝本项目
  	|-- trunk/ # 线上环境代码
  	| |-- CordovaHyApp/ # 标准蓝本项目
  	|-- tags/
  	|-- branches/ # 当前开发版本
```

# 基础插件

### 说明：

- 为App提供各种基础软件或者硬件功能服务

## 分享服务API

>  <a id="HYAShared">HYAShared</a>
>

### 发起分享

> <a id="HYAShared.excute">HYAShared.excute(type,title,imgURL,content,clickURL)</a>
>
> 作者：裴胜
>
> [3.0.5]
>
> 时间：2017-02-27

**请求数据：**

| 字段       | 数据类型    | 可空   | 备注                                       |
| -------- | ------- | ---- | ---------------------------------------- |
| type     | Integer | 否    | 分享类型 1.QQ 2.WX 3.WXCircle 4.SMS 5.CopyLink 6.QR[3.0.5暂时不做] 7.QQSpace |
| title    | String  | 否    | 分享标题                                     |
| imgURL   | String  | 否    | 分享图片地址                                   |
| content  | String  | 是    | 分享内容                                     |
| clickURL | String  | 是    | 点击文本块后访问地址                               |

**返回数据：**

- 无




## 视频服务API

>  <a id="HYAVideo">HYAVideo</a>
>

### 拍摄视频

> <a id="HYAVideo.shoot">HYAVideo.shoot(quality,isSaveLocal,width,height,frameStyle,positionX,positionY,time)</a>
>
> 作者：裴胜
>
> 时间：2017-02-27

**请求数据：**

| 字段          | 数据类型    | 可空   | 备注                   |
| ----------- | ------- | ---- | -------------------- |
| quality     | Double  | 否    | 推荐压缩系数               |
| isSaveLocal | Integer | 否    | 是否存储于本地 0.否 1.是      |
| width       | Double  | 是    | 像框宽度（默认为手机屏幕宽度）      |
| height      | Double  | 是    | 像框高度（默认为手机屏幕宽度）      |
| frameStyle  | Integer | 是    | 像框样式 1.矩形 2.圆形 3.三角形 |
| positionX   | Double  | 是    | 像框中心点X轴坐标（默认居中）      |
| positionY   | Double  | 是    | 像框中心点Y轴坐标（默认居中）      |
| time        | Double  | 否    | 拍摄时长 单位秒             |

**返回数据：**

| 字段   | 数据类型   | 可空   | 备注              |
| ---- | ------ | ---- | --------------- |
| path | String | 否    | 文件所在本地路径／数据唯一标示 |



## 图片服务API

>  <a id="HYAImge">HYAImge</a>
>

### 拍摄图片

> <a id="HYAImge.shoot">HYAImge.shoot(quality,isSaveLocal,width,height,frameStyle,positionX,positionY)</a>
>
> 作者：裴胜
>
> 时间：2017-02-27

**请求数据：**

| 字段          | 数据类型    | 可空   | 备注                   |
| ----------- | ------- | ---- | -------------------- |
| quality     | Double  | 否    | 推荐压缩系数               |
| isSaveLocal | Integer | 否    | 是否存储于本地 0.否 1.是      |
| width       | Double  | 是    | 像框宽度（默认为手机屏幕宽度）      |
| height      | Double  | 是    | 像框高度（默认为手机屏幕宽度）      |
| frameStyle  | Integer | 是    | 像框样式 1.矩形 2.圆形 3.三角形 |
| positionX   | Double  | 是    | 像框中心点X轴坐标（默认居中）      |
| positionY   | Double  | 是    | 像框中心点Y轴坐标（默认居中）      |

**返回数据：**

| 字段   | 数据类型   | 可空   | 备注              |
| ---- | ------ | ---- | --------------- |
| path | String | 否    | 文件所在本地路径／数据唯一标示 |



## 音频服务API

> <a id="HYAAudio">HYAAudio</a>
>

### 录音

> <a id="HYAAudio.record">HYAAudio.record(dealParam,success,error,serverURL)</a>
>
> 作者：裴胜
>
> 时间：2017-02-27

**请求数据：**

| 字段          | 数据类型    | 可空   | 备注              |
| ----------- | ------- | ---- | --------------- |
| quality     | Double  | 否    | 推荐压缩系数          |
| isSaveLocal | Integer | 否    | 是否存储于本地 0.否 1.是 |
| time        | Double  | 否    | 录制时长 单位秒        |

**返回数据：**

| 字段   | 数据类型   | 可空   | 备注              |
| ---- | ------ | ---- | --------------- |
| path | String | 否    | 文件所在本地路径／数据唯一标示 |



### 麦克风监听开始

> <a id="HYAAudio.micMonitorStart">HYAAudio.micMonitorStart</a>
>
> 作者：裴胜
>
> 时间：2017-05-05
>
> - 原生支持 3.1.0

**请求数据：**

| 字段     | 数据类型   | 可空   | 备注         |
| ------ | ------ | ---- | ---------- |
| sample | Double | 否    | 采样间隔时间 /ms |

**返回数据：**

- 无

**注:**

- App进入后台是自动停止监听
- App进入前台是自动重启监听(前提是开启监听的情况下进入后台)
- 返回上一个页面时自动停止监听

### 麦克风监听结束

> <a id="HYAAudio.micMonitorStop">HYAAudio.micMonitorStop</a>
>
> 作者：裴胜
>
> 时间：2017-05-05
>
> - 原生支持 3.1.0

**请求数据：**

- 无

**返回数据：**

- 无

###【通知】麦克风通知

> <a id="hyaudio-mic-monitor-data">hyaudio-mic-monitor-data</a>
>
> 作者：裴胜
>
> 时间：2017-05-05
>
> - 原生支持 3.1.0

**返回数据：**

| 字段        | 数据类型   | 可空   | 备注        |
| --------- | ------ | ---- | --------- |
| soundSize | Double | 否    | 声音大小 / 分贝 |


### 语音识别

> <a id="HYAAudio.recognize">HYAAudio.recognize</a>
>
> 作者：裴胜
>
> 时间：2017-05-05
>
> - 原生支持 (延迟)

**请求数据：**

| 字段       | 数据类型   | 可空   | 备注        |
| -------- | ------ | ---- | --------- |
| minValue | Double | 否    | 进行识别的最小音量 |
| maxValue | Double | 否    | 进行识别的最大音量 |
| time     | Double | 否    | 监听时长 单位秒  |

**返回数据：**

| 字段       | 数据类型             | 可空   | 备注       |
| -------- | ---------------- | ---- | -------- |
| contents | ``List<String>`` | 否    | 识别出的内容列表 |



### ~~加载音频文件~~

> <a id="HYAAudio.load">HYAAudio.load</a>
>
> - 【原生】3.0.5 3.0.7去除
>
> 作者：裴胜
>
> 时间：2017-04-15

**请求数据：**

| 字段   | 数据类型   | 可空   | 备注     |
| ---- | ------ | ---- | ------ |
| url  | String | 否    | 音频资源地址 |

**返回数据：**

- 无




### ~~【通知】加载音频文件完成~~

> <a id="hyaudio-load-complete">hyaudio-load-complete</a>
>
> - 【原生】3.0.5 3.0.7去除
>
> 作者：裴胜
>
> 时间：2017-04-15

**返回数据：**

| 字段   | 数据类型   | 可空   | 备注              |
| ---- | ------ | ---- | --------------- |
| path | String | 否    | 文件所在本地路径／数据唯一标示 |
| url  | String | 否    | 音频资源地址          |



### 播放音频文件

> <a id="HYAAudio.play">HYAAudio.play</a>
>
> - 【原生】3.0.5
>
> 作者：裴胜
>
> 时间：2017-04-15

**请求数据：**

| 字段             | 数据类型    | 可空   | 备注                                      |
| -------------- | ------- | ---- | --------------------------------------- |
| path           | String  | 否    | 文件所在本地路径／数据唯一标示  文件名                    |
| id             | String  | 否    | 本次播放编号                                  |
| playTime       | String  | 是    | 播放时长 / ms   默认为单次播放   为0 时单次播放 -1 为无限循环 |
| ~~isInNative~~ | Integer | 是    | 是否是App本地资源 0.false 1.true   (3.0.7去除)   |
| webUrl         | String  | 否    | 文件资源网络地址   (3.0.7新增优先使用)                |

**返回数据：**

| 字段                     | 数据类型    | 可空   | 备注                         |
| ---------------------- | ------- | ---- | -------------------------- |
| webResourceState       | Integer | 否    | 网络资源状态 0.下载失败 1.下载中 2.下载成功 |
| playingResourseIsInWeb | Integer | 否    | 播放中资源是否是网路资源 0.否 1.是       |

**注:**

- App进入后台是自动停止播放
- 返回上一个页面时自动停止播放

### 停止播放音频文件

> <a id="HYAAudio.stopPlay">HYAAudio.stopPlay</a>
>
> - 【原生】3.0.5
>
> 作者：裴胜
>
> 时间：2017-04-15

**请求数据：**

| 字段   | 数据类型   | 可空   | 备注     |
| ---- | ------ | ---- | ------ |
| id   | String | 否    | 本次播放编号 |

**返回数据：**

- 无



## 文件上传服务API

> <a id="HYAFile">HYAFile</a>

###  <a id="HYAFile.Type"> 文件类型</a>

> 作者：裴胜
>
> 时间：2017-02-27

**Props**

| 字段    | CODE | 备注   |
| ----- | ---- | ---- |
| FILE  | 1    |      |
| IMG   | 2    |      |
| AUDIO | 3    |      |
| VIDEO | 4    |      |
| BMP   | 5    |      |
| GIF   | 6    |      |
| JPEG  | 7    |      |
| SVG   | 8    |      |
| PNG   | 9    |      |
| WebP  | 10   |      |
| AVI   | 11   |      |
| MPEG4 | 12   |      |
| RM    | 13   |      |
| MOV   | 14   |      |
| RMVB  | 15   |      |
| WMV   | 16   |      |
| ASF   | 17   |      |
| MPEG  | 18   |      |
| XML   | 19   |      |
| Zip   | 20   |      |
| MP3   | 21   |      |
| WAV   | 22   |      |
| WMA   | 23   |      |
| AAC   | 24   |      |
| AC3   | 25   |      |
| AIFF  | 26   |      |
| FLAC  | 27   |      |
| M4A   | 28   |      |

### 上传

> <a id="HYAFile.upload">HYAFile.upload(serverURL,fileInfos,dealParam)</a>
>
> 作者：裴胜
>
> 时间：2017-02-27

**请求数据：**

| 字段        | 数据类型       | 可空   | 备注                |
| --------- | ---------- | ---- | ----------------- |
| serverURL | String     | 是    | 服务器上传接口地址         |
| fileInfos | `List<T1>` | 是    | 文件列表／null自行获取文件信息 |
| dealParam | T2         | 是    | 上传前处理参数           |

**T1 => fileInfo**

| 字段   | 数据类型   | 可空   | 备注              |
| ---- | ------ | ---- | --------------- |
| path | String | 是    | 文件所在本地路径／数据唯一标示 |

**T2 => dealParam**

| 字段            | 数据类型   | 可空   | 备注                  |
| ------------- | ------ | ---- | ------------------- |
| quality       | Double | 是    | 建议压缩质量（WebApp模式时无效） |
| maxSize       | Double | 是    | 最大上传大小／byte         |
| width         | Double | 是    | 宽度／px（图片时有效）        |
| height        | Double | 是    | 高度／px（图片时有效）        |
| rejectMaxSize | Double | 是    | 直接拒绝最大大小／byte       |
| rejectMinSize | Double | 是    | 直接拒绝最小大小／byte       |

**返回数据：List**

| 字段           | 数据类型   | 可空   | 备注                  |
| ------------ | ------ | ---- | ------------------- |
| path         | String | 是    | 文件所在本地路径／数据唯一标示     |
| resourcesId  | String | 否    | 服务器返回资源Id           |
| resourcesUrl | String | 否    | 服务器返回资源地址（带http://） |

### 选择文件

> <a id="HYAFile.select">HYAFile.select(dealParam,success,error,allowSelectCount,serverURL,allowTypes)</a>
>
> 作者：裴胜
>
> 时间：2017-02-27

**请求数据：**

| 字段               | 数据类型            | 可空   | 备注                                |
| ---------------- | --------------- | ---- | --------------------------------- |
| allowSelectCount | Integer         | 否    | 允许做多选中数量/1单选                      |
| allowTypes       | `List<Integer>` | 是    | 允许的<a id="HYAFile.Type"> 文件类型</a> |

**返回数据：List**

| 字段           | 数据类型    | 可空   | 备注                                     |
| ------------ | ------- | ---- | -------------------------------------- |
| path         | String  | 否    | 文件所在本地路径 / 标示                          |
| resourcesId  | String  | 是    | 服务器返回资源Id                              |
| resourcesUrl | String  | 是    | 服务器返回资源地址（带http://）                    |
| type         | Integer | 是    | 文件格式    <a id="HYAFile.Type"> 文件类型</a> |



# 支付服务API

> 待设计



# 地图／定位服务API

> 待设计



# OCR识别服务API

> 待设计



# 日历／时间选择器服务API

> 待设计

# 扫描二维码／条码服务API

> <a id="HYAScanCode">HYAScanCode</a>

### 扫描二维码

> <a id="HYAScanCode.scanQR">HYAScanCode.scanQR()</a>
>
> 作者：裴胜
>
> 时间：2017-02-27

**请求数据：**

| 字段   | 数据类型   | 可空   | 备注              |
| ---- | ------ | ---- | --------------- |
| path | String | 是    | 文件所在本地路径／数据唯一标示 |

**返回数据：**

| 字段      | 数据类型   | 可空   | 备注      |
| ------- | ------ | ---- | ------- |
| content | String | 否    | 图形码包含内容 |

### 扫描条形码

> <a id="HYAScanCode.scanBarCode">HYAScanCode.scanBarCode()</a>
>
> 作者：裴胜
>
> 时间：2017-02-27

**请求数据：**

| 字段   | 数据类型   | 可空   | 备注              |
| ---- | ------ | ---- | --------------- |
| path | String | 是    | 文件所在本地路径／数据唯一标示 |

**返回数据：**

| 字段      | 数据类型   | 可空   | 备注      |
| ------- | ------ | ---- | ------- |
| content | String | 否    | 图形码包含内容 |



# 通知服务API

>  待设计

# 基础框架服务API

> <a id="HYACore">HYACore</a>

### 网路请求



# 二维码服务API

> <a id="HYAQR">HYAQR</a>
>
> - 二维码相关服务

> <a id="HYAQR.show">HYAQR.show({请求数据})</a>
>
> 显示二维码
>
> 作者：裴胜
>
> 时间：2017-02-27

**请求数据：**

| 字段           | 数据类型    | 可空   | 备注                                       |
| ------------ | ------- | ---- | ---------------------------------------- |
| type         | Integer | 否    | 二维码类型 1.个人收款二维码 2.商户收款二维码 3.店铺首页/兴店 二维码 9.通用支付二维码 |
| titleImgUrl  | String  | 是    | 头像图片URL地址                                |
| centerImgUrl | String  | 是    | 二维码中心图片URL地址                             |
| title        | String  | 是    | 显示标题内容                                   |
| content      | String  | 否    | 二维码内容                                    |
| prompt0      | String  | 是    | 提示内容0：标题第二行类标签提示                         |
| prompt1      | String  | 是    | 提示内容1：二维码下方提示 / 保存提示                     |
| prompt2      | String  | 是    | 提示内容2：二维码下方按钮提示                          |

**返回数据：**

| 字段   | 数据类型   | 可空   | 备注      |
| ---- | ------ | ---- | ------- |
| code | String | 否    | 二维码唯一编号 |



> <a id="HYAQR.hide">HYAQR.hide()</a>
>
> 隐藏二维码
>
> 作者：裴胜
>
> 时间：2017-02-27

  **请求数据：**

| 字段   | 数据类型   | 可空   | 备注      |
| ---- | ------ | ---- | ------- |
| code | String | 否    | 二维码唯一编号 |

**返回数据：**

- 无



###【通知】二维码关闭时通知

> <a id="hyaqr-close">hyaqr-close</a>
>
> 作者：裴胜
>
> 时间：2017-02-27

**返回数据：**

| 字段   | 数据类型   | 可空   | 备注      |
| ---- | ------ | ---- | ------- |
| code | String | 否    | 二维码唯一编号 |



### 【通知】二维码保存完成时通知

> <a id="hyaqr-save-complete">hyaqr-save-complete</a>
>
> 作者：裴胜
>
> 时间：2017-02-27

**返回数据：**

| 字段         | 数据类型    | 可空   | 备注            |
| ---------- | ------- | ---- | ------------- |
| saveResult | Integer | 否    | 1.保存成功 2.保存失败 |



# 向WebView中注入代码

> <a id="HYAInjectWebView">HYAInjectWebView</a>
>
> - 当检测到url中存在 __zkver时停止注入
> - 原生版本3.0.5

### 开始注入
> <a id="HYAInjectWebView.start">HYAInjectWebView.start</a>
>
> 开始注入
>
> 作者：裴胜
>
> 时间：2017-04-12

**请求数据：**

| 字段             | 数据类型   | 可空   | 备注                            |
| -------------- | ------ | ---- | ----------------------------- |
| contentCode    | String | 否    | 注入内容的编号                       |
| ubBankNo       | String | 是    | 银行卡号                          |
| userRealName   | String | 是    | 真实姓名                          |
| idCardNo       | String | 是    | 身份证号码                         |
| userAccount    | String | 是    | 用户手机账号                        |
| errPageUrl     | String | 是    | 错误页地址                         |
| termOfValidity | String | 是    | 信用卡有效期                        |
| cvv2           | String | 是    | 信用卡背面最后三位数                    |
| backUrl        | String | 是    | 在注入过程中点击返回时跳转的Url地址 需支持跳转原生页面 |

**返回数据：**

- 无




# 获取设置API

> <a id="HYAMySetting">HYAMySetting</a>
>
> - 获取音效设置
> - 原生版本3.0.5

> <a id="HYAMySetting.get">HYAMySetting.get</a>
>
> 获取设置
>
> 作者：裴胜
>
> 时间：2017-02-27

**请求数据：**
- 无


**返回数据：**


| 字段              | 数据类型    | 可空   | 备注            |
| --------------- | ------- | ---- | ------------- |
| countingAudio   | Integer | 否    | 点钞音效 0关闭  1开启 |
| collectionAudio | Integer | 否    | 收款音效 0关闭  1开启 |
| appVersion      | String  | 否    | 当前App版本号      |



# 摇一摇监测API

> <a id="HYAShakeMonitor">HYAShakeMonitor</a>
>
> - 摇一摇监测
> - 原生版本3.0.5

### 开始监监听

> <a id="HYAShakeMonitor.start">HYAShakeMonitor.start</a>
>
> 作者：裴胜
>
> 时间：2017-02-27

**请求数据：**

| 字段           | 数据类型    | 可空   | 备注                                      |
| ------------ | ------- | ---- | --------------------------------------- |
| hasVibration | Integer | 是    | 是否伴随振动 0.否 1.是 默认为1   (3.1.0) 待新增振动控制接口 |

**返回数据：**

- 无

**注:**

- App进入后台是自动停止监听
- App进入前台是自动重启监听(前提是开启监听的情况下进入后台)
- 返回上一个页面时自动停止监听

### 停止监听

> <a id="HYAShakeMonitor.stop">HYAShakeMonitor.stop</a>
>
> 作者：裴胜
>
> 时间：2017-02-27

**请求数据：**

- 无

**返回数据：**

- 无

> 【通知】摇一摇时通知
>
> <a id="HYAShakeMonitorHasShake">HYAShakeMonitorHasShake</a>
>
> 作者：裴胜
>
> 时间：2017-02-27

**返回数据：**

- 无


# 公共控制API

> <a id="HYACommonCtrl">HYACommonCtrl</a>
>
> - 公共控制接口



### 初始设置

> <a id="HYACommonCtrl.init">HYACommonCtrl.init</a>
>
> - 原生版本3.0.5
>
>  作者：裴胜
>
>  时间：2017-02-27

**请求数据：**

| 字段           | 数据类型    | 可空   | 备注             |
| ------------ | ------- | ---- | -------------- |
| title        | String  | 否    | 页面标题           |
| stopInjectJS | Integer | 是    | 停止js注入 0.否 1.是 |

### 设置导航栏

><a id="HYACommonCtrl.setNav">HYACommonCtrl.setNav</a>
>
> 作者：裴胜
>
> 时间：2017-02-27

**请求数据：**

| 字段    | 数据类型   | 可空   | 备注   |
| ----- | ------ | ---- | ---- |
| title | String | 否    | 页面标题 |



# App基础通知

### 【通知】App进入后台

> - 原生版本3.1.0
>
> <a id="hya-notice-to-background">hya-notice-to-background</a>

**返回数据：**

-  无

### 【通知】App进入前台

> - 原生版本3.1.0
>
> <a id="hya-notice-to-foreground">hya-notice-to-foreground</a>

**返回数据：**

-  无

## App页面标识

- 通过标准schema URL方式打开App具体页面
- schema为app唯一标志
- 参数值均做encodeURIComponent编码处理
- 请求参数挂接到url上如 ?key=value&key1=value1
- 返回数据挂接到回调url上如 ?key=value&key1=value1
- 当检测到``//p/``时使用新解析方式
- 当检测到http://或https://时使用WebView打开,遵行[WebView规则相关约定](#webview_statute)

### 登录

><a id="p/login">``schema://p/login``</a>
>
>- 原生版本3.1.0

**请求数据：**

| 字段          | 数据类型   | 可空   | 备注                        |
| ----------- | ------ | ---- | ------------------------- |
| callbackUrl | String | 是    | 登录成功后跳转页面 (当为0时返回前一个页面 ) |

### 认证

><a id="p/certified">``schema://p/certified``</a>
>
> 作者：裴胜
>
> 时间：2017-05-15
>
>- 原生版本3.1.0

**请求数据：**

| 字段               | 数据类型    | 可空   | 备注                                     |
| ---------------- | ------- | ---- | -------------------------------------- |
| type             | Integer | 否    | 认证类型 0.实名认证 1.商家入住 2.视频认证 3.实体店认证     |
| refId            | String  | 是    | 关联Id 实体店:店铺Id                          |

**返回数据：**

- 无

## 业务支持Api
### 上传充值银行卡信息

><a id="HYABss.ybUploadOtherBankCard">HYABss.ybUploadOtherBankCard</a>
>
>- 原生版本3.1.0

**请求数据：**

| 字段          | 数据类型   | 可空   | 备注                        |
| ----------- | ------ | ---- | ------------------------- |
| bankCardNo | String | 是    | 银行卡号 |
| bankName | String | 是    | 银行名称 |
| bankCardType | Integer | 是    | 银行卡类型：1-储蓄卡 2-信用卡 |
| bankCardValid | String | 是    | 有效期 |
| bankCardCvv2 | String | 是    | 验证码 |
| remark       | String | 是    | 备注 |

**返回数据：**

- 无

### 获取充值银行卡列表

><a id="HYABss.ybGetOtherBankCard">HYABss.ybGetOtherBankCard</a>
>
>- 原生版本3.1.0

**请求数据：**

- 无

**返回数据：List**

| 字段          | 数据类型   | 可空   | 备注                        |
| ----------- | ------ | ---- | ------------------------- |
| bankCardNo | String | 是    | 银行卡号 |
| bankName | String | 是    | 银行名称 |
| bankCardType | Integer | 是    | 银行卡类型：1-储蓄卡 2-信用卡 |
| bankCardValid | String | 是    | 有效期 |
| bankCardCvv2 | String | 是    | 验证码 |

