package com.inspray.checkin.service;

import com.inspray.checkin.dao.BaseDao;
import com.inspray.checkin.util.Log;
import com.inspray.checkin.zpl.Printer;

import java.sql.SQLException;

public class SystemTest {
    public static void testAll() {
        int successCount = 0;
        try {
            Log.getLogger().info("TESTING DATABASE");
            BaseDao.getConnection();
            successCount++;
            Log.getLogger().info("DATABASE AVAILABLE");
        } catch (SQLException throwables) {
            Log.getLogger().warning("DATABASE FAILURE: " + throwables.getSQLState());
        }
        try {
            Printer.getAvailablePrinters();
            successCount++;
        } catch (Exception e) {
            Log.getLogger().warning("PRINTER FAILURE: " + e.getMessage());
        }
        System.out.println("已通过" + successCount + "/2项测试");

    }
}
