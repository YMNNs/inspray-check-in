package com.inspray.checkin.service;

import com.inspray.checkin.bean.Order;
import com.inspray.checkin.dao.OrderDao;
import com.inspray.checkin.dao.TicketDao;
import com.inspray.checkin.util.FileUtil;
import com.inspray.checkin.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DatabaseParser {
    private static final OrderDao orderDao = new OrderDao();

    private static List<Order> parseCSV() {
        String csv = FileUtil.readFile(Config.getOrderCsv());
        if (csv.length() < 10) {
            Log.getLogger().warning("INVALID CSV FILE");
            System.out.println("INFO 未执行任何操作");
        }
        List<Order> orders = new ArrayList<>();
        String[] lines = csv.split("\n");
        for (int i = 1; i < lines.length; i++) {
            String[] line = lines[i].split("\",\"");
            Order order = new Order();
            order.setId(Integer.parseInt(line[0].substring(1)));
            order.setCustomer(line[1]);
            order.setTicketClass(line[2]);
            order.setSeat(line[4]);
            order.setStatus(line[5]);
            order.setUsedNum(Integer.parseInt(line[6]));
            order.setAvailableNum(Integer.parseInt(line[7]));
            order.setTel(line[9]);
            order.setTime(line[12]);
            order.setCoupon(line[13]);
            order.setChannel(line[14]);
            order.setRemark(line[15]);
            order.setName(line[19]);
            order.setCode(line[20]);
            orders.add(order);
        }
        return orders;
    }

    public static void updateDatabase() {
        List<Order> orders = parseCSV();
        if (orders.size() == 0)
            return;
        try {
            System.out.println();
            System.out.print("WORKING 正在清除数据库...");
            System.out.print("\r                              ");
            System.out.print("\rDONE 已清除数据库");
            orderDao.removeAll();
        } catch (SQLException throwables) {
            Log.getLogger().warning("UNABLE TO PURGE DATABASE");
            System.out.println("INFO 未执行任何操作");
        }
        try {
            System.out.println();
            System.out.print("WORKING 正在插入数据...");
            int[] count = orderDao.batchInsert(orders);
            System.out.print("\r                              ");
            System.out.print("\rDONE 已插入" + count.length + "条数据");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void purgePrintRecord() {
        System.err.println("确定要清除取票记录吗？此操作不可逆(y/n)");
        Scanner scanner = new Scanner(System.in);
        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("y")) {
            TicketDao ticketDao = new TicketDao();
            try {
                System.out.println();
                System.out.print("WORKING 正在清除数据库...");
                ticketDao.removeAll();
                System.out.print("\r                              ");
                System.out.print("\rDONE 已清除数据库");
            } catch (SQLException throwables) {
                Log.getLogger().warning("UNABLE TO PURGE DATABASE");
                System.out.println("INFO 未执行任何操作");
            }
        }
    }
}
