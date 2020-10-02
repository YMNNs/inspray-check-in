package com.inspray.checkin.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.inspray.checkin.service.Config;
import com.inspray.checkin.util.FileUtil;
import com.inspray.checkin.util.Log;

public class ParseConfig {

    private static void parseConfig(String jsonString) {
        JSONObject json;
        try {
            json = JSON.parseObject(jsonString);
        } catch (Exception e) {
            //e.printStackTrace();
            Log.getLogger().warning("UNABLE TO PARSE CONFIG FILE: " + e.getMessage());
            return;
        }
        String url = null;
        String user = null;
        String password = null;
        boolean debug = false;
        String ticketModel = null;
        String cardTop = null;
        String cardBack = null;
        String orderCsv = null;
        try {
            url = json.getString("url");
            user = json.getString("user");
            password = json.getString("password");
            debug = json.getBoolean("debug");
            ticketModel = json.getString("ticketModel");
            cardTop = json.getString("cardTop");
            cardBack = json.getString("cardBack");
            orderCsv = json.getString("orderCsv");

        } catch (Exception e) {
            Log.getLogger().warning("UNABLE TO APPLY CONFIG: " + e.getMessage());
        }
        Config.setUrl(url);
        Config.setUser(user);
        Config.setPassword(password);
        Config.setDebug(debug);
        Config.setTicketModelPath(ticketModel);
        Config.setCardTopPath(cardTop);
        Config.setCardBackPath(cardBack);
        Config.setOrderCsv(orderCsv);
        Log.getLogger().info("CONFIG APPLIED");
    }

    public static void applyConfig() {
        parseConfig(FileUtil.readFile("config.json"));
    }


}
