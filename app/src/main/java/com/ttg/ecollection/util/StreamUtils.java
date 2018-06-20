package com.ttg.ecollection.util;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class StreamUtils {

    /**
     * 判断字符串是否是数字
     */
    public static boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }
    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是浮点数
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return value.contains(".");
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /*** @return 指定编码格式字节流*/
    private static byte[] getContentBytes(String content, String charset) {
        try {
            if (charset == null || "".equals(charset)) {
                return content.getBytes();
            }
            return content.getBytes(charset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T getBean(String jsonString, Class<T> cls) {
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(jsonString, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

    public static <T> List<T> getBeans(String jsonString, Class<T> cls) {
        ArrayList<T> mList = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(jsonString).getAsJsonArray();
        for(final JsonElement elem : array){
            mList.add(new Gson().fromJson(elem, cls));
        }
        return mList;
//        List<T> list = new ArrayList<T>();
//        try {
//            Gson gson = new Gson();
//            list = gson.fromJson(jsonString, new TypeToken<List<T>>() {
//            }.getType());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
    }

    public static List<Map<String, Object>> listKeyMaps(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            Gson gson = new Gson();
            list = gson.fromJson(jsonString, new TypeToken<List<Map<String, Object>>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    /**读取properties文件*/
    public static Properties getProperties(Context context, String fileName) {
        Properties properties = new Properties();
        try {
            InputStream stream = context.getAssets().open(fileName);
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }


    public static String creatJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    /**网络访问获得json字符串*/
    public static String getJson(final String path) {
        String result;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            int responseCode = conn.getResponseCode();
            if (200 == responseCode) {
                InputStream is = conn.getInputStream();
                result = streamToString(is);
            } else {
                result = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "";
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return result;
    }

    /**将输入流转换成字符串*/
    public static String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            return null;
        }
    }

    //创建加密串
    public static String createLinkString(Map<String, Object> map){
        String sortStr = null;
        String sign    = null;
        String SHA1    =null;
        try {
            /**
             * 1 A~z排序
             */
            sortStr=sort(map);

            /**
             * 2 sha1加密(小写)
             */
            SHA1= CryptoUtil.SHA1(sortStr);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return SHA1;
    }

    //排序
    public static String sort(Map paramMap) throws Exception {
        String sort = "";
        MapUtils signMap = new MapUtils();
        if (paramMap != null) {
            String key;
            for (Iterator it = paramMap.keySet().iterator(); it.hasNext();) {
                key = (String) it.next();
                String value = ((paramMap.get(key) != null) && (!(""
                        .equals(paramMap.get(key).toString())))) ? paramMap
                        .get(key).toString() : "";
                signMap.put(key, value);
            }
            signMap.sort();
            for (Iterator it = signMap.keySet().iterator(); it.hasNext();) {
                key = (String) it.next();
                sort = sort + key + "=" + signMap.get(key).toString() + "&";
            }
            if ((sort != null) && (!("".equals(sort)))) {
                sort = sort.substring(0, sort.length() - 1);
            }
        }
        return sort;
    }

    public static String sortjson(JSONObject jsonMap) throws Exception {
        Map<String ,String > paramMap=new HashMap<String, String>();
        // 将json字符串转换成jsonObject
        Iterator ite = jsonMap.keys();
        // 遍历jsonObject数据,添加到Map对象
        while (ite.hasNext()) {
            String key = ite.next().toString();
            String value = jsonMap.get(key).toString();
            paramMap.put(key, value);
        }
        String sort = "";
        MapUtil signMap = new MapUtil();
        if (paramMap != null) {
            String key1;
            for (Iterator it = paramMap.keySet().iterator(); it.hasNext();) {
                key1 = (String) it.next();
                String value = ((paramMap.get(key1) != null) && (!(""
                        .equals(paramMap.get(key1).toString())))) ? paramMap
                        .get(key1).toString() : "";
                signMap.put(key1, value);
            }
            signMap.sort();
            for (Iterator it = signMap.keySet().iterator(); it.hasNext();) {
                key1 = (String) it.next();
                sort = sort + key1 + "=" + signMap.get(key1).toString() + "&";
            }
            if ((sort != null) && (!("".equals(sort)))) {
                sort = sort.substring(0, sort.length() - 1);
            }
        }
        return sort;
    }

}
