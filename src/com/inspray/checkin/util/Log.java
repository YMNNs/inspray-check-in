package com.inspray.checkin.util;

import java.io.UnsupportedEncodingException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Log {
    private static final Logger logger = Logger.getLogger("default");
    private static final ConsoleHandler consoleHandler = new ConsoleHandler();

    private static void init() {
        try {
            consoleHandler.setEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        logger.addHandler(consoleHandler);
        Log.setLevel(0);
    }

    public static Logger getLogger() {
        if (logger == null) {
            init();
        }
        return logger;
    }

    public static void setLevel(int level) {
        switch (level) {
            case 1: {
                logger.setLevel(Level.ALL);
                logger.info("LOGGING ACTIVATED");
                break;
            }
            case 0: {
                logger.setLevel(Level.WARNING);
                logger.info("LOGGING DISABLED");
            }
        }
    }


}
