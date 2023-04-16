package com.lianyi.app.api;

import rxhttp.wrapper.annotation.DefaultDomain;
import rxhttp.wrapper.annotation.Domain;

/**
 * @ClassName: ApiService
 * @Description: API
 * @Author: Lee
 * @CreateDate: 2020/6/28 14:24
 */
public class ApiService {
    //默认接口地址
    @DefaultDomain() //设置为默认域名
    public static String API_BASE = "https://zjjy.zbglxt.com/emsschool";

    // Iportal3 节点地址获取
    @Domain(name = "Iportal")
    public static String API_IPORTAL = "https://oss.zbglxt.com/";
    public static String API_ADDRESS = "oss/api/app/login-address";

    //登录地址
    @Domain(name = "Login")
    public static String API_LOGIN = "https://ys5.lianyitech.com.cn/iportal3/auth/password/access/token";

    public static String API_NODE = "https://oss.zbglxt.com/oss/api/app/deploy";




    //开启新清查任务
    public static final String API_ASSET_TASK = "/eems/api/new/asset/asset-task";
    //完成并入库
    public static final String API_FINISH_TASK = "/eems/api/new/asset/asset-task/%s/finish";
    //获取所有科目
    public static final String API_ALL_SUBJECT = "/eems/api/baseInfo/all-subject";
    //获取资产对应科目
    public static final String API_ASSET_SUBJECT = "/eems/api/baseInfo/asset-subject";

    //获取科目下的房间
    public static final String API_CATEGORY = "/eems/api/baseInfo/category";
    //房间列表
    public static final String API_ROOM_LIST = "/eems/api/building/list";
    //获取房间列表
    public static final String API_BUILDING_LIST = "/eems/api/building/buildings ";

    //房间下的柜子列表
    public static final String API_CABINET_LIST = "/eems/api/cabinet/find";
    //物品列表
    public static final String API_GOODS_LIST = "/eems/api/asset/asset-detail/list";
    //物品复制
    public static final String API_GOODS_COPY = "/eems/api/asset/asset-detail/copy";
    //物品删除
    public static final String API_GOODS_DELETE = "/eems/api/asset/asset-detail/delete";
    //获取柜子列表
    public static final String API_GET_CABINET_LIST = "/eems/api/cabinet/asset/find";
    //添加柜子
    public static final String API_CREATE_CABINET = "/eems/api/cabinet/insert";
    //获取柜子编码
    public static final String API_CABINET_CODE = "/eems/api/cabinet/queryCode";
    //物品详情
    public static final String API_GOODS_DETAILS = "/eems/api/asset/asset-detail/get";
    //物品列表保存
    public static final String API_GOODS_LIST_SAVE = "/eems/api/asset/asset-detail/batch-save";
    //选入物品列表 列表类型（1 查询无房间物品 2 查询已被选入房间物品）  queryStr 查询关键字
    public static final String API_GOODS_TYPES = "/eems/api/asset/asset-detail/select-in-list";
    //选入物品
    public static final String API_SELECT_GOODS = "/eems/api/asset/asset-detail/select-in";
    //物品数据新增
    public static final String API_INSERT_GOODS = "/eems/api/asset/asset-detail/insert";
    //物品数据保存
    public static final String API_SAVE_GOODS = "/eems/api/asset/asset-detail/save";
    //资产属性
    public static final String API_SYS_DICT = "/api/sys/dict/list";
    //根据科目获取用途
    public static final String API_USAGE = "/eems/api/baseInfo/usage";
    //输入名称获取标准目录
    public static final String API_NAME_CATALOGUE = "/eems/api/baseInfo/name-catalogue";
    //输入编号获取标准目录
    public static final String API_CODE_CATALOGUE = "/eems/api/baseInfo/code-catalogue";
    //标签打印
    public static final String API_LABEL_PRINT = "/eems/api/asset/asset-detail/label-print";
    //获取rfid盘点任务
    public static final String API_RFID_TASK = "/eems/api/rfid/task";
    //rfid用房信息列表
    public static final String API_RFID_ROOM_LIST = "/eems/api/building/rfid/list";
    //完成RFID盘点
    public static final String API_RFID_INVENTORY_FINISH = "/eems/api/rfid/task/%s/finish";
    //rfid用房间物品列表
    public static final String API_RFID_GOODS_LIST = "/eems/api/rfid/asset/list";
    //rfid用房间物品复制
    public static final String API_RFID_GOODS_COPY = "/eems/api/rfid/asset/copy?id=%s";
    //rfid用房间物品删除
    public static final String API_RFID_GOODS_DELETE = "/eems/api/rfid/asset/delete?id=%s";
    //rfid用房间物品详情
    public static final String API_RFID_GOODS_DETAILS = "/eems/api/rfid/asset?id=%s";
    //rfid用房间物品保存
    public static final String API_RFID_GOODS_SAVE = "/eems/api/rfid/asset/save";
    //rfid用选入物品查询
    public static final String API_RFID_SELECT_GOODS_LIST = "/eems/api/rfid/asset/select-in-list";
    //rfid用选入物品
    public static final String API_RFID_SELECT_GOODS = "/eems/api/rfid/asset/select-in";
    //rfid用扫一扫
    public static final String API_RFID_QRCODE = "/eems/api/rfid/asset/label";
    //rfid标签匹配
    public static final String API_RFID_MATCH = "/eems/api/rfid/asset/match";
}
