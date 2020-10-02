package com.inspray.checkin.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Ticket {
    int id;
    String ticketClass; //票种
    String name; //姓名
    int seatFloor; //层
    int seatRow; //排
    int seatColumn;  //座
    String coupon;  //优惠券
    String channel; //渠道
    String code;  //票码
    Date printTime; //打印时间
    String ticketClassChar; //票种字母
    String remark; //备注
    String entrance; //入口
    int orderId;//订单号

    public Ticket() {
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEntrance() {
        return entrance;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTicketClass() {
        return ticketClass;
    }

    public void setTicketClass(String ticketClass) {
        this.ticketClass = ticketClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeatFloor() {
        return seatFloor;
    }

    public void setSeatFloor(int seatFloor) {
        this.seatFloor = seatFloor;
    }

    public int getSeatRow() {
        return seatRow;
    }

    public void setSeatRow(int seatRow) {
        this.seatRow = seatRow;
    }

    public int getSeatColumn() {
        return seatColumn;
    }

    public void setSeatColumn(int seatColumn) {
        this.seatColumn = seatColumn;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getPrintTime() {
        return printTime;
    }

    public void setPrintTime(Date printTime) {
        this.printTime = printTime;
    }

    public String getTicketClassChar() {
        return ticketClassChar;
    }

    public void setTicketClassChar(String ticketClassChar) {
        this.ticketClassChar = ticketClassChar;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return code + "\t" + ticketClass + "\t" + name + "\t" + seatFloor + "层" + seatRow + "排" + seatColumn + "座\t" + coupon + "\t" + channel + "\t" + sdf.format(printTime) + "\n";
    }
}
