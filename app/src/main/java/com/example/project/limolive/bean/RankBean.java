package com.example.project.limolive.bean;

/**
 * Created by AAA on 2017/8/24.
 */

public class RankBean {

    String comment_id;
    String suppliers_id;
    String goods_id;
    String username;
    String email;
    String content;
    String deliver_rank;
    String add_time;
    String ip_address;
    String is_show;
    String user_id;
    String parent_id;
    String img;
    String order_id;
    String goods_rank;
    String service_rank;
    String courier_rank;

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getSuppliers_id() {
        return suppliers_id;
    }

    public void setSuppliers_id(String suppliers_id) {
        this.suppliers_id = suppliers_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDeliver_rank() {
        return deliver_rank;
    }

    public void setDeliver_rank(String deliver_rank) {
        this.deliver_rank = deliver_rank;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getIp_address() {
        return ip_address;
    }

    public void setIp_address(String ip_address) {
        this.ip_address = ip_address;
    }

    public String getIs_show() {
        return is_show;
    }

    public void setIs_show(String is_show) {
        this.is_show = is_show;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getGoods_rank() {
        return goods_rank;
    }

    public void setGoods_rank(String goods_rank) {
        this.goods_rank = goods_rank;
    }

    public String getService_rank() {
        return service_rank;
    }

    public void setService_rank(String service_rank) {
        this.service_rank = service_rank;
    }

    public String getCourier_rank() {
        return courier_rank;
    }

    public void setCourier_rank(String courier_rank) {
        this.courier_rank = courier_rank;
    }


    @Override
    public String toString() {
        return "RankBean{" +
                "comment_id='" + comment_id + '\'' +
                ", suppliers_id='" + suppliers_id + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", deliver_rank='" + deliver_rank + '\'' +
                ", add_time='" + add_time + '\'' +
                ", ip_address='" + ip_address + '\'' +
                ", is_show='" + is_show + '\'' +
                ", user_id='" + user_id + '\'' +
                ", parent_id='" + parent_id + '\'' +
                ", img='" + img + '\'' +
                ", order_id='" + order_id + '\'' +
                ", goods_rank='" + goods_rank + '\'' +
                ", service_rank='" + service_rank + '\'' +
                ", courier_rank='" + courier_rank + '\'' +
                '}';
    }
}
