package com.inspray.checkin.zpl;

import com.inspray.checkin.bean.Ticket;
import com.inspray.checkin.service.Config;
import com.inspray.checkin.util.FileUtil;
import com.inspray.checkin.util.Log;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.comm.DriverPrinterConnection;
import com.zebra.sdk.printer.discovery.DiscoveredPrinterDriver;
import com.zebra.sdk.printer.discovery.UsbDiscoverer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Printer {
    static DiscoveredPrinterDriver discoveredPrinterDriver = null;
    static Connection printerConnection = null;

    public static void getAvailablePrinters() {
        try {
            Log.getLogger().info("SEARCHING PRINTER VIA USB");
            System.out.println();
            int count = 0;
            ArrayList<DiscoveredPrinterDriver> printers = new ArrayList<>();
            for (DiscoveredPrinterDriver printer : UsbDiscoverer.getZebraDriverPrinters()) {
                count++;
                System.out.println(count + ". " + printer);
                printers.add(printer);
            }
            Scanner scanner = new Scanner(System.in);
            if (count > 1) {
                System.out.println("输入序号来选择打印机");
                try {
                    int selected = scanner.nextInt();
                    discoveredPrinterDriver = printers.get(selected - 1);
                } catch (Exception e) {
                    Log.getLogger().info("INVALID INPUT");
                }
            } else if (count == 1) {
                discoveredPrinterDriver = printers.get(0);
            } else {
                Log.getLogger().warning("PRINTER NOT FOUND");
            }
            if (discoveredPrinterDriver != null) {
                System.out.println("已选取打印机：" + discoveredPrinterDriver);
                printerConnection = new DriverPrinterConnection(discoveredPrinterDriver.printerName);
            } else {
                System.out.println("未选取任何打印机");
            }
        } catch (ConnectionException e) {
            Log.getLogger().warning("ERROR SEARCHING PRINTER: " + e.getMessage());
        }

        Log.getLogger().info("DONE SEARCHING PRINTER");
    }

    public static void openConnection() {
        try {
            printerConnection.open();
        } catch (Exception e) {
            Log.getLogger().warning("UNABLE TO CONNECT TO PRINTER" + e.getMessage());
        }
    }

    public static void closeConnection() {
        try {
            printerConnection.close();
        } catch (Exception e) {
            Log.getLogger().warning("ERROR CLOSING CONNECTION" + e.getMessage());
        }
    }

    public static void printTicket(Ticket ticket) throws Exception {
        if (printerConnection == null) {
            getAvailablePrinters();
        }
        if (!printerConnection.isConnected()) {
            openConnection();
        }
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        String zpl = FileUtil.readFile(Config.getTicketModelPath());
        zpl = zpl.substring(zpl.indexOf("^XZ") + 3);//从第二个^XA开始打印
        zpl = zpl.replace("^MMT", "^CW1,Z:D.FNT^CW2,E:SIMSUN.TTF^CI28^FS^FLE:SIMSUN.TTF,Z:D.FNT,1^FS");//设置组合字体以便打印中文，替换掉^MMT（撕掉）
        zpl = zpl.replace("^ADN", "^A1N");//把字体D替换为设置的组合字体
        zpl = zpl.replace("88888888", ticket.getCode());
        zpl = zpl.replace("99999999", ticket.getCode());
        zpl = zpl.replace("777777", ticket.getOrderId() + "");
        zpl = zpl.replace("666666", ticket.getOrderId() + "");
        if (ticket.getCoupon() != null) {
            zpl = zpl.replace("COUPON", ticket.getCoupon());
        } else {
            zpl = zpl.replace("COUPON", "");
        }
        if (ticket.getCoupon() != null) {
            zpl = zpl.replace("REMARK", ticket.getRemark());
        } else {
            zpl = zpl.replace("REMARK", "");
        }
        zpl = zpl.replace("ENTRANCE", ticket.getEntrance());
        zpl = zpl.replace("YOURNAME", ticket.getName());
        if (ticket.getChannel() != null) {
            zpl = zpl.replace("CHANNEL", ticket.getChannel());
        } else {
            zpl = zpl.replace("CHANNEL", "");
        }
        zpl = zpl.replace("^FD$^FS", "^FD" + ticket.getSeatFloor() + "^FS");
        zpl = zpl.replace("^FDAA^FS", "^FD" + ticket.getSeatRow() + "^FS");
        zpl = zpl.replace("^FDBB^FS", "^FD" + ticket.getSeatColumn() + "^FS");
        zpl = zpl.replace("TICKET_TYPE", ticket.getTicketClass());
        zpl = zpl.replace("13:50:46", format.format(ticket.getPrintTime()));
        zpl = zpl.replace("^FD?^FS", "^FD" + ticket.getTicketClassChar() + "^FS");
        try {
            printerConnection.write(zpl.getBytes());
        } catch (ConnectionException e) {
            System.out.println("向打印机发送命令失败，正在重试");
            try {
                printerConnection.write(zpl.getBytes());
            } catch (ConnectionException connectionException) {
                System.out.println("向打印机发送命令失败，正在重试");
                printerConnection.write(zpl.getBytes());
            }
        }
    }


    public static void testPrint() {
        if (printerConnection != null) {
            String zpl = FileUtil.readFile(Config.getTicketModelPath());
            zpl = zpl.substring(zpl.indexOf("^XZ") + 3);//从第二个^XA开始打印
            zpl = zpl.replace("^MMT", "^CW1,Z:D.FNT^CW2,E:SIMSUN.TTF^CI28^FS^FLE:SIMSUN.TTF,Z:D.FNT,1^FS");//设置组合字体以便打印中文，替换掉^MMT（撕掉）
            zpl = zpl.replace("^ADN", "^A1N");//把字体D替换为设置的组合字体
            zpl = zpl.replace("88888888", "12345678");
            zpl = zpl.replace("NAME", "WYY");
            System.out.println(zpl);
            if (printerConnection.isConnected()) {
                try {
                    printerConnection.write(zpl.getBytes());
                    closeConnection();
                } catch (ConnectionException e) {
                    Log.getLogger().warning("PRINT FAILED: " + e.getMessage());
                }
            } else {
                openConnection();
            }
        } else {
            getAvailablePrinters();
        }
    }

}
