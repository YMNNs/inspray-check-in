package com.inspray.checkin;

import com.inspray.checkin.dao.ParseConfig;
import com.inspray.checkin.service.CheckIn;
import com.inspray.checkin.service.DatabaseParser;
import com.inspray.checkin.service.SystemTest;
import com.inspray.checkin.zpl.Printer;

import java.util.Scanner;

public class Main {
    static String ver = "1.0";
    static String title = "INSPRAY CHECKIN CONSOLE " + ver;
    static String copyright = "COPYRIGHT 2020 INSPRAY.ORG";
    static String menuHeader = "\n==============================================\n";
    static String menuFooter = "\n输入\"q\"退出\n" +
            "==============================================\n";
    static String menu = "1. 查看并选择可用打印机\n" +
            "2. 测试打印机\n" +
            "3. 入场取票\n" +
            "4. 查询取票记录\n" +
            "5. 从文件刷新数据库\n" +
            "6. 清空取票记录\n" +
            "7. 测试系统可用性\n";


    public static void main(String[] args) {
        ParseConfig.applyConfig();
        System.out.println(title + "\n" + copyright);
        while (true) {
            System.out.println(menuHeader + menu + menuFooter);
            parseCommand();
        }
    }

    private static void parseCommand() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(">");
        String[] commands = scanner.nextLine().split(" ");
        switch (commands[0]) {
            case "1": {
                Printer.getAvailablePrinters();
                break;
            }
            case "2": {
                Printer.testPrint();
                break;
            }
            case "3": {
                CheckIn.checkIn();
                break;
            }
            case "4": {
                CheckIn.getRecord();
                break;
            }
            case "5": {
                DatabaseParser.updateDatabase();
                break;
            }
            case "6": {
                DatabaseParser.purgePrintRecord();
                break;
            }
            case "7": {
                SystemTest.testAll();
                break;
            }
            case "q": {
                System.exit(0);
                break;
            }
            default: {
                System.out.println("输入的指令有误，未执行任何操作");
            }

        }
    }


}
