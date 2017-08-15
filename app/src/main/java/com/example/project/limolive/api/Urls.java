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
    public static final String GET_GETSIG = "/appapi/user/getSig";  //用户获取腾讯sig
    public static final String POST_LOGIN = "appapi/user/login";  //登录
    public static final String GET_LIVE_LIST = "appapi/live/live_list";  //直播列表
    public static final String GET_CAROUSEL = "appapi/Goodstype/Carousel";  //获取首页轮播图
    public static final String GET_GOODSTYPE = "appapi/Goodstype/get_goodstype";  //获取商品类型
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
    public static final String FRIENDLIST="/appapi/ucenter/friendLists";  //好友列表
    public static final String SEARCHFRIENDS="/appapi/Ucenter/searchfriend";  //搜索加好友
    public static final String FRENDUSERINFO="/appapi/Ucenter/userInfo";  //用户详情
    public static final String LIVELISTS="/appapi/Ucenter/livelists";  //在直播的2个（更多）

    public static final String RECOMMEND="appapi/Goods/stylish";  //淘物-爆款推荐
    public static final String NORMAL_GOODS ="appapi/Goods/newlist";  //淘物-普通商品
    public static final String GOODS_CONTENT ="appapi/Goods/goodsInfo";  //店铺商品详情
    public static final String COLLECT ="appapi/Goods/goods_collect";  //添加或取消收藏
    public static final String ADD_CAR ="appapi/Cart/addCart";  //添加购物车
    public static final String COLLECT_LIST ="appapi/Cart/addCart";  //获取收藏列表
    public static final String GOODS_MANAGER ="/appapi/Goods/goods_manager";  //商品管理
    public static final String RECHARGE = "/appapi/Cart/recharge";  //柠檬订单生成
    public static final String GOOS_ORDER2 = "/appapi/Cart/goods_order2";  // 商品详情确认订单和提交订单
    public static final String GROUPMEMBER_LISTINFO ="/appapi/ucenter/group_member_info";  //获取直播间用户列表
    public static final String FOLLOWHANDLE ="/appapi/ucenter/followHandle";  //关注 取消关注
    public static final String NEARLIVE ="appapi/Ucenter/nearlive";  //附近的直播
    public static final String ADDFRIEND ="/appapi/Ucenter/add_friend";  //加好友
    public static final String SEARCHFRIENDNAME ="/appapi/Ucenter/search_friendname";  //搜索好友店铺中的好友
    //发布宝贝
    public static final String SEND_PRODUCTS = "/appapi/Goods/add_good";
    public static final String POSt_DELETEFRIEND ="/appapi/Ucenter/del_friend";  //删除好友
    public static final String POSt_PULLBLACK ="/appapi/Ucenter/pullBlack";  //拉黑取消操作接口

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


    public static final String GET_ORDER ="/appapi/Cart/getOrderList";  //按类型查询订单
    public static final String GET_GOODS_INF ="/appapi/Cart/goods_order";  //点击购买获取商品信息
    public static final String GIFT_COINLIST ="/appapi/Ucenter/coinLists";  //柠檬币充值列接口
    public static final String GIFT_SEND ="/Appapi/Ucenter/sendgift";  //送礼物接口
    public static final String GET_HAVECOINS ="/appapi/Ucenter/have_coins";  //获取主播魅力值接口
    public static final String GET_MEMBERCOINS ="/appapi/Ucenter/have_charm";  //获取成员柠檬币接口
    public static final String GET_FOLLOWS ="/appapi/ucenter/follows";  //主播的关注人数
    public static final String GET_FANSLIST ="/Appapi/Ucenter/followLists";  //粉丝列表

    public static final String GET_SYSTEM_MSG = "Appapi/live/live_notice";//获取系统通知消息
}
