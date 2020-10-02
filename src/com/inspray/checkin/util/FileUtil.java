package com.inspray.checkin.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtil {
    public static String readFile(String name) {
        BufferedReader reader = null;
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File(System.getProperty("user.dir") + "\\" + name);
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                stringBuilder.append(tempString).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            Log.getLogger().warning("FILE IO ERROR: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    Log.getLogger().warning("FILE CLOSE ERROR: " + e1.getMessage());
                }
            }

        }
        return stringBuilder.toString();

//        InputStream fis = FileUtil.class.getResourceAsStream(url.getFile());
//        StringBuilder sb = new StringBuilder();
//        try {
//            int temp = 0;
//            //当temp等于-1时，表示已经到了文件结尾，停止读取
//            while ((temp = fis.read()) != -1) {
//                sb.append((char) temp);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                //这种写法，保证了即使遇到异常情况，也会关闭流对象。
//                if (fis != null) {
//                    fis.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//        return sb.toString();
//        System.out.println(System.getProperty("user.dir"));
//        InputStream is = FileUtil.class.getResourceAsStream("/config.json");
//        String out = null;
//        try {
//            out = process(is);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return out;
//    }
//
//    private static String process(InputStream input) throws IOException {
//        InputStreamReader isr = new InputStreamReader(input);
//        BufferedReader reader = new BufferedReader(isr);
//        StringBuilder stringBuilder = new StringBuilder();
//        String line;
//        while ((line = reader.readLine()) != null) {
//            System.out.println(line);
//            stringBuilder.append(line);
//        }
//        reader.close();
//        return stringBuilder.toString();
//    }
    }
}
