package com.inspray.checkin.service;

import com.inspray.checkin.bean.Order;
import com.inspray.checkin.bean.Ticket;
import com.inspray.checkin.dao.OrderDao;
import com.inspray.checkin.dao.TicketDao;
import com.inspray.checkin.util.Log;
import com.inspray.checkin.zpl.Printer;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class CheckIn {
    static OrderDao orderDao = new OrderDao();
    static TicketDao ticketDao = new TicketDao();

    private static void printTempTicket() {
        System.out.println("请选择票种");
        System.out.println("1. 青年优惠票");
        System.out.println("2. 标准票C");
        System.out.println("3. 标准票B");
        System.out.println("4. 标准票A");
        System.out.println("5. 尊享票");
        System.out.print(">");
        Scanner scanner = new Scanner(System.in);
        int ticketType;
        try {
            ticketType = scanner.nextInt();
        } catch (Exception e) {
            Log.getLogger().warning("INVALID INPUT");
            return;
        }
        String type;
        switch (ticketType) {
            case 1: {
                type = "青年优惠票#Y";
                break;
            }
            case 2: {
                type = "标准票C#C";
                break;
            }
            case 3: {
                type = "标准票B#B";
                break;
            }
            case 4: {
                type = "标准票A#A";
                break;
            }
            case 5: {
                type = "尊享票#P";
                break;
            }
            default: {
                Log.getLogger().warning("INVALID TICKET TYPE");
                return;
            }
        }
        int floor;
        int row;
        int column;
        try {
            System.out.println("请输入楼层");
            System.out.print(">");
            floor = scanner.nextInt();
        } catch (Exception e) {
            Log.getLogger().warning("INVALID INPUT");
            return;
        }
        try {
            System.out.println("请输入排号");
            System.out.print(">");
            row = scanner.nextInt();
        } catch (Exception e) {
            Log.getLogger().warning("INVALID INPUT");
            return;
        }
        try {
            System.out.println("请输入座号");
            System.out.print(">");
            column = scanner.nextInt();
        } catch (Exception e) {
            Log.getLogger().warning("INVALID INPUT");
            return;
        }
        Ticket ticket = new Ticket();
        ticket.setCode("000000");
        ticket.setName("现场出票");
        ticket.setSeatFloor(floor);
        ticket.setSeatRow(row);
        ticket.setRemark("");
        ticket.setChannel("");
        ticket.setCoupon("");
        ticket.setSeatColumn(column);
        ticket.setEntrance(getEntrance(ticket));
        ticket.setTicketClass(type.split("#")[0]);
        ticket.setTicketClassChar(type.split("#")[1]);
        ticket.setPrintTime(new Date());
        try {
            Printer.printTicket(ticket);
            ticketDao.insert(ticket);
            System.out.print("DONE 打印成功\n");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("FAILURE 打印失败，请重试\n");
        }
    }

    /**
     * 通过票码签到
     * 狗日的老板睡的比爷还早
     *
     * @param checkInCode 票码
     */
    private static void checkInByCheckInCode(String checkInCode) {
        System.out.print("WORKING 正在搜索...");
        Order order;
        try {
            order = orderDao.findByCode(checkInCode);
        } catch (Exception e) {
            Log.getLogger().warning("DATABASE ERROR: " + e.getMessage());
            e.printStackTrace();
            return;
        }
        if (order != null) {
            System.out.print("\r                              ");
            System.out.println("\rDONE 已找到门票\n");
            String[] seats = order.getSeat().split(", ");
            String[] names = order.getName().split(", ");
            System.out.println(order);
            if (order.getAvailableNum() == 0) {
                System.out.print("\n该票已经打印过，输入'y'重新打印\n>");
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                if (!input.equalsIgnoreCase("y")) {
                    return;
                }
                order.setAvailableNum(seats.length);
            }
            int count = 0;
            for (int i = 0; i < order.getAvailableNum(); i++) {
                Ticket ticket = new Ticket();
                ticket.setChannel(order.getChannel());
                ticket.setTicketClass(order.getTicketClass());
                ticket.setCode(order.getCode());
                ticket.setCoupon(order.getCoupon());
                ticket.setRemark(order.getRemark());
                ticket.setName(names[i]);
                ticket.setOrderId(order.getId());
                try {
                    ticket.setSeatFloor(Integer.parseInt(seats[i].split("[\\u4e00-\\u9fa5]")[0]));
                    ticket.setSeatRow(Integer.parseInt(seats[i].split("[\\u4e00-\\u9fa5]")[1]));
                    ticket.setSeatColumn(Integer.parseInt(seats[i].split("[\\u4e00-\\u9fa5]")[2]));
                } catch (NumberFormatException e) {
                    Log.getLogger().warning("CANNOT PARSE SEAT");
                }
                ticket.setPrintTime(new Date());
                switch (ticket.getTicketClass()) {
                    case "青年优惠票": {
                        ticket.setTicketClassChar("Y");
                        break;
                    }
                    case "标准票A": {
                        ticket.setTicketClassChar("A");
                        break;
                    }
                    case "标准票B": {
                        ticket.setTicketClassChar("B");
                        break;
                    }
                    case "标准票C": {
                        ticket.setTicketClassChar("C");
                        break;
                    }
                    case "尊享票": {
                        ticket.setTicketClassChar("P");
                        break;
                    }
                    case "赞助人票": {
                        ticket.setTicketClassChar("S");
                        break;
                    }
                }
                ticket.setEntrance(getEntrance(ticket));
                int _i = i + 1;
                System.out.print("\r                                  ");
                System.out.print("\rWORKING 正在打印第" + _i + "张门票...");
                try {
                    Printer.printTicket(ticket);
                    ticketDao.insert(ticket);
                    System.out.print("\r                              ");
                    System.out.print("\rDONE 第" + _i + "张门票打印成功\n");
                    count++;
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.print("\r                                 ");
                    System.out.print("\rFAILURE 第" + _i + "张门票打印失败\n");
                }
            }
            System.out.print("\rDONE 已打印" + count + "张门票\n");
            Printer.closeConnection();
            try {
                orderDao.updateForDone(checkInCode);
            } catch (Exception e) {
                Log.getLogger().warning("DATABASE ERROR: " + e.getMessage());
            }
        } else {
            System.out.print("\r                                 ");
            System.out.println("\rDONE 未找到门票，打印临时票请输入l\n");
        }
    }


    public static void checkIn() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("取票模式，输入'q'退出，输入'l'打印临时票");
        while (true) {
            System.out.print(">");
            String[] commands = scanner.nextLine().split(" ");
            if (commands[0].equalsIgnoreCase("q")) {
                return;
            }
            if (commands[0].equalsIgnoreCase("l")) {
                printTempTicket();
                return;
            }
            checkInByCheckInCode(commands[0]);
        }
    }

    private static String getEntrance(Ticket ticket) {
        String entrance = "请从";
        if (ticket.getSeatFloor() == 2) {
            entrance += "三楼";
        } else if (ticket.getSeatRow() <= 6) {
            entrance += "一楼";
        } else {
            entrance += "二楼";
        }
        if (ticket.getSeatColumn() % 2 != 0) {
            entrance += "单号";
        } else {
            entrance += "双号";
        }
        entrance += "入场";
        return entrance;
    }

    public static void getRecord() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("查询模式，输入'a'显示全部，输入'q'退出");
        while (true) {
            System.out.print(">");
            String[] commands = scanner.nextLine().split(" ");
            if (commands[0].equalsIgnoreCase("q")) {
                return;
            }
            if (commands[0].equalsIgnoreCase("a")) {
                try {
                    ArrayList<Ticket> tickets;
                    System.out.print("WORKING 正在查询数据...");
                    tickets = ticketDao.findAll();
                    System.out.print("\rDONE 数据查询完成\n");
                    System.out.println("取票码\t\t票类\t\t\t姓名\t\t座位\t\t\t优惠券\t\t渠道\t\t\t打印时间");
                    for (Ticket ticket : tickets) {
                        System.out.println(ticket);
                    }
                } catch (Exception e) {
                    Log.getLogger().warning("DATABASE ERROR: " + e.getMessage());
                    return;
                }
                return;
            }
            try {
                ArrayList<Ticket> tickets;
                System.out.print("WORKING 正在查询数据...");
                tickets = ticketDao.findByCode(commands[0]);
                System.out.print("\r                              ");
                System.out.print("\rDONE 数据查询完成\n");
                System.out.println("取票码\t\t票类\t\t\t姓名\t\t座位\t\t\t优惠券\t\t渠道\t\t\t打印时间");
                for (Ticket ticket : tickets) {
                    System.out.println(ticket);
                }
                return;
            } catch (Exception e) {
                Log.getLogger().warning("DATABASE ERROR: " + e.getMessage());
                return;
            }
        }


    }

}
