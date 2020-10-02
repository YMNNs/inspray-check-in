package com.inspray.checkin.dao;

import com.inspray.checkin.bean.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class TicketDao extends BaseDao {

    public void insert(Ticket ticket) throws Exception {
        Connection conn = BaseDao.getConnection();
        String sql = "INSERT INTO `ticket`(`ticketClass`, `name`, `seatFloor`, `seatRow`, `seatColumn`, `coupon`, `channel`, `code`, `printTime`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, ticket.getTicketClass());
        stmt.setString(2, ticket.getName());
        stmt.setInt(3, ticket.getSeatFloor());
        stmt.setInt(4, ticket.getSeatRow());
        stmt.setInt(5, ticket.getSeatColumn());
        stmt.setString(6, ticket.getCoupon());
        stmt.setString(7, ticket.getChannel());
        stmt.setString(8, ticket.getCode());
        stmt.setTimestamp(9, new Timestamp(ticket.getPrintTime().getTime()));
        stmt.executeUpdate();
    }

    public ArrayList<Ticket> findAll() throws Exception {
        Connection conn = BaseDao.getConnection();
        String sql = "SELECT * FROM `ticket`";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet resultSet = stmt.executeQuery();
        return getTickets(resultSet);
    }

    public ArrayList<Ticket> findByCode(String code) throws Exception {
        Connection conn = BaseDao.getConnection();
        String sql = "SELECT * FROM `ticket` where code=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, code);
        ResultSet resultSet = stmt.executeQuery();
        return getTickets(resultSet);

    }

    private ArrayList<Ticket> getTickets(ResultSet resultSet) throws SQLException {
        ArrayList<Ticket> tickets = new ArrayList<>();
        while (resultSet.next()) {
            Ticket ticket = new Ticket();
            ticket.setId(resultSet.getInt("id"));
            ticket.setTicketClass(resultSet.getString("ticketClass"));
            ticket.setName(resultSet.getString("name"));
            ticket.setSeatFloor(resultSet.getInt("seatFloor"));
            ticket.setSeatRow(resultSet.getInt("seatRow"));
            ticket.setSeatColumn(resultSet.getInt("seatColumn"));
            ticket.setCoupon(resultSet.getString("coupon"));
            ticket.setChannel(resultSet.getString("channel"));
            ticket.setCode(resultSet.getString("code"));
            ticket.setPrintTime(new Date(resultSet.getTime("printTime").getTime()));
            tickets.add(ticket);
        }
        return tickets;
    }

    public void removeAll() throws SQLException {
        Connection conn = BaseDao.getConnection();
        String sql = "truncate table `ticket`";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();
    }

}
