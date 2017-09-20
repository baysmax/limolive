package com.example.project.limolive.api;

/**
 * 各个接口的路径
 */
public class Urls {

    public static final String POST_REGISTER = "appapi/user/register";  //注册
    public static final String POST_THIRDLOGIN = "/appapi/user/thirdLogin";  //第三方登录注册
    public static final String POST_ISFIRSTLOGIN = "/appapi/user/is_first";  //是否是第一次登录
    public static final String POST_ISBANED = "/Appapi/Ucenter/user_live_disable";  //是否被禁播
    public static final String POST_GETUSER_DIAMONDS = "/Appapi/Ucenter/diamonds_coins";  //获取用户钻石
    public static final String POST_GETUSER_NEWS = "/Appapi/Live/live_recommend_create_time";  //获取最新列表数据
    public static final String POST_GETUSER_NMZT = "/Appapi/Live/live_recommend_charm";  //获取柠檬主推列表数据
    public static final String POST_GETUSER_ZPHB = "/Appapi/Ucenter/platform_charm_list";  //获取总排行榜数据
    public static final String POST_GETUSER_FOLLOW = "/Appapi/Ucenter/page_ifollow";  //获取关注页面数据
    public static final String POST_GETUSER_SEARCH_HOT = "/Appapi/Live/search_hot";  //搜索热搜(随即10条)接口
    public static final String POST_GETUSER_SEARCH_RECOMMEND = "/Appapi/Live/search_recommend";  //说明	搜索主播推荐(随即5条)
    public static final String POST_GETUSER_SEARCH = "/Appapi/Live/search";  //搜索直播间
    public static final String POST_GOODS_SEARCH = "/Appapi/Goodcategory/search";  //搜索商品
    public static final String POST_GOODS_MYORDER = "/Appapi/Myorder/my_order";  //申用户点击我的店铺时,触发事件接口
    public static final String POST_GOODS_APPLY = "/Appapi/Myorder/apply_order";  //申请店铺接口
    public static final String POST_GOODS_ORDER_RETURN = "/Appapi/Cart/order_return";  //申请店铺接口
    public static final String POST_GOODS_MY_STORE = "/Appapi/Goods/MyStore";  //我的店铺
    public static final String POST_CART4 = "/Appapi/Cart/cart4";  //等级接口
    public static final String POST_USER_ROBOT = "/Appapi/Ucenter/user_robot";  //等级接口
    public static final String POST_LIVE_GRADE = "/Appapi/Live/live_grade";  //等级接口
    public static final String GET_GETSIG = "/appapi/user/getSig";  //用户获取腾讯sig
    public static final String POST_LOGIN = "appapi/user/login";  //登录
    public static final String GET_LIVE_LIST = "appapi/live/live_list";  //直播列表 ????人数
    public static final String GET_CAROUSEL = "appapi/Goodstype/Carousel";  //获取首页轮播图
    public static final String GET_GOODSTYPE = "appapi/Goodstype/get_goodstype";  //获取商品类型
    public static final String GET_GOODS_CATGORY = "/Appapi/Goods/categorylist";  //获取商品类型的商品
    public static final String GET_GOODS_COMMENT_LIST = "/Appapi/Cart/comment_list";  //获取商品评论
    public static final String GET_GOODS_ADD_COM = "/Appapi/Cart/add_comment";  //添加商品评论
    public static final String GET_GOODS_ORDER_MYORER_SELL_UP = "/Appapi/Myorder/myorder_sell_up";  //退货审核
    public static final String GET_GOODS_ORDER_ORDER_RETURN_WL_ADD = "/Appapi/Cart/order_return_wl_add";  //退货物流
    public static final String GET_GOODS_ORDER_MYORER_SELL_AFTER = "/Appapi/Myorder/myorder_sell_after";  //售后列表
    public static final String GET_GOODS_ORDER_ORDER_RETURN_LIST = "/Appapi/Cart/order_return_list";  //申请退换货显示接口
    public static final String GET_GOODS_ORDER_SHIPPING = "/Appapi/Myorder/myorder_shipping";  //审核通过
    public static final String GET_GOODS_ORDER_CONFIRM = "/Appapi/Cart/orderConfirm";  //确认收货
    public static final String POST_HOTGOODSTYPE = "/appapi/Goodcategory/hot_goodscatgory";  //获取热门分类
    public static final String POST_GETADDRESS_LIST = "appapi/Goodstype/getAddressList";  //获取直播列表
    public static final String POST_EDIT_ADDRESS = "appapi/Goodstype/addAddress";  //修改收货地址
    public static final String GET_DELETE_ADDRESS = "appapi/Goodstype/del_address";  //删除收货地址
    public static final String SET_DEFAULT_ADDRESS = "appapi/Goodstype/setDefaultAddress";  //设置默认收货地址
    public static final String GET_CART_LIST = "appapi/Cart/cartList";  //购物车列表
    public static final String GET_CART_CART3 = "/Appapi/Cart/cart3";  //购物车第三步确定页面(结算)接口
    public static final String GET_DELETE_CART_LIST = "appapi/Cart/delCart";  //删除购物车
    public static final String GET_CHANGE_CART_LIST = "appapi/Cart/batch_cartgoods_num";  //批量修改
    public static final String GET_COMMIT_CART_LIST = "appapi/Cart/cart2";  //结算

    public static final String GET_FIND_PASSWORD = "appapi/user/passwordReset";  //找回密码
    public static final String POST_CHANGE_INFO = "appapi/ucenter/edit";  //修改个人信息
    public static final String POST_RANKING_INFO = "/Appapi/Ucenter/gift_Ranking";  //修改个人信息

    public static final String GET_MYROOMID = "/appapi/live/roomnum";  //获取房间号码
    public static final String NEW_ROOM_INFO = "/appapi/live/create";  //用户创建直播间接口
    public static final String GET_LIVELIST = "/appapi/live/live_list";  //获取直播列表
    public static final String STOP_ROOM = "/appapi/live/delroom";  //关闭直播间
    public static final String SEARCH_ROOM = "/appapi/live/search";  //搜索直播间
    public static final String GET_HEARTBEAT = "/appapi/live/Heartbeat";  //接收心跳包 定时接收直播间信息 时间间隔 60秒

    public static final String GETALLCATGORY = "/appapi/Goodcategory/Allcatgory";  //全部分类
    public static final String GOODSTYPE = "/appapi/Goodstype/get_goodstype";  //商品类型
    public static final String FRIENDLIST = "/appapi/ucenter/friendLists";  //好友列表
    public static final String SEARCHFRIENDS = "/appapi/Ucenter/searchfriend";  //搜索加好友
    public static final String FRENDUSERINFO = "/appapi/Ucenter/userInfo";  //用户详情
    public static final String LIVELISTS = "/appapi/Ucenter/livelists";  //在直播的2个（更多）

    public static final String RECOMMEND = "appapi/Goods/stylish";  //淘物-爆款推荐
    public static final String NORMAL_GOODS = "appapi/Goods/newlist";  //淘物-普通商品
    public static final String GOODS_CONTENT = "appapi/Goods/goodsInfo";  //店铺商品详情
    public static final String COLLECT = "appapi/Goods/goods_collect";  //添加或取消收藏
    public static final String ADD_CAR = "appapi/Cart/addCart";  //添加购物车
    public static final String COLLECT_LIST = "appapi/Cart/addCart";  //获取收藏列表
    public static final String GOODS_MANAGER = "/appapi/Goods/goods_manager";  //商品管理
    public static final String GOODS_DEL_MANAGER_GOODS = "Appapi/goods/dele_goods/";  //删除商品
    public static final String RECHARGE = "/appapi/Cart/recharge";  //柠檬订单生成
    public static final String GOOS_ORDER2 = "/appapi/Cart/goods_order2";  // 商品详情确认订单和提交订单
    public static final String GROUPMEMBER_LISTINFO = "/appapi/ucenter/group_member_info";  //获取直播间用户列表
    public static final String GROUPMEMBER_LISTINFOS = "/Appapi/ucenter/group_member_info";  //获取直播间用户列表
    public static final String FOLLOWHANDLE = "/appapi/ucenter/followHandle";  //关注 取消关注
    public static final String NEARLIVE = "appapi/Ucenter/nearlive";  //附近的直播
    public static final String ADDFRIEND = "/appapi/Ucenter/add_friend";  //加好友
    public static final String SEARCHFRIENDNAME = "/appapi/Ucenter/search_friendname"; //搜索好友店铺中的好友
    //发布宝贝
    public static final String SEND_PRODUCTS = "/appapi/Goods/add_good";
    public static final String POSt_DELETEFRIEND = "/appapi/Ucenter/del_friend"; //删除好友
    public static final String POSt_PULLBLACK = "/appapi/Ucenter/pullBlack"; //拉黑取消操作接口

    //交易管理
    public static final String DEAL_MANAGER = "/appapi/Goods/deal_manager";
    //我的收藏列表
    public static final String GET_GOODS_COLLECT = "/appapi/Goods/getGoodsCollect";
    //浏览足迹
    public static final String FOOT_MARKER = "/appapi/Goods/footmark";
    //提交用户反馈
    public static final String COMMIT_REBACK = "/appapi/Goodstype/user_retroact";
    //账单明细
    public static final String BILL_DETAILS = "/appapi/Ucenter/Bill_details";
    //黑名单列表
    public static final String BLACK_LIST = "/appapi/Ucenter/blackLists";
    //取消黑名单操作
    public static final String CANCEL_BLACK = "/appapi/Ucenter/pullBlack";


    public static final String GET_ORDER = "/appapi/Cart/getOrderList";  //按类型查询订单
    public static final String POST_ORDER = "/Appapi/Myorder/myorder_order";  //按类型查询订单
    public static final String GET_GOODS_INF = "/appapi/Cart/goods_order";  //点击购买获取商品信息
    public static final String GIFT_COINLIST = "/appapi/Ucenter/coinLists";  //柠檬币充值列接口
    public static final String GIFT_SEND = "/Appapi/Ucenter/sendgift";  //送礼物接口
    public static final String GET_HAVECOINS = "/appapi/Ucenter/have_coins";  //获取主播魅力值接口
    public static final String GET_MEMBERCOINS = "/appapi/Ucenter/have_charm";  //获取成员柠檬币接口
    public static final String GET_FOLLOWS = "/appapi/ucenter/follows";  //主播的关注人数
    public static final String GET_FANSLIST = "/Appapi/Ucenter/followLists";  //粉丝列表

    public static final String GET_SYSTEM_MSG = "Appapi/live/live_notice";//获取系统通知消息

    /**
     * 色子游戏接口
     */
    public static final String POST_DICE_LIST = "/Appapi/Dicegame/dice_list";//摇色子接口
    public static final String POST_USER_INTEGRAL_TO_DIAMONDS = "/Appapi/Ucenter/user_integral_coins";//积分转换钻石
    public static final String POST_USER_DIAMONDS_TO_INTEGRAL = "/Appapi/Ucenter/user_coins_integral";//钻石转换积分
    public static final String POST_USER_GET_INTEGRAL = "/Appapi/Ucenter/integral";//获取用户积分
    public static final String POST_USER_BEI = "/Appapi/Dicegame/dice_user_integral_reduce";//下注接口
    public static final String POST_USER_BEI_HEART_BEAT = "/Appapi/Dicegame/user_integral_heartbeat";//下注心跳
    public static final String POST_USER_BEI_DEL = "/Appapi/Dicegame/dice_shang";//游戏下一局开始前清除上一局数据接口
    public static final String POST_USER_STATUS = "/Appapi/Dicegame/dicegame_state_add";//上传状态心跳包
    public static final String POST_USER_STATUS_GET = "/Appapi/Dicegame/dicegame_state_list";//获取游戏状态
    public static final String POST_USER_DATA = "/Appapi/Dicegame/dice_list_data";//获取色子
    /**
     * 牛牛
     */
    public static final String POST_USER_NN_BET = "/Appapi/Niuniu/niuniu_dice_user_integral_reduce";//下注接口
    public static final String POST_USER_NN_HEART_BEAT = "/Appapi/Niuniu/niuniu_user_integral_heartbeat";//获取每桌下注金额总合接口
    public static final String POST_USER_NN_STATUS = "/Appapi/Niuniu/niuniu_state_add";//上传状态心跳包
    public static final String POST_USER_NN_STATUS_GET = "/Appapi/Niuniu/niuniu_state_list";//获取游戏状态
    public static final String POST_USER_NN_BEI_DEL = "/Appapi/Niuniu/niuniu_dice_shang";//游戏下一局开始前清除上一局数据接口
    public static final String POST_USER_NN_DATA  = "/Appapi/Niuniu/niuniu_dice_list_data";//获取牌
    public static final String POST_USER_NN_LIST  = "/Appapi/Niuniu/niuniu_dice_list";//发牌接口

}
