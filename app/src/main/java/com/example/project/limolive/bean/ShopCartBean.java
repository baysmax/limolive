package com.example.project.limolive.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * 购物车
 * @author hwj on 2016/12/21.
 */

public class ShopCartBean implements Parcelable{

    private String id;
    private String user_id;
    private String goods_id;
    private String goods_name;
    private String goods_price;
    private String shiping_price;
    private String original_img;
    private String member_goods_price;
    private String goods_num;
    private String spec_key;
    private String spec_key_name;
    private String bar_code;
    private String selected;
    private String add_time;
    private String prom_type;
    private String prom_id;
    private String sku;
    private String first_leader;
    private String stores_id;
    private int isSelect;  //如果选中则为1 未选中则为0
    private String start_number;

    public ShopCartBean(){}

    protected ShopCartBean(Parcel in) {
        id = in.readString();
        user_id = in.readString();
        goods_id = in.readString();
        goods_name = in.readString();
        goods_price = in.readString();
        shiping_price = in.readString();
        original_img = in.readString();
        member_goods_price = in.readString();
        goods_num = in.readString();
        spec_key = in.readString();
        spec_key_name = in.readString();
        bar_code = in.readString();
        selected = in.readString();
        add_time = in.readString();
        prom_type = in.readString();
        prom_id = in.readString();
        sku = in.readString();
        first_leader = in.readString();
        stores_id = in.readString();
        isSelect = in.readInt();
        start_number = in.readString();
    }

    public static final Creator<ShopCartBean> CREATOR = new Creator<ShopCartBean>() {
        @Override
        public ShopCartBean createFromParcel(Parcel in) {
            return new ShopCartBean(in);
        }

        @Override
        public ShopCartBean[] newArray(int size) {
            return new ShopCartBean[size];
        }
    };

    public String getStart_number() {
        return start_number;
    }

    public void setStart_number(String start_number) {
        this.start_number = start_number;
    }

    public void compareNumber(){
        this.start_number=goods_num;
    }

    public boolean isChange(){
        if(!TextUtils.isEmpty(start_number)&&!TextUtils.isEmpty(goods_num)){
            return !start_number.equals(goods_num);
        }else{
            return false;
        }
    }

    public int getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(int isSelect) {
        this.isSelect = isSelect;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getShiping_price() {
        return shiping_price;
    }

    public void setShiping_price(String shiping_price) {
        this.shiping_price = shiping_price;
    }

    public String getOriginal_img() {
        return original_img;
    }

    public void setOriginal_img(String original_img) {
        this.original_img = original_img;
    }

    public String getMember_goods_price() {
        return member_goods_price;
    }

    public void setMember_goods_price(String member_goods_price) {
        this.member_goods_price = member_goods_price;
    }

    public String getGoods_num() {
        return goods_num;
    }

    public void setGoods_num(String goods_num) {
        this.goods_num = goods_num;
    }

    public String getSpec_key() {
        return spec_key;
    }

    public void setSpec_key(String spec_key) {
        this.spec_key = spec_key;
    }

    public String getSpec_key_name() {
        return spec_key_name;
    }

    public void setSpec_key_name(String spec_key_name) {
        this.spec_key_name = spec_key_name;
    }

    public String getBar_code() {
        return bar_code;
    }

    public void setBar_code(String bar_code) {
        this.bar_code = bar_code;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getProm_type() {
        return prom_type;
    }

    public void setProm_type(String prom_type) {
        this.prom_type = prom_type;
    }

    public String getProm_id() {
        return prom_id;
    }

    public void setProm_id(String prom_id) {
        this.prom_id = prom_id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getFirst_leader() {
        return first_leader;
    }

    public void setFirst_leader(String first_leader) {
        this.first_leader = first_leader;
    }

    public String getStores_id() {
        return stores_id;
    }

    public void setStores_id(String stores_id) {
        this.stores_id = stores_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(user_id);
        dest.writeString(goods_id);
        dest.writeString(goods_name);
        dest.writeString(goods_price);
        dest.writeString(shiping_price);
        dest.writeString(original_img);
        dest.writeString(member_goods_price);
        dest.writeString(goods_num);
        dest.writeString(spec_key);
        dest.writeString(spec_key_name);
        dest.writeString(bar_code);
        dest.writeString(selected);
        dest.writeString(add_time);
        dest.writeString(prom_type);
        dest.writeString(prom_id);
        dest.writeString(sku);
        dest.writeString(first_leader);
        dest.writeString(stores_id);
        dest.writeInt(isSelect);
        dest.writeString(start_number);
    }
}
