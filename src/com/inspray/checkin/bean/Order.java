package com.inspray.checkin.bean;

public class Order {
    int id;
    String customer; //购票人
    String ticketClass; //票类
    String seat; //座位
    String status; //状态
    int usedNum; //已用数量
    int availableNum; //可用数量
    String tel; //电话
    String time; //时间
    String coupon; //优惠券
    String channel; //渠道
    String remark; //备注
    String name; //姓名
    String code; //票码

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getTicketClass() {
        return ticketClass;
    }

    public void setTicketClass(String ticketClass) {
        this.ticketClass = ticketClass;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getUsedNum() {
        return usedNum;
    }

    public void setUsedNum(int usedNum) {
        this.usedNum = usedNum;
    }

    public int getAvailableNum() {
        return availableNum;
    }

    public void setAvailableNum(int availableNum) {
        this.availableNum = availableNum;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "订单号：" + id +
                "\n购票人：" + customer +
                "\n票类：" + ticketClass +
                "\n座位：" + seat +
                "\n状态：" + status +
                "\n已用数量：" + usedNum +
                "\n可用数量：" + availableNum +
                "\n电话：" + tel +
                "\n购票时间：" + time +
                "\n优惠券：" + coupon +
                "\n渠道：" + channel +
                "\n备注：" + remark +
                "\n姓名：" + name +
                "\n票码：" + code;
    }
}
