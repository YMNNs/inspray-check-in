package com.inspray.checkin.service;

import com.inspray.checkin.util.Log;

public class Config {
    private static String url;
    private static String user;
    private static String password;
    private static boolean debug;
    private static String ticketModelPath;
    private static String cardTopPath;
    private static String cardBackPath;
    private static String orderCsv;

    public static String getOrderCsv() {
        return orderCsv;
    }

    public static void setOrderCsv(String orderCsv) {
        Config.orderCsv = orderCsv;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        Config.url = url;
    }

    public static String getCardTopPath() {
        return cardTopPath;
    }

    public static void setCardTopPath(String cardTopPath) {
        Config.cardTopPath = cardTopPath;
    }

    public static String getCardBackPath() {
        return cardBackPath;
    }

    public static void setCardBackPath(String cardBackPath) {
        Config.cardBackPath = cardBackPath;
    }

    public static String getTicketModelPath() {
        return ticketModelPath;
    }

    public static void setTicketModelPath(String ticketModelPath) {
        Config.ticketModelPath = ticketModelPath;
    }

    public static String getUser() {
        return user;
    }

    public static void setUser(String user) {
        Config.user = user;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        Config.password = password;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        Config.debug = debug;
        if (Config.debug)
            Log.setLevel(1);
        else Log.setLevel(0);
    }
}
