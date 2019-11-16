package com.hjkj.fuduoduo.okgo;

public class Api {
    // 服务器地址--和个人有关
    private static final String BASE_URL = "http://192.168.1.11:1001/user-service";
    /**
     * 登录发送密码
     */
    public static final String USER_DOLOGIN = BASE_URL + "/user/doLogin";
    /**
     * 获取验证码
     */
    public static final String USER_DOSEND = BASE_URL + "/user/doSend";
    /**
     * 用户激活/忘记密码
     */
    public static final String USER_DOACTIVATE = BASE_URL + "/user/doActivate";
    /**
     * 添加收货地址
     */
    public static final String FREIGHTADDRESS_DOSAVE = BASE_URL + "/freightAddress/doSave";
    /**
     * 查询收货地址
     */
    public static final String FREIGHTADDRESS_DOQUERY = BASE_URL + "/freightAddress/doQuery";
    /**
     * 用户删除收货地址
     */
    public static final String FREIGHTADDRESS_DODELETE = BASE_URL + "/freightAddress/doDelete";
    /**
     * 用户查询商品
     */
    public static final String USER_DOQUERYCOLLECTIONS = BASE_URL + "/user/doQueryCollections";
    /**
     * 用户删除收藏夹
     */
    public static final String USER_DODELETECOLLECTIONS = BASE_URL + "/user/doDeleteCollections";
    /**
     * 用户个人资料添加
     */
    public static final String USER_DOUPDATE = BASE_URL + "/user/doUpdate";
    /**
     * 个人足迹浏览
     */
    public static final String USER_DOQUERYFOOTPRINT = BASE_URL + "/user/doQueryFootprint";
    /**
     * 个人足迹删除
     */
    public static final String USER_DODELETEFOOTPRINT = BASE_URL + "/user/doDeleteFootprint";
    /**
     * 个人信息查询
     */
    public static final String USER_DOQUERY = BASE_URL + "/user/doQuery";
    /**
     * 转赠中心
     */
    public static final String USER_DOEXCHANGE = BASE_URL + "/user/doExchange";
    /**
     * 添加收藏夹
     */
    public static final String USER_DOCOLLECT = BASE_URL + "/user/doCollect";
    /**
     *  购物车新增
     */
    public static final String CART_DOSAVE = BASE_URL + "/cart/doSave";
    /**
     *  用户购物车查询
     */
    public static final String CART_DOQUERY = BASE_URL + "/cart/doQuery";

    /**
     *  用户购物车删除
     */
    public static final String CART_DODELETE = BASE_URL + "/cart/doDelete";

//************************************************    订单有关    **************************************************************

    // 服务器地址--和订单有关
    private static final String BASE_ORDERS = "http://192.168.1.11:1001/orders-service";
    /**
     * 查询交易明细
     */
    public static final String ORDERS_DOQUERYTRANSACTIONRECORD = BASE_ORDERS + "/orders/doQueryTransactionRecord";
    /**
     * 全部订单查询
     */
    public static final String ORDERS_DOQUERYORDERSDETAILS = BASE_ORDERS + "/orders/doQueryOrdersDetails";
    /**
     * 确认订单
     */
    public static final String ORDERS_DOCONFIRMORDER = BASE_ORDERS + "/orders/doConfirmOrder";
    /**
     * 购物车多规格确认订单
     */
    public static final String ORDERS_DOCONFIRMORDERS = BASE_ORDERS + "/orders/doConfirmOrders";
    /**
     * 单个商品提交订单
     */
    public static final String ORDERS_DOSUBMITORDER = BASE_ORDERS + "/orders/doSubmitOrder";
    /**
     * 多订单立即支付
     */
    public static final String ORDERS_DOPAY = BASE_ORDERS + "/orders/doPay";
    /**
     * 多个商品提交订单
     */
    public static final String ORDERS_DOSUBMITORDERS = BASE_ORDERS + "/orders/doSubmitOrders";
    /**
     * 个人售后全部订单查询
     */
    public static final String ORDERS_DOQUERYRETURNORDERS = BASE_ORDERS + "/orders/doQueryReturnOrders";
    /**
     * 待付款买家提醒发货
     */
    public static final String ORDERS_DOREMINDSEND = BASE_ORDERS + "/orders/doRemindSend";
    /**
     * 查询订单的物流信息
     */
    public static final String ORDERS_DOQUERYORDEREXPRESSES = BASE_ORDERS + "/orders/doQueryOrderExpresses";
    /**
     * 根据物流id查询物流
     */
    public static final String ORDERS_DOQUERYEXPRESS = BASE_ORDERS + "/orders/doQueryExpress";
    /**
     * 退/换货
     */
    public static final String ORDERS_DORETURNORDERS = BASE_ORDERS + "/orders/doReturnOrders";
    /**
     * 单 订单详情运费计算
     */
    public static final String ORDERS_DOCOUNTFREIGHTPRICE = BASE_ORDERS + "/orders/doCountFreightPrice";
    /**
     *  查询退货订单详情
     */
    public static final String ORDERS_DOQUERYRETURNORDERDETAILS = BASE_ORDERS + "/orders/doQueryReturnOrderDetails";
    /**
     *  修改退货申请
     */
    public static final String ORDERS_DOMODIFYRETURNORDER = BASE_ORDERS + "/orders/doModifyReturnOrder";
    /**
     *  撤销退货申请
     */
    public static final String ORDERS_DOCANCELRETURNORDER = BASE_ORDERS + "/orders/doCancelReturnOrder";
    /**
     *  协商历史查询
     */
    public static final String ORDERS_DOQUERYCONSULT = BASE_ORDERS + "/orders/doQueryConsult";
    /**
     *  待付款取消订单
     */
    public static final String ORDERS_DOCANCELORDER = BASE_ORDERS + "/orders/doCancelOrder";
    /**
     *  待付款取消订单
     */
    public static final String ORDERS_DOEVALUATE = BASE_ORDERS + "/orders/doEvaluate";
    /**
     *  查询物流公司
     */
    public static final String ORDERS_DOQUERYEXPRESSCOMPANY = BASE_ORDERS + "/orders/doQueryExpressCompany";

    //************************************************    商品有关    **************************************************************

    // 图片地址---和商品有关
    private static final String BASE_IMAGE_URL = "http://192.168.1.11:1001/commodity-service";

    /**
     * 图片接口
     */
    public static final String IMAGE_DOFINDIMAGES = BASE_IMAGE_URL + "/image/doFindImages";
    /**
     * 爆款热卖接口
     */
    public static final String COMMODITY_HOTSALE = BASE_IMAGE_URL + "/commodity/hotSale";
    /**
     * 首页推荐-猜你喜欢接口
     */
    public static final String COMMODITY_DOFINDMAYBEYOULIKE = BASE_IMAGE_URL + "/commodity/doFindMaybeYouLike";
    /**
     * 首页分类接口
     */
    public static final String CATEGORY_DOFINDCATEGORY = BASE_IMAGE_URL + "/category/doFindCategory";
    /**
     * 首页类目推荐接口
     */
    public static final String COMMODITY_DORECOMMENDCATEGORY = BASE_IMAGE_URL + "/commodity/doRecommendCategory";
    /**
     * 首页分类推荐接口
     */
    public static final String HOMEPAGESORT_DOFINDHOMEPAGESORTS = BASE_IMAGE_URL + "/homePageSort/doFindHomePageSorts";
    /**
     * 个人用户头像添加
     */
    public static final String IMAGE_DOUPLOADPORTRAIT = BASE_IMAGE_URL + "/image/doUploadPortrait";
    /**
     * 分类-顶级类目查询
     */
    public static final String CATEGORY_DOQUERYCATEGOR = BASE_IMAGE_URL + "/category/doQueryCategory";
    /**
     * 分类-顶级类目查询
     */
    public static final String CATEGORY_DOQUERYCATEGORYDETAILS = BASE_IMAGE_URL + "/category/doQueryCategoryDetails";
    /**
     * 根据类目/名称查询商品（销量）
     */
    public static final String COMMODITY_DOQUERYBYFICTITIOUSVOLUME = BASE_IMAGE_URL + "/commodity/doQueryByFictitiousVolume";
    /**
     * 根据类目/名称查询商品（按价格排序）
     */
    public static final String COMMODITY_DOQUERYBYPRICE = BASE_IMAGE_URL + "/commodity/doQueryByPrice";
    /**
     * 根据名称综合查询
     */
    public static final String COMMODITY_DOQUERYBYNAME = BASE_IMAGE_URL + "/commodity/doQueryByName";
    /**
     * 查询商品详情
     */
    public static final String COMMODITY_DOQUERYCOMMODITYDETAILS = BASE_IMAGE_URL + "/commodity/doQueryCommodityDetails";
    /**
     * 商品规格查询
     */
    public static final String COMMODITY_DOQUERYSPECIFICATIONS = BASE_IMAGE_URL + "/commodity/doQuerySpecifications";
    /**
     * 店铺商品详情查询（综合）
     */
    public static final String COMMODITY_DOQUERYSHOPDETAILS = BASE_IMAGE_URL + "/commodity/doQueryShopDetails";
}
