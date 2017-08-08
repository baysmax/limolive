package com.example.project.limolive.api;

import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by hwj on 2016/7/29.
 * 请求网络调用的model
 */
public class Api {
    /**
     * 请求成功
     */
    public static final int SUCCESS = 0;
    public static final int CANCLE = 1;

    /**
     * 注册
     *
     * @param phone    电话
     * @param password 密码
     * @param nickname 用户名
     * @param code     验证码
     */
    public static void register(String phone, String password, String nickname, String code, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        params.put("password", password);
        params.put("nickname", nickname);
        params.put("code", code);
        ApiHttpClient.post(ApiHttpClient.API_URL + Urls.POST_REGISTER, params, handler);
    }
    public static void getRanking(String uid, String userid, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("user_id", userid);
        ApiHttpClient.post(ApiHttpClient.API_URL + Urls.POST_RANKING_INFO, params, handler);
    }

    /**
     * 登录
     *
     * @param phone    电话
     * @param password 密码
     */
    public static void login(String phone, String password, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        params.put("password", password);
        ApiHttpClient.post(ApiHttpClient.API_URL + Urls.POST_LOGIN, params, handler);
    }

    /**
     * 找回密码
     *
     * @param phone    电话
     * @param password 密码
     * @param code     验证码
     */
    public static void findPassword(String phone, String password, String code, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        params.put("password", password);
        params.put("code", code);
        ApiHttpClient.post(ApiHttpClient.API_URL + Urls.GET_FIND_PASSWORD, params, handler);
    }

    /**
     * 修改个人信息
     *
     * @param params 需要修改的属性
     */
    public static void changeInfo(RequestParams params, AsyncHttpResponseHandler handler) {
        ApiHttpClient.post(ApiHttpClient.API_URL + Urls.POST_CHANGE_INFO, params, handler);
    }

    /**
     * 用户获取腾讯sig
     *
     * @param phone 电话
     */
    public static void getUserSig(String phone, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.GET_GETSIG, params, handler);
    }

    /**
     * 获取房间号码
     */
    public static void getMyRoomId(String phone, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.GET_MYROOMID, params, handler);
    }

    public static void getSystemMsg(String phone, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.GET_SYSTEM_MSG, params, handler);
    }




    /**
     * 用户创建直播间接口
     *
     * @param phone     用户电话
     * @param longitude 经度
     * @param latitude  纬度
     * @param address   地址
     * @param live_type 直播类型
     * @param title     直播标题
     * @param handler
     */

    public static void GreatNewRoom(String phone, String longitude, String latitude, String address, String live_type, String title, String chat_room_id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        params.put("longitude", longitude);
        params.put("latitude", latitude);
        params.put("address", address);
        params.put("live_type", live_type);
        params.put("title", title);
        params.put("chat_room_id", chat_room_id);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.NEW_ROOM_INFO, params, handler);
    }

    /**
     * 获取直播列表
     */
    public static void getLiveList(String phone, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.GET_LIVELIST, params, handler);
    }

    /**
     * 关闭直播间
     */
    public static void stopLiveRoom(String av_room_id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("av_room_id", av_room_id);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.STOP_ROOM, params, handler);
    }

    /**
     * 搜索直播间
     */
    public static void searchRoom(String phone, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("phone", phone);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.SEARCH_ROOM, params, handler);
    }

    /**
     * 接收心跳包 定时接收直播间信息 时间间隔 60秒
     * host_phone 	是  	主播电话
     * admire_count 是  	点赞人数
     * watch_count 	是  	观看人数
     * time_span 	是  	直播时长 时间戳
     */
    public static void getHeartBeat(String host_phone, int admire_count, int watch_count, int time_span, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("host_phone", host_phone);
        params.put("admire_count", admire_count);
        params.put("watch_count", watch_count);
        params.put("time_span", time_span);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.GET_HEARTBEAT, params, handler);
    }

    /**
     * 直播列表
     *
     * @param page 分页
     */
    public static void liveList(String page, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("page", page);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.GET_LIVE_LIST, params, handler);
    }

    /**
     * 首页轮播图
     */
    public static void carousel(AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.GET_CAROUSEL, params, handler);
    }

    /**
     * 商品类型
     */
    public static void get_goodstype(AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.GET_GOODSTYPE, params, handler);
    }

    /**
     * 热门分类类型
     */
    public static void get_hotGoodstype(AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.POST_HOTGOODSTYPE, params, handler);
    }

    /**
     * 获取收货地址
     */
    public static void getAddress(String user_id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("user_id", user_id);
        ApiHttpClient.post(ApiHttpClient.API_URL + Urls.POST_GETADDRESS_LIST, params, handler);
    }

    /**
     * 新增或者编辑收货地址
     *
     * @param user_id
     * @param consignee     收货人
     * @param mobile        手机号
     * @param province_name 省
     * @param city_name     市
     * @param district_name 区
     * @param address       地址
     * @param is_default    是否默认
     * @param handler
     */
    public static void editAddress(String user_id, String consignee, String mobile, String province_name,
                                   String city_name, String district_name, String address, String is_default, AsyncHttpResponseHandler handler) {
        changeAddress(user_id, null, consignee, mobile, province_name, city_name, district_name, address, is_default, handler);
    }

    /**
     * 编辑收货地址
     *
     * @param user_id
     * @param address_id    地址ID
     * @param consignee     收货人
     * @param mobile        手机号
     * @param province_name 省
     * @param city_name     市
     * @param district_name 区
     * @param address       地址
     * @param is_default    是否默认
     * @param handler
     */
    public static void changeAddress(String user_id, String address_id, String consignee, String mobile, String province_name,
                                     String city_name, String district_name, String address, String is_default, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("user_id", user_id);
        if (!TextUtils.isEmpty(address_id)) {
            params.put("address_id", address_id);
        }
        params.put("consignee", consignee);
        params.put("mobile", mobile);
        params.put("province_name", province_name);
        params.put("city_name", city_name);
        params.put("district_name", district_name);
        params.put("address", address);
        if (!TextUtils.isEmpty(is_default)) {
            params.put("is_default", is_default);
        }
        ApiHttpClient.post(ApiHttpClient.API_URL + Urls.POST_EDIT_ADDRESS, params, handler);
    }

    /**
     * 删除收货地址
     */
    public static void deleteAddress(String id, String user_id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("user_id", user_id);
        params.put("id", id);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.GET_DELETE_ADDRESS, params, handler);
    }

    /**
     * 设置默认收货地址
     */
    public static void modifyDefaultAddress(String user_id, String address_id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("user_id", user_id);
        params.put("address_id", address_id);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.SET_DEFAULT_ADDRESS, params, handler);
    }

    /**
     * 商品类型接口
     */
    public static void getGoodsType(AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.GOODSTYPE, params, handler);
    }

    /**
     * 购物车列表
     */
    public static void cartList(String user_id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("user_id", user_id);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.GET_CART_LIST, params, handler);
    }

    /**
     * 删除购物车列表
     */
    public static void deleteCart(String user_id, String ids, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("user_id", user_id);
        params.put("ids", ids);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.GET_DELETE_CART_LIST, params, handler);
    }

    /**
     * 批量修改购物车列表
     */
    public static void changeCart(String user_id, String batch_goodsnum, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("user_id", user_id);
        params.put("batch_goodsnum", batch_goodsnum);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.GET_CHANGE_CART_LIST, params, handler);
    }

    /**
     * 结算购物车
     */
    public static void commitCart(String user_id, String ids, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("user_id", user_id);
        params.put("ids", ids);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.GET_COMMIT_CART_LIST, params, handler);
    }

    /**
     * 好友列表
     */
    public static void getFriendLists(String uid, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        ApiHttpClient.post(ApiHttpClient.API_URL + Urls.FRIENDLIST, params, handler);
    }

    /**
     * 搜索加好友
     */
    public static void searchfriend(String uid, String name, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("name", name);
        ApiHttpClient.post(ApiHttpClient.API_URL + Urls.SEARCHFRIENDS, params, handler);
    }

    /**
     * 用户详情
     */
    public static void frendUserInfo(String uid, String userid, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("userid", userid);
        ApiHttpClient.post(ApiHttpClient.API_URL + Urls.FRENDUSERINFO, params, handler);
    }

    /**
     * 在直播的2个（更多）
     */
    public static void livelists(String uid, String type, int page, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("type", type);
        params.put("page", page);
        ApiHttpClient.post(ApiHttpClient.API_URL + Urls.LIVELISTS, params, handler);
    }


    /**
     * 淘物-爆款商品
     */
    public static void getRecommend(String uid, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.RECOMMEND, params, handler);
    }

    /**
     * 淘物-普通商品
     */
    public static void getGoods(String uid, int page, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("page", page);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.NORMAL_GOODS, params, handler);
    }

    /**
     * 商品详情
     */
    public static void goodsContent(String uid, String goods_id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("goods_id", goods_id);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.GOODS_CONTENT, params, handler);
    }

    /**
     * 添加或取消收藏
     */
    public static void addCollect(String uid, String goods_id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("goods_id", goods_id);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.COLLECT, params, handler);
    }

    /**
     * 加入购物车
     */
    public static void addCar(String user_id, String goods_id, String goods_num, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("user_id", user_id);
        params.put("goods_id", goods_id);
        params.put("goods_num", goods_num);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.ADD_CAR, params, handler);
    }

    /**
     * 获取收藏列表  待修改
     */
    public static void getCollectList(String user_id, String goods_id, String goods_num, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("user_id", user_id);
        params.put("goods_id", goods_id);
        params.put("goods_num", goods_num);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.ADD_CAR, params, handler);
    }

    /**
     * 商品管理
     */
    public static void goodsManager(String uid, String type, String page, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("type", type);
        params.put("page", page);
        ApiHttpClient.post(ApiHttpClient.API_URL + Urls.GOODS_MANAGER, params, handler);
    }

    /**
     * 获取直播间用户列表
     */
    public static void groupMemberInfo(String uid, String group_id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("group_id", group_id);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.GROUPMEMBER_LISTINFO, params, handler);
    }

    /**
     * 直播 关注 取消关注
     */
    public static void followHandle(String uid, String fuid, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("fuid", fuid);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.FOLLOWHANDLE, params, handler);
    }
    /**
     * 获取粉丝列表
     */
    public static void getFansHandle(String uid, String type, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("type", type);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.GET_FANSLIST, params, handler);
    }

    /**
     * 附近的直播
     */
    public static void nearLive(String uid, String latitude, String longitude, String page, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        params.put("latitude", latitude);//纬度
        params.put("longitude", longitude);//经度
        params.put("page", page);
        ApiHttpClient.post(ApiHttpClient.API_URL + Urls.NEARLIVE, params, handler);
    }

    /**
     *
     */
    public static void Allcatgory(String uid, String latitude, String longitude, String page, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        ApiHttpClient.post(ApiHttpClient.API_URL + Urls.GETALLCATGORY, params, handler);
    }



    /**
     * 添加好友
     */
    public static void addfriend(String uid,String friend_phone,AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid",uid);
        params.put("friend_phone",friend_phone);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.ADDFRIEND, params, handler);
    }

    /**
     * 搜索好友店铺中的好友
     */
    public static void searchFriendname(String uid,String name,AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid",uid);
        params.put("name",name);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.SEARCHFRIENDNAME, params, handler);
    }

    /************
     * 我的店铺----发布宝贝
     * uid	是	int	登录者的用户id
     cat_id	是	int	分类id
     original_img[]	是	file	商品图片(以数组的形式传图)
     goods_name	是	int	分类id
     shop_price	是	int	分类id
     store_count	是	int	库存
     goods_remark	是	string	商品描述
     goods_content[]	否	file	商品详情图片(以数组的形式传图)
     */
    public static void sendProducts(String uid, String cat_id, File headsmall,
                                    String goods_name, String shop_price, String store_count,
                                    String goods_remark, List<File> goods_content,AsyncHttpResponseHandler handler){

        RequestParams params = new RequestParams();
        params.put("uid",uid);
        params.put("cat_id",cat_id);
        params.put("goods_name",goods_name);
        params.put("shop_price",shop_price);
        params.put("store_count",store_count);
        params.put("goods_remark",goods_remark);
        params.put("goods_content",goods_content);

        try {
            params.put("original_img",headsmall);
//            Log.e("headsmall",headsmall.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        params.put("goods_content",goods_content);
//        Log.e("接收的头像张数",headsmall.size()+"");

//        if (null != headsmall) {
//            try {
//                for (int i = 0; i < headsmall.size(); i++) {
//                    params.put(headsmall.get(i).toString(), headsmall.get(i));
//                }
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }

        if (null != goods_content) {
            try {
                for (int i = 0; i < goods_content.size(); i++) {
                    params.put(goods_content.get(i).toString(), goods_content.get(i));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        ApiHttpClient.post(ApiHttpClient.API_URL + Urls.SEND_PRODUCTS, params, handler);
    }


    /**
     * 删除好友
     */
    public static void deleteFriend(String uid,String friend_phone,AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid",uid);
        params.put("friend_phone",friend_phone);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.POSt_DELETEFRIEND, params, handler);
    }

    /**
     * 拉黑取消操作接口
     */
    public static void pullBlack(String uid,String friend_phone,AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid",uid);
        params.put("friend_phone",friend_phone);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.POSt_PULLBLACK, params, handler);
    }


    /************
     * 黄亚菲
     * 交易管理
     */
    public static  void getTradeData(String uid,AsyncHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("uid",uid);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.DEAL_MANAGER,params,handler);
    }


    /*********
     * 黄亚菲
     * 我的收藏列表
     */
    public static void getMyCollect(String uid,int page,AsyncHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("uid",uid);
        params.put("page",page);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.GET_GOODS_COLLECT,params,handler);
    }

    /***********
     * 浏览足迹
     */
    public static void getFootMarkData(String uid,int page,AsyncHttpResponseHandler handler){

        RequestParams params = new RequestParams();
        params.put("uid",uid);
        params.put("page",page);

        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.FOOT_MARKER,params,handler);
    }

    /******
     * 黄亚菲
     * 提交用户反馈信息
     * @param user_id
     * @param content
     * @param handler
     */
    public static void commitReback(String user_id,String content,AsyncHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("user_id",user_id);
        params.put("content",content);
        ApiHttpClient.post(ApiHttpClient.API_URL + Urls.COMMIT_REBACK , params , handler);
    }

    /**********
     * 黄亚菲
     * 账单明细
     */
    public static  void getBillDatas(String uid,AsyncHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("uid",uid);
        ApiHttpClient.post(ApiHttpClient.API_URL + Urls.BILL_DETAILS , params,handler);
    }

    /*********
     * 黄亚菲
     * 黑名单列表
     */
    public static void  getBlackList(String uid,AsyncHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("uid",uid);
        ApiHttpClient.post(ApiHttpClient.API_URL + Urls.BLACK_LIST , params , handler);
    }

    /********
     * 黄亚菲
     * 取消黑名单操作
     */
    public static void cancelBlack(String uid,String friend_phone,AsyncHttpResponseHandler handler){
        RequestParams params = new RequestParams();
        params.put("uid",uid);
        params.put("friend_phone",friend_phone);
        ApiHttpClient.post(ApiHttpClient.API_URL + Urls.CANCEL_BLACK , params ,handler);
    }






    /**
     * 按类型查订单
     */
    public static void getOrder(String user_id,String type,AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("user_id",user_id);
        params.put("type",type);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.GET_ORDER, params, handler);
    }

    /**
     * 点击购买获取商品信息
     */
    public static void getGoodsInf(String user_id,String goods_id,int goods_num,AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("user_id",user_id);
        params.put("goods_id",goods_id);
        params.put("goods_num",goods_num);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.GET_GOODS_INF, params, handler);
    }

    /**
     * 第三方登录注册
     */
    public static void thirdLogin(String nickname,String login_type,String openid,String headsmall,AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("nickname",nickname);//用户昵称
        params.put("login_type",login_type);
        params.put("openid",openid);
        params.put("headsmall",headsmall);

        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.POST_THIRDLOGIN, params, handler);
    }

    /**
     * 第三方登录注册
     */
    public static void thirdLogin(String phone,String nickname,String login_type,String openid,String headsmall,AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("phone",phone);
        params.put("nickname",nickname);//用户昵称
        params.put("login_type",login_type);
        params.put("openid",openid);
        params.put("headsmall",headsmall);


        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.POST_THIRDLOGIN, params, handler);
    }


    /**
     * 是否是第一次登录
     */
    public static void is_first(String login_type,String openid,AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("login_type",login_type);
        params.put("openid",openid);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.POST_ISFIRSTLOGIN, params, handler);
    }

    /**
     * 柠檬币充值
     */
    public static void getCoinLists(String uid,AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid",uid);
        ApiHttpClient.post(ApiHttpClient.API_URL + Urls.GIFT_COINLIST, params, handler);
    }

    /**
     * 送礼物接口
     */
    public static void sendGift(String uid,String give_user_id,String amount,int val,AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid",uid);
        params.put("give_user_id",give_user_id);
        params.put("amount",amount);
        params.put("val",val);
        ApiHttpClient.post(ApiHttpClient.API_URL + Urls.GIFT_SEND, params, handler);
    }

    /**
     * 获取主播魅力值接口
     */
    public static void getHaveCoins(String uid,AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid",uid);
        ApiHttpClient.post(ApiHttpClient.API_URL + Urls.GET_HAVECOINS, params, handler);
    }

    /**
     * 获取观众柠檬币接口
     */
    public static void getMemberCoins(String uid,AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid",uid);
        ApiHttpClient.post(ApiHttpClient.API_URL + Urls.GET_MEMBERCOINS, params, handler);
    }


    /**
     * 柠檬币订单生成
     */
    public static void Recharge(String user_id, String lemon_id, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("user_id", user_id);
        params.put("lemon_id", lemon_id);
        ApiHttpClient.post(ApiHttpClient.API_URL + Urls.RECHARGE, params, handler);
    }

    /**
     * 商品详情确认订单和提交订单
     */
    public static void goos_Order2(String user_id, String goods_id, String address_id, String goods_num, String remark, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("user_id", user_id);
        params.put("goods_id", goods_id);
        params.put("address_id", address_id);
        params.put("goods_num", goods_num);
        params.put("remark", remark);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.GOOS_ORDER2, params, handler);
    }

    /**
     * 主播的关注人数
     */
    public static void getFollows(String uid, AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", uid);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.GET_FOLLOWS, params, handler);
    }

    /**
     * 该用户是否被禁播
     */
    public static void isBaned(String userID,AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("uid", userID);
        Log.i("禁播",""+ApiHttpClient.API_URL + Urls.POST_ISBANED+"userID="+userID);
        ApiHttpClient.get(ApiHttpClient.API_URL + Urls.POST_ISBANED, params, handler);
    }
}
