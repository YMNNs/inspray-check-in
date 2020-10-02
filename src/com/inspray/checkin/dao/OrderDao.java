package com.inspray.checkin.dao;

import com.inspray.checkin.bean.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class OrderDao extends BaseDao {

    public Order findByCode(String checkInCode) throws Exception {
        Connection conn = BaseDao.getConnection();
        String sql = "SELECT * FROM `order` WHERE code = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, checkInCode);
        ResultSet rs = stmt.executeQuery();
        Order order = null;
        if (rs.next()) {
            order = new Order();
            order.setId(rs.getInt("id"));
            order.setCustomer(rs.getString("customer"));
            order.setTicketClass(rs.getString("ticketClass"));
            order.setSeat(rs.getString("seat"));
            order.setStatus(rs.getString("status"));
            order.setUsedNum(rs.getInt("usedNum"));
            order.setAvailableNum(rs.getInt("availableNum"));
            order.setTel(rs.getString("tel"));
            order.setTime(rs.getString("time"));
            order.setCoupon(rs.getString("coupon"));
            order.setChannel(rs.getString("channel"));
            order.setRemark(rs.getString("remark"));
            order.setName(rs.getString("name"));
            order.setCode(rs.getString("code"));
        }
        return order;
    }

    public void updateForDone(String checkInCode) throws Exception {
        Connection conn = BaseDao.getConnection();
        String sql = "UPDATE `order` SET `usedNum` = availableNum, `availableNum` = 0 WHERE `code` = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, checkInCode);
        stmt.executeUpdate();
    }

    public void removeAll() throws SQLException {
        Connection conn = BaseDao.getConnection();
        String sql = "truncate table `order`";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();
    }

    public int[] batchInsert(List<Order> orders) throws SQLException {
        Connection conn = BaseDao.getConnection();
        String sql = "INSERT INTO `check_in`.`order`(`id`, `customer`, `ticketClass`, `seat`, `status`, `usedNum`, `availableNum`, `tel`, `time`, `coupon`, `channel`, `remark`, `name`, `code`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        conn.setAutoCommit(false); // 关闭自动提交
        for (Order order : orders) {
            stmt.setInt(1, order.getId());
            stmt.setString(2, order.getCustomer());
            stmt.setString(3, order.getTicketClass());
            stmt.setString(4, order.getSeat());
            stmt.setString(5, order.getStatus());
            stmt.setInt(6, order.getUsedNum());
            stmt.setInt(7, order.getAvailableNum());
            stmt.setString(8, order.getTel());
            stmt.setString(9, order.getTime());
            stmt.setString(10, order.getCoupon());
            stmt.setString(11, order.getChannel());
            stmt.setString(12, order.getRemark());
            stmt.setString(13, order.getName());
            stmt.setString(14, order.getCode());
            stmt.addBatch();
        }
        int[] counts = stmt.executeBatch();
        conn.commit(); //提交更改
        return counts;
    }
}
