package com.fdw.fdd.okgo;

public class Api {
    // 服务器地址--和个人有关
//    private static final String BASE_URL = "http://192.168.1.11:1001";
    private static final String BASE_URL = "http://user.fuduowang.com:1001";
    /**
     * 登录发送密码
     */
    public static final String USER_DOLOGIN = BASE_URL + "/user-service/user/doLogin";
    /**
     * 获取验证码
     */
    public static final String USER_DOSEND = BASE_URL + "/user-service/user/doSend";
    /**
     * 用户激活/忘记密码
     */
    public static final String USER_DOACTIVATE = BASE_URL + "/user-service/user/doActivate";
    /**
     * 添加收货地址
     */
    public static final String FREIGHTADDRESS_DOSAVE = BASE_URL + "/user-service/freightAddress/doSave";
    /**
     * 查询收货地址
     */
    public static final String FREIGHTADDRESS_DOQUERY = BASE_URL + "/user-service/freightAddress/doQuery";
    /**
     * 用户删除收货地址
     */
    public static final String FREIGHTADDRESS_DODELETE = BASE_URL + "/user-service/freightAddress/doDelete";
    /**
     * 用户查询商品
     */
    public static final String USER_DOQUERYCOLLECTIONS = BASE_URL + "/user-service/user/doQueryCollections";
    /**
     * 用户删除收藏夹
     */
    public static final String USER_DODELETECOLLECTIONS = BASE_URL + "/user-service/user/doDeleteCollections";
    /**
     * 用户个人资料添加
     */
    public static final String USER_DOUPDATE = BASE_URL + "/user-service/user/doUpdate";
    /**
     * 个人足迹浏览
     */
    public static final String USER_DOQUERYFOOTPRINT = BASE_URL + "/user-service/user/doQueryFootprint";
    /**
     * 个人足迹删除
     */
    public static final String USER_DODELETEFOOTPRINT = BASE_URL + "/user-service/user/doDeleteFootprint";
    /**
     * 个人信息查询
     */
    public static final String USER_DOQUERY = BASE_URL + "/user-service/user/doQuery";
    /**
     * 转赠中心
     */
    public static final String USER_DOEXCHANGE = BASE_URL + "/user-service/user/doExchange";
    /**
     * 添加收藏夹
     */
    public static final String USER_DOCOLLECT = BASE_URL + "/user-service/user/doCollect";
    /**
     *  购物车新增
     */
    public static final String CART_DOSAVE = BASE_URL + "/user-service/cart/doSave";
    /**
     *  用户购物车查询
     */
    public static final String CART_DOQUERY = BASE_URL + "/user-service/cart/doQuery";

    /**
     *  用户购物车删除
     */
    public static final String CART_DODELETE = BASE_URL + "/user-service/cart/doDelete";

    /**
     *  激活验证接口
     */
    public static final String USER_DOCHECKACTIVE = BASE_URL + "/user-service/user/doCheckActive";
    /**
     *   客服云接口
     */
    public static final String USER_DOCREATENEWIM = BASE_URL + "/user-service/user/doCreateNewIM";

//************************************************    订单有关    **************************************************************

    // 服务器地址--和订单有关
//    private static final String BASE_URL = "http://192.168.1.11:1001/orders-service";
//    private static final String BASE_URL = "http://user.fuduowang.com/orders-service";
    /**
     * 查询交易明细
     */
    public static final String ORDERS_DOQUERYTRANSACTIONRECORD = BASE_URL + "/orders-service/orders/doQueryTransactionRecord";
    /**
     * 全部订单查询
     */
    public static final String ORDERS_DOQUERYORDERSDETAILS = BASE_URL + "/orders-service/orders/doQueryOrdersDetails";
    /**
     * 确认订单
     */
    public static final String ORDERS_DOCONFIRMORDER = BASE_URL + "/orders-service/orders/doConfirmOrder";
    /**
     * 购物车多规格确认订单
     */
    public static final String ORDERS_DOCONFIRMORDERS = BASE_URL + "/orders-service/orders/doConfirmOrders";
    /**
     * 单个商品提交订单
     */
    public static final String ORDERS_DOSUBMITORDER = BASE_URL + "/orders-service/orders/doSubmitOrder";
    /**
     * 多订单立即支付
     */
    public static final String ORDERS_DOPAY = BASE_URL + "/orders-service/orders/doPay";
    /**
     * 多个商品提交订单
     */
    public static final String ORDERS_DOSUBMITORDERS = BASE_URL + "/orders-service/orders/doSubmitOrders";
    /**
     * 个人售后全部订单查询
     */
    public static final String ORDERS_DOQUERYRETURNORDERS = BASE_URL + "/orders-service/orders/doQueryReturnOrders";
    /**
     * 待付款买家提醒发货
     */
    public static final String ORDERS_DOREMINDSEND = BASE_URL + "/orders-service/orders/doRemindSend";
    /**
     * 查询订单的物流信息
     */
    public static final String ORDERS_DOQUERYORDEREXPRESSES = BASE_URL + "/orders-service/orders/doQueryOrderExpresses";
    /**
     * 根据物流id查询物流
     */
    public static final String ORDERS_DOQUERYEXPRESS = BASE_URL + "/orders-service/orders/doQueryExpress";
    /**
     * 退/换货
     */
    public static final String ORDERS_DORETURNORDERS = BASE_URL + "/orders-service/orders/doReturnOrders";
    /**
     * 单 订单详情运费计算
     */
    public static final String ORDERS_DOCOUNTFREIGHTPRICE = BASE_URL + "/orders-service/orders/doCountFreightPrice";
    /**
     *  查询退货订单详情
     */
    public static final String ORDERS_DOQUERYRETURNORDERDETAILS = BASE_URL + "/orders-service/orders/doQueryReturnOrderDetails";
    /**
     *  修改退货申请
     */
    public static final String ORDERS_DOMODIFYRETURNORDER = BASE_URL + "/orders-service/orders/doModifyReturnOrder";
    /**
     *  撤销退货申请
     */
    public static final String ORDERS_DOCANCELRETURNORDER = BASE_URL + "/orders-service/orders/doCancelReturnOrder";
    /**
     *  协商历史查询
     */
    public static final String ORDERS_DOQUERYCONSULT = BASE_URL + "/orders-service/orders/doQueryConsult";
    /**
     *  待付款取消订单
     */
    public static final String ORDERS_DOCANCELORDER = BASE_URL + "/orders-service/orders/doCancelOrder";
    /**
     *  待付款取消订单
     */
    public static final String ORDERS_DOEVALUATE = BASE_URL + "/orders-service/orders/doEvaluate";
    /**
     *  查询物流公司
     */
    public static final String ORDERS_DOQUERYEXPRESSCOMPANY = BASE_URL + "/orders-service/orders/doQueryExpressCompany";
    /**
     *  查询退货物流地址
     */
    public static final String ORDERS_DOQUERYRETURNFREIGHTADDRESS = BASE_URL + "/orders-service/orders/doQueryReturnFreightAddress";
    /**
     *  添加退换货物流
     */
    public static final String ORDERS_DOSAVERETURNFREIGHT = BASE_URL + "/orders-service/orders/doSaveReturnFreight";
    /**
     *  确认收货(订单)
     */
    public static final String ORDERS_DORECEIVE = BASE_URL + "/orders-service/orders/doReceive";

    /**
     *  购物车国外物品查询
     */
    public static final String ORDERS_DOCONFIRMFOREIGNCOMMODITIES = BASE_URL + "/orders-service/orders/doConfirmForeignCommodities";

    /**
     *  换货确认收货
     */
    public static final String ORDERS_DORECEIVERETURNORDERS = BASE_URL + "/orders-service/orders/doReceiveReturnOrders";

    //************************************************    商品有关    **************************************************************

    // 图片地址---和商品有关
//    private static final String BASE_URL = "http://192.168.1.11:1001/commodity-service";
//    private static final String BASE_URL = "http://user.fuduowang.com/commodity-service";

    /**
     * 图片接口
     */
    public static final String IMAGE_DOFINDIMAGES = BASE_URL + "/commodity-service/image/doFindImages";
    /**
     * 爆款热卖接口
     */
    public static final String COMMODITY_HOTSALE = BASE_URL + "/commodity-service/commodity/hotSale";
    /**
     * 首页推荐-猜你喜欢接口
     */
    public static final String COMMODITY_DOFINDMAYBEYOULIKE = BASE_URL + "/commodity-service/commodity/doFindMaybeYouLike";
    /**
     * 首页分类接口
     */
    public static final String CATEGORY_DOFINDCATEGORY = BASE_URL + "/commodity-service/category/doFindCategory";
    /**
     * 首页类目推荐接口
     */
    public static final String COMMODITY_DORECOMMENDCATEGORY = BASE_URL + "/commodity-service/commodity/doRecommendCategory";
    /**
     * 首页分类推荐接口
     */
    public static final String HOMEPAGESORT_DOFINDHOMEPAGESORTS = BASE_URL + "/commodity-service/homePageSort/doFindHomePageSorts";
    /**
     * 个人用户头像添加
     */
    public static final String IMAGE_DOUPLOADPORTRAIT = BASE_URL + "/commodity-service/image/doUploadPortrait";
    /**
     * 分类-顶级类目查询
     */
    public static final String CATEGORY_DOQUERYCATEGOR = BASE_URL + "/commodity-service/category/doQueryCategory";
    /**
     * 分类-顶级类目查询
     */
    public static final String CATEGORY_DOQUERYCATEGORYDETAILS = BASE_URL + "/commodity-service/category/doQueryCategoryDetails";
    /**
     * 根据类目/名称查询商品（销量）
     */
    public static final String COMMODITY_DOQUERYBYFICTITIOUSVOLUME = BASE_URL + "/commodity-service/commodity/doQueryByFictitiousVolume";
    /**
     * 根据类目/名称查询商品（按价格排序）
     */
    public static final String COMMODITY_DOQUERYBYPRICE = BASE_URL + "/commodity-service/commodity/doQueryByPrice";
    /**
     * 根据名称综合查询
     */
    public static final String COMMODITY_DOQUERYBYNAME = BASE_URL + "/commodity-service/commodity/doQueryByName";
    /**
     * 查询商品详情
     */
    public static final String COMMODITY_DOQUERYCOMMODITYDETAILS = BASE_URL + "/commodity-service/commodity/doQueryCommodityDetails";
    /**
     * 商品规格查询
     */
    public static final String COMMODITY_DOQUERYSPECIFICATIONS = BASE_URL + "/commodity-service/commodity/doQuerySpecifications";
    /**
     * 店铺商品详情查询（综合）
     */
    public static final String COMMODITY_DOQUERYSHOPDETAILS = BASE_URL + "/commodity-service/commodity/doQueryShopDetails";

    /**
     *  首页分类详情（京东优选）接口
     */
    public static final String HOMEPAGESORT_DOFINDJUMPINFO = BASE_URL + "/commodity-service/homePageSort/doFindJumpInfo";
    /**
     *  首页热卖活动信息获取
     */
    public static final String HOMEPAGESORT_DOQUERYHOTSALE = BASE_URL + "/commodity-service/homePageSort/doQueryHotSale";
    /**
     *  首页热卖活动详细信息查询
     */
    public static final String HOMEPAGESORT_DOQUERYHOTSALEDETAILS = BASE_URL + "/commodity-service/homePageSort/doQueryHotSaleDetails";

    /**
     *  轮播图接口
     */
    public static final String HOMEPAGESORT_DOQUERYCAROUSEL = BASE_URL + "/commodity-service/homePageSort/doQueryCarousel";
    /**
     *  联想搜索
     */
    public static final String CATEGORY_DOQUERYASSOCIATION = BASE_URL + "/commodity-service/category/doQueryAssociation";
}
